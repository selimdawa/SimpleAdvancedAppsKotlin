package com.flatcode.simpleadvancedapps.crypto.ui.home

import android.widget.ImageView
import android.widget.TextView
import com.flatcode.simpleadvancedapps.crypto.model.home.Data

interface ItemClickListener {
    fun onItemClick(coin: Data, ivRowImage: ImageView, tvRowTitle: TextView, tvRowSymbol: TextView)
}