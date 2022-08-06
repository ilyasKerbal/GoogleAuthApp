package io.github.ilyaskerbal.googleauthapp.presentation.screen.login

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.github.ilyaskerbal.googleauthapp.R
import io.github.ilyaskerbal.googleauthapp.ui.theme.topAppBarBackgroundColor
import io.github.ilyaskerbal.googleauthapp.ui.theme.topAppBarContentColor

@Composable
fun LoginTopBar() {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.sign_in),
                color = MaterialTheme.colors.topAppBarContentColor,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor
    )
}

@Composable
@Preview
fun LoginTopBarPreview() {
    LoginTopBar()
}