package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.ilyaskerbal.googleauthapp.R
import io.github.ilyaskerbal.googleauthapp.components.AuthButton
import io.github.ilyaskerbal.googleauthapp.components.MessageBar
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState

@Composable
fun LoginContent(
    singedInState: Boolean,
    messageBarState: MessageBarState,
    onButtonClicked: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MessageBar(messageBarState = messageBarState)
        }
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CentralContent(singedInState = singedInState, onButtonClicked = onButtonClicked)
        }
    }
}

@Composable
private fun CentralContent(
    singedInState: Boolean,
    onButtonClicked: () -> Unit = {}
) {
    Image(
        modifier = Modifier
            .size(120.dp)
            .padding(bottom = 20.dp),
        painter = painterResource(id = R.drawable.ic_google_logo),
        contentDescription = stringResource(id = R.string.google_btn_content_description)
    )
    Text(
        text = stringResource(id = R.string.sign_in_title),
        fontSize = MaterialTheme.typography.h5.fontSize,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = stringResource(id = R.string.sign_in_subtitle),
        fontSize = MaterialTheme.typography.subtitle1.fontSize,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .alpha(ContentAlpha.medium)
            .padding(top = 10.dp, bottom = 40.dp)
    )
    AuthButton(
        isLoading = singedInState,
        onTap = onButtonClicked
    )
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginContentPreview() {
    LoginContent(singedInState = false, messageBarState = MessageBarState())
}