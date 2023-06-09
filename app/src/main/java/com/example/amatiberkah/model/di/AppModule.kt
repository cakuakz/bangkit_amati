package com.example.amatiberkah.model.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.amatiberkah.model.local.UserPreferences
import com.example.amatiberkah.model.remote.api.ApiConfig
import com.example.amatiberkah.model.remote.api.ApiServiceAuth
import com.example.amatiberkah.model.remote.api.ApiServiceMasterData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "application")
@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideApiServiceAuth(): ApiServiceAuth {
        return ApiConfig.getApiServiceAuth()
    }

    @Provides
    @Singleton
    fun provideApiServiceMasterData(): ApiServiceMasterData {
        return ApiConfig.getApiServiceMasterData()
    }


    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideUserPreferences(dataStore: DataStore<Preferences>): UserPreferences {
        return UserPreferences.getInstance(dataStore)
    }
}