package com.flatcode.simpleadvancedapps.main

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private var mainViewModel: MainViewModel? = null
    private var mainInfoViewModel: MainInfoViewModel? = null

    private var adapter: MainAdapter? = null
    private var adapterInfo: MainInfoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbarMargin = (binding.toolbar.card.layoutParams as ViewGroup.MarginLayoutParams).topMargin
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.toolbar.card.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = systemBars.top + toolbarMargin
            }
            binding.recyclerView.updatePadding(
                bottom = systemBars.bottom
            )
            insets
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