package com.flatcode.simpleadvancedapps.meals.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryList(val categories: List<Category>) : Parcelable