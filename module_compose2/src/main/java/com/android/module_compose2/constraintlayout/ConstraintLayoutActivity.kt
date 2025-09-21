package com.android.module_compose2.constraintlayout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.android.module_compose2.ui.theme.MyApplicationTheme


class ConstraintLayoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Column {
                        //            TestConstraintLayout()
                        DynamicConstraintLayout(innerPadding)
                    }
                }
            }
        }
    }
}
