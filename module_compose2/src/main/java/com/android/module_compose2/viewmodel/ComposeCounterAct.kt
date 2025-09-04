package com.android.module_compose2.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.module_compose2.ui.theme.MyApplicationTheme
import androidx.compose.runtime.State
/**
 * https://juejin.cn/post/7277912168493957160
 */
class ComposeCounterAct : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TestCounter()
                }
            }
        }
    }

    @Composable
    fun TestCounter(){
        val viewModel:ComposeCounterViewModel = viewModel()
        CounterComponent(viewModel.counter.value,viewModel::increment,viewModel::decrement)
    }

    @Composable
    fun CounterComponent(
        counter: Int, // 重组时传入当前需要显示的计数
        onIncrement: () -> Unit,// 回调点击加号的事件
        onDecrement: () -> Unit // 回调单击减号的事件
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "$counter",
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Row {
                Button(
                    onClick = { onDecrement() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("-")
                }

                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = { onIncrement() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+")
                }
            }
        }
    }
}

class ComposeCounterViewModel: ViewModel(){
    private val _counter = mutableStateOf(0)
    val counter: State<Int> = _counter

    fun increment(){
        _counter.value = _counter.value + 1
    }

    fun decrement(){
        if(_counter.value>0){
            _counter.value = _counter.value -1
        }
    }
}

