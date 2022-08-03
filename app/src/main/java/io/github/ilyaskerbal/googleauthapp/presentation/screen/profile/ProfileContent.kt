package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import io.github.ilyaskerbal.googleauthapp.R
import io.github.ilyaskerbal.googleauthapp.components.AuthButton
import io.github.ilyaskerbal.googleauthapp.components.MessageBar
import io.github.ilyaskerbal.googleauthapp.domain.model.ApiResponse
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.ui.theme.BlueColor
import io.github.ilyaskerbal.googleauthapp.utils.RequestState

@Composable
fun ProfileContent(
    apiResponse: RequestState<ApiResponse>,
    messageBarState: MessageBarState,
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    emailAddress: String?,
    profilePicture: String?,
    onSignOutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(modifier = Modifier.weight(1f)) {
            if (apiResponse is RequestState.Loading) {
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth(),
                    color = BlueColor
                )
            } else {
                MessageBar(messageBarState = messageBarState)
            }
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CentralContent(
                firstName = firstName,
                onFirstNameChanged = onFirstNameChanged,
                lastName = lastName,
                onLastNameChanged = onLastNameChanged,
                emailAddress = emailAddress,
                profilePhoto = profilePicture,
                onSignOutClicked = onSignOutClicked
            )
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CentralContent(
    firstName: String,
    onFirstNameChanged: (String) -> Unit,
    lastName: String,
    onLastNameChanged: (String) -> Unit,
    emailAddress: String?,
    profilePhoto: String?,
    onSignOutClicked: () -> Unit
) {
    val painter = rememberImagePainter(data = profilePhoto) {
        crossfade(1000)
        placeholder(R.drawable.ic_placeholder)
    }
    Image(
        modifier = Modifier.padding(bottom = 40.dp)
            .size(150.dp)
            .clip(CircleShape),
        painter = painter,
        contentDescription = "Profile picture")
    OutlinedTextField(
        value = firstName,
        onValueChange = { onFirstNameChanged(it) },
        label = { Text(text = "First Name") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
    OutlinedTextField(
        value = lastName,
        onValueChange = { onLastNameChanged(it) },
        label = { Text(text = "Last Name") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true
    )
    OutlinedTextField(
        value = emailAddress.toString(),
        onValueChange = { },
        label = { Text(text = "Email Address") },
        textStyle = MaterialTheme.typography.body1,
        singleLine = true,
        enabled = false
    )
    AuthButton(
        modifier = Modifier
            .padding(top = 24.dp, bottom = 24.dp)
            .width(280.dp),
        primaryText = "Sign Out",
        secondaryText = "Sign Out",
        onTap = onSignOutClicked
    )
}