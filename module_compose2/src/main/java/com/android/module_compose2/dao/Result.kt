package com.android.module_compose2.dao

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    object Error : Result<Nothing>()
    data class Success<T>(val data: T) : Result<T>()
}
