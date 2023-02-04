package com.flatcode.simpleadvancedapps.countries.Util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flatcode.simpleadvancedapps.R
import com.flatcode.simpleadvancedapps.Unit.VOID

fun ImageView.downloadFromUrl(
    blur: Boolean?,
    url: String?,
    progressDrawable: CircularProgressDrawable,
) {
    if (blur!!) {
        VOID.GlideBlur(context, url!!, this, 50)
    } else {
        val options = RequestOptions()
            .placeholder(progressDrawable)
            .error(R.drawable.ic_connection_error)

        Glide.with(context)
            .setDefaultRequestOptions(options)
            .load(url)
            .into(this)
    }
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}