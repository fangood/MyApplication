package com.android.module_compose2.loading

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface ILoadingComponentDefaults {

    val enabled: Boolean

    val loading: @Composable BoxScope.() -> Unit

    @Composable
    fun LoadingComponent(
        component: LoadingComponent,
        modifier: Modifier,
        enabled: Boolean,
        loading: @Composable BoxScope.() -> Unit,
        content: @Composable BoxScope.() -> Unit
    )
}
