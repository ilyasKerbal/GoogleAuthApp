package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import android.app.Activity
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.identity.Identity
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.navigation.Screen
import io.github.ilyaskerbal.googleauthapp.utils.RequestState

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val apiResponse by viewModel.apiResponse
    val messageBarState by viewModel.messageBarState
    val clearSessionResponse by viewModel.clearSessionResponse

    val user by viewModel.user
    val firstName by viewModel.firstName
    val lastName by viewModel.lastName


    Scaffold(
        topBar = {
            ProfileTopBar(
                onSave = {
                         viewModel.updateUserInfo()
                },
                onDeleteAll = {
                    viewModel.deleteUser()
                }
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
                profilePicture = user?.profilePhoto,
                onSignOutClicked = {
                    viewModel.clearSession()
                }
            )
        }
    )

    val activity = LocalContext.current as Activity

    LaunchedEffect(key1 = clearSessionResponse) {
        if (clearSessionResponse is RequestState.Success &&
            (clearSessionResponse as RequestState.Success<ApiResponse>).data.success
        ) {
            val oneTapClient = Identity.getSignInClient(activity)
            oneTapClient.signOut()
            viewModel.saveSignedInState(signedIn = false)
            navigateToLoginScreen(navController = navController)
        }
    }
}

private fun navigateToLoginScreen(
    navController: NavHostController
) {
    navController.navigate(route = Screen.Login.route) {
        popUpTo(route = Screen.Profile.route) {
            inclusive = true
        }
    }
}