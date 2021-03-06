package com.pass.taskmanager.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pass.taskmanager.R
import com.pass.taskmanager.models.Task
import com.pass.taskmanager.viewmodels.ProjectViewModel
import com.pass.taskmanager.views.TaskItem
import java.util.*

@Composable
fun ProjectPage(
    navigateToAuthPage: () -> Unit
) {
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
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add,"" )
            }
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.BottomStart, modifier = Modifier.padding(10.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
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
                        TaskItem(Task("", "Description", "Status", listOf(), Date()))
                    }
                })
        }
    }
}
