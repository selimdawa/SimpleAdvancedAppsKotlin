package com.flatcode.simpleadvancedapps.crypto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCryptoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainCryptoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCryptoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}