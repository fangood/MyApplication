package com.android.module_compose2.constraintlayout

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintSet
import androidx.wear.compose.material3.Button
//import androidx.compose.ui.semantics.Role.Companion.Button
//import androidx.wear.compose.material3.Button
import androidx.wear.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import com.android.module_compose2.ui.theme.MyApplicationTheme

@Preview
@Composable
fun PreviewTestConstraintLayout() {
    TestConstraintLayout()
}

@Composable
fun TestConstraintLayout() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (text1, buttonLeft, buttonRight) = createRefs()

        Text(
            text = "This is the content of Text",
            color = androidx.compose.ui.graphics.Color.Red,
            modifier = Modifier.constrainAs(text1) {
                centerTo(parent)
            })
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(buttonLeft) {
                top.linkTo(text1.bottom, margin = 16.dp)
                centerAround(text1.start)
            }) {
            Text(text = "Button Left")
        }
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(buttonRight) {
                top.linkTo(buttonLeft.top)
                centerAround(text1.end)
            }) {
            Text(text = "Button Right")
        }
    }

}

@Preview
@Composable
fun PreviewTestConstraintLayout2() {
    DecoupledConstraintLayoutExample()
}

@Composable
fun DecoupledConstraintLayoutExample() {
    // 注意：解耦方式可能需要提供 Context，尤其在 Preview 中
    val context = LocalContext.current
    val constraintSet = decoupledConstraints()

    ConstraintLayout(
        constraintSet = constraintSet, // 传入定义好的 ConstraintSet
        modifier = Modifier.size(200.dp)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Blue)
                .layoutId("myBox") // 通过 layoutId 关联约束
        )
        Text(
            text = "Hello",
            modifier = Modifier.layoutId("myText") // 通过 layoutId 关联约束
        )
    }
}


private fun decoupledConstraints(): ConstraintSet {
    return ConstraintSet {
        // 1. 根据 ID 创建引用
        val boxRef = createRefFor("myBox")
        val textRef = createRefFor("myText")

        // 2. 定义约束规则
        constrain(boxRef) {
            centerTo(parent)
        }
        constrain(textRef) {
            top.linkTo(boxRef.bottom, margin = 8.dp)
            start.linkTo(boxRef.start)
        }
    }
}

@Preview
@Composable
fun PreviewDynamicConstraintLayout() {
    MyApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column {
                DynamicConstraintLayout(innerPadding)
            }

        }

    }
}

@Composable
fun DynamicConstraintLayout(innerPadding: PaddingValues) {
    var expanded by remember { mutableStateOf(false) }
    // 定义两种不同的约束集
    val collapsedConstraints = remember {
        createCollapsedConstraints()
    }
    val expandedConstraints = remember {
        createExpandedConstraints()
    }

    // 根据状态选择当前的约束集
    val currentConstraintSet = if (expanded) expandedConstraints else collapsedConstraints

    Column (modifier = Modifier.padding(innerPadding)){
        ConstraintLayout(
            constraintSet = currentConstraintSet,
            modifier = Modifier
                .size(200.dp)
                .clickable { expanded = !expanded },
            // 可选：启用约束变化的动画
            animateChangesSpec = // 需要配置 animationSpec 等参数:cite[4]
                tween<Float>()
        ) {
            Box(modifier = Modifier
                .layoutId("box")
                .background(Color.Magenta))
            Text(
                color = Color.Blue,
                modifier = Modifier.layoutId("text"),
                text = "Click me!"
            )
        }
    }
}

fun createExpandedConstraints(): ConstraintSet {
    return ConstraintSet {
        val box = createRefFor("box")
        val text = createRefFor("text")

        constrain(box) {
            centerTo(parent)
        }
        constrain(text) {
            top.linkTo(box.bottom, margin = 8.dp)
            start.linkTo(box.start)
        }
    }
}

fun createCollapsedConstraints(): ConstraintSet {
    return ConstraintSet {
        val box = createRefFor("box")
        val text = createRefFor("text")

        constrain(box) {
            centerHorizontallyTo(parent)
        }
        constrain(text) {
            top.linkTo(box.bottom, margin = 8.dp)
            start.linkTo(box.start)
        }
    }
}
