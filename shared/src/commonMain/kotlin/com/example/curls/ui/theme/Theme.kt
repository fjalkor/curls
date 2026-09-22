package com.example.curls.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF00897B),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB2DFDB),
    onPrimaryContainer = Color(0xFF00251F),

    secondary = Color(0xFFFF7043),
    onSecondary = Color(0xFFFFFFFF),

    background = Color(0xFFFDFDFB),
    onBackground = Color(0xFF1C1C1C),

    surface = Color(0xFFFDFDFB),
    onSurface = Color(0xFF1C1C1C),

    surfaceVariant = Color(0xFFE3E6E4),
    onSurfaceVariant = Color(0xFF454847),
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF4DB6AC),
    onPrimary = Color(0xFF00382F),
    primaryContainer = Color(0xFF00504429),
    onPrimaryContainer = Color(0xFFB2DFDB),

    secondary = Color(0xFFFFAB91),
    onSecondary = Color(0xFF5D1900),

    background = Color(0xFF121212),
    onBackground = Color(0xFFE6E6E6),

    surface = Color(0xFF121212),
    onSurface = Color(0xFFE6E6E6),

    surfaceVariant = Color(0xFF3A3D3C),
    onSurfaceVariant = Color(0xFFC3C7C5),
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        content = content,
    )
}