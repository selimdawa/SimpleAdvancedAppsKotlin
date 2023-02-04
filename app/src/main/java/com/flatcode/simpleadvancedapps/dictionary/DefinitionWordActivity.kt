package com.flatcode.simpleadvancedapps.dictionary

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityDefinitionWordBinding

class DefinitionWordActivity : AppCompatActivity() {

    private var binding: ActivityDefinitionWordBinding? = null
    var context: Context = this@DefinitionWordActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityDefinitionWordBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.toolbar.nameSpace.text = DATA.meaning_of_the_word
        binding!!.tvDefinition.text = intent.getStringExtra(DATA.DICTIONARY_KEY)
    }
}