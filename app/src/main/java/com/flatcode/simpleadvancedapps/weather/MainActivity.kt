package com.flatcode.simpleadvancedapps.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainWeatherBinding
import com.flatcode.simpleadvancedapps.weather.fragment.MainFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, MainFragment.newInstance()).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}