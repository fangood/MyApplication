@file:OptIn(ExperimentalMaterial3Api::class)

package com.android.module_compose2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.sp
import com.android.module_compose2.dao.User
import com.android.module_compose2.database.UserPreviewParameterProvider
import com.android.module_compose2.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.android.module_compose2.dao.Result

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

                    MoviesScreen(snackbarHostState = remember { SnackbarHostState() })
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
private fun HelloCompose(contents: List<User>){
    Column {
        for (user in contents){
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
        Column(modifier = Modifier.padding(innerPadding).wrapContentSize()) {
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
fun LandingScreen(onTimeOut:() -> Unit) {
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
fun loadNetworkImage(url: String, imageRepository: ImageRepository): androidx.compose.runtime.State<Result<android.media.Image?>> {
    return produceState<Result<android.media.Image?>>(initialValue = Result.Loading, key1 = url, imageRepository) {
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
fun TodoList(highPriorityKeywords: List<String> = listOf("Review", "Unblock", "Compose"), todos: List<String>) {
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