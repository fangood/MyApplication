package com.android.module_compose2.loading

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.lifecycle.viewModelScope
import com.android.module_compose2.loading2.HomeVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeVM : BaseViewModel() {

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            showLoading(true)
            // 假设有耗时逻辑
            delay(2000)
            showLoading(false)
        }
    }
}


@Composable
fun HomeScreen(vm: HomeVM = HomeVM()) {
    Column(modifier = Modifier.systemBarsPadding()) {
        Text(text = "标题栏")
        LoadingComponent(component = vm.loadingComponent) {
            Box(modifier = Modifier.fillMaxSize()) {
                TextButton(onClick = { vm.loadData() }) {
                    Text(text = "Load Data")
                }
            }
        }
    }
}

@Composable
fun LoadingComponent(
    component: LoadingComponent,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: @Composable BoxScope.() -> Unit = {
        Box(
            modifier = Modifier
                .matchParentSize()
                .pointerInput(Unit) {
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    },
    content: @Composable BoxScope.() -> Unit
) {
    Box(modifier = modifier) {
        content()

        val showLoading = component.loading
        val containsCancelable= component.containsCancelable
        BackHandler(enabled = enabled && showLoading && containsCancelable) {
            component.cancelLoading()
        }
        if (enabled && showLoading) {
            loading()
        }
    }
}


