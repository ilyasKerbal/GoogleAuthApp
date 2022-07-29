package io.github.ilyaskerbal.googleauthapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import io.github.ilyaskerbal.googleauthapp.domain.repository.DataStoreOperations
import io.github.ilyaskerbal.googleauthapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class DataStoreOperationsImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreOperations {

    private object PreferencesKey {
        val signedInKey = booleanPreferencesKey(Constants.PREFERENCES_SIGNED_IN_KEY)
    }

    override suspend fun saveSingedInState(signedInSate: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.signedInKey] = signedInSate
        }
    }

    override fun readSignedInstate(): Flow<Boolean> {
        return dataStore.data.catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val signedInState = preferences[PreferencesKey.signedInKey] ?: false
            signedInState
        }
    }
}