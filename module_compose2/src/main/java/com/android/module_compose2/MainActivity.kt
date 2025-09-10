@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.module_compose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.module_compose2.dao.Result
import com.android.module_compose2.dao.User
import com.android.module_compose2.database.UserPreviewParameterProvider
import com.android.module_compose2.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )


//                    MoviesScreen(snackbarHostState = remember { SnackbarHostState() })
                    RippleTest(modifier = Modifier.padding(innerPadding)
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000045
)
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
        UserPreviewParameterProvider::class, limit = 2
    ) user: User
) {
    UserProfile(user)
}

@Composable
private fun UserProfile(user: User?) {
    Text(
        text = user?.name ?: "未知用户",
        color = Color.Black,
        fontSize = 20.sp
    )
}

@Composable
private fun HelloCompose(contents: List<User>) {
    Column {
        for (user in contents) {
            key(user.id) {
                Text(text = user.name)
            }
        }
    }

}


/**
 * rememberScaffoldState
 */
//@Composable
//fun MyScreen(state: UiState<List<Any>>, scaffoldState: androidx.compose.material3.ScaffoldState = rememberScaffoldState()) {
//    if (state.hasError) {
//        LaunchedEffect(scaffoldState.snackbarHostState) {
//            scaffoldState.snackbarHostState.showSnackbar(
//                message = "加载失败",
//                actionLabel = "重试",
//                duration = SnackbarDuration.Short
//            )
//        }
//    }
//
//    Scaffold(
//        scaffoldState = scaffoldState
//    ) { innerPadding ->
//        Column(modifier = Modifier.padding(innerPadding)) {
//            Text(text = "Hello World")
//        }
//    }
//}

/**
 * rememberCoroutineScope()
 * ScaffoldState 和 rememberScaffoldState 是 Material 2 中的 API，在 Material 3 中已被移除
 */
@Composable
fun MoviesScreen(snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }) {
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            androidx.compose.material3.SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
//        Column(modifier = Modifier.padding(innerPadding).wrapContentWidth().wrapContentHeight()) {
        Column(modifier = Modifier
            .padding(innerPadding)
            .wrapContentSize()) {
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Hello World")
                }
            }) {
                Text(text = "Show Snackbar")
            }
        }
    }
}

@Preview(showBackground = false, backgroundColor = 0xFFB07C7C)
@Composable
private fun MoviesScreenPreview() {
    MoviesScreen()
}

/**
 * rememberUpdatedState
 */
@Composable
fun LandingScreen(onTimeOut: () -> Unit) {
    val currentOnTimeOut by rememberUpdatedState((onTimeOut))
    LaunchedEffect(true) {

        delay(3000L)
        currentOnTimeOut()
    }
}

// 添加 ImageRepository 类和 Result 密封类定义
class ImageRepository {
    // 模拟从网络加载图片
    suspend fun loadImage(url: String): android.media.Image? {
        delay(1000L) // 模拟网络延迟
        // 实际实现中这里会从网络加载图片
        return null
    }
}


/**
 * produceState
 */
@Composable
fun loadNetworkImage(
    url: String,
    imageRepository: ImageRepository
): androidx.compose.runtime.State<Result<android.media.Image?>> {
    return produceState<Result<android.media.Image?>>(
        initialValue = Result.Loading,
        key1 = url,
        imageRepository
    ) {
        val image = imageRepository.loadImage(url)
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}

/**
 * derivedStateOf
 */
@Composable
fun TodoList(
    highPriorityKeywords: List<String> = listOf("Review", "Unblock", "Compose"),
    todos: List<String>
) {
    val todoTasks = remember { mutableListOf<String>() }
    val highPriorityTasks: List<String> by remember(highPriorityKeywords) {
        derivedStateOf {
            todoTasks.filter { task ->
                highPriorityKeywords.any { keyword ->
                    task.contains(keyword, ignoreCase = true)
                }
            }
        }
    }
    // 注意：这里的LazyColumn使用可能不完整，实际应用中需要导入相应包并完善实现
}

@Composable
fun ButtonTest() {
    Button(
        onClick = { /*TODO*/ },
        enabled = true,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.White,
            disabledContainerColor = MaterialTheme.colorScheme.onBackground
                .copy(alpha = 0.5f)
                .compositeOver(MaterialTheme.colorScheme.background)
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.padding(8.dp),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )

    ) {
        Text(text = "Button")
    }
}

@Preview(showBackground = false, backgroundColor = 0xFFB07C7C)
@Composable
fun ButtonTestPreview() {
    ButtonTest()
}

/**
 * 水波纹 repple
 */
@Composable
fun RippleTest(modifier: Modifier = Modifier){
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .background(
                color = Color.Green,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current
            ) {
                // 点击事件处理
            }
    ) {
        Text(text = "Click me")
    }
}

@Preview(showBackground = false, backgroundColor = 0xFFB07C7C)
@Composable
fun RippleTestPreview() {
    RippleTest()
}