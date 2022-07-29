package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = { LoginTopBar() },
        content = {
            LoginContent(
                singedInState = false,
                messageBarState = MessageBarState(),
                onButtonClicked = {}
            )
        }
    )
}