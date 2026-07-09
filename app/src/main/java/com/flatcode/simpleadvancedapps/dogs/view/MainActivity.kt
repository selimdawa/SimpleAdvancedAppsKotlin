package com.flatcode.simpleadvancedapps.dogs.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivityMainDogBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainDogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainDogBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}