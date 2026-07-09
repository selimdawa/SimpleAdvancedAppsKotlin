package com.flatcode.simpleadvancedapps.countries.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.countriesDataStore: DataStore<Preferences> by preferencesDataStore(name = "countries_settings")

class CustomDataStore private constructor(private val context: Context) {

    private val preferencesTimeKey = longPreferencesKey("preferences_time")

    suspend fun saveTime(time: Long) {
        context.countriesDataStore.edit { preferences -> preferences[preferencesTimeKey] = time }
    }

    fun getTimeFlow(): Flow<Long> = context.countriesDataStore.data.catch { exception ->
        if (exception is IOException) {
            emit(emptyPreferences())
        } else {
            throw exception
        }
    }.map { preferences -> preferences[preferencesTimeKey] ?: 0L }

    suspend fun getTimeSync(): Long = getTimeFlow().first()

    companion object {
        @Volatile
        private var instance: CustomDataStore? = null
        private val lock = Any()

        operator fun invoke(context: Context): CustomDataStore = instance ?: synchronized(lock) {
            instance ?: CustomDataStore(context.applicationContext).also {
                instance = it
            }
        }
    }
}