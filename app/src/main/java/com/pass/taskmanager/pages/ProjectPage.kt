package com.pass.taskmanager.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pass.taskmanager.R
import com.pass.taskmanager.models.Task
import com.pass.taskmanager.views.TaskItem

@Preview
@Composable
fun ProjectPage() {
    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Title") },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Favorite, null)
                    }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, null)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = {}) {
                            Icon(Icons.Filled.Refresh, null)
                        }
                        DropdownMenuItem(onClick = {}) {
                            Icon(Icons.Filled.Call, null)
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {}
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                Image(
                    painter = painterResource(id = R.drawable.project_background),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                )
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Project Name", fontSize = 40.sp, color = Color.Black)
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(2.dp),
                content = {
                    items(10) {
                        TaskItem(Task("Description", "Status", listOf()))
                    }
                })
        }
    }
}
