package com.example.harry_potter_app.local.storage


import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "HARRY_POTTER_DATA_STORE")

object PreferencesKeys {
    val FAVORITE_CHARACTER_INDEXES = stringPreferencesKey("favorite_character_indexes")
}

suspend fun <T> saveToDataStore(context: Context, value: T, key: Preferences.Key<T>) {
    context.dataStore.edit { preferences ->
        preferences[key] = value
    }
}

fun <T> getFromDataStore(context: Context, key: Preferences.Key<T>): Flow<T?> {
    return context.dataStore.data
        .map { preferences ->
            preferences[key]
        }
}