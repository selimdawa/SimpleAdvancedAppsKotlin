package com.flatcode.simpleadvancedapps.poke.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainPokeBinding
import com.flatcode.simpleadvancedapps.poke.domain.SelectedListener

class MainActivity : AppCompatActivity(), SelectedListener {

    private var _binding: ActivityMainPokeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainPokeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onSelected(id: Int) {
        val bundle = Bundle().apply {
            putInt("id", id)
        }

        val detailFragment = DetailFragment().apply {
            arguments = bundle
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, detailFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}