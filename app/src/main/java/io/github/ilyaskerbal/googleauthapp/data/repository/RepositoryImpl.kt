package io.github.ilyaskerbal.googleauthapp.data.repository

import io.github.ilyaskerbal.googleauthapp.data.remote.KtorApi
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiRequest
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.UserUpdateRequest
import io.github.ilyaskerbal.googleauthapp.domain.repository.DataStoreOperations
import io.github.ilyaskerbal.googleauthapp.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val dataStoreOperations: DataStoreOperations,
    private val ktorApi: KtorApi
) : Repository {
    override suspend fun saveSignedInState(signedIn: Boolean) {
        dataStoreOperations.saveSingedInState(signedIn)
    }

    override fun readSignedInState(): Flow<Boolean> {
        return dataStoreOperations.readSignedInstate()
    }

    override suspend fun verifyTokenOnBackend(request: ApiRequest): ApiResponse {
        return try {
            ktorApi.verifyTokenOnBackend(request = request)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun getUserInfo(): ApiResponse {
        return try {
            ktorApi.getUserInfo()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun updateUserInfo(request: UserUpdateRequest): ApiResponse {
        return try {
            ktorApi.updateUserInfo(request = request)
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun deleteUser(): ApiResponse {
        return try {
            ktorApi.deleteUser()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }

    override suspend fun signOut(): ApiResponse {
        return try {
            ktorApi.signOut()
        } catch (e: Exception) {
            ApiResponse(success = false, error = e)
        }
    }
}