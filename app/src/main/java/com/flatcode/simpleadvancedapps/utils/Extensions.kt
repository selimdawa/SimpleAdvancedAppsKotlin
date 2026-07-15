package com.flatcode.simpleadvancedapps.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.createBitmap
import androidx.core.graphics.scale
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.size.Size
import coil.transform.Transformation
import com.flatcode.simpleadvancedapps.R

fun Context.openActivity(activityClass: Class<out Activity>, finish: Boolean = false) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
    if (finish && this is Activity) {
        this.finish()
    }
}

fun ImageView.loadImage(url: String) {
    this.load(url) {
        crossfade(true)
        placeholder(R.color.image_profile)
    }
}

fun ImageView.loadImageWithBlur(
    url: String?, blurUrl: String? = null, blurLevel: Int = 25, blurTarget: ImageView? = null
) {
    load(url) {
        placeholder(R.color.image_profile)
        error(R.color.image_profile)
    }
    blurTarget?.load(blurUrl ?: url) {
        placeholder(R.color.image_profile)
        error(R.color.image_profile)
        if (blurLevel > 0) {
            transformations(SimpleBlurTransformation(blurLevel.toFloat()))
        }
    }
}

class SimpleBlurTransformation(private val radius: Float) : Transformation {
    override val cacheKey: String = "${SimpleBlurTransformation::class.java.name}-$radius"

    override suspend fun transform(input: Bitmap, size: Size): Bitmap {
        val scaleFactor = 4
        val w = (input.width / scaleFactor).coerceAtLeast(1)
        val h = (input.height / scaleFactor).coerceAtLeast(1)
        val small = input.scale(w, h, true)
        val r = (radius / scaleFactor).toInt().coerceAtLeast(1)
        val pix = IntArray(w * h)
        small.getPixels(pix, 0, w, 0, 0, w, h)
        val blurred = IntArray(w * h)
        for (y in 0 until h) for (x in 0 until w) {
            var rs = 0
            var gs = 0
            var bs = 0
            var c = 0
            for (i in -r..r) {
                val p = pix[y * w + (x + i).coerceIn(0, w - 1)]
                rs += (p shr 16) and 0xff
                gs += (p shr 8) and 0xff
                bs += p and 0xff
                c++
            }
            blurred[y * w + x] = (0xff shl 24) or (rs / c shl 16) or (gs / c shl 8) or (bs / c)
        }
        for (x in 0 until w) for (y in 0 until h) {
            var rs = 0
            var gs = 0
            var bs = 0
            var c = 0
            for (i in -r..r) {
                val p = blurred[(y + i).coerceIn(0, h - 1) * w + x]
                rs += (p shr 16) and 0xff
                gs += (p shr 8) and 0xff
                bs += p and 0xff
                c++
            }
            pix[y * w + x] = (0xff shl 24) or (rs / c shl 16) or (gs / c shl 8) or (bs / c)
        }
        val output = createBitmap(w, h, Bitmap.Config.ARGB_8888)
        output.setPixels(pix, 0, w, 0, 0, w, h)
        return output.scale(input.width, input.height, true)
    }
}

fun ImageView.downloadFromUrl(
    blur: Boolean?, url: String?, progressDrawable: CircularProgressDrawable,
) {
    load(url) {
        placeholder(progressDrawable)
        error(R.drawable.ic_connection_error)
        if (blur == true) {
            transformations(SimpleBlurTransformation(50f))
        }
    }
}

fun placeholderProgressBar(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}