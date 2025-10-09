package com.example.envobytenote.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("envobyte_prefs")



object PreferencesKeys {
    val FIRST_LAUNCH_DONE = booleanPreferencesKey("first_launch_done")
}


class ThemePreference(private val context: Context) {

    companion object {
        private val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data
        .map { prefs -> prefs[IS_DARK_THEME] ?: false } // default: Light mode

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_DARK_THEME] = isDark
        }
    }
}