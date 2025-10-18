package com.android.module_compose2.animation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun TestExpandableContent (){
    val expanded = remember { mutableStateOf(false) }
    Column(modifier = Modifier.animateContentSize()) {

    }
}