package com.example.bittrack.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class Spacing(
    val xs: Int = 4,
    val s: Int = 8,
    val m: Int = 12,
    val l: Int = 16,
    val xl: Int = 20,
    val xxl: Int = 24
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }
