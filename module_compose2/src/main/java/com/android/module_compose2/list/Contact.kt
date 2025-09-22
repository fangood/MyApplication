package com.android.module_compose2.list

import java.util.UUID

// 联系人数据类
data class Contact(
    val name: String,
    val phone: String,
    val id: Long = UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
)

