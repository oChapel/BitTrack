package com.example.bittrack.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

private val DarkScheme = darkColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.Black,
    primaryContainer = GreenContainer,
    secondary = GreenSurface,
    onSecondary = TextPrimary,
    background = NeutralBgDark,
    onBackground = TextPrimary,
    surface = NeutralCard,
    onSurface = TextPrimary,
    outline = NeutralOutline,
    surfaceVariant = GreenOn,
    onSurfaceVariant = TextSecondary,
    error = Error,
    onError = Color.White
)

@Composable
fun BitTrackTheme(
    colorScheme: ColorScheme = DarkScheme,
    content: @Composable () -> Unit
) {
    val extended = ExtendedColors(
        appBackground = Brush.verticalGradient(
            listOf(
                NeutralBgDarker,
                NeutralBgDark,
                NeutralBgGreen
            )
        ),
        cardGradient = Brush.verticalGradient(listOf(GreenSurface, GreenOn)),
        outlineSoft = GreenBorder
    )

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalExtendedColors provides extended
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}
