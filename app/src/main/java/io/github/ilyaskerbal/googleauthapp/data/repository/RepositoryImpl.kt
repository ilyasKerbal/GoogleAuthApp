package io.github.ilyaskerbal.googleauthapp.data.repository

import io.github.ilyaskerbal.googleauthapp.domain.repository.DataStoreOperations
import io.github.ilyaskerbal.googleauthapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations
) : Repository {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSingedInState(signedIn)
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignedInstate()
    }
}