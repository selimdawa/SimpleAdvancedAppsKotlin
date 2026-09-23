package com.flatcode.simpleadvancedapps.meals.pojo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MealList(val meals: List<Meal>) : Parcelable