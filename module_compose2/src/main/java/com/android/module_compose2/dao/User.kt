package com.android.module_compose2.dao

data class User(val id: Int = 0, val name: String) {
    constructor(name: String) : this(0, name)
}