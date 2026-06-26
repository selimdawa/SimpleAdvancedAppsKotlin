package com.flatcode.simpleadvancedapps.movies

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SaveShared {

    companion object {
        fun setFavorite(context: Context, key: String, value: Boolean) {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putBoolean(key, value)
            }
        }

        fun getFavorite(context: Context, key: String): Boolean {
            return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false)
        }
    }
}