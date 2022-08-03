package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.utils.RequestState

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val apiResponse by viewModel.apiResponse
    val messageBarState by viewModel.messageBarState

    val user by viewModel.user
    val firstName by viewModel.firstName
    val lastName by viewModel.lastName


    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {
                         viewModel.updateUserInfo()
                },
                onDeleteAll = {}
            )
        },
        content = {
            ProfileContent(
                apiResponse = apiResponse,
                messageBarState = messageBarState,
                firstName = firstName,
                onFirstNameChanged = {
                     viewModel.updateFirstName(it)
                },
                lastName = lastName,
                onLastNameChanged = {
                    viewModel.updateLastName(it)
                },
                emailAddress = user?.email,
                profilePicture = user?.profilePhoto
            ) {

            }
        }
    )
}