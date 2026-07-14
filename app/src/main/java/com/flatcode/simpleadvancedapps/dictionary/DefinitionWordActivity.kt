package com.flatcode.simpleadvancedapps.dictionary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flatcode.simpleadvancedapps.databinding.ActivityDefinitionWordBinding
import com.flatcode.simpleadvancedapps.utils.Constants

class DefinitionWordActivity : AppCompatActivity() {

    private var _binding: ActivityDefinitionWordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDefinitionWordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            toolbar.nameSpace.text = Constants.MEANING_OF_THE_WORD
            tvDefinition.text = intent.getStringExtra(Constants.DICTIONARY_KEY)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}