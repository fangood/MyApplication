package com.android.module_compose2.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

private val roundShape = RoundedCornerShape(4.dp)

private val cutCornerShape = CutCornerShape(4.dp)

internal val LocalShapes = staticCompositionLocalOf {
    Shapes(
        small = CutCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )
}