package io.github.ilyaskerbal.googleauthapp.presentation.screen.profile

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.github.ilyaskerbal.googleauthapp.R
import io.github.ilyaskerbal.googleauthapp.components.DisplayAlertDialog
import io.github.ilyaskerbal.googleauthapp.ui.theme.topAppBarBackgroundColor
import io.github.ilyaskerbal.googleauthapp.ui.theme.topAppBarContentColor

@Composable
fun ProfileTopBar(
    onSave: () -> Unit,
    onDeleteAll: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Profile",
                color = MaterialTheme.colors.topAppBarContentColor,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            ProfileTopBarActions(onSave = onSave, onDeleteAll = onDeleteAll)
        }
    )
}

@Composable
private fun ProfileTopBarActions(
    onSave: () -> Unit,
    onDeleteAll: () -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        openDialog = openDialog,
        onYesClicked = { onDeleteAll() },
        onDialogClosed = { openDialog = false}
    )

    SaveAction(onSave = onSave)
    DeleteAction(onDeleteAll = { openDialog = true })
}

@Composable
private fun SaveAction(
    onSave: () -> Unit,
) {
    IconButton(onClick = onSave) {
        Icon(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = "Save icon",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
    }
}

@Composable
private fun DeleteAction(
    onDeleteAll: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    IconButton(onClick = { expanded = true }) {
        Icon(
            painter = painterResource(id = R.drawable.ic_vertical_menu),
            contentDescription = "Vertical menu",
            tint = MaterialTheme.colors.topAppBarContentColor
        )
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onDeleteAll()
            }) {
                Text(
                    text = "Delete Account",
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProfileTobBarPreview() {
    ProfileTopBar({}, {})
}
