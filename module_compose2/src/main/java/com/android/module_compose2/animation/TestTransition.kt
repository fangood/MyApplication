package com.android.module_compose2.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestTransition() {
    val currentState = remember { mutableStateOf(BoxState.Collapsed) }
    val transition = updateTransition(currentState.value, label = "boxState")

    val color by transition.animateColor(
        transitionSpec = {
            when {
                BoxState.Expanded isTransitioningTo BoxState.Collapsed -> {
                    //进入动画
                    spring(stiffness = 50f)
                }
                else -> {
                    tween(durationMillis = 500)
                }
            }
        },
        label = "color"
    ) { state ->
        when (state) {
            BoxState.Collapsed -> MaterialTheme.colorScheme.primary
            BoxState.Expanded -> MaterialTheme.colorScheme.background
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .size(200.dp)
                .background(color)
        )

        Button(onClick = {
            currentState.value = if (currentState.value == BoxState.Collapsed) {
                BoxState.Expanded
            } else {
                BoxState.Collapsed
            }
        }) {
            Text(text = "Click")
        }
    }
}
@Composable
fun TestTransition2() {
    val currentState = remember { MutableTransitionState(BoxState.Collapsed) }
    val transition = updateTransition(currentState.currentState, label = "boxState")

    val color by transition.animateColor(
        transitionSpec = {
            when {
                BoxState.Expanded isTransitioningTo BoxState.Collapsed -> {
                    //进入动画
                    spring(stiffness = 50f)
                }
                else -> {
                    tween(durationMillis = 500)
                }
            }
        },
        label = "color"
    ) { state ->
        when (state) {
            BoxState.Collapsed -> MaterialTheme.colorScheme.primary
            BoxState.Expanded -> MaterialTheme.colorScheme.background
        }
    }

    Column(
        Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .size(200.dp)
                .background(color)
        )

        Button(onClick = {
            currentState.targetState = if (currentState.currentState == BoxState.Collapsed) {
                BoxState.Expanded
            } else {
                BoxState.Collapsed
            }
        }) {
            Text(text = "Click")
        }
    }
}