package com.android.module_compose2.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.android.module_compose2.R
import com.android.module_compose2.ui.theme.MyApplicationTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column {
                        TestSelectAbleText(innerPadding)
                        Spacer(modifier = Modifier.padding(5.dp))
                        TestTextFieldStyle()
                        Spacer(modifier = Modifier.padding(5.dp))
                        TestOutlinedTextFieldStyle()
                        Spacer(modifier = Modifier.height(5.dp))
                        InputFeaturesScreen()
                    }

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!".repeat(40),
        maxLines = 3,
        color = Color.Green,
        fontFamily = MaterialTheme.typography.headlineMedium.fontFamily,
        letterSpacing = 5.sp,
        lineHeight = 50.sp
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting(
            stringResource(R.string.app_name)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TestSelectAbleTextPreview() {
    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column {
                TestSelectAbleText(innerPadding)
                Spacer(modifier = Modifier.padding(5.dp))
                TestTextFieldStyle()
                Spacer(modifier = Modifier.padding(5.dp))
                TestOutlinedTextFieldStyle()
            }

        }

    }
}

@Composable
fun TestSelectAbleText(paddingValues: PaddingValues) {
    SelectionContainer(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
            .padding(paddingValues)
            .clickable(onClick = {
                println("点击了")
            })
    ) {
        Text(
            text = "这些文字可以被选择"
        )
    }

}

@Composable
fun TestOutlinedTextFieldStyle() {
    var text by remember { mutableStateOf("Hello") }
    Column {
        TextField(
            value = text, onValueChange = { text = it },
            label = { Text(text = "请输入") },
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text(text = "请输入OutLineText") },
        )
    }

}

@Composable
fun TestTextFieldStyle() {
    var value by remember {
        mutableStateOf("Hello \nCompose \nInvisible")
    }
    TextField(
        value = value,
        onValueChange = { value = it },
        label = { Text(text = "请输入") },
        modifier = Modifier.padding(20.dp),
        textStyle = MaterialTheme.typography.bodyLarge.copy(
            color = Color.Red,
            fontSize = 20.sp
        )
    )
}