package com.pass.taskmanager.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pass.taskmanager.utils.Response
import com.pass.taskmanager.viewmodels.ProjectViewModel

@Composable
fun ProjectListPage(
    projectVm: ProjectViewModel,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Title") })
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(2.dp),
            content = {
                if (projectVm.projects.value is Response.Success) {
                    items(items = (projectVm.projects.value as Response.Success).data!!) { project ->
                        Card(modifier = Modifier.clip(RoundedCornerShape(4.dp))) {
                            Column {
                                AsyncImage(
                                    model = project.image,
                                    contentDescription = ""
                                )
                                Text(text = "Description of image")
                            }
                        }
                    }
                } else {

                }
            })
    }
    projectVm.getProjects()
}
