package com.android.module_compose2.basic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.module_compose2.R

@Preview
@Composable
fun ColorFilterDemoPreview() {
    ColorFilterDemo()
}
@Composable
fun ColorFilterDemo() {
    Column {
        // 1. ImageBitmap + ColorFilter
        val bitmap = ImageBitmap.imageResource(id = R.drawable.xigua)
        Image(
            bitmap = bitmap,
            contentDescription = "Tinted Bitmap",
            colorFilter = ColorFilter.tint(Color.Red.copy(alpha = 0.5f)),
            modifier = Modifier.size(200.dp)
        )

        // 2. ImageVector + ColorFilter
        val vector = ImageVector.vectorResource(id = R.drawable.cat)
        Image(
            imageVector = vector,
            contentDescription = "Color Matrix Vector",
            colorFilter = ColorFilter.colorMatrix(
                ColorMatrix(
                    floatArrayOf(
                        -1f, 0f, 0f, 0f, 255f, // 红色通道反相
                        0f, 1f, 0f, 0f, 0f,    // 绿色通道保留
                        0f, 0f, 1f, 0f, 0f,    // 蓝色通道保留
                        0f, 0f, 0f, 1f, 0f     // Alpha通道保留
                    )
                )
            ),
            modifier = Modifier.size(200.dp)
        )

        // 3. 混合模式ColorFilter
        Image(
            imageVector = vector,
            contentDescription = "Blend Mode Filter",
            colorFilter = ColorFilter.lighting(
                multiply = Color.Blue,
                add = Color.Yellow
            ),
            modifier = Modifier.size(200.dp)
        )
    }
}