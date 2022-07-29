package io.github.ilyaskerbal.googleauthapp.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.ilyaskerbal.googleauthapp.domain.model.MessageBarState
import io.github.ilyaskerbal.googleauthapp.ui.theme.ErrorRed
import io.github.ilyaskerbal.googleauthapp.ui.theme.InfoGreen
import kotlinx.coroutines.delay
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun MessageBar(
    messageBarState: MessageBarState
) {
    var startAnimation by remember {
        mutableStateOf(false)
    }
    var errorMessage by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = messageBarState) {
        messageBarState.error?.let {
            errorMessage = when(it) {
                is ConnectException -> "Internet connection unavailable"
                is SocketTimeoutException -> "Connection timeout exception"
                else -> "Oops! Something went wrong try again."
            }
        }
        startAnimation = true
        delay(300L)
        startAnimation = false
    }

    AnimatedVisibility(
        visible = (messageBarState.error != null || messageBarState.message != null) && startAnimation,
        enter = expandVertically(
            animationSpec = tween(300),
            expandFrom = Alignment.Top
        ),
        exit = shrinkVertically(
            animationSpec = tween(300),
            shrinkTowards = Alignment.Top
        )
    ) {
        MessageRow(messageBarState = messageBarState, errorMessage = errorMessage)
    }
}

@Composable
private fun MessageRow(messageBarState: MessageBarState, errorMessage: String) {
    val isError = messageBarState.error != null
    val barColor = if (isError) ErrorRed else InfoGreen
    val barIcon = if (isError) Icons.Default.Warning else Icons.Default.Check
    val barText = if (isError) errorMessage else messageBarState.message ?: ""
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = barColor)
            .padding(horizontal = 20.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = barIcon,
            contentDescription = "message bar icon",
            tint = Color.White
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = barText,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.button.fontSize,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
@Preview
fun PreviewMessageBarError() {
    MessageRow(
        messageBarState = MessageBarState(error = SocketTimeoutException()),
        errorMessage = "Connection timeout exception"
    )
}

@Composable
@Preview
fun PreviewMessageBarSuccess() {
    MessageRow(
        messageBarState = MessageBarState(message = "You've successfully logged in."),
        errorMessage = ""
    )
}