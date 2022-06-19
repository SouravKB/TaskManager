package com.pass.taskmanager.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pass.taskmanager.utils.Response
import com.pass.taskmanager.viewmodels.ProjectViewModel
import com.pass.taskmanager.views.ProgressBar

@Composable
fun ProjectListPage(
    projectVm: ProjectViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Title") })
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(2.dp)
        ) {
            if (projectVm.projects.value is Response.Success) {
                items(items = (projectVm.projects.value as Response.Success).data!!) { project ->
                    Card(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .fillMaxWidth()
                            .padding(5.dp),
                        elevation = 10.dp
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .height(190.dp)
                                    .fillMaxWidth()
                                    .padding(end = 5.dp),
                                model = project.image,
                                contentDescription = ""
                            )
                            Text(
                                text = project.description,
                                fontStyle = FontStyle(20),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            } else {
                item {
                    ProgressBar()
                }
            }
        }
    }
}
