package com.example.amatiberkah.model.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.amatiberkah.model.remote.response.LoginResponseDataUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getUserToken(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[TOKEN_KEY]
        }
    }



    suspend fun saveUserToken(token: String) {
        dataStore.edit { it[TOKEN_KEY] = token }
    }

    suspend fun saveUserId(id: String) {
        dataStore.edit { it[USER_ID] = id }
    }



    suspend fun saveUserName(name: String) {
        dataStore.edit { it[NAME] = name }
    }

    suspend fun saveUserEmail(email: String) {
        dataStore.edit { it[EMAIL] = email }
    }

    fun getUserId(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[USER_ID]
        }
    }

    fun getUserDataEmail(): Flow<String?> {
        return dataStore.data.map { preference ->
            preference[EMAIL]
        }
    }

    fun getUserDataName(): Flow<String?> {
        return dataStore.data.map { preference ->
            preference[NAME]
        }
    }

    suspend fun clearUserToken() {
        dataStore.edit { it.remove(TOKEN_KEY) }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val NAME = stringPreferencesKey("name")
        private val EMAIL = stringPreferencesKey("email")
        private val USER_ID = stringPreferencesKey("user_id")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}