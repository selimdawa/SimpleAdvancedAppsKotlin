package com.flatcode.simpleadvancedapps

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.flatcode.simpleadvancedapps.utils.applyAppTheme
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    companion object {
        private lateinit var instance: Application
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
                activity.applyAppTheme()
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            override fun onActivityDestroyed(activity: Activity) {}
        })
    }
}