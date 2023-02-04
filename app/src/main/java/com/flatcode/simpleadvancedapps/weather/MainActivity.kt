package com.flatcode.simpleadvancedapps.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainWeatherBinding
import com.flatcode.simpleadvancedapps.weather.fragmennts.MainFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainWeatherBinding
    var context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        //THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.toolbar.nameSpace.text = DATA.Weather

        supportFragmentManager.beginTransaction()
            .replace(R.id.placeholder, MainFragment.newInstance()).commit()
    }
}