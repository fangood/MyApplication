package com.android.module_compose2.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.filterIsInstance

class TestButtonActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column {
                       Greeting(stringResource(R.string.app_name),
                           Modifier.padding(innerPadding))
                        Spacer(modifier = Modifier.height(20.dp))
                        TestButton(Modifier.padding(innerPadding))
                        Spacer(modifier = Modifier.height(20.dp))
                        OutlinedButton(onClick =  {}, modifier = Modifier.padding(innerPadding)) {
                            Text(text = "OutlinedButton")
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Image(painter =
                            androidx.compose.ui.res.painterResource(id = R.mipmap.avatar),
                            contentDescription = "avatar")

                    }

                }

            }
        }
    }
}

@Composable
fun Greeting(name: String,modifier: Modifier) {
    Button(modifier = modifier.wrapContentWidth().wrapContentHeight(),
        shape = MaterialTheme.shapes.small,
        onClick = {
        println("点击了")
    }){
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column {
                Greeting(stringResource(R.string.app_name),
                    Modifier.padding(innerPadding))
            }
        }

    }
}
@Preview(showBackground = true)
@Composable
fun TestButtonPreview() {
    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column {
                Greeting(stringResource(R.string.app_name),
                    Modifier.padding(innerPadding))
                Spacer(modifier = Modifier.height(20.dp))
                TestButton(Modifier.padding(innerPadding))
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(onClick =  {}, modifier = Modifier.padding(innerPadding)) {
                    Text(text = "OutlinedButton")
                }
                TextButton(onClick = {}, modifier = Modifier.padding(innerPadding)) {
                    Text(text = "TextButton")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Image(painter =
                    androidx.compose.ui.res.painterResource(id = R.mipmap.avatar),
                    contentDescription = "avatar")
            }

        }

    }
}
@Composable
fun TestButton(modifier: Modifier) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.interactions
        .filterIsInstance<PressInteraction.Press>()
        .collectAsState(initial = null)

    Button(
        modifier = modifier.wrapContentWidth().wrapContentHeight(),
        interactionSource = interactionSource,
        onClick = { /* ... */ }
    ) {
        Text(if (isPressed != null) "Pressed!" else "Click Me")
    }

}





