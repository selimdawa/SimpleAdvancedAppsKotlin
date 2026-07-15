package com.flatcode.simpleadvancedapps.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivitySplashBinding
import com.flatcode.simpleadvancedapps.utils.CLASS
import com.flatcode.simpleadvancedapps.utils.openActivity

class SplashActivity : AppCompatActivity() {

    private var _binding: ActivitySplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({ launch() }, TIME_PER_MILLIS.toLong())
    }

    private fun launch() {
        openActivity(CLASS.MAIN, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val TIME_PER_MILLIS = 1000
    }
}