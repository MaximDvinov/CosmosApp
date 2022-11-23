package com.cosmos.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val ColorPalette = darkColors(
    primary = primary,
    primaryVariant = primaryVariant,
    background = blue_bg,
    secondary = secondaryVariant,
    onPrimary = primary,
    surface = blue_bg

)

@Composable
fun CosmosAppTheme(content: @Composable () -> Unit) {
    val colors = ColorPalette

    MaterialTheme(
        colors = colors,
        shapes = Shapes,
        content = content
    )
}