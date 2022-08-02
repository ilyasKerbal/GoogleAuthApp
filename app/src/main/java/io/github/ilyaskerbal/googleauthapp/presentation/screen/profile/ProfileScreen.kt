package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {},
                onDeleteAll = {}
            )
        },
        content = {

        }
    )
}