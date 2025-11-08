package com.android.module_compose2.animation

import androidx.compose.animation.core.DecayAnimation
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TargetBasedAnimationDemo() {
    var box1Offset by remember { mutableStateOf(Offset(50f, 100f)) }
    var box2Offset by remember { mutableStateOf(Offset(50f, 250f)) }
    val coroutineScope = rememberCoroutineScope()

    // 用于手动控制动画时间的状态
    var animationPlayTime by remember { mutableStateOf(0L) }
    var isAnimating by remember { mutableStateOf(false) }

    // 在 Composable 上下文中创建 decayAnimationSpec
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 16.dp, bottom = 16.dp, end = 16.dp)
    ) {
        // 控制按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                coroutineScope.launch {
                    // TargetBasedAnimation 示例
                    val targetAnimation = TargetBasedAnimation(
                        animationSpec = tween(
                            1000,
                            easing = FastOutSlowInEasing
                        ),
                        typeConverter = Float.VectorConverter,
                        initialValue = 50f,
                        targetValue = 300f
                    )

                    val startTime = withFrameMillis { it }
                    isAnimating = true

                    do {
                        val playTime = withFrameMillis { it } - startTime
                        animationPlayTime = playTime
                        val animationValue =
                            targetAnimation.getValueFromNanos(
                                playTime * 1_000_000L)

                        // 更新第一个方块的位置（水平移动）
                        box1Offset = box1Offset.copy(x = animationValue)
                    } while (
                        !targetAnimation.isFinishedFromNanos(
                            playTime * 1_000_000L
                        )
                    )
                    isAnimating = false
                }
            }) {
                Text("启动 Target 动画")
            }

            Button(onClick = {
                coroutineScope.launch {
                    // DecayAnimation 示例
                    val decayAnimation = DecayAnimation(
                        animationSpec = decayAnimationSpec, // 使用在 Composable 上下文中创建的实例
                        initialValue = 50f,
                        initialVelocity = 1000f, // 初始速度
                        typeConverter = Float.VectorConverter
                    )

                    val startTime = withFrameMillis { it }
                    isAnimating = true

                    do {
                        val playTime = withFrameMillis { it } - startTime
                        animationPlayTime = playTime
                        val animationValue = decayAnimation.getValueFromNanos(playTime * 1_000_000L)

                        // 更新第二个方块的位置（水平移动）
                        box2Offset = box2Offset.copy(x = animationValue)
                    } while (!decayAnimation.isFinishedFromNanos(playTime * 1_000_000L))

                    isAnimating = false
                }
            }) {
                Text("启动 Decay 动画")
            }

            Button(onClick = {
                // 重置位置
                box1Offset = Offset(50f, 100f)
                box2Offset = Offset(50f, 250f)
                animationPlayTime = 0L
                isAnimating = false
            }) {
                Text("重置")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 显示动画信息
        Text(
            text = "动画时间: ${animationPlayTime}ms",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 绘制区域
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawBehind {
                    // 绘制参考线
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, 80f),
                        end = Offset(size.width, 80f)
                    )
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, 230f),
                        end = Offset(size.width, 230f)
                    )
                }
        ) {
            // 第一个方块 - TargetBasedAnimation
            Box(
                modifier = Modifier
                    .offset(box1Offset.x.dp, box1Offset.y.dp)
                    .size(50.dp)
                    .background(Color.Blue)
            )

            // 第二个方块 - DecayAnimation
            Box(
                modifier = Modifier
                    .offset(box2Offset.x.dp, box2Offset.y.dp)
                    .size(50.dp)
                    .background(Color.Red)
            )

            // 标签
            Text(
                text = "TargetBasedAnimation",
                modifier = Modifier.offset(120.dp, 115.dp),
                color = Color.Blue,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "DecayAnimation",
                modifier = Modifier.offset(120.dp, 265.dp),
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}