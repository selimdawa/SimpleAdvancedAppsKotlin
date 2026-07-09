package com.flatcode.simpleadvancedapps.poke.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.utils.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainPokeBinding
import com.flatcode.simpleadvancedapps.poke.domain.SelectedListener

class MainActivity : AppCompatActivity(), SelectedListener {

    private var _binding: ActivityMainPokeBinding? = null
    private val binding get() = _binding!!

    var context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
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