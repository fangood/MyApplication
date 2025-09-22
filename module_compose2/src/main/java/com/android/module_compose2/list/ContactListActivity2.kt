package com.android.module_compose2.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.util.*

class ContactListActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 生成示例联系人数据
        val contacts = generateSampleContacts()

        setContent {
            MaterialTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { contentPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(contentPadding),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ContactListScreen2(contacts = contacts)
                    }
                }

            }
        }
    }

    private fun generateSampleContacts(): List<Contact> {
        return listOf(
            Contact("Alice", "123-456-7890"),
            Contact("Andrew", "234-567-8901"),
            Contact("Bob", "345-678-9012"),
            Contact("Brian", "456-789-0123"),
            Contact("Charlie", "567-890-1234"),
            Contact("Chris", "678-901-2345"),
            Contact("David", "789-012-3456"),
            Contact("Daniel", "890-123-4567"),
            Contact("Emma", "901-234-5678"),
            Contact("Ethan", "012-345-6789"),
            Contact("Frank", "123-456-7890"),
            Contact("Fiona", "234-567-8901"),
            Contact("George", "345-678-9012"),
            Contact("Grace", "456-789-0123"),
            Contact("Henry", "567-890-1234"),
            Contact("Hannah", "678-901-2345"),
            Contact("Ivy", "789-012-3456"),
            Contact("Ian", "890-123-4567"),
            Contact("Jack", "901-234-5678"),
            Contact("Julia", "012-345-6789"),
            Contact("Kevin", "123-456-7890"),
            Contact("Katie", "234-567-8901"),
            Contact("Liam", "345-678-9012"),
            Contact("Luna", "456-789-0123"),
            Contact("Mike", "567-890-1234"),
            Contact("Mia", "678-901-2345"),
            Contact("Nathan", "789-012-3456"),
            Contact("Nora", "890-123-4567"),
            Contact("Oscar", "901-234-5678"),
            Contact("Olivia", "012-345-6789"),
            Contact("Peter", "123-456-7890"),
            Contact("Paula", "234-567-8901"),
            Contact("Quinn", "345-678-9012"),
            Contact("Rachel", "456-789-0123"),
            Contact("Ryan", "567-890-1234"),
            Contact("Sara", "678-901-2345"),
            Contact("Steve", "789-012-3456"),
            Contact("Tina", "890-123-4567"),
            Contact("Tom", "901-234-5678"),
            Contact("Uma", "012-345-6789"),
            Contact("Victor", "123-456-7890"),
            Contact("Violet", "234-567-8901"),
            Contact("William", "345-678-9012"),
            Contact("Wendy", "456-789-0123"),
            Contact("Xander", "567-890-1234"),
            Contact("Yara", "678-901-2345"),
            Contact("Zack", "789-012-3456"),
            // 添加更多数据以便更好地测试滚动
            Contact("Aaron", "111-111-1111"),
            Contact("Abigail", "222-222-2222"),
            Contact("Adam", "333-333-3333"),
            Contact("Alex", "444-444-4444"),
            Contact("Amanda", "555-555-5555"),
            Contact("Amy", "666-666-6666"),
            Contact("Benjamin", "777-777-7777"),
            Contact("Brandon", "888-888-8888"),
            Contact("Catherine", "999-999-9999"),
            Contact("Cynthia", "000-000-0000")
        )
    }
}


// 主屏幕
@Composable
fun ContactListScreen2(contacts: List<Contact>) {
    // 将联系人按首字母分组
    val groupedContacts = remember(contacts) {
        contacts.groupBy { it.name.uppercase().first() }
            .map { (initial, contacts) ->
                ContactGroup(initial, contacts.sortedBy { it.name })
            }
            .sortedBy { it.initial }
    }

    // 获取字母索引列表
    val indexChars = remember(groupedContacts) {
        groupedContacts.map { it.initial }
    }

    // 记录滚动状态
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 是否显示返回顶部按钮
    val showScrollToTop by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 联系人列表
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            groupedContacts.forEach { group ->
                // 粘性标题
                stickyHeader {
                    ContactGroupHeader2(group.initial.toString())
                }

                // 分组中的联系人
                items(group.contacts) { contact ->
                    ContactListItem2(
                        contact = contact,
                        onContactClick = {
                            // 处理点击事件
                            println("Clicked on ${contact.name}")
                        }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = DividerDefaults.color)
                }
            }
        }

        // 右侧字母索引条
        AlphabetIndex2(
            indexChars = indexChars,
            onIndexSelected = { initial ->
                // 滚动到选中的字母分组
                val index = groupedContacts.indexOfFirst { it.initial == initial }
                if (index != -1) {
                    coroutineScope.launch {
                        // 计算该分组的起始位置
                        var itemIndex = 0
                        for (i in 0 until index) {
                            itemIndex += groupedContacts[i].contacts.size + 1 // +1 为标题
                        }
                        listState.animateScrollToItem(itemIndex)
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )

        // 返回顶部按钮 - 使用动画显示/隐藏
        AnimatedVisibility(
            visible = showScrollToTop,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            ScrollToTopButton(
                onClick = {
                    coroutineScope.launch {
                        // 平滑滚动到顶部
                        listState.animateScrollToItem(
                            index = 0,
                            scrollOffset = 0
                        )
                    }
                }
            )
        }
    }
}

// 返回顶部按钮组件
@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
            .shadow(4.dp, CircleShape)
            .size(56.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowUp,
            contentDescription = "返回顶部"
        )
    }
}

// 分组标题组件
@Composable
fun ContactGroupHeader2(initial: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth()
        )
    }
}

// 联系人列表项组件
@Composable
fun ContactListItem2(contact: Contact, onContactClick: (Contact) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onContactClick(contact) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 头像/图标
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = contact.name.take(1).uppercase(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // 联系人信息
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contact.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = contact.phone,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // 右侧菜单按钮
            IconButton(onClick = { /* 更多操作 */ }) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "更多选项",
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

// 字母索引条组件
@Composable
fun AlphabetIndex2(
    indexChars: List<Char>,
    onIndexSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .width(28.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f),
                shape = CircleShape
            )
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        indexChars.forEach { char ->
            Text(
                text = char.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable { onIndexSelected(char) }
                    .padding(vertical = 2.dp)
                    .width(20.dp)
            )
        }
    }
}

// 搜索栏组件（额外功能）
@Composable
fun SearchBar(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchText,
        onValueChange = {
            searchText = it
            onSearch(it)
        },
        placeholder = { Text("搜索联系人...") },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Default.Person, contentDescription = "搜索")
        }
    )
}

// 预览
@Preview(showBackground = true)
@Composable
fun ContactListPreview2() {
    MaterialTheme {
        val sampleContacts = listOf(
            Contact("Alice", "123-456-7890"),
            Contact("Bob", "234-567-8901"),
            Contact("Charlie", "345-678-9012")
        )
        ContactListScreen(contacts = sampleContacts)
    }
}

@Preview(showBackground = true)
@Composable
fun ScrollToTopButtonPreview() {
    MaterialTheme {
        ScrollToTopButton(onClick = {})
    }
}