package com.android.module_compose2.scaffold

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.android.module_compose2.ui.theme.MyApplicationTheme

class ScaffoldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
//                ScaffoldActivityUI()
                ScaffoldWithFab()
            }
        }
    }
}

@Preview
@Composable
fun ScaffoldActivityPreview() {
    MyApplicationTheme {
        ScaffoldActivityUI()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldActivityUI() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Scaffold") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                // 新版本中通过content配置底部栏内容
                Text("Bottom App Bar Content")
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, "FAB")
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
    ) { paddingValues ->
        // 主内容区
        Box(modifier = Modifier.padding(paddingValues)) {
            Text("Main Content")
        }
    }
}
@Preview
@Composable
fun ScaffoldWithFabPreview() {
    MyApplicationTheme {
        ScaffoldWithFab()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldWithFab() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("App Title") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Menu, "Menu")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Favorite, "Favorite")
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.Search, "Search")
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {},
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Filled.Add, "Add")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            Text("Main Content")
        }
    }
}

