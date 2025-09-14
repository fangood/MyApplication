package com.android.module_compose2.basic


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlin.math.max
import kotlin.math.min

@Preview
@Composable
fun InputFeaturesPreview() {
    InputFeaturesScreen()
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputFeaturesScreen() {
    var text by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var creditCard by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // 1. ImeAction 示例
        Text(
            text = "ImeAction 示例 (搜索功能)",
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("输入搜索内容") },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    // 执行搜索操作
                    showDialog = true
                    keyboardController?.hide()
                }
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. VisualTransformation 示例 - 密码
        Text(
            text = "VisualTransformation 示例 (密码隐藏)",
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("输入密码") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. 自定义 VisualTransformation - 信用卡号格式化
        Text(
            text = "自定义 VisualTransformation (信用卡号格式化)",
            style = MaterialTheme.typography.bodyMedium
        )
        OutlinedTextField(
            value = creditCard,
            onValueChange = {
                if (it.length <= 16 && it.all { c -> c.isDigit() }) {
                    creditCard = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("信用卡号") },
            visualTransformation = CreditCardTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            )
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("搜索触发") },
            text = { Text("您触发了搜索操作，搜索内容: $text") },
            confirmButton = {
                Button(onClick = { showDialog = false }) {
                    Text("确定")
                }
            }
        )
    }
}

// 自定义信用卡号格式化转换
class CreditCardTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val original = text.text
        val formatted = buildString {
            for (i in original.indices) {
                append(original[i])
                if ((i + 1) % 4 == 0 && i != original.lastIndex) {
                    append(" ")
                }
            }
        }

        return TransformedText(
            text = AnnotatedString(formatted),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    // 每4个数字加一个空格，但不超过原始长度
                    return offset + min(offset / 4, (formatted.length - original.length))
                }

                override fun transformedToOriginal(offset: Int): Int {
                    // 减去空格数，但不少于0
                    return max(0, offset - offset / 5)
                }
            }
        )
    }
}

