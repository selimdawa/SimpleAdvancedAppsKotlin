package com.flatcode.simpleadvancedapps.main

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainBinding
import com.flatcode.simpleadvancedapps.utils.dataStore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var mainViewModel: MainViewModel? = null
    private var mainInfoViewModel: MainInfoViewModel? = null

    private var adapter: MainAdapter? = null
    private var adapterInfo: MainInfoAdapter? = null

    private val themeKey = stringPreferencesKey("color_option")
    private var initialized = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            dataStore.data.map { it[themeKey] ?: "ONE" }.collectLatest {
                if (initialized) binding.root.post { recreate() } else initialized = true
            }
        }

        binding.toolbar.settings.setOnClickListener {
            val entries = resources.getStringArray(R.array.reply_entries)
            val values = resources.getStringArray(R.array.reply_values)
            AlertDialog.Builder(this).setTitle("Select Theme").setItems(entries) { _, which ->
                    lifecycleScope.launch {
                        dataStore.edit { prefs -> prefs[themeKey] = values[which] }
                    }
                }.show()
        }

        binding.toolbar.info.setOnClickListener { showDialogAboutApps() }

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapter = MainAdapter(this)
        binding.recyclerView.adapter = adapter

        mainViewModel?.dataMain?.observe(this) { mainList ->
            adapter?.addList(mainList as? ArrayList<Main> ?: ArrayList(mainList.orEmpty()))
        }
        mainViewModel?.getItems(binding.recyclerView, binding.bar)
    }

    private fun showDialogAboutApps() {
        val dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_main_info)
            setCancelable(true)
            window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        }

        val lp = WindowManager.LayoutParams().apply {
            copyFrom(dialog.window?.attributes)
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }

        val recyclerView = dialog.findViewById<RecyclerView>(R.id.recyclerView)
        mainInfoViewModel = ViewModelProvider(this)[MainInfoViewModel::class.java]
        adapterInfo = MainInfoAdapter(this)
        recyclerView.adapter = adapterInfo

        mainInfoViewModel?.dataMainInfo?.observe(this) { mainInfoList ->
            adapterInfo?.submitList(mainInfoList)
        }
        mainInfoViewModel?.getInfoItems()

        dialog.show()
        dialog.window?.attributes = lp
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}