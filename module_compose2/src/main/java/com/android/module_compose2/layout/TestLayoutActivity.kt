package com.android.module_compose2.layout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.LayoutDirection

class TestLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetView()
        }
    }
}
@Composable
private fun SetView(){
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Rtl){
        Column {

            Column (modifier = Modifier.padding(8.dp)){
                Text(text = "Hello World")
                Text(text = "纵向排列")
                Text(text = "所有子元素居中对齐")
            }
            MyCustomColumn(modifier = Modifier.padding(8.dp)) {
                Text(text = "Hello World")
                Text(text = "纵向排列")
                Text(text = "所有子元素居中对齐")
            }
        }
    }
}
@Preview
@Composable
fun PreviewMyCustomColumn() {
    SetView()
}