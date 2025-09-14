package com.android.module_compose2.basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AnnotatedStringExampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    AnnotatedStringExamples()
                }
            }
        }
    }
}
@Preview
@Composable
fun AnnotatedStringExamplePreview() {
    AnnotatedStringExamples()
}

@Composable
fun AnnotatedStringExamples() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // 示例 1: 基本样式混合
        Text(
            text = buildAnnotatedStringWithMultipleStyles(),
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 示例 2: 可点击的文本部分
        var clickResult by remember { mutableStateOf("点击上面的链接") }

        Text(
            text = buildClickableAnnotatedString(),
            modifier = Modifier.padding(vertical = 8.dp),
            style = TextStyle(fontSize = 18.sp)
        )

        Text(
            text = clickResult,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 示例 3: 复杂的富文本组合
        Text(
            text = buildComplexAnnotatedString(),
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 8.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}

// 示例 1: 创建包含多种样式的 AnnotatedString
fun buildAnnotatedStringWithMultipleStyles(): AnnotatedString {
    return buildAnnotatedString {
        // 普通文本
        append("这是一个")

        // 加粗文本
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append("加粗的")
        }

        append("文本，还有")

        // 斜体文本
        withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
            append("斜体")
        }

        append("和")

        // 带颜色的文本
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("蓝色文字")
        }

        append("。")
    }
}

// 示例 2: 创建可点击的 AnnotatedString
fun buildClickableAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        append("欢迎访问我们的")

        // 添加可点击的链接部分
        withLink(
            link = LinkAnnotation.Url(
                url = "https://example.com"
            )
        ) {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("官方网站")
            }
        }

        append("或者我们的")

        withLink(
            link = LinkAnnotation.Url(
                url = "https://blog.example.com"
            )
        ) {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append("博客")
            }
        }

        append("获取更多信息。")
    }
}

// 示例 3: 创建复杂的 AnnotatedString
fun buildComplexAnnotatedString(): AnnotatedString {
    return buildAnnotatedString {
        // 添加不同字体的文本
        withStyle(style = SpanStyle(fontFamily = FontFamily.SansSerif)) {
            append("多种样式组合: ")
        }

        // 背景色
        withStyle(style = SpanStyle(background = Color.LightGray)) {
            append("背景色")
        }

        append(" + ")

        // 删除线
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.LineThrough,
                color = Color.Red
            )
        ) {
            append("删除线")
        }

        append(" + ")

        // 上标
        withStyle(style = SpanStyle(baselineShift = BaselineShift.Superscript)) {
            append("上标")
        }

        append(" + ")

        // 下标
        withStyle(style = SpanStyle(baselineShift = BaselineShift.Subscript)) {
            append("下标")
        }
    }
}

// 示例 4: 使用 ParagraphStyle 进行段落级格式化
@Composable
fun ParagraphStyleExample() {
    Text(
        text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(lineHeight = 30.sp)) {
                append("这是第一行文本，具有自定义行高。")
                append("\n")
                append("这是第二行文本，继承了相同的段落样式。")
            }
            append("\n\n")
            withStyle(style = ParagraphStyle(textIndent = TextIndent(20.sp))) {
                append("这是缩进的段落文本，适用于长篇文章的排版需求。")
            }
        },
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
fun DynamicAnnotatedStringExamplePreview() {
    DynamicAnnotatedStringExample(listOf("Item 1", "Item 2", "Item 3"))
}
// 示例 5: 动态构建 AnnotatedString
@Composable
fun DynamicAnnotatedStringExample(items: List<String>) {
    val annotatedText = remember(items) {
        buildAnnotatedString {
            items.forEachIndexed { index, item ->
                if (index % 2 == 0) {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("$item ")
                    }
                } else {
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append("$item ")
                    }
                }
            }
        }
    }

    Text(
        text = annotatedText,
        modifier = Modifier.padding(16.dp)
    )
}