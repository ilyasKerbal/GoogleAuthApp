package io.github.ilyaskerbal.googleauthapp.components

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun DisplayAlertDialog(
    title: String = "Delete your account?",
    message: String = "Are you sure you want to delete your account?",
    openDialog: Boolean,
    onYesClicked: () -> Unit,
    onDialogClosed: () -> Unit
) {
    if (openDialog) {
        AlertDialog(
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = message,
                    fontSize = MaterialTheme.typography.subtitle2.fontSize,
                    fontWeight = FontWeight.Normal
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onYesClicked()
                        onDialogClosed()
                    }
                ) {
                    Text(text = "Yes")
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = {
                        onDialogClosed()
                    }
                ) {
                    Text(text = "No")
                }
            },
            onDismissRequest = { onDialogClosed() }
        )
    }
}