package com.android.module_compose2.scaffold

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

// 定义导航项的数据结构
data class DrawerItem(
    val id: String,
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Material3BackdropStyleDemo() {
    // 定义抽屉导航项
    val drawerItems = listOf(
        DrawerItem("home", "首页", Icons.Filled.Home),
        DrawerItem("favorites", "收藏", Icons.Filled.Favorite),
        DrawerItem("settings", "设置", Icons.Filled.Settings),
        DrawerItem("profile", "个人资料", Icons.Filled.Person)
    )

    // 状态管理
    var selectedItemId by remember { mutableStateOf("home") } // 当前选中的导航项
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // 抽屉状态
    val scope = rememberCoroutineScope() // 协程作用域，用于控制抽屉动画

    // 根据选中的导航项决定主内容区显示的文本
    val currentContentText = when (selectedItemId) {
        "home" -> "这里是首页内容区域。"
        "favorites" -> "这里展示你收藏的内容。"
        "settings" -> "在这里进行各项设置。"
        "profile" -> "这是你的个人资料页。"
        else -> "未知页面"
    }

    // 使用 ModalNavigationDrawer 作为根布局
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp) // 设置抽屉宽度
            ) {
                // 抽屉头部
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 这里可以放置用户头像、名称等
                    Text(
                        text = "应用导航",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }
                // 抽屉导航项列表
                drawerItems.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(text = item.label) },
                        selected = item.id == selectedItemId,
                        onClick = {
                            selectedItemId = item.id
                            scope.launch {
                                drawerState.close() // 选择后关闭抽屉
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        },
        content = {
            // 主内容区域使用 Scaffold
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        title = { Text("Material 3 风格应用") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        drawerState.open() // 点击图标打开抽屉
                                    }
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "打开导航")
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    // 主内容区
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = drawerItems.first { it.id == selectedItemId }.icon,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "欢迎来到 ${drawerItems.first { it.id == selectedItemId }.label}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = currentContentText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        }
    )
}