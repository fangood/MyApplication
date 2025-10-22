package com.android.module_compose2.animation

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.module_compose2.R

@Composable
fun TestCrossFade() {
    var state by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Spacer(Modifier.padding(200.dp))
        Button(onClick = { state = !state }) {
            Text(text = if (!state) "Page A" else "Page B")
        }
        Crossfade(
            targetState = state,
            label = "Crossfade",
            animationSpec = tween(
                1000, 100,
                LinearOutSlowInEasing
            )
        ) { screen ->
            when (screen) {
                true -> Image(
                    painter = painterResource(id = R.drawable.cat),
                    contentDescription = null,
                    modifier = Modifier.sizeIn(maxWidth = 200.dp, maxHeight = 200.dp)
                        .background(androidx.compose.ui.graphics.Color.Red)
                )

                false -> Image(
                    painter = painterResource(id = R.drawable.xigua),
                    contentDescription = null,
                    modifier = Modifier.sizeIn(maxWidth = 256.dp, maxHeight = 128.dp)
                        .background(androidx.compose.ui.graphics.Color.Blue)
                )
            }
        }
    }

}