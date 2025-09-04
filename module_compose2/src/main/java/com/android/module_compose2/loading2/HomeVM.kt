package com.android.module_compose2.loading2

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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.module_compose2.loading.BaseViewModel
import com.android.module_compose2.loading.LoadingComponent
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
fun HomeScreen(vm: HomeVM = viewModel ()) {
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
    enabled: Boolean = LoadingComponentDefaults.instance.enabled,
    loading: @Composable BoxScope.() -> Unit = LoadingComponentDefaults.instance.loading,
    content: @Composable BoxScope.() -> Unit
) {
    LoadingComponentDefaults.instance.LoadingComponent(
        component = component,
        modifier = modifier,
        enabled = enabled,
        loading = loading,
        content = content
    )
}



