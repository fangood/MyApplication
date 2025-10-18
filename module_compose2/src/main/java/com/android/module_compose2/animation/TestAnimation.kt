package com.android.module_compose2.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.module_compose2.R

@Composable
fun TestAnimation() {
    val visible = remember {
        mutableStateOf(true)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(100.dp))
        Button(
            onClick = {
                visible.value = !visible.value
            }
        ) {
            Text(text = if (!visible.value) "进入动画" else "退出动画")
        }
        AnimatedVisibility(
            visible = visible.value,
            enter = androidx.compose.animation.slideInHorizontally (
                initialOffsetX = { it }
            ) + androidx.compose.animation.fadeIn(),
            exit = androidx.compose.animation.slideOutVertically(
                targetOffsetY = { it }
            ) + androidx.compose.animation.fadeOut()
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = null
            )
        }

    }

}