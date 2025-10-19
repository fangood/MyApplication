package com.android.module_compose2.gesture

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TestNestScroll() {
    val gradient = Brush.verticalGradient(0f to Color.Gray, 1000f to Color.White)
    Box(modifier = Modifier.padding(100.dp, 150.dp,0.dp,0.dp)
        .background(Color.Gray)
        .height(320.dp)
        .verticalScroll(rememberScrollState())
        .padding(32.dp)
    ){
        Column {
            repeat(6){
                Box(modifier = Modifier
                    .height(120.dp)
                    .verticalScroll(rememberScrollState())
                ){
                    Text("Scroll here",
                        modifier = Modifier
                            .border(12.dp, Color.DarkGray)
                            .background(gradient)
                            .padding(24.dp)
                            .height(150.dp)
                        )
                }
            }
        }
    }
}