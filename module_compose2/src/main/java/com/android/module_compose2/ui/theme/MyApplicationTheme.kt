package com.android.module_compose2.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object MyApplicationTheme {
    val colors : ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
    val typography : androidx.compose.material3.Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
    val shapes : androidx.compose.material3.Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current
}