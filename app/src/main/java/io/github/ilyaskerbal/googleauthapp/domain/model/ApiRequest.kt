package io.github.ilyaskerbal.googleauthapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val tokenId: String
)