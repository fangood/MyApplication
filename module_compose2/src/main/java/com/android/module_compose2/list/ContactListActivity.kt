package com.android.module_compose2.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.util.*

class ContactListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 生成示例联系人数据
        val contacts = listOf(
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
            Contact("Zack", "789-012-3456")
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContactListScreen(contacts = contacts)
                }
            }
        }
    }
}

// 主屏幕
@Composable
fun ContactListScreen(contacts: List<Contact>) {
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

    Box(modifier = Modifier.fillMaxSize()) {
        // 联系人列表
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            groupedContacts.forEach { group ->
                // 粘性标题
                stickyHeader {
                    ContactGroupHeader(group.initial.toString())
                }

                // 分组中的联系人
                items(group.contacts) { contact ->
                    ContactListItem(
                        contact = contact,
                        onContactClick = { /* 处理点击事件 */ }
                    )
                    HorizontalDivider(thickness = 0.5.dp, color = DividerDefaults.color)
                }
            }
        }

        // 右侧字母索引条
        AlphabetIndex(
            indexChars = indexChars,
            onIndexSelected = { initial ->
                // 滚动到选中的字母分组
                val index = groupedContacts.indexOfFirst { it.initial == initial }
                if (index != -1) {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index * 100) // 乘以一个足够大的数确保滚动到正确位置
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
        Button (
            onClick = {
                // 滚动到顶部
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        ) {
            Text("Scroll to Top")
        }
    }
}

// 分组标题组件
@Composable
fun ContactGroupHeader(initial: String) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth()
        )
    }
}

// 联系人列表项组件
@Composable
fun ContactListItem(contact: Contact, onContactClick: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onContactClick(contact) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 头像/图标
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Contact Avatar",
            modifier = Modifier
                .size(40.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
                .padding(8.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(16.dp))

        // 联系人信息
        Column {
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
    }
}

// 字母索引条组件
@Composable
fun AlphabetIndex(
    indexChars: List<Char>,
    onIndexSelected: (Char) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .width(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        indexChars.forEach { char ->
            Text(
                text = char.toString(),
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable { onIndexSelected(char) }
                    .padding(vertical = 2.dp)
            )
        }
    }
}

// 预览
@Preview(showBackground = true)
@Composable
fun ContactListPreview() {
    MaterialTheme {
        val sampleContacts = listOf(
            Contact("Alice", "123-456-7890"),
            Contact("Bob", "234-567-8901"),
            Contact("Charlie", "345-678-9012")
        )
        ContactListScreen(contacts = sampleContacts)
    }
}
