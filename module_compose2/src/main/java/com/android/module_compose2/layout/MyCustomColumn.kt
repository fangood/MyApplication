package com.android.module_compose2.layout

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout

@Composable
fun MyCustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeAbles = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        val width = placeAbles.maxOf { it.width }
        val height = placeAbles.sumOf { it.height }

        layout(width, height) {
            var yPosition = 0

            placeAbles.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}