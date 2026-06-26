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

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private var mainViewModel: MainViewModel? = null
    private var mainInfoViewModel: MainInfoViewModel? = null
    private var adapter: MainAdapter? = null
    private var adapterInfo: MainInfoAdapter? = null
    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferenceManager.getDefaultSharedPreferences(baseContext)
            .registerOnSharedPreferenceChangeListener(this)
        THEME.setThemeOfApp(context)
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

        binding.toolbar.info.setOnClickListener { showDialogAboutApps() }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = MainAdapter(context)
        binding.recyclerView.adapter = adapter

        mainViewModel?.dataMain?.observe(this) { mainList ->
            adapter?.addList(mainList as? ArrayList<Main> ?: ArrayList(mainList.orEmpty()))
        }
        mainViewModel?.getItems(binding.recyclerView, binding.bar)
    }

    private fun showDialogAboutApps() {
        val dialog = Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_main_info)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        val lp = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerView)
        mainInfoViewModel = ViewModelProvider(this)[MainInfoViewModel::class.java]
        adapterInfo = MainInfoAdapter(context)
        recyclerView.adapter = adapterInfo

        mainInfoViewModel?.dataMainInfo?.observe(this) { mainInfoList ->
            adapterInfo?.submitList(mainInfoList)
        }
        mainInfoViewModel?.getInfoItems()

        dialog.show()
        dialog.window?.attributes = lp
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "color_option") {
            recreate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(baseContext)
            .unregisterOnSharedPreferenceChangeListener(this)
        _binding = null
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }
    }
}