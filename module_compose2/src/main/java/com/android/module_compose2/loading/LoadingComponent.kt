package com.android.module_compose2.loading

import androidx.compose.runtime.Stable

@Stable
interface LoadingComponent {

    val loading: Boolean

    //拦截返回键
    val containsCancelable: Boolean

    fun showLoading(show: Boolean)
    fun cancelLoading()
}