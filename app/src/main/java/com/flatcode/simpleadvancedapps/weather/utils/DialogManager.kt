package com.flatcode.simpleadvancedapps.weather.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toDrawable
import com.flatcode.simpleadvancedapps.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

object DialogManager {

    fun locationSettingsDialog(context: Context, onPositiveClick: () -> Unit) {
        AlertDialog.Builder(context).apply {
            setTitle("Enable location?")
            setMessage("Location disabled, do you want enable location?")
            setPositiveButton("OK") { _, _ ->
                onPositiveClick()
            }
            setNegativeButton("Cancel", null)
        }.show()
    }

    fun searchByNameDialog(context: Context, onSearchClick: (String) -> Unit) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_search, null)
        val dialog = AlertDialog.Builder(context).apply {
            setView(view)
        }.create()
        dialog.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        val edCity = view.findViewById<TextInputEditText>(R.id.edCity)
        val btnOk = view.findViewById<MaterialButton>(R.id.btnOk)
        val btnCancel = view.findViewById<MaterialButton>(R.id.btnCancel)

        btnOk.setOnClickListener {
            val name = edCity.text.toString()
            if (name.isNotEmpty()) {
                onSearchClick(name)
                dialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}