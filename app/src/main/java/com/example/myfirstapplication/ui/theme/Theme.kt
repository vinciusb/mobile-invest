package com.example.myfirstapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = White256,
    secondary = Orange20,
    background = Black23,
    outline = Gray73,
    inverseSurface = Orange0
)

private val LightColorScheme = lightColorScheme(
    primary = Black23,
    secondary = Orange80,
    background = White256,
    outline = Gray73,
)

@Composable
fun TemaPersonalizado(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}