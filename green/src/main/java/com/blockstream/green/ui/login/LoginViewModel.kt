package com.blockstream.green.ui.login

import android.util.Base64
import androidx.lifecycle.*
import com.blockstream.common.gdk.data.TorEvent
import com.blockstream.common.gdk.device.DeviceResolver
import com.blockstream.common.gdk.params.LoginCredentialsParams
import com.blockstream.common.lightning.AppGreenlightCredentials
import com.blockstream.common.managers.SettingsManager
import com.blockstream.green.ApplicationScope
import com.blockstream.green.data.AppEvent
import com.blockstream.green.data.Countly
import com.blockstream.green.data.NavigateEvent
import com.blockstream.green.database.CredentialType
import com.blockstream.green.database.LoginCredentials
import com.blockstream.green.database.Wallet
import com.blockstream.green.database.WalletRepository
import com.blockstream.common.data.WatchOnlyCredentials
import com.blockstream.green.devices.Device
import com.blockstream.green.extensions.logException
import com.blockstream.green.extensions.string
import com.blockstream.green.gdk.GdkSession
import com.blockstream.green.gdk.isNotAuthorized
import com.blockstream.green.lifecycle.PendingLiveData
import com.blockstream.green.managers.SessionManager
import com.blockstream.green.ui.wallet.AbstractWalletViewModel
import com.blockstream.green.utils.AppKeystore
import com.blockstream.green.utils.ConsumableEvent
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.crypto.Cipher

class LoginViewModel @AssistedInject constructor(
    private var appKeystore: AppKeystore,
    sessionManager: SessionManager,
    walletRepository: WalletRepository,
    countly: Countly,
    private val applicationScope: ApplicationScope,
    private val settingsManager: SettingsManager,
    @Assisted wallet: Wallet,
    @Assisted val device: Device?
) : AbstractWalletViewModel(sessionManager, walletRepository, countly, wallet) {

    sealed class LoginEvent: AppEvent {
        data class LaunchBiometrics(val loginCredentials: LoginCredentials) : LoginEvent()
        object LoginDevice : LoginEvent()
        object AskBip39Passphrase : LoginEvent()
    }

    val onErrorMessage = MutableLiveData<ConsumableEvent<Throwable>>()

    var biometricsCredentials: MutableLiveData<LoginCredentials> = MutableLiveData()
    var watchOnlyCredentials: MutableLiveData<LoginCredentials> = MutableLiveData()
    var pinCredentials: PendingLiveData<LoginCredentials> = PendingLiveData()
    var passwordCredentials: PendingLiveData<LoginCredentials> = PendingLiveData()
    var lightningCredentials: PendingLiveData<LoginCredentials> = PendingLiveData()

    var password = MutableLiveData("")
    var watchOnlyPassword = MutableLiveData("")

    val torEvent: MutableLiveData<TorEvent> = MutableLiveData()

    val applicationSettingsLiveData = settingsManager.appSettingsStateFlow.asLiveData()

    var bip39Passphrase = MutableLiveData("")

    private val isBip39Login
        get() = bip39Passphrase.string().trim().isNotBlank()

    val isWatchOnlyLoginEnabled: LiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>().apply {
            val block = { _: Any? ->
                val isInitial = (watchOnlyCredentials.value != null && initialAction.value == false)
                value = (!watchOnlyPassword.value.isNullOrBlank() || wallet.isWatchOnlySingleSig) && !onProgress.value!! || isInitial
            }
            addSource(watchOnlyPassword, block)
            addSource(onProgress, block)
            addSource(watchOnlyCredentials, block)
            addSource(initialAction, block)
        }
    }

    /**
     * {@code initialAction} describes if the initial action is been taken.
     *
     * Also is used to prevent multiple actions to be initiated automatically .eg BiometricsPrompt
     *
     * For example initialAction is used in Watch-only login to display only the username and the login button.
     * If login fails, the password field is then shown.
     *
     */
    var initialAction = MutableLiveData(session.isConnected)

    var loginCredentialsInitialized = false

    val isEmergencyRecoveryPhrase = MutableLiveData(false)

    init {
        if(session.isConnected){
            onEvent.postValue(ConsumableEvent(NavigateEvent.NavigateWithData(wallet)))
        }else{
            if (wallet.askForBip39Passphrase) {
                onEvent.postValue(ConsumableEvent(LoginEvent.AskBip39Passphrase))
            }

            device?.let {
                onEvent.postValue(ConsumableEvent(LoginEvent.LoginDevice))
            }

            // Beware as this will fire new values if eg. you change a login credential
            walletRepository.getWalletLoginCredentialsFlow(wallet.id).filterNotNull().onEach {
                    loginCredentialsInitialized = true
                    biometricsCredentials.value = it.biometricsPinData
                    watchOnlyCredentials.value = it.watchOnlyCredentials
                    pinCredentials.value = it.pinPinData
                    passwordCredentials.value = it.passwordPinData
                    lightningCredentials.value = it.lightningCredentials

                    val biometricsBasedCredentials = it.biometricsPinData ?: it.biometricsWatchOnlyCredentials

                    if(initialAction.value == false && !wallet.askForBip39Passphrase){
                        biometricsBasedCredentials?.let { biometricsCredentials ->
                            onEvent.postValue(ConsumableEvent(
                                LoginEvent.LaunchBiometrics(
                                    biometricsCredentials
                                )
                            ))
                            initialAction.postValue(true)
                        }
                    }
                }.launchIn(viewModelScope)

            sessionManager.torProxyProgress.onEach {
                torEvent.value = it
            }.launchIn(viewModelScope)
        }
    }

    fun setBip39Passphrase(passphrase: String?, alwaysAsk: Boolean){
        bip39Passphrase.value = passphrase?.trim()
        viewModelScope.launch(context = logException(countly)) {
            wallet.askForBip39Passphrase = alwaysAsk
            walletRepository.updateWallet(wallet)
        }

        if(initialAction.value == false){
            biometricsCredentials.value?.let { biometricsCredentials ->
                onEvent.postValue(ConsumableEvent(LoginEvent.LaunchBiometrics(biometricsCredentials)))
                initialAction.postValue(true)
            }
        }
    }

    private fun emergencyRecoveryPhrase(pin: String, loginCredentials: LoginCredentials) {
        doUserAction({
            session.emergencyRestoreOfRecoveryPhrase(
                wallet = wallet,
                pin = pin,
                loginCredentials = loginCredentials
            ).also {
                session.disconnect()
            }
        }, onSuccess = {credentials ->
            onEvent.postValue(ConsumableEvent(NavigateEvent.NavigateWithData(credentials)))
            isEmergencyRecoveryPhrase.postValue(false)
        }, onError = {

            if (it.isNotAuthorized()) {
                loginCredentials.counter += 1

                viewModelScope.launch {
                    walletRepository.updateLoginCredentials(loginCredentials)
                }
            } else {
                onErrorMessage.postValue(ConsumableEvent(it))
            }

            onError.postValue(ConsumableEvent(it))
            countly.failedWalletLogin(session, it)
        })
    }

    fun loginWithDevice() {
        device?.gdkHardwareWallet?.also { gdkHardwareWallet ->
            login {
                session.loginWithDevice(
                    wallet = wallet,
                    device = device,
                    hardwareWalletResolver = DeviceResolver(gdkHardwareWallet, this),
                    hwInteraction = this
                )
            }
        }
    }

    fun loginWithPin(pin: String, loginCredentials: LoginCredentials) {
        if(isEmergencyRecoveryPhrase.value == true){
            emergencyRecoveryPhrase(pin, loginCredentials)
            return
        }

        // If Wallet Hash ID is empty (from migration) do a one-time wallet restore to make a real account discovery
        val isRestore = wallet.walletHashId.isEmpty()

        val appGreenlightCredentials = try {
            lightningCredentials.value.takeIf { !isBip39Login }?.encryptedData?.let { ed ->
                appKeystore.decryptData(ed).let { AppGreenlightCredentials.fromJsonString(String(it)) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        login(loginCredentials) {
            // if bip39 passphrase, don't initialize the session as we need to re-connect || initializeSession = bip39Passphrase.isNullOrBlank())
            session.loginWithPin(
                wallet = wallet,
                pin = pin,
                loginCredentials = loginCredentials,
                appGreenlightCredentials = appGreenlightCredentials,
                isRestore = isRestore,
                initializeSession = !isBip39Login
            )
        }
    }

    fun loginWatchOnlyWithLoginCredentials(loginCredentials: LoginCredentials) {
        loginCredentials.encryptedData?.let { encryptedData ->
            login(loginCredentials, isWatchOnly = true, updateWatchOnlyPassword = false) {

                initialAction.postValue(true)

                val watchOnlyCredentials = appKeystore.decryptData(encryptedData).let {
                    if(loginCredentials.credentialType == CredentialType.KEYSTORE_PASSWORD){
                        WatchOnlyCredentials(
                            password = String(it)
                        )
                    }else{
                        WatchOnlyCredentials.fromByteArray(it)
                    }
                }

                session.loginWatchOnly(wallet = wallet, username = wallet.watchOnlyUsername ?: "", watchOnlyCredentials)
            }
        }
    }

    private fun loginWatchOnlyWithWatchOnlyCredentials(loginCredentials: LoginCredentials, watchOnlyCredentials: WatchOnlyCredentials){
        login(loginCredentials, isWatchOnly = true, updateWatchOnlyPassword = false) {
            session.loginWatchOnly(wallet = wallet, username = wallet.watchOnlyUsername ?: "", watchOnlyCredentials)
        }
    }

    fun loginWithBiometrics(cipher: Cipher, loginCredentials: LoginCredentials) {
        loginCredentials.encryptedData?.let { encryptedData ->
            try {
                appKeystore.decryptData(cipher, encryptedData)
            }catch (e: Exception){
                countly.recordException(e)
                onError.postValue(ConsumableEvent(e))
                null
            }
        }?.also { decryptedData ->
            if(loginCredentials.credentialType == CredentialType.BIOMETRICS_PINDATA){
                loginWithPin(String(decryptedData), loginCredentials)
            }else{
                loginWatchOnlyWithWatchOnlyCredentials(loginCredentials, WatchOnlyCredentials.fromByteArray(decryptedData))
            }
        }
    }

    fun loginWithBiometricsV3(cipher: Cipher, loginCredentials: LoginCredentials) {
        loginCredentials.encryptedData?.also { encryptedData ->
            try {
                val decrypted = appKeystore.decryptData(cipher, encryptedData)
                // Migrated from v3
                val pin = Base64.encodeToString(decrypted, Base64.NO_WRAP).substring(0, 15)
                loginWithPin(pin, loginCredentials)
            }catch (e: Exception){
                countly.recordException(e)
                onError.postValue(ConsumableEvent(e))
            }
        }
    }

    fun watchOnlyLogin() {
        login(null, isWatchOnly = true, updateWatchOnlyPassword = !wallet.isWatchOnlySingleSig) {
            session.loginWatchOnly(
                wallet = wallet,
                username = wallet.watchOnlyUsername ?: "",
                watchOnlyCredentials = WatchOnlyCredentials(password = watchOnlyPassword.string())
            )
        }
    }

    private fun login(
        loginCredentials: LoginCredentials? = null,
        isWatchOnly: Boolean = false,
        updateWatchOnlyPassword: Boolean = false,
        logInMethod: suspend (GdkSession) -> Unit
    ) {

        doUserAction({
            countly.loginWalletStart()

            logInMethod.invoke(session)

            // Migrate - add walletHashId
            if (wallet.walletHashId != session.walletHashId) {
                wallet.walletHashId = session.walletHashId ?: ""
                if (!wallet.isEphemeral) {
                    walletRepository.updateWallet(wallet)
                }
            }

            // Change active account if necessary (account archived)
            if (wallet.activeNetwork != (session.activeAccountOrNull?.networkId ?: "") ||
                wallet.activeAccount != (session.activeAccountOrNull?.pointer ?: 0)
            ) {
                wallet.activeNetwork = session.activeAccountOrNull?.networkId ?: session.defaultNetwork.id
                wallet.activeAccount = session.activeAccountOrNull?.pointer ?: 0

                if(!wallet.isEphemeral) {
                    walletRepository.updateWallet(wallet)
                }
            }

            // Reset counter
            loginCredentials?.also{
                it.counter = 0
                walletRepository.updateLoginCredentials(it)
            }

            // Update watch-only password if needed
            if(updateWatchOnlyPassword && watchOnlyCredentials.value?.credentialType != CredentialType.BIOMETRICS_WATCHONLY_CREDENTIALS){
                watchOnlyCredentials.value?.let {
                    // Delete deprecated credential type
                    if(it.credentialType == CredentialType.KEYSTORE_PASSWORD){
                        walletRepository.deleteLoginCredentials(it)
                    }

                    // Upgrade from old KEYSTORE_PASSWORD if required
                    it.credentialType = CredentialType.KEYSTORE_WATCHONLY_CREDENTIALS
                    it.encryptedData = appKeystore.encryptData(WatchOnlyCredentials(password = watchOnlyPassword.string()).toString().toByteArray())
                    walletRepository.insertOrReplaceLoginCredentials(it)
                }
            }

            if(isBip39Login){
                val network = session.defaultNetwork

                val mnemonic = session.getCredentials().mnemonic

                // Disconnect as no longer needed
                session.disconnectAsync()

                val walletName = wallet.name

                val ephemeralWallet = Wallet.createEphemeralWallet(
                    ephemeralId = sessionManager.getNextEphemeralId(),
                    name = walletName,
                    networkId = network.id,
                    isHardware = false
                )

                // Create an ephemeral session
                val ephemeralSession = sessionManager.getWalletSession(ephemeralWallet)

                // Set Ephemeral wallet
                ephemeralSession.ephemeralWallet = ephemeralWallet

                val loginData = ephemeralSession.loginWithMnemonic(
                    isTestnet = wallet.isTestnet,
                    loginCredentialsParams = LoginCredentialsParams(
                        mnemonic = mnemonic,
                        bip39Passphrase = bip39Passphrase.string().trim()
                    ),
                    initializeSession = true,
                    isSmartDiscovery = true,
                    isCreate = false,
                    isRestore = false
                )

                // Check if there is already a BIP39 ephemeral wallet
                sessionManager.getEphemeralWalletSession(loginData.walletHashId)?.let {
                    // Disconnect the no longer needed session
                    ephemeralSession.disconnectAsync()

                    // Return the previous connected BIP39 ephemeral session
                    it.ephemeralWallet!! to it
                } ?: run {
                    ephemeralWallet.walletHashId = loginData.walletHashId

                    // Return the ephemeral wallet
                    ephemeralWallet to ephemeralSession
                }
            }else{
                // Return the wallet
                wallet to session
            }
        }, postAction = {
            onProgress.value = it == null
        }, onSuccess = { pair ->
            countly.loginWalletEnd(
                wallet = pair.first,
                session = pair.second,
                loginCredentials = loginCredentials
            )
            onEvent.postValue(ConsumableEvent(NavigateEvent.NavigateWithData(pair.first)))
        }, onError = {
            if(it.isNotAuthorized()){
                loginCredentials?.also{ loginCredentials ->
                    loginCredentials.counter += 1

                    viewModelScope.launch {
                        walletRepository.updateLoginCredentials(loginCredentials)
                    }
                }

                if(isWatchOnly){
                    onErrorMessage.postValue(ConsumableEvent(it))
                }

            }else if(isBip39Login && it.message == "id_login_failed"){
                // On Multisig & BIP39 Passphrase login, instead of registering a new wallet, show error "Wallet not found"
                // Jade users restoring can still login
                onErrorMessage.postValue(ConsumableEvent(Exception("id_wallet_not_found")))
            }else{
                onErrorMessage.postValue(ConsumableEvent(it))
            }

            onError.postValue(ConsumableEvent(it))
            countly.failedWalletLogin(session, it)
        })
    }

    fun deleteLoginCredentials(loginCredentials: LoginCredentials){
        applicationScope.launch(context = logException(countly)) {
            walletRepository.deleteLoginCredentials(loginCredentials)
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(
            wallet: Wallet,
            device: Device?
        ): LoginViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            wallet: Wallet,
            device: Device?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(wallet, device) as T
            }
        }
    }
}