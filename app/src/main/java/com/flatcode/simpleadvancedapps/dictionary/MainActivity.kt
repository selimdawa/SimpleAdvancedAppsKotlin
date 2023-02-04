package com.flatcode.simpleadvancedapps.dictionary

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.flatcode.simpleadvancedapps.Unit.CLASS
import com.flatcode.simpleadvancedapps.Unit.DATA
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.Unit.VOID
import com.flatcode.simpleadvancedapps.databinding.ActivityMainDictionaryBinding
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainDictionaryBinding? = null
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainDictionaryBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        binding!!.toolbar.nameSpace.text = DATA.DICTIONARY
        binding!!.findButton.setOnClickListener { stringRequest() }
    }

    private fun extractDefinitionFromJason(response: String) {
        val jsonArray = JSONArray(response)
        val firstIndex = jsonArray.getJSONObject(0)
        val getShotDefinition = firstIndex.getJSONArray(DATA.Short_Def)
        val firstShortDefinition = getShotDefinition.get(0)

        VOID.IntentExtra(
            this, CLASS.Definition_Word, DATA.DICTIONARY_KEY, firstShortDefinition.toString()
        )
    }

    private fun getUrl(): String {
        val word = binding!!.searchEditText.text
        val apiKey = DATA.DICTIONARY_API_KEY
        val basicUrl = DATA.DICTIONARY_BASIC_URL
        return "$basicUrl$word?key=$apiKey"
    }

    private fun stringRequest() {
        val url = getUrl()
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            try {
                extractDefinitionFromJason(response)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }, { error ->
            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
        })
        queue.add(stringRequest)
    }
}