package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import android.app.Activity
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiRequest
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.navigation.Screen
import io.github.ilyaskerbal.googleauthapp.presentation.screen.common.StartActivityForResult
import io.github.ilyaskerbal.googleauthapp.presentation.screen.common.signIn
import io.github.ilyaskerbal.googleauthapp.utils.RequestState

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val signedInState by loginViewModel.signedInSate
    val messageBarState by loginViewModel.messageBarState
    val apiResponse by loginViewModel.apiResponse

    Scaffold(
        topBar = { LoginTopBar() },
        content = {
            LoginContent(
                singedInState = signedInState,
                messageBarState = messageBarState,
                onButtonClicked = {
                    loginViewModel.saveSignedInstate(true)
                }
            )
        }
    )

    val activity = LocalContext.current as Activity

    StartActivityForResult(
        key = signedInState,
        onResultReceived = { tokenID ->
            // https://oauth2.googleapis.com/tokeninfo?id_token=##
            Log.i("P/SOME", tokenID)
            loginViewModel.verifyTokenOnBackend(
                request = ApiRequest(
                    tokenId = tokenID
                )
            )
        },
        onDialogDismissed = {
            loginViewModel.saveSignedInstate(false)
        },
    ) {
        if (signedInState) {
            signIn(
                activity = activity,
                launchActivityResult = { intentSenderRequest ->
                    it.launch(intentSenderRequest)
                },
                accountNotFound = {
                    loginViewModel.saveSignedInstate(false)
                    loginViewModel.updateMessageBarState()
                }
            )
        }
    }

    LaunchedEffect(key1 = apiResponse) {
        when(apiResponse) {
            is RequestState.Success -> {
                val response = (apiResponse as RequestState.Success<ApiResponse>).data.success
                if (response) {
                    navigateToProfileScreen(navController = navController)
                }  else {
                    loginViewModel.saveSignedInstate(false)
                }
            }
            else -> {}
        }
    }
}

private fun navigateToProfileScreen(
    navController: NavHostController
) {
    navController.navigate(Screen.Profile.route) {
        popUpTo(route = Screen.Login.route) {
            inclusive = true
        }
    }
}