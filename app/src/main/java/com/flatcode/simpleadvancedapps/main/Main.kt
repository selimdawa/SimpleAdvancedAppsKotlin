package com.flatcode.simpleadvancedapps.main

import android.app.Activity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Main(
    var image: Int = 0,
    var title: String? = null,
    var number: Int = 0,
    var c: @RawValue Class<out Activity>? = null
) : Parcelable