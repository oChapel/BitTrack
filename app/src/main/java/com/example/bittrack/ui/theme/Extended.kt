package com.example.bittrack.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Immutable
data class ExtendedColors(
    val appBackground: Brush,
    val cardGradient: Brush,
    val outlineSoft: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        appBackground = Brush.verticalGradient(listOf(NeutralBgDarker, NeutralBgDark, Color(0xFF12291C))),
        cardGradient = Brush.verticalGradient(listOf(GreenSurface, GreenOn)),
        outlineSoft = GreenBorder
    )
}
