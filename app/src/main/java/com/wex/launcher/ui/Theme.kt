package com.wex.launcher.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object WexColors {
    val Background = Color(0xFF000000)
    val Purple = Color(0xFF9D4EDD)
    val Cyan = Color(0xFF00E5FF)
    val GlassFill = Color(0x1AFFFFFF)
    val TextPrimary = Color(0xFFF5F5F7)
    val TextMuted = Color(0x99F5F5F7)
}

@Composable
fun WexTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = WexColors.Background,
            surface = WexColors.Background,
            primary = WexColors.Purple,
            secondary = WexColors.Cyan,
            onBackground = WexColors.TextPrimary,
            onSurface = WexColors.TextPrimary
        ),
        content = content
    )
}
