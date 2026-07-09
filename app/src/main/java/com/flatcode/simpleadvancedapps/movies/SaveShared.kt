package com.flatcode.simpleadvancedapps.movies

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "movie_favorites")

class SaveShared {

    companion object {
        suspend fun setFavorite(context: Context, key: String, value: Boolean) {
            val dataStoreKey = booleanPreferencesKey(key)
            context.dataStore.edit { preferences ->
                preferences[dataStoreKey] = value
            }
        }

        suspend fun getFavorite(context: Context, key: String): Boolean {
            val dataStoreKey = booleanPreferencesKey(key)
            return context.dataStore.data.catch { exception ->
                    if (exception is IOException) {
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }.map { preferences ->
                    preferences[dataStoreKey] ?: false
                }.first()
        }
    }
}