package com.krm0219.mooda.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PrefSettingManager(val context: Context) {

    private val dataStore = context.createDataStore("settings")


    val TEST_COUNTER = intPreferencesKey("test_counter")

    val testCounterFlow: Flow<Int> = dataStore.data
        .map { preferences ->

            preferences[TEST_COUNTER] ?: 0
        }


    suspend fun incrementCounter() {

        dataStore.edit { settings ->

            val cureentValue = settings[TEST_COUNTER] ?: 0
            settings[TEST_COUNTER] = cureentValue + 1

        }
    }
}