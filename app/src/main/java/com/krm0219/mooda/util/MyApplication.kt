package com.krm0219.mooda.util

import android.app.Application
import com.facebook.stetho.Stetho
import com.krm0219.mooda.util.Preferences

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        Preferences.init(this)
    }
}