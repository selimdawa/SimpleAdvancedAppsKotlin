package com.flatcode.simpleadvancedapps

import android.app.Application
import android.content.Context
import com.flatcode.simpleadvancedapps.pop.repository.FunkoRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application(){

    val repository: FunkoRepository by lazy { FunkoRepository() }

    companion object {
        private lateinit var instance: HiltApplication
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}