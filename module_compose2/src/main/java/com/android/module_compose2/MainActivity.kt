package com.android.module_compose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.android.module_compose2.dao.User
import com.android.module_compose2.database.UserPreviewParameterProvider
import com.android.module_compose2.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        UserProfile(user = User("张三"))
    }

}

@Preview(showBackground = true,
    backgroundColor = 0xFF000045)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = false, backgroundColor = 0xFFB07C7C)
@Composable
private fun UserProfilePreview(
    @PreviewParameter(
        UserPreviewParameterProvider::class,limit = 2)user: User) {
    UserProfile(user)
}

@Composable
private fun UserProfile(user: User?){
    Text(
        text = user?.name ?: "未知用户",
        color = Color.Black,
        fontSize = 20.sp
    )
}