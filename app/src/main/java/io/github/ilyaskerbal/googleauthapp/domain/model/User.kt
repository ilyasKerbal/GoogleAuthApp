package io.github.ilyaskerbal.googleauthapp.domain.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @Contextual
    val _id: String?,
    val googleId: String,
    val name: String,
    val email: String,
    val profilePhoto: String
)