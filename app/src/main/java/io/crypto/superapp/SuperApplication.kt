package io.crypto.superapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.crypto.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class SuperApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}