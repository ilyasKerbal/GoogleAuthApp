package io.github.ilyaskerbal.googleauthapp.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.ilyaskerbal.googleauthapp.R
import io.github.ilyaskerbal.googleauthapp.ui.theme.Shapes

@Composable
fun AuthButton(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    primaryText: String = stringResource(id = R.string.primary_text_google),
    secondaryText: String = stringResource(id = R.string.please_wait),
    icon: Int = R.drawable.ic_google_logo,
    shape: Shape = Shapes.medium,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colors.surface,
    progressIndicatorColor: Color = MaterialTheme.colors.primaryVariant,
    onTap: () -> Unit = {}
) {
    var buttonText by remember {
        mutableStateOf(primaryText)
    }

    LaunchedEffect(key1 = isLoading) {
        buttonText = if (isLoading) secondaryText else primaryText
    }
    Surface(
        modifier = modifier
            .clickable(!isLoading) {
               onTap()
            },
        shape = shape,
        color = backgroundColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .animateContentSize(
                    animationSpec = tween(
                        400, 0, LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.google_btn_content_description),
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = buttonText)
            if (isLoading) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(
                    modifier = Modifier.size(16.dp),
                    color = progressIndicatorColor,
                    strokeWidth = 2.dp
                )
            }
        }
    }
}

@Composable
@Preview
fun BtnPreview() {
    AuthButton()
}