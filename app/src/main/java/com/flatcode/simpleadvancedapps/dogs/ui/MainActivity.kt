package com.flatcode.simpleadvancedapps.dogs.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.THEME

class MainActivity : AppCompatActivity() {

    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_dog)
    }
}