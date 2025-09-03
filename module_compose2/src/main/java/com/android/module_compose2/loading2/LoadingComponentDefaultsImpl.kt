package com.android.module_compose2.loading2

import android.app.Application
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.android.module_compose2.loading.LoadingComponent

class LoadingComponentDefaultsImpl : LoadingComponentDefaults() {

    override val enabled: Boolean = true

    @Composable
    override fun LoadingComponent(
        component: LoadingComponent,
        modifier: Modifier,
        enabled: Boolean,
        loading: @Composable BoxScope.() -> Unit,
        content: @Composable BoxScope.() -> Unit
    ) {
        val loadingContent = if (loading !== this.loading) loading else {
            {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .pointerInput(Unit) {
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // 改成文本显示
                    Text(text = "加载中...")
                }
            }
        }
        super.LoadingComponent(
            component = component,
            modifier = modifier,
            enabled = enabled,
            loading = loadingContent,
            content = content
        )
    }
}


