package com.pass.taskmanager.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pass.taskmanager.R
import com.pass.taskmanager.models.Task
import com.pass.taskmanager.views.TaskItem

@Preview
@Composable
fun ProjectMembers() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Project details") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(contentAlignment = Alignment.BottomStart) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "",
                    modifier = Modifier.fillMaxWidth(),
                )
                Box(modifier = Modifier.padding(12.dp)) {
                    Text(text = "Project Name", fontSize = 40.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Card(
                elevation = 0.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "description", fontSize = 20.sp, modifier = Modifier.padding(
                        10.dp
                    )
                )
            }
            LazyColumn(
                contentPadding = PaddingValues(2.dp),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(10) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.default_contact_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.size(48.dp),
                                contentDescription = "Profile picture holder"
                            )
                            Column() {
                                Text(
                                    text = "Contact",
                                    fontSize = 30.sp,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                                Text(
                                    text = "number",
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(start = 10.dp),
                                    color = Color.LightGray
                                )
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(Icons.Filled.Delete, "")
                        }
                    }
                })
        }
    }
}