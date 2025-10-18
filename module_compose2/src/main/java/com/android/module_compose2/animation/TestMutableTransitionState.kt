package com.android.module_compose2.animation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TestMutableTransitionState() {
    val state = remember {
        androidx.compose.animation.core.MutableTransitionState(false).apply {
            targetState = true
        }
    }
    Column {
        Spacer(modifier = Modifier.padding(100.dp))
        Button(onClick = {
            state.targetState = !state.currentState
        }) {
            Text(text = "Toggle")
        }
        AnimatedVisibility(visibleState = state) {
            Text(text = "MutableTransitionState")
        }
        Text(
            text = when {
                state.isIdle && state.currentState -> "Visible"
                !state.isIdle && state.currentState -> "Disappearing"
                !state.isIdle && !state.currentState -> "InVisible"
                else -> "Appearing"
            }
        )

    }
}