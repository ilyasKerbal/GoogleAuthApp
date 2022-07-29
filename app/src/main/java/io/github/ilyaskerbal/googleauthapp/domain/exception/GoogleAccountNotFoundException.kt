package io.github.ilyaskerbal.googleauthapp.domain.exception

class GoogleAccountNotFoundException(
    override val message: String? = "There is no account on this phone. "
) : Exception()