package io.github.ilyaskerbal.googleauthapp.data.remote

import io.github.ilyaskerbal.googleauthapp.domain.model.ApiRequest
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.UserUpdateRequest
import retrofit2.http.*

interface KtorApi {

    @POST(value = "/token_verification")
    suspend fun verifyTokenOnBackend(
        @Body request: ApiRequest
    ) : ApiResponse

    @GET(value = "/get_user")
    suspend fun getUserInfo(): ApiResponse

    @PUT(value = "/update_user")
    suspend fun updateUserInfo(
        @Body request: UserUpdateRequest
    ): ApiResponse

    @DELETE(value = "/delete_user")
    suspend fun deleteUser(): ApiResponse

    @GET(value = "/sign_out")
    suspend fun signOut(): ApiResponse
}