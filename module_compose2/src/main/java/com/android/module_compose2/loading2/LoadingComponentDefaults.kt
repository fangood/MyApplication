package com.android.module_compose2.loading2

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import com.android.module_compose2.loading.Defaults
import com.android.module_compose2.loading.ILoadingComponentDefaults
import com.android.module_compose2.loading.LoadingComponent

open class LoadingComponentDefaults : Defaults(), ILoadingComponentDefaults {

    companion object : Target<LoadingComponentDefaults>(LoadingComponentDefaults())

    override val enabled: Boolean = true

    final override val loading: @Composable BoxScope.() -> Unit = { /*EMPTY*/ }

    @Composable
    override fun LoadingComponent(
        component: LoadingComponent,
        modifier: Modifier,
        enabled: Boolean,
        loading: @Composable BoxScope.() -> Unit,
        content: @Composable BoxScope.() -> Unit
    ) {
        LoadingComponentImpl(
            component = component,
            modifier = modifier,
            enabled = enabled,
            loading = loading,
            content = content
        )
    }

    @Composable
    private fun LoadingComponentImpl(
        component: LoadingComponent,
        modifier: Modifier,
        enabled: Boolean,
        loading: @Composable BoxScope.() -> Unit,
        content: @Composable BoxScope.() -> Unit
    ) {
        val loadingContent = if (loading !== this.loading) loading else {
            {
                Box(
                    modifier = Modifier.Companion
                        .matchParentSize()
                        .pointerInput(Unit) {
                        },
                    contentAlignment = Alignment.Companion.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.Companion.align(Alignment.Companion.Center))
                }
            }
        }

        Box(modifier = modifier) {
            content()

            val showLoading = component.loading
            val containsCancelable = component.containsCancelable
            BackHandler(enabled = enabled && showLoading && containsCancelable) {
                component.cancelLoading()
            }
            if (enabled && showLoading) {
                loadingContent()
            }
        }
    }
}