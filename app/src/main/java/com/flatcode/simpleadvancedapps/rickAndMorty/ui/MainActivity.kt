package com.flatcode.simpleadvancedapps.rickAndMorty.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainRickBinding
import com.flatcode.simpleadvancedapps.utils.DATA
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainRickBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRickBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.nameSpace.text = DATA.RICK_AND_MORTY

        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigation.setupWithNavController(navController)
    }
}