package com.flatcode.simpleadvancedapps.weather.utils

import android.content.pm.PackageManager
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.isPermissionGranted(p: String): Boolean =
    ContextCompat.checkSelfPermission(requireContext(), p) == PackageManager.PERMISSION_GRANTED

fun View.startRotation() {
    val rotate = RotateAnimation(
        0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
    ).apply {
        duration = 1000
        repeatCount = Animation.INFINITE
        interpolator = LinearInterpolator()
    }
    startAnimation(rotate)
}

fun View.stopRotation() {
    clearAnimation()
}