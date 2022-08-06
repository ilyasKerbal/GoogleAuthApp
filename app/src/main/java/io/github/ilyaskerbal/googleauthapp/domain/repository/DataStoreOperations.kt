package io.github.ilyaskerbal.googleauthapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveSingedInState(signedInSate: Boolean) : Unit
    fun readSignedInstate() : Flow<Boolean>
}