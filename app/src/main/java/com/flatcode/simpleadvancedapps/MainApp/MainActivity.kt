package com.flatcode.simpleadvancedapps.MainApp

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.THEME
import com.flatcode.simpleadvancedapps.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private var binding: ActivityMainBinding? = null
    var mainViewModel: MainViewModel? = null
    var mainInfoViewModel: MainInfoViewModel? = null
    var adapter: MainAdapter? = null
    var adapterInfo: MainInfoAdapter? = null
    var context: Context = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager.getDefaultSharedPreferences(baseContext)
            .registerOnSharedPreferenceChangeListener(this)
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding!!.root
        setContentView(view)

        // Color Mode ----------------------------- Start
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        // Color Mode -------------------------------- End

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(baseContext)
        if (sharedPreferences.getString("color_option", "ONE") == "ONE"
            || sharedPreferences.getString("color_option", "TWO") == "TWO"
            || sharedPreferences.getString("color_option", "THREE") == "THREE"
            || sharedPreferences.getString("color_option", "FOUR") == "FOUR"
            || sharedPreferences.getString("color_option", "FIVE") == "FIVE"
            || sharedPreferences.getString("color_option", "SIX") == "SIX"
            || sharedPreferences.getString("color_option", "SEVEN") == "SEVEN"
            || sharedPreferences.getString("color_option", "EIGHT") == "EIGHT"
            || sharedPreferences.getString("color_option", "NINE") == "NINE"
            || sharedPreferences.getString("color_option", "TEEN") == "TEEN"
        ) binding!!.toolbar.mode.setBackgroundResource(R.drawable.sun)
        else if (sharedPreferences.getString("color_option", "NIGHT_ONE") == "NIGHT_ONE"
            || sharedPreferences.getString("color_option", "NIGHT_TWO") == "NIGHT_TWO"
            || sharedPreferences.getString("color_option", "NIGHT_THREE") == "NIGHT_THREE"
            || sharedPreferences.getString("color_option", "NIGHT_FOUR") == "NIGHT_FOUR"
            || sharedPreferences.getString("color_option", "NIGHT_FIVE") == "NIGHT_FIVE"
            || sharedPreferences.getString("color_option", "NIGHT_SIX") == "NIGHT_SIX"
            || sharedPreferences.getString("color_option", "NIGHT_SEVEN") == "NIGHT_SEVEN"
        ) binding!!.toolbar.mode.setBackgroundResource(R.drawable.moon)

        binding!!.toolbar.info.setOnClickListener { showDialogAboutApps() }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //binding!!.recyclerView.setHasFixedSize(true)
        adapter = MainAdapter(context)
        binding!!.recyclerView.adapter = adapter
        mainViewModel!!.dataMain.observe(this) { mainArrayList: ArrayList<Main>? ->
            adapter!!.addList(mainArrayList)
        }
        mainViewModel!!.getItems(binding!!.recyclerView, binding!!.bar)
    }

    private fun showDialogAboutApps() {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_main_info)
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val lp: WindowManager.LayoutParams = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerView)
        mainInfoViewModel = ViewModelProvider(this)[MainInfoViewModel::class.java]
        //recyclerView.setHasFixedSize(true)
        adapterInfo = MainInfoAdapter(context)
        recyclerView.adapter = adapterInfo
        mainInfoViewModel!!.dataMainInfo.observe(this) { mainInfoArrayList: ArrayList<MainInfo>? ->
            adapterInfo!!.addList(mainInfoArrayList)
        }
        mainInfoViewModel!!.getInfoItems()

        dialog.show()
        dialog.window!!.attributes = lp
    }

    // Color Mode ----------------------------- Start
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "color_option") {
            recreate()
        }
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
    // Color Mode ----------------------------- End
}