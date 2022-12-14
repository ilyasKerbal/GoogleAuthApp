package io.github.ilyaskerbal.googleauthapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val ErrorRed = Color(0xFFc0392b)
val InfoGreen = Color(0xFF27ae60)
val BlueColor = Color(0xFF3498db)

val Colors.topAppBarContentColor : Color
    get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) Purple500 else Color.Black