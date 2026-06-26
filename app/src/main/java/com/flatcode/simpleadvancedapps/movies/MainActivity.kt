package com.flatcode.simpleadvancedapps.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA.MAIN
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainMovieBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainMovieBinding? = null
    val binding get() = _binding!!
    lateinit var navController: NavController
    private val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MAIN = this
        navController = findNavController(R.id.nav_host)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}