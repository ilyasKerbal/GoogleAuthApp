package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import android.app.Activity
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.presentation.screen.common.StartActivityForResult
import io.github.ilyaskerbal.googleauthapp.presentation.screen.common.signIn

@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val signedInState by loginViewModel.signedInSate
    val messageBarState by loginViewModel.messageBarState

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
}