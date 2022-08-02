package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.utils.RequestState

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
            ProfileContent(
                apiResponse = RequestState.Success(data = ApiResponse(success = true)),
                messageBarState = MessageBarState(),
                firstName = "",
                onFirstNameChanged = {},
                lastName = "",
                onLastNameChanged = {},
                emailAddress = "",
                profilePicture = ""
            ) {

            }
        }
    )
}