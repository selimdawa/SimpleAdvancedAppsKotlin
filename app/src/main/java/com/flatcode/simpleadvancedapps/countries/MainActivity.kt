package com.flatcode.simpleadvancedapps.countries

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCountryBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}