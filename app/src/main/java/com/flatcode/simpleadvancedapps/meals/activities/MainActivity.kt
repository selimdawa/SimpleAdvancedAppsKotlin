package com.flatcode.simpleadvancedapps.meals.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainMealsBinding
import com.flatcode.simpleadvancedapps.meals.db.MealDatabase
import com.flatcode.simpleadvancedapps.meals.mvvm.HomeViewModel
import com.flatcode.simpleadvancedapps.meals.mvvm.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel: HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this, homeViewModelFactory)[HomeViewModel::class.java]
    }

    private var binding: ActivityMainMealsBinding? = null
    var context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainMealsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.toolbar.nameSpace.text = DATA.MEALS

        val bottomNavigationView = binding!!.btmNav
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment_container)

        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}