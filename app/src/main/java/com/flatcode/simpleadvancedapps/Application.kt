package com.flatcode.simpleadvancedapps

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import io.selimdawa.multicolors.MultiColorManager

@HiltAndroidApp
class Application : Application() {

    companion object {
        private lateinit var instance: Application
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        MultiColorManager.init(this)
    }
}