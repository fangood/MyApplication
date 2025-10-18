package com.android.module_compose2.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun TestChildAnimation() {
    val state = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(100.dp))
        Button(
            onClick = {
                state.value = !state.value
            }
        ) {
            Text(text = if (state.value) "进入动画" else "退出动画")
        }

        AnimatedVisibility(
            visible = state.value,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)) {
                Image(
                    painter = androidx.compose.ui.res.painterResource(
                        id = com.android.module_compose2.R.drawable.ic_launcher_background),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            enter = expandIn { IntSize(-it.width, -it.height) },
                            exit = slideOut { IntOffset(it.width, it.height) }
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 256.dp)
                        .background(Color.Red)
                )

                Text(text = "TestChildAnimation")
            }
        }
    }
}