package com.flatcode.simpleadvancedapps.dictionary

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityDefinitionWordBinding

class DefinitionWordActivity : AppCompatActivity() {

    private var _binding: ActivityDefinitionWordBinding? = null
    private val binding get() = _binding!!
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivityDefinitionWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.nameSpace.text = DATA.meaning_of_the_word
            tvDefinition.text = intent.getStringExtra(DATA.DICTIONARY_KEY)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}