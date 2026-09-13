package com.flatcode.simpleadvancedapps

import android.app.Application
import android.content.Context
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import dagger.hilt.android.HiltAndroidApp
import io.selimdawa.multicolors.MultiColorManager

@HiltAndroidApp
class Application : Application(), SingletonImageLoader.Factory {

    companion object {
        private lateinit var instance: Application
        fun getAppContext(): Context = instance.applicationContext
    }

    override fun newImageLoader(context: Context): ImageLoader {
        return ImageLoader.Builder(context)
            .components {
                add(OkHttpNetworkFetcherFactory())
            }
            .crossfade(true)
            .logger(DebugLogger())
            .build()
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        MultiColorManager.init(this)
    }
}