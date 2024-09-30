package com.example.harry_potter_app.local.storage


import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "HARRY_POTTER_DATA_STORE")
