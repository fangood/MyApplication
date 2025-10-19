package com.android.module_compose2.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun GestureTest1() {
    Row(
        modifier = Modifier.size(100.dp).background(androidx.compose.ui.graphics.Color.Red)
            .pointerInput( Unit){
                detectTapGestures(
                    onPress = {
                        println("Press at $it")
                    },
                    onTap = {
                        //单击
                        println("Tap at $it")
                    },
                    onDoubleTap = {
                        //双击
                        println("Double Tap at $it")
                    },
                    onLongPress = {
                        //长按
                        println("Long Press at $it")
                    }
                )
            }
    ) {

    }
}