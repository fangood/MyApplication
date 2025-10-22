package com.android.module_compose2.animation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.module_compose2.R

@Composable
fun TestExpandableContent() {
    val expanded = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Spacer(modifier = Modifier.size(100.dp))
        Column(modifier = Modifier.animateContentSize()) {
            Text(
                text = "核心功能说明：\n" +
                        "AnimatedContent 基础用法\n" +
                        "\n" +
                        "根据 targetState 的变化自动触发动画\n" +
                        "\n" +
                        "在不同页面之间平滑过渡\n" +
                        "\n" +
                        "自定义动画效果\n" +
                        "\n" +
                        "使用 transitionSpec 定义进入和退出动画\n" +
                        "\n" +
                        "根据页面切换方向应用不同的动画（向上/向下滑动）\n" +
                        "\n" +
                        "结合淡入淡出和滑动动画\n",
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Justify,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                maxLines = if (expanded.value) Int.MAX_VALUE else 3
            )
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = null,
                modifier = Modifier
                    .padding(6.dp, 10.dp)
                    .size(
                        if (expanded.value) 256.dp else 128.dp,
                        if (expanded.value) 192.dp else 96.dp
                    )
            )
            Text(
                text = "点击查看更多",
                fontSize = 16.sp,
                color = androidx.compose.ui.graphics.Color.Gray,
                modifier = Modifier.clickable(onClick = { expanded.value = !expanded.value })
            )
        }
    }
}