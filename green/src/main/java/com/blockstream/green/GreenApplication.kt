package com.blockstream.green

import android.app.Application
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.blockstream.common.managers.AssetManager
import com.blockstream.common.managers.LifecycleManager
import com.blockstream.common.managers.SettingsManager
import com.blockstream.green.data.Countly
import com.blockstream.green.database.WalletRepository
import com.blockstream.green.lifecycle.ActivityLifecycle
import com.blockstream.green.managers.NotificationManager
import com.blockstream.green.managers.SessionManager
import com.blockstream.green.settings.Migrator
import com.blockstream.green.ui.MainActivity
import com.blockstream.green.ui.QATesterActivity
import com.blockstream.green.utils.QATester
import com.blockstream.green.utils.isDevelopmentFlavor
import com.pandulapeter.beagle.Beagle
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch
import mu.KLogging
import org.slf4j.impl.HandroidLoggerAdapter
import javax.inject.Inject

typealias ApplicationScope = kotlinx.coroutines.CoroutineScope

@HiltAndroidApp
class GreenApplication : Application() {
    @Inject
    lateinit var migrator: Migrator

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var walletRepository: WalletRepository

    @Inject
    lateinit var assetManager: AssetManager

    @Inject
    lateinit var settingsManager: SettingsManager

    @Inject
    lateinit var qaTester: QATester

    @Inject
    lateinit var beagle: Beagle

    @Inject
    lateinit var applicationScope: ApplicationScope

    @Inject
    lateinit var notificationManager: NotificationManager

    @Inject
    lateinit var activityLifecycle: ActivityLifecycle

    @Inject
    lateinit var countly: Countly

    @Inject
    lateinit var lifecycleManager: LifecycleManager

    override fun onCreate() {
        super.onCreate()

        // Listen to foreground / background events
        ProcessLifecycleOwner.get().lifecycle.addObserver(object: DefaultLifecycleObserver{
            override fun onResume(owner: LifecycleOwner) {
                lifecycleManager.setOnForeground(true)
            }

            override fun onPause(owner: LifecycleOwner) {
                lifecycleManager.setOnForeground(false)
            }
        })

        registerActivityLifecycleCallbacks(activityLifecycle)

        HandroidLoggerAdapter.DEBUG = BuildConfig.DEBUG
        HandroidLoggerAdapter.ANDROID_API_LEVEL = Build.VERSION.SDK_INT
        HandroidLoggerAdapter.APP_NAME = "Green"

        countly.applicationOnCreate()

        applicationScope.launch {
            migrator.migrate()
        }

        if (isDevelopmentFlavor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            initShortcuts()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N_MR1)
    fun initShortcuts(){
        val shortcutManager = getSystemService(ShortcutManager::class.java)

        val hideAmountsShortcut : ShortcutInfo = ShortcutInfo.Builder(this, MainActivity.HIDE_AMOUNTS)
            .setShortLabel(getString(R.string.id_hide_amounts))
            .setLongLabel(getString(R.string.id_hide_amounts))
            .setIcon(Icon.createWithResource(this, R.drawable.ic_shortcut_eye_close))
            .setIntent(Intent(MainActivity.HIDE_AMOUNTS, null, this, MainActivity::class.java)).build()

        val qaTesterShortcut : ShortcutInfo? =
            if (isDevelopmentFlavor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                ShortcutInfo.Builder(this, "QATester")
                    .setShortLabel("QA Tester")
                    .setLongLabel("QA Tester")
                    .setIcon(Icon.createWithResource(this,  R.drawable.blockstream_jade_device))
                    .setIntent(Intent(Intent.ACTION_VIEW, null, this, QATesterActivity::class.java))
                    .build()
            } else {
                null
            }


        shortcutManager!!.dynamicShortcuts = listOfNotNull(hideAmountsShortcut, qaTesterShortcut)
    }

    companion object: KLogging()
}