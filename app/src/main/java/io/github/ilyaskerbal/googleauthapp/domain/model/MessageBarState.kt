package io.github.ilyaskerbal.googleauthapp.domain.model

data class MessageBarState(
    val message : String? = null,
    val error: Exception? = null
)
