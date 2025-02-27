package com.blockstream.green.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Parcelable
import androidx.core.content.edit
import androidx.fragment.app.FragmentManager
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.blockstream.common.CountlyInteface
import com.blockstream.common.data.ApplicationSettings
import com.blockstream.common.data.Banner
import com.blockstream.common.data.CountlyWidget
import com.blockstream.common.data.EnrichedAsset
import com.blockstream.common.gdk.JsonConverter.Companion.JsonDeserializer
import com.blockstream.common.gdk.data.Account
import com.blockstream.common.gdk.data.AccountAsset
import com.blockstream.common.gdk.data.Network
import com.blockstream.common.gdk.device.DeviceInterface
import com.blockstream.common.managers.SettingsManager
import com.blockstream.green.ApplicationScope
import com.blockstream.green.database.CredentialType
import com.blockstream.green.database.LoginCredentials
import com.blockstream.green.database.Wallet
import com.blockstream.green.database.WalletRepository
import com.blockstream.green.devices.Device
import com.blockstream.green.gdk.*
import com.blockstream.green.ui.AppActivity
import com.blockstream.green.ui.dialogs.CountlyNpsDialogFragment
import com.blockstream.green.ui.dialogs.CountlySurveyDialogFragment
import com.blockstream.green.utils.isDevelopmentFlavor
import com.blockstream.green.utils.isDevelopmentOrDebug
import com.blockstream.green.utils.isProductionFlavor
import com.blockstream.green.views.GreenAlertView
import com.greenaddress.greenbits.wallets.FirmwareFileData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.put
import ly.count.android.sdk.Countly
import ly.count.android.sdk.CountlyConfig
import ly.count.android.sdk.ModuleFeedback
import ly.count.android.sdk.ModuleFeedback.CountlyFeedbackWidget
import ly.count.android.sdk.RemoteConfigCallback
import mu.NamedKLogging
import java.net.URLDecoder
import kotlin.properties.Delegates



class Countly constructor(
    private val context: Context,
    private val sharedPreferences: SharedPreferences,
    private val applicationScope: ApplicationScope,
    private val settingsManager: SettingsManager,
    private val walletRepository: WalletRepository,
): CountlyInteface {

    private var _torProxy: String? = null
    private val _remoteConfigUpdateEvent = MutableSharedFlow<Unit>(replay = 1)
    override val remoteConfigUpdateEvent = _remoteConfigUpdateEvent.asSharedFlow()

    private val countly = Countly.sharedInstance().also { countly ->
        val config = CountlyConfig(
            context as Application,
            if (isProductionFlavor) PRODUCTION_APP_KEY else DEVELOPMENT_APP_KEY,
            SERVER_URL,
            SERVER_URL_ONION
        ).also {
            if (isDevelopmentOrDebug) {
                it.setEventQueueSizeToSend(1)
            }
            // it.setLoggingEnabled(isDevelopmentOrDebug)
            // Disable automatic view tracking
            it.setViewTracking(false)
            // Enable crash reporting
            it.enableCrashReporting()
            // APM
            it.setRecordAppStartTime(true)
            // Disable Location
            //it.setDisableLocation()
            // Require user consent
            it.setRequiresConsent(true)
            // Set Device ID
            it.setDeviceId(settingsManager.getCountlyDeviceId())
            // Set automatic remote config download
            it.setRemoteConfigAutomaticDownload(true, RemoteConfigCallback { error ->
                logger.info { if (error.isNullOrBlank()) "Remote Config Completed" else "Remote Config error: $error" }

                if(error.isNullOrBlank()){
                    _remoteConfigUpdateEvent.tryEmit(Unit)
                }
            })
            // Add initial enabled features
            it.setConsentEnabled(
                if (settingsManager.appSettings.analytics) {
                    noConsentRequiredGroup + consentRequiredGroup
                } else {
                    noConsentRequiredGroup
                }
            )
            it.setProxy(countlyProxy)
        }

        updateOffset()

        countly.init(config)
    }

    private val apm = countly.apm()
    private val events = countly.events()
    private val views = countly.views()
    private val crashes = countly.crashes()
    private val consent = countly.consent()
    private val userProfile = countly.userProfile()
    private val remoteConfig = countly.remoteConfig()
    private val attribution = countly.attribution()
    private val feedback = countly.feedback()

    private var analyticsConsent : Boolean by Delegates.observable(settingsManager.appSettings.analytics) { _, oldValue, newValue ->
        if(oldValue != newValue){
            consent.setConsentFeatureGroup(ANALYTICS_GROUP, newValue)

            if(!newValue){
                resetDeviceId()
            }
        }
    }

    var exceptionCounter = 0L
        private set

    val deviceId: String
        get() = countly.deviceId().id

    private var appSettingsAsString: String? = null

    private val countlyProxy: String?
        get() {
            val appSettings = settingsManager.appSettings
            return if (appSettings.tor) {
                // Use Orbot
                if (appSettings.proxyUrl?.startsWith("socks5://") == true) {
                    appSettings.proxyUrl
                } else {
                    _torProxy ?: "socks5://tor_not_initialized"
                }
            } else if (!appSettings.proxyUrl.isNullOrBlank()) {
                appSettings.proxyUrl
            } else {
                null
            }
        }

    init {
        // Create Feature groups
        consent.createFeatureGroup(ANALYTICS_GROUP, consentRequiredGroup)

        settingsManager.appSettingsStateFlow.onEach {
            analyticsConsent = it.analytics
            appSettingsAsString = appSettingsToString(it)

            updateProxy()
        }.launchIn(scope = CoroutineScope(context = Dispatchers.Default))

        // Set number of user software wallets
        walletRepository.getSoftwareWalletsLiveData().observeForever {
            updateUserProfile(it)
        }

        // If no referrer is set, try to get it from the install referrer
        // Empty string is also allowed
        if (!this.sharedPreferences.contains(REFERRER_KEY)) {
            handleReferrer { referrer ->
                // Mark it as complete
                sharedPreferences.edit {
                    putString(REFERRER_KEY, referrer)
                }
            }
        }
        updateFeedbackWidget()
    }

    private val _feedbackWidgetStateFlow = MutableStateFlow<CountlyFeedbackWidget?>(null)
    val feedbackWidgetStateFlow get() = _feedbackWidgetStateFlow.asStateFlow()
    val feedbackWidget get() = _feedbackWidgetStateFlow.value

    fun sendFeedbackWidgetData(widget: CountlyFeedbackWidget, data: Map<String, Any>?){
        feedback.reportFeedbackWidgetManually(widget, null, data)
        // can't use updateFeedback() as the data are sent async
        _feedbackWidgetStateFlow.value = null
    }

    fun getFeedbackWidgetData(widget: CountlyFeedbackWidget, callback: (CountlyWidget?) -> Unit){
        countly.feedback().getFeedbackWidgetData(widget) { data, _ ->
            try{
                callback.invoke(JsonDeserializer.decodeFromString<CountlyWidget>(data.toString()).also {
                    it.widget = widget
                })

                // Set it to null to hide it from UI, this way user can know that this is a temporary FAB
                _feedbackWidgetStateFlow.value = null
            }catch (e: Exception){
                logger.info { data.toString() }
                e.printStackTrace()
                callback.invoke(null)
            }
        }
    }

    private fun updateFeedbackWidget(){
        countly.feedback().getAvailableFeedbackWidgets { countlyFeedbackWidgets, _ ->
            _feedbackWidgetStateFlow.value = countlyFeedbackWidgets?.firstOrNull()
        }
    }

    fun showFeedbackWidget(supportFragmentManager: FragmentManager) {
        feedbackWidget?.type.also { type ->
            if(type == ModuleFeedback.FeedbackWidgetType.nps){
                CountlyNpsDialogFragment.show(supportFragmentManager)
            }else if(type == ModuleFeedback.FeedbackWidgetType.survey){
                CountlySurveyDialogFragment.show(supportFragmentManager)
            }
        }
    }

    private fun handleReferrer(onComplete: (referrer: String) -> Unit) {
        InstallReferrerClient.newBuilder(context).build().also { referrerClient ->
            referrerClient.startConnection(object : InstallReferrerStateListener {
                override fun onInstallReferrerSetupFinished(responseCode: Int) {
                    when (responseCode) {
                        InstallReferrerClient.InstallReferrerResponse.OK -> {
                            var cid: String? = null
                            var uid: String? = null
                            var referrer: String? = null

                            try {
                                // The string may be URL Encoded, so decode it just to be sure.
                                // eg. utm_source=google-play&utm_medium=organic
                                // eg. "cly_id=0eabe3eac38ff74556c69ed25a8275b19914ea9d&cly_uid=c27b33b16ac7947fae0ed9e60f3a5ceb96e0e545425dd431b791fe930fabafde4b96c69e0f63396202377a8025f008dfee2a9baf45fa30f7c80958bd5def6056"
                                referrer = URLDecoder.decode(
                                    referrerClient.installReferrer.installReferrer,
                                    "UTF-8"
                                )

                                logger.info { "Referrer: $referrer" }

                                val parts = referrer.split("&")

                                for (part in parts) {
                                    // Countly campaign
                                    if (part.startsWith("cly_id")) {
                                        cid = part.replace("cly_id=", "").trim()
                                    }
                                    if (part.startsWith("cly_uid")) {
                                        uid = part.replace("cly_uid=", "").trim()
                                    }

                                    // Google Play organic
                                    if (part.trim() == "utm_medium=organic") {
                                        cid = if (isProductionFlavor) GOOGLE_PLAY_ORGANIC_PRODUCTION else GOOGLE_PLAY_ORGANIC_DEVELOPMENT
                                    }
                                }

                                attribution.recordDirectAttribution("countly", buildJsonObject {
                                    put("cid", cid)
                                    if (uid != null) {
                                        put("cuid", uid)
                                    }
                                }.toString())

                            } catch (e: Exception) {
                                recordException(e)
                            }

                            onComplete.invoke(referrer ?: "")
                        }
                        InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                            // API not available on the current Play Store app.
                            // logger.info { "InstallReferrerService FEATURE_NOT_SUPPORTED" }
                            onComplete.invoke("")
                        }
                        InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                            // Connection couldn't be established.
                            // logger.info { "InstallReferrerService SERVICE_UNAVAILABLE" }
                        }
                    }

                    // Disconnect the client
                    referrerClient.endConnection()
                }

                override fun onInstallReferrerServiceDisconnected() {}
            })
        }
    }

    private fun updateUserProfile(wallets: List<Wallet>) {
        // All wallets
        userProfile.setProperty(USER_PROPERTY_TOTAL_WALLETS, wallets.size.toString())
        userProfile.save()
    }

    fun updateOffset(){
        Countly.sharedInstance().setOffset(settingsManager.getCountlyOffset(if (isDevelopmentFlavor) MAX_OFFSET_DEVELOPMENT else MAX_OFFSET_PRODUCTION))
    }

    private fun updateProxy(){
        countly.requestQueue().proxy = countlyProxy
    }

    override fun updateTorProxy(proxy: String){
        _torProxy = proxy

        updateProxy()
    }

    fun applicationOnCreate(){
        Countly.applicationOnCreate()
    }

    fun resetDeviceId() {
        logger.info { "Reset Device ID" }
        settingsManager.resetCountlyDeviceId()
        settingsManager.resetCountlyOffset()

        countly.deviceId().changeWithoutMerge(settingsManager.getCountlyDeviceId()) {
            // Update offset after the DeviceId is changed in the sdk
            updateOffset()
        }

        // Changing device ID without merging will now clear all consent. It has to be given again after this operation.
        consent.setConsent(noConsentRequiredGroup, true)

        // The following block is required only if you initiate a reset from the ConcentBottomSheetDialog
        if(analyticsConsent){
            consent.setConsentFeatureGroup(ANALYTICS_GROUP, true)
        }

        walletRepository.getSoftwareWalletsLiveData().value?.let {
            updateUserProfile(it)
        }

        updateFeedbackWidget()
    }

    override fun recordException(throwable: Throwable) {
        if(!skipExceptionRecording.contains(throwable.message)) {
            exceptionCounter++
            crashes.recordHandledException(throwable)
        }
        if(isDevelopmentOrDebug){
            throwable.printStackTrace()
        }
    }

    fun onStart(activity: AppActivity) {
        countly.onStart(activity)
    }

    fun onStop() {
        countly.onStop()
    }

    fun onConfigurationChanged(newConfig: Configuration) {
        countly.onConfigurationChanged(newConfig)
    }

    fun screenView(view: ScreenView) {
        if (!view.screenIsRecorded && !view.screenName.isNullOrBlank()) {
            view.screenIsRecorded = true
            views.recordView(view.screenName, view.segmentation)
        }
    }

    override fun activeWalletStart(){
        apm.startTrace(apmEvent(Events.WALLET_ACTIVE))
        events.cancelEvent(Events.WALLET_ACTIVE.toString())
        events.startEvent(Events.WALLET_ACTIVE.toString())
    }

    override fun activeWalletEnd(
        session: Any,
        walletAssets: Map<String, Long>,
        accountAssets: Map<AccountId, Assets>,
        accounts: List<Account>
    ) {
        session as GdkSession
        apm.endTrace(apmEvent(Events.WALLET_ACTIVE), mutableMapOf())
        events.endEvent(
            Events.WALLET_ACTIVE.toString(),
            sessionSegmentation(session).also { segmentation ->
                // Total account balance
                // Note: Balance amount is not tracked, only used to identify a funded wallet
                val walletTotalBalance = walletAssets.values.sum()

                segmentation[PARAM_WALLET_FUNDED] = (walletTotalBalance > 0) // Boolean if wallet is funded with any asset
                segmentation[PARAM_ACCOUNTS_FUNDED] = accountAssets.filter { it.value.values.sum() > 0 }.count() // number of funded accounts

                segmentation[PARAM_ACCOUNTS] = accounts.size
                segmentation[PARAM_ACCOUNTS_TYPES] = accounts.map { it.type.gdkType }.toSet().sorted().joinToString(",")
            },1, 0.0
        )
    }

    override fun loginLightningStart(){
        apm.startTrace(apmEvent(Events.LIGHTNING_LOGIN))
    }

    override fun loginLightningStop(){
        apm.endTrace(apmEvent(Events.LIGHTNING_LOGIN), mutableMapOf())
    }

    fun loginWalletStart(){
        apm.startTrace(apmEvent(Events.WALLET_LOGIN))
        events.cancelEvent(Events.WALLET_LOGIN.toString())
        events.startEvent(Events.WALLET_LOGIN.toString())
    }

    fun loginWalletEnd(
        wallet: Wallet,
        session: GdkSession,
        loginCredentials: LoginCredentials? = null
    ) {
        apm.endTrace(apmEvent(Events.WALLET_LOGIN), mutableMapOf())
        events
            .endEvent(
                Events.WALLET_LOGIN.toString(),
                sessionSegmentation(session).also { segmentation ->
                    when {
                        loginCredentials?.credentialType == CredentialType.PIN_PINDATA -> {
                            LOGIN_TYPE_PIN
                        }
                        loginCredentials?.credentialType == CredentialType.BIOMETRICS_PINDATA -> {
                            LOGIN_TYPE_BIOMETRICS
                        }
                        wallet.isWatchOnly -> {
                            LOGIN_TYPE_WATCH_ONLY
                        }
                        wallet.isHardware -> {
                            LOGIN_TYPE_HARDWARE
                        }
                        else -> null
                    }?.let { method ->
                        segmentation[PARAM_METHOD] = method
                    }
                },
                1, 0.0
            )
    }

    fun hardwareConnect(device: Device) {
        events.recordEvent(Events.HWW_CONNECT.toString(), deviceSegmentation(device))
    }

    fun hardwareConnected(device: Device) {
        events.recordEvent(Events.HWW_CONNECTED.toString(), deviceSegmentation(device))
    }

    override fun jadeInitialize() {
        events.recordEvent(Events.JADE_INITIALIZE.toString())
    }

    fun jadeOtaStart(device: Device, firmwareFileData: FirmwareFileData) {
        events.recordEvent(Events.OTA_START.toString(), deviceSegmentation(device , baseSegmentation()).also { segmentation ->
            segmentation[PARAM_SELECTED_CONFIG] = firmwareFileData.image.config.lowercase()
            segmentation[PARAM_SELECTED_DELTA] = firmwareFileData.image.patchSize != null
            segmentation[PARAM_SELECTED_VERSION] = firmwareFileData.image.version
        })

        events.cancelEvent(Events.OTA_COMPLETE.toString())
        events.startEvent(Events.OTA_COMPLETE.toString())
    }

    fun jadeOtaComplete(device: Device, firmwareFileData: FirmwareFileData) {
        events.endEvent(Events.OTA_COMPLETE.toString(), deviceSegmentation(device , baseSegmentation()).also { segmentation ->
            segmentation[PARAM_SELECTED_CONFIG] = firmwareFileData.image.config.lowercase()
            segmentation[PARAM_SELECTED_DELTA] = firmwareFileData.image.patchSize != null
            segmentation[PARAM_SELECTED_VERSION] = firmwareFileData.image.version
        }, 1, 0.0)
    }

    fun addWallet() {
        events.recordEvent(Events.WALLET_ADD.toString())
    }

    fun hardwareWallet() {
        events.recordEvent(Events.WALLET_HWW.toString())
    }

    fun newWallet() {
        events.recordEvent(Events.WALLET_NEW.toString())
    }

    fun restoreWallet() {
        events.recordEvent(Events.WALLET_RESTORE.toString())
    }

    fun watchOnlyWallet() {
        events.recordEvent(Events.WALLET_WATCH_ONLY.toString())
    }

    fun createWallet(session: GdkSession) {
        events.recordEvent(Events.WALLET_CREATE.toString(), sessionSegmentation(session))
    }

    fun importWallet(session: GdkSession) {
        events.recordEvent(Events.WALLET_IMPORT.toString(), sessionSegmentation(session))
    }

    fun renameWallet() {
        events.recordEvent(Events.WALLET_RENAME.toString())
    }

    fun deleteWallet() {
        events.recordEvent(Events.WALLET_DELETE.toString())
    }

    fun renameAccount(session: GdkSession, account: Account?) {
        events.recordEvent(Events.ACCOUNT_RENAME.toString(), accountSegmentation(session, account))
    }

    fun firstAccount(session: GdkSession) {
        events.recordEvent(Events.ACCOUNT_FIRST.toString(), sessionSegmentation(session = session))
    }

    fun accountNew(session: GdkSession) {
        events.recordEvent(Events.ACCOUNT_NEW.toString(), sessionSegmentation(session = session))
    }

    fun accountSelect(session: GdkSession, accountAsset: AccountAsset) {
        events.recordEvent(
            Events.ACCOUNT_SELECT.toString(),
            accountSegmentation(session = session, account = accountAsset.account)
        )
    }

    fun assetChange(session: GdkSession) {
        events.recordEvent(Events.ASSET_CHANGE.toString(),
            sessionSegmentation(session = session))
    }

    fun assetSelect(session: GdkSession) {
        events.recordEvent(Events.ASSET_SELECT.toString(),
            sessionSegmentation(session = session))
    }

    fun createAccount(session: GdkSession, account: Account) {
        events.recordEvent(
            Events.ACCOUNT_CREATE.toString(),
            accountSegmentation(session, account = account)
        )
    }

    fun balanceConvert(session: GdkSession) {
        events.recordEvent(Events.BALANCE_CONVERT.toString(),
            sessionSegmentation(session = session))
    }

    fun startSendTransaction(){
        apm.startTrace(apmEvent(Events.SEND_TRANSACTION))
        // Cancel any previous event
        events.cancelEvent(Events.SEND_TRANSACTION.toString())
        events.startEvent(Events.SEND_TRANSACTION.toString())
    }

    fun endSendTransaction(
        session: GdkSession,
        account: Account,
        transactionSegmentation: TransactionSegmentation,
        withMemo: Boolean
    ) {
        apm.endTrace(apmEvent(Events.SEND_TRANSACTION), mutableMapOf())
        events
            .endEvent(
                Events.SEND_TRANSACTION.toString(),
                transactionSegmentation(session, account, transactionSegmentation).also {
                    it[PARAM_WITH_MEMO] = withMemo
                }
                , 1, 0.0)
    }

    fun receiveAddress(
        addressType: AddressType,
        mediaType: MediaType,
        isShare: Boolean = false,
        account: Account,
        session: GdkSession
    ) {
        events.recordEvent(
            Events.RECEIVE_ADDRESS.toString(),
            accountSegmentation(session, account).also {
                it[PARAM_TYPE] = addressType.toString()
                it[PARAM_MEDIA] = mediaType.toString()
                it[PARAM_METHOD] = SHARE.takeIf { isShare } ?: COPY
            }
        )
    }

    fun shareTransaction(session: GdkSession, account: Account?, isShare: Boolean = false) {
        events.recordEvent(
            Events.SHARE_TRANSACTION.toString(),
            accountSegmentation(session, account).also {
                it[PARAM_METHOD] = SHARE.takeIf { isShare } ?: COPY
            }
        )
    }

    fun qrScan(session: GdkSession?, onBoardingOptions: OnboardingOptions?, screenName: String?) {
        if (screenName == null) return

        val segmentation = session?.let { sessionSegmentation(it) }
            ?: onBoardingOptions?.let { onBoardingSegmentation(it) } ?: hashMapOf()

        events.recordEvent(
            Events.QR_SCAN.toString(),
            segmentation.also {
                it[PARAM_SCREEN] = screenName
            }
        )
    }

    fun appReview(session: GdkSession, account: Account?) {
        events.recordEvent(Events.APP_REVIEW.toString(),
            accountSegmentation(session, account))
    }

    fun verifyAddress(session: GdkSession, account: Account?) {
        events.recordEvent(Events.VERIFY_ADDRESS.toString(), accountSegmentation(session, account))
    }

    fun failedWalletLogin(session: GdkSession, error: Throwable) {
        events
            .recordEvent(
                Events.FAILED_WALLET_LOGIN.toString(),
                sessionSegmentation(session)
                    .also {
                        it[PARAM_ERROR] = error.message ?: "error"
                    }
            )
    }

    fun recoveryPhraseCheckFailed(page: Int) {
        events
            .recordEvent(
                Events.FAILED_RECOVERY_PHRASE_CHECK.toString(),
                mapOf(
                    PARAM_PAGE to page
                )
            )
    }

    fun startFailedTransaction(){
        apm.startTrace(apmEvent(Events.FAILED_TRANSACTION))
        events.cancelEvent(Events.FAILED_TRANSACTION.toString())
        events.startEvent(Events.FAILED_TRANSACTION.toString())
    }

    fun failedTransaction(
        session: GdkSession,
        account: Account,
        transactionSegmentation: TransactionSegmentation,
        error: Throwable
    ) {
        apm.endTrace(apmEvent(Events.FAILED_TRANSACTION), mutableMapOf())
        events
            .endEvent(
                Events.FAILED_TRANSACTION.toString(),
                transactionSegmentation(session, account, transactionSegmentation).also {
                    it[PARAM_ERROR] = error.message ?: "error"
                }
            , 1, 0.0)
    }

    private fun baseSegmentation(): HashMap<String, Any>{
        return hashMapOf<String, Any>().also { segmentation ->
            appSettingsAsString?.also {
                segmentation[USER_PROPERTY_APP_SETTINGS] = it
            }
        }
    }

    private fun networkSegmentation(session: GdkSession): HashMap<String, Any> {
        if(!session.isNetworkInitialized){
            return hashMapOf()
        }

        val isMainnet = session.isMainnet

        val hasBitcoin = session.accounts.any { it.isBitcoin } // check for unarchived bitcoin accounts
        val hasLiquid = session.accounts.any { it.isLiquid } // check for unarchived liquid accounts

        // "Networks: mainnet / liquid / mainnet-mixed / testnet / testnet-liquid / testnet-mixed
        val network = when{
            hasBitcoin && !hasLiquid -> "mainnet".takeIf { isMainnet } ?: "testnet"
            !hasBitcoin && hasLiquid -> "liquid".takeIf { isMainnet } ?: "testnet-liquid"
            hasBitcoin && hasLiquid -> "mainnet-mixed".takeIf { isMainnet } ?: "testnet-mixed"
            else -> "none"
        }

        val hasSinglesig = session.accounts.any { it.isSinglesig } // check for unarchived singlesig accounts
        val hasMultisig = session.accounts.any { it.isMultisig } // check for unarchived multisig accounts
        val hasLightning = session.accounts.any { it.isLightning } // check for unarchived multisig accounts

        // Security: singlesig / multisig / lightning / single-multi / single-light / multi-light / single-multi-light"
        val security = mutableListOf<String>()

        if(hasSinglesig) {
            security += if(hasMultisig || hasLightning) "single" else "singlesig"
        }

        if(hasMultisig){
            security += if(hasSinglesig || hasLightning) "multi" else "multisig"
        }

        if(hasLightning){
            security += if(hasSinglesig || hasMultisig) "light" else "lightning"
        }


        return baseSegmentation().also{
            it[PARAM_WALLET_NETWORKS] = network
            it[PARAM_SECURITY] = security.joinToString("-")
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun onBoardingSegmentation(onboardingOptions: OnboardingOptions): HashMap<String, Any> {
        return hashMapOf(
            PARAM_FLOW to when {
                onboardingOptions.isRestoreFlow -> RESTORE
                onboardingOptions.isWatchOnly -> WATCH_ONLY
                else -> CREATE
            },
        ).also {
            it[PARAM_MAINNET] = (onboardingOptions.isTestnet != true).toString()
        } as HashMap<String, Any>
    }

    private fun deviceSegmentation(device: DeviceInterface, segmentation: HashMap<String, Any> = hashMapOf()): HashMap<String, Any>{
        device.deviceBrand.brand.let { segmentation[PARAM_BRAND] = it }
        device.gdkHardwareWallet?.also {
            segmentation[PARAM_FIRMWARE] = it.firmwareVersion ?: ""
            segmentation[PARAM_MODEL] = it.model
        }
        segmentation[PARAM_CONNECTION] = if (device.isUsb) USB else BLE

        return segmentation
    }

    fun sessionSegmentation(session: GdkSession): HashMap<String, Any> =
        networkSegmentation(session)
            .also { segmentation ->
                session.device?.also { device ->
                    deviceSegmentation(device, segmentation)
                }

                session.ephemeralWallet?.also {
                    if(it.isBip39Ephemeral){
                        segmentation[PARAM_EPHEMERAL_BIP39] = true
                    }
                }
            }

    fun accountSegmentation(session: GdkSession, account: Account?): HashMap<String, Any> =
        sessionSegmentation(session)
            .also { segmentation ->
                account?.also { account ->
                    segmentation[PARAM_ACCOUNT_TYPE] = account.type.gdkType
                    segmentation[PARAM_ACCOUNT_NETWORK] = account.countlyId
                }
            }

    private fun transactionSegmentation(session: GdkSession, account: Account, transactionSegmentation: TransactionSegmentation): HashMap<String, Any> =
        accountSegmentation(session, account)
            .also { segmentation ->
                segmentation[PARAM_TRANSACTION_TYPE] = transactionSegmentation.transactionType.toString()
                segmentation[PARAM_ADDRESS_INPUT] = transactionSegmentation.addressInputType.toString()
            }

    fun twoFactorSegmentation(session: GdkSession, network: Network, twoFactorMethod: TwoFactorMethod): HashMap<String, Any> =
        networkSegmentation(session)
            .also { segmentation ->
                segmentation[PARAM_2FA] = twoFactorMethod.gdkType
                segmentation[PARAM_ACCOUNT_NETWORK] = network.countlyId
            }

    fun recordRating(rating: Int, comment :String){
        countly.ratings().recordRatingWidgetWithID(RATING_WIDGET_ID, rating, null, comment, false)
    }

    fun recordFeedback(rating: Int, email: String?, comment :String){
        countly.ratings().recordRatingWidgetWithID(RATING_WIDGET_ID, rating, email.takeIf { !it.isNullOrBlank() }, comment, !email.isNullOrBlank())
    }

    fun getRemoteConfigValueAsString(key: String): String? {
        return remoteConfig.getValueForKey(key)?.toString()
    }

    fun getRemoteConfigValueAsInt(key: String): Int? {
        return remoteConfig.getValueForKey(key) as? Int
    }

    fun getRemoteConfigValueAsLong(key: String): Long? {
        return getRemoteConfigValueAsInt(key)?.toLong() ?: remoteConfig.getValueForKey(key) as? Long
    }

    fun getRemoteConfigValueAsBoolean(key: String): Boolean? {
        return remoteConfig.getValueForKey(key) as? Boolean
    }

    fun getRemoteConfigValueAsJsonElement(key: String): JsonElement? {
        return remoteConfig.getValueForKey(key)?.let {
            Json.parseToJsonElement(it.toString())
        }
    }

    fun getRemoteConfigValueAsJsonArray(key: String): JsonArray? {
        return getRemoteConfigValueAsJsonElement(key)?.jsonArray
    }

    fun getRemoteConfigValueForBanners(key: String): List<Banner>? {
        return try {
            getRemoteConfigValueAsString(key)?.let {
                JsonDeserializer.decodeFromString<List<Banner>>(it)
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    override fun getRemoteConfigValueForAssets(key: String): Map<String, EnrichedAsset>? {
        return try {
            getRemoteConfigValueAsString(key)?.let {
                JsonDeserializer.decodeFromString<List<EnrichedAsset>>(it)
            }?.associate {
                it.assetId to it
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    fun apmEvent(event: Events): String{
        return if(settingsManager.appSettings.tor){
            "${event}_tor"
        }else {
            "$event"
        }
    }

    override val isLightningFeatureEnabled: Boolean
        get() = getRemoteConfigValueAsBoolean("feature_lightning") ?: false

    private fun appSettingsToString(appSettings: ApplicationSettings): String {
        val settingsAsSet = mutableSetOf<String>()

        if (appSettings.enhancedPrivacy) { settingsAsSet.add(ENCHANCED_PRIVACY) }
        if (appSettings.tor) { settingsAsSet.add(TOR) }
        if (!appSettings.proxyUrl.isNullOrBlank()) { settingsAsSet.add(PROXY) }
        if (appSettings.testnet) { settingsAsSet.add(TESTNET) }
        if (appSettings.electrumNode){ settingsAsSet.add(ELECTRUM_SERVER) }
        if (appSettings.spv) { settingsAsSet.add(SPV) }

        return settingsAsSet.sorted().joinToString(",")
    }

    enum class Events(val event: String) {
        HWW_CONNECT("hww_connect"),
        HWW_CONNECTED("hww_connected"),
        JADE_INITIALIZE("jade_initialize"),
        JADE_OTA("jade_ota"),

        OTA_START("ota_start"),
        OTA_COMPLETE("ota_complete"),

        WALLET_ADD("wallet_add"),
        WALLET_HWW("wallet_hww"),

        WALLET_NEW("wallet_new"),
        WALLET_RESTORE("wallet_restore"),
        WALLET_WATCH_ONLY("wallet_wo"),

        WALLET_LOGIN("wallet_login"),
        LIGHTNING_LOGIN("lightning_login"),

        WALLET_CREATE("wallet_create"),
        WALLET_IMPORT("wallet_import"),

        WALLET_RENAME("wallet_rename"),
        WALLET_DELETE("wallet_delete"),

        WALLET_ACTIVE("wallet_active"),

        FAILED_WALLET_LOGIN("failed_wallet_login"),

        ACCOUNT_FIRST("account_first"),
        ACCOUNT_NEW("account_new"),
        ACCOUNT_SELECT("account_select"),
        ACCOUNT_CREATE("account_create"),
        ACCOUNT_RENAME("account_rename"),

        BALANCE_CONVERT("balance_convert"),
        ASSET_CHANGE("asset_change"),
        ASSET_SELECT("asset_select"),

        RECEIVE_ADDRESS("receive_address"),

        SHARE_TRANSACTION("share_transaction"),
        QR_SCAN("qr_scan"),

        APP_REVIEW("app_review"),

        VERIFY_ADDRESS("verify_address"),

        SEND_TRANSACTION("send_transaction"),
        FAILED_TRANSACTION("failed_transaction"),
        FAILED_RECOVERY_PHRASE_CHECK("failed_recovery_phrase_check");

        override fun toString(): String = event
    }


    companion object : NamedKLogging("AppCountly") {
        const val SERVER_URL = "https://countly.blockstream.com"
        const val SERVER_URL_ONION = "http://greciphd2z3eo6bpnvd6mctxgfs4sslx4hyvgoiew4suoxgoquzl72yd.onion/"

        const val PRODUCTION_APP_KEY = "351d316234a4a83169fecd7e760ef64bfd638d21"
        const val DEVELOPMENT_APP_KEY = "cb8e449057253add71d2f9b65e5f66f73c073e63"

        const val GOOGLE_PLAY_ORGANIC_PRODUCTION = "95d7943329b90c07d6d7d16b874f97de68fbf67c"
        const val GOOGLE_PLAY_ORGANIC_DEVELOPMENT = "fba90e3e3959c95c18cca2f173bdf31cfb934d47"

        const val REFERRER_KEY = "referrer"

        const val MAX_OFFSET_PRODUCTION     = 12 * 60 * 60 * 1000L // 12 hours
        const val MAX_OFFSET_DEVELOPMENT    =      30 * 60 * 1000L // 30 mins

        const val RATING_WIDGET_ID = "5f15c01425f83c169c33cb65"

        const val PARAM_WALLET_NETWORKS = "wallet_networks"
        const val PARAM_ACCOUNT_NETWORK = "account_network"
        const val PARAM_SECURITY = "security"
        const val PARAM_ACCOUNT_TYPE = "account_type"
        const val PARAM_2FA = "2fa"
        const val PARAM_TYPE = "type"
        const val PARAM_MEDIA = "media"
        const val PARAM_METHOD = "method"
        const val PARAM_SCREEN = "screen"
        const val PARAM_PAGE = "page"
        const val PARAM_BRAND = "brand"
        const val PARAM_MODEL = "model"
        const val PARAM_FIRMWARE = "firmware"
        const val PARAM_CONNECTION = "connection"
        const val PARAM_ERROR = "error"
        const val PARAM_FLOW = "flow"
        const val PARAM_EPHEMERAL_BIP39 = "ephemeral_bip39"
        const val PARAM_MAINNET = "mainnet"

        const val PARAM_SELECTED_CONFIG = "selected_config"
        const val PARAM_SELECTED_DELTA = "selected_delta"
        const val PARAM_SELECTED_VERSION = "selected_version"

        const val PARAM_TRANSACTION_TYPE = "transaction_type"
        const val PARAM_ADDRESS_INPUT = "address_input"
        const val PARAM_WITH_MEMO = "with_memo"

        const val PARAM_WALLET_FUNDED = "wallet_funded"
        const val PARAM_ACCOUNTS = "accounts"
        const val PARAM_ACCOUNTS_TYPES = "accounts_types"
        const val PARAM_ACCOUNTS_FUNDED = "accounts_funded"

        const val LOGIN_TYPE_PIN = "pin"
        const val LOGIN_TYPE_BIOMETRICS = "biometrics"
        const val LOGIN_TYPE_WATCH_ONLY = "watch_only"
        const val LOGIN_TYPE_HARDWARE = "hardware"

        const val USER_PROPERTY_APP_SETTINGS = "app_settings"

        const val USER_PROPERTY_TOTAL_WALLETS = "total_wallets"

        const val TOR = "tor"
        const val PROXY = "proxy"
        const val TESTNET = "testnet"
        const val ELECTRUM_SERVER = "electrum_server"
        const val SPV = "spv"
        const val ENCHANCED_PRIVACY = "enhanced_privacy"

        const val BLE = "BLE"
        const val USB = "USB"

        const val CREATE = "create"
        const val RESTORE = "restore"
        const val WATCH_ONLY = "watch_only"

        const val SHARE = "share"
        const val COPY = "copy"

        const val ANALYTICS_GROUP = "analytics"

        val skipExceptionRecording = listOf(
            "id_invalid_amount",
            "id_invalid_address",
            "id_insufficient_funds",
            "id_invalid_private_key",
            "id_action_canceled",
            "id_login_failed",
            "id_error_parsing",
            "id_invalid_address",
            "id_invalid_asset_id"
        )

        val consentRequiredGroup = arrayOf(
            Countly.CountlyFeatureNames.sessions,
            Countly.CountlyFeatureNames.events,
            Countly.CountlyFeatureNames.views,
            Countly.CountlyFeatureNames.location,
            Countly.CountlyFeatureNames.scrolls,
            Countly.CountlyFeatureNames.clicks,
            Countly.CountlyFeatureNames.apm
        )

        val noConsentRequiredGroup = arrayOf(
            Countly.CountlyFeatureNames.metrics,
            Countly.CountlyFeatureNames.users,
            Countly.CountlyFeatureNames.push,
            Countly.CountlyFeatureNames.starRating,
            Countly.CountlyFeatureNames.feedback,
            Countly.CountlyFeatureNames.remoteConfig,
            Countly.CountlyFeatureNames.attribution,
            Countly.CountlyFeatureNames.crashes
        )
    }
}

enum class AddressType(val string: String) {
    ADDRESS("address"),
    URI("uri");

    override fun toString(): String = string
}

enum class MediaType(val string: String) {
    TEXT("text"),
    IMAGE("image");

    override fun toString(): String = string
}

enum class TransactionType(val string: String) {
    SEND("send"),
    SWEEP("sweep"),
    BUMP("bump"),
    SWAP("swap");

    override fun toString(): String = string
}

@Parcelize
enum class AddressInputType(val string: String) : Parcelable {
    PASTE("paste"),
    SCAN("scan"),
    BIP21("bip21");

    override fun toString(): String = string
}

@Parcelize
data class TransactionSegmentation constructor(
    val transactionType: TransactionType,
    val addressInputType: AddressInputType?,
    val sendAll: Boolean
) : Parcelable

interface ScreenView{
    var screenIsRecorded: Boolean
    val screenName: String?
    val segmentation: HashMap<String, Any>?
}

interface BannerView {
    fun getBannerAlertView() : GreenAlertView?
}