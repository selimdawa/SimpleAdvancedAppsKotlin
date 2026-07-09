package com.flatcode.simpleadvancedapps.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.flatcode.simpleadvancedapps.R
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

fun Context.applyAppTheme() {
    val option = runBlocking {
        dataStore.data.map { it[stringPreferencesKey("color_option")] ?: "ONE" }.first()
    }
    setTheme(when (option) {
        "TWO" -> R.style.Base_Theme_TwoTheme
        "THREE" -> R.style.Base_Theme_ThreeTheme
        "FOUR" -> R.style.Base_Theme_FourTheme
        "FIVE" -> R.style.Base_Theme_FiveTheme
        "SIX" -> R.style.Base_Theme_SixTheme
        "SEVEN" -> R.style.Base_Theme_SevenTheme
        "EIGHT" -> R.style.Base_Theme_EightTheme
        "NINE" -> R.style.Base_Theme_NineTheme
        "GRADUAL_ONE" -> R.style.Base_Theme_GradientOneTheme
        "GRADUAL_TWO" -> R.style.Base_Theme_GradientTwoTheme
        "GRADUAL_THREE" -> R.style.Base_Theme_GradientThreeTheme
        "GRADUAL_FOUR" -> R.style.Base_Theme_GradientFourTheme
        "GRADUAL_FIVE" -> R.style.Base_Theme_GradientFiveTheme
        "GRADUAL_SIX" -> R.style.Base_Theme_GradientSixTheme
        "GRADUAL_SEVEN" -> R.style.Base_Theme_GradientSevenTheme
        else -> R.style.Base_Theme_OneTheme
    })
}
