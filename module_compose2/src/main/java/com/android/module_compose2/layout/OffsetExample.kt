package com.android.module_compose2.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun PreviewOffsetExample() {
    OffsetExample()
}
@Composable
fun OffsetExample() {
    Box(modifier = Modifier.size(200.dp).background(Color.LightGray)) {
        // 默认位置（无偏移）
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
        )

        // 向右下方偏移 (x=30dp, y=40dp)
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Blue)
                .offset(x = 30.dp, y = 40.dp)
        )

        // 向左上方偏移 (x=-20dp, y=-10dp)
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Green)
                .offset(x = (-20).dp, y = (-10).dp)
        )
    }
}