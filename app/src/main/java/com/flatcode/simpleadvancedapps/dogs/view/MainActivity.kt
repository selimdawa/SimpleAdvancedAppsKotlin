package com.flatcode.simpleadvancedapps.dogs.view

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
import androidx.navigation.fragment.NavHostFragment
import com.flatcode.simpleadvancedapps.databinding.ActivityMainDogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainDogBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainDogBinding.inflate(layoutInflater)
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

        supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}