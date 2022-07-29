package io.github.ilyaskerbal.googleauthapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.ilyaskerbal.googleauthapp.data.repository.DataStoreOperationsImpl
import io.github.ilyaskerbal.googleauthapp.data.repository.RepositoryImpl
import io.github.ilyaskerbal.googleauthapp.domain.repository.DataStoreOperations
import io.github.ilyaskerbal.googleauthapp.domain.repository.Repository
import io.github.ilyaskerbal.googleauthapp.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStorePreferences(
        @ApplicationContext context: Context
    ) : DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(Constants.PREFERENCES_NAME)
            }
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        dataStore: DataStore<Preferences>
    ) : DataStoreOperations {
        return DataStoreOperationsImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideRepository(
        dataStoreOperations: DataStoreOperations
    ) : Repository {
        return RepositoryImpl(dataStoreOperations = dataStoreOperations)
    }
}