package com.android.module_compose2.list

// 按字母分组的联系人数据类
data class ContactGroup(
    val initial: Char,
    val contacts: List<Contact>
)