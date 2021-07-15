package com.robin.mvlproject.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: BaseApplication? = null
    }
}