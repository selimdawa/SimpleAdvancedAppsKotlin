package com.flatcode.simpleadvancedapps.Unit

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.flatcode.simpleadvancedapps.R
import jp.wasabeef.glide.transformations.BlurTransformation
import java.io.Serializable

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

    fun IntentSerializable(context: Context, c: Class<*>?, key: String?, value: Serializable?) {
        val intent = Intent(context, c)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }

    /*fun IntentExtraChannel(context: Context, c: Class<*>?, key: String?, value: Category?) {
        val intent = Intent(context, c)
        intent.putExtra("categoryName", DATA.EMPTY)
        intent.putExtra(key, value)
        context.startActivity(intent)
    }*/

    fun IntentExtra2(
        context: Context,
        c: Class<*>?,
        key: String?,
        value: String?,
        key2: String?,
        value2: String?,
    ) {
        val intent = Intent(context, c)
        intent.putExtra(key, value)
        intent.putExtra(key2, value2)
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
        context: Context?, Url: String?, Image: ImageView,
        ImageBlur: ImageView, level: Int,
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

    fun Intro(context: Context?, background: ImageView, backWhite: ImageView, backDark: ImageView) {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context!!)
        if (sharedPreferences.getString("color_option", "ONE") == "ONE") {
            background.setImageResource(R.drawable.background_day)
            backWhite.visibility = View.VISIBLE
            backDark.visibility = View.GONE
        } else if (sharedPreferences.getString("color_option", "NIGHT_ONE") == "NIGHT_ONE") {
            background.setImageResource(R.drawable.background_night)
            backWhite.visibility = View.GONE
            backDark.visibility = View.VISIBLE
        }
    }

    fun Logo(context: Context?, background: ImageView) {
        val sharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context!!)
        if (sharedPreferences.getString("color_option", "ONE") == "ONE") {
            background.setImageResource(R.drawable.logo)
        } else if (sharedPreferences.getString("color_option", "NIGHT_ONE") == "NIGHT_ONE") {
            background.setImageResource(R.drawable.logo_night)
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
}