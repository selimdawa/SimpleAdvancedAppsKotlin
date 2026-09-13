package com.flatcode.simpleadvancedapps.crypto.utils

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil3.asImage
import coil3.load
import coil3.request.crossfade

fun ImageView.loadImage(url: String?) {
    val placeholder = createPlaceHolder(this.context)
    this.load(url) {
        crossfade(enable = true)
        crossfade(durationMillis = 500)
        placeholder(placeholder.asImage())
    }
}

private fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 12f
        centerRadius = 40f
        start()
    }
}