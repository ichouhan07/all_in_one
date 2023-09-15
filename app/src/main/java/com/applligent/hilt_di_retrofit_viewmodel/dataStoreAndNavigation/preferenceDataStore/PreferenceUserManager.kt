package com.applligent.hilt_di_retrofit_viewmodel.dataStoreAndNavigation.preferenceDataStore

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferenceUserManager(context: Context) {
    private val dataStore = context.createDataStore(name = "user_prefs")
    companion object {
        val USER_AGE_KEY = preferencesKey<Int>("USER_AGE")
        val USER_NAME_KEY = preferencesKey<String>("USER_NAME")
    }
    suspend fun storeUser(age: Int, name: String) {
        dataStore.edit {
            it[USER_AGE_KEY] = age
            it[USER_NAME_KEY] = name
        }
    }
    val userAgeFlow: Flow<Int> = dataStore.data.map {
        it[USER_AGE_KEY] ?: 0
    }

    // Create a name flow to retrieve name from the preferences
    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USER_NAME_KEY] ?: ""
    }
}