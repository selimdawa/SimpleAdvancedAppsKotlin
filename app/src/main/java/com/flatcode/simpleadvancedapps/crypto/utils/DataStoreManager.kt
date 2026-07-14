package com.flatcode.simpleadvancedapps.crypto.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "navigation_prefs")

@Singleton
class DataStoreManager @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        val LAST_COIN_ID = intPreferencesKey("last_coin_id")
        val LAST_COIN_SYMBOL = stringPreferencesKey("last_coin_symbol")
    }

    suspend fun saveLastNav(id: Int, symbol: String) {
        context.dataStore.edit { prefs ->
            prefs[LAST_COIN_ID] = id
            prefs[LAST_COIN_SYMBOL] = symbol
        }
    }

    val lastCoinId: Flow<Int?> = context.dataStore.data.map { prefs ->
        prefs[LAST_COIN_ID]
    }

    val lastCoinSymbol: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[LAST_COIN_SYMBOL]
    }
}
