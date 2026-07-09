package com.flatcode.simpleadvancedapps.utils

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flatcode.simpleadvancedapps.R
import jp.wasabeef.glide.transformations.BlurTransformation

object VOID {
    fun IntentClear(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }

    fun Intent1(context: Context, c: Class<*>?) {
        val intent = Intent(context, c)
        context.startActivity(intent)
    }

    fun IntentExtra(context: Context, c: Class<*>?, key: String?, value: String?) {
        val intent = Intent(context, c)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    fun Glide(context: Context?, Url: String?, Image: ImageView) {
        try {
            Glide.with(context!!).load(Url).placeholder(R.color.image_profile).into(Image)
        } catch (e: Exception) {
            Image.setImageResource(R.color.image_profile)
        }
    }

    fun GlideRickAndMorty(
        context: Context?, Url: String?, Image: ImageView, ImageBlur: ImageView, level: Int,
    ) {
        try {
            Glide.with(context!!).load(Url).into(Image)
            Glide.with(context).load(Url)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(level))).into(ImageBlur)
        } catch (e: Exception) {
            Image.setImageResource(R.color.image_profile)
            ImageBlur.setImageResource(R.color.image_profile)
        }
    }

    fun GlideBlur(context: Context?, Url: String, Image: ImageView, level: Int) {
        try {
            Glide.with(context!!).load(Url).placeholder(R.color.image_profile)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(level))).into(Image)
        } catch (e: Exception) {
            Image.setImageResource(R.color.image_profile)
        }
    }

    fun ImageView.downloadFromUrl(
        blur: Boolean?, url: String?, progressDrawable: CircularProgressDrawable,
    ) {
        if (blur!!) {
            GlideBlur(context, url!!, this, 50)
        } else {
            val options =
                RequestOptions().placeholder(progressDrawable).error(R.drawable.ic_connection_error)

            Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
        }
    }

    fun placeholderProgressBar(context: Context): CircularProgressDrawable {
        return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        }
    }
}