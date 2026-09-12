package com.ekoehler.expressivecutout.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "island_persistence")

object PersistencePreferences {
    val KEEP_NOTIFICATIONS_PERSISTENT = booleanPreferencesKey("keep_notifications_persistent")
    val KEEP_MEDIA_PLAYER_PERSISTENT = booleanPreferencesKey("keep_media_player_persistent")
    val AUTO_DISMISS_TIMEOUT_MS = intPreferencesKey("auto_dismiss_timeout_ms")
    val ENABLE_ANIMATED_PROGRESS_BAR = booleanPreferencesKey("enable_animated_progress_bar")
}

class PersistenceSettingsRepository(private val context: Context) {
    private val dataStore = context.dataStore

    val keepNotificationsPersistent: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PersistencePreferences.KEEP_NOTIFICATIONS_PERSISTENT] ?: false
    }

    val keepMediaPlayerPersistent: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PersistencePreferences.KEEP_MEDIA_PLAYER_PERSISTENT] ?: false
    }

    val autoDismissTimeoutMs: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PersistencePreferences.AUTO_DISMISS_TIMEOUT_MS] ?: 5000 // Default 5 seconds
    }

    val enableAnimatedProgressBar: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[PersistencePreferences.ENABLE_ANIMATED_PROGRESS_BAR] ?: true
    }

    suspend fun setKeepNotificationsPersistent(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PersistencePreferences.KEEP_NOTIFICATIONS_PERSISTENT] = enabled
        }
    }

    suspend fun setKeepMediaPlayerPersistent(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PersistencePreferences.KEEP_MEDIA_PLAYER_PERSISTENT] = enabled
        }
    }

    suspend fun setAutoDismissTimeoutMs(timeoutMs: Int) {
        dataStore.edit { preferences ->
            preferences[PersistencePreferences.AUTO_DISMISS_TIMEOUT_MS] = timeoutMs
        }
    }

    suspend fun setEnableAnimatedProgressBar(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[PersistencePreferences.ENABLE_ANIMATED_PROGRESS_BAR] = enabled
        }
    }
}
