package io.github.ilyaskerbal.googleauthapp.domain.repository

import io.github.ilyaskerbal.googleauthapp.domain.model.ApiRequest
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.UserUpdateRequest
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun saveSignedInState(signedIn: Boolean)
    fun readSignedInState(): Flow<Boolean>
    suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse
    suspend fun getUserInfo(): ApiResponse
    suspend fun updateUserInfo(request: UserUpdateRequest): ApiResponse
    suspend fun deleteUser(): ApiResponse
    suspend fun signOut(): ApiResponse
}