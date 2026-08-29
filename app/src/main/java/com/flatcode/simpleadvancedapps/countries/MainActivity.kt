package com.flatcode.simpleadvancedapps.countries

import android.view.View
import android.view.ViewGroup
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainCountryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainCountryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val container = findViewById<View>(R.id.fragmentContainerView)
        val containerMargin = (container.layoutParams as ViewGroup.MarginLayoutParams).topMargin
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            container.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBars.top + containerMargin
            }
            binding.root.updatePadding(
                bottom = systemBars.bottom
            )
            insets
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}