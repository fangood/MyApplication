package com.android.module_compose2.database

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.android.module_compose2.dao.User

class UserPreviewParameterProvider: PreviewParameterProvider<User> {
    override val values: Sequence<User> = sequenceOf(User("张三"),User("李四"),User("王五"))
}