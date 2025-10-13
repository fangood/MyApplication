package com.android.module_compose2.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Stroke.Companion.DefaultJoin
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * blendMode
 */
@Preview
@Composable
fun TestBlendMode () {
    DrawCircleAndRectangle()
}
@Composable
fun DrawCircleAndRectangle() {
    val density = LocalDensity.current
    val circleSize = with(density) { 110.dp.toPx() }
    val rectWidth = with(density) { 130.dp.toPx() }
    val rectHeight = with(density) { 130.dp.toPx() } // 矩形高度可以自定义

    Canvas(
        modifier = Modifier
            .size(100.dp) // 画布大小
//            .background(Color.White)
    ) {
        // 绘制圆形
        drawCircle(
            color = Color.Blue,
            center = Offset(x = circleSize, y = circleSize),
            radius =80f,
            blendMode = androidx.compose.ui.graphics.BlendMode.SrcIn

        )

        // 绘制矩形
        drawRect(
            color = Color.Red,
            topLeft = Offset(x = 110f, y = 110f),
            size = Size(rectWidth, rectHeight),
            blendMode = androidx.compose.ui.graphics.BlendMode.Difference
        )
    }
}
@Preview
@Composable
fun TestDrawRectPreview(){
    TestDrawRect()
}
@Composable
fun TestDrawRect(){
    Canvas(modifier = Modifier.size(200.dp)){
        drawRect(
            color = Color.Red,
            topLeft = Offset(x = 10f, y = 10f),
            size = Size(150f, 100f),
            style = Stroke(
                width = 5.dp.toPx(),
                join = StrokeJoin.Bevel
            )
        )
    }
}