package com.pass.taskmanager.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Preview
@Composable
fun ProjectListPage() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Title") })
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(2.dp),
            content = {
                items(10) {
                    Card(modifier = Modifier.clip(RoundedCornerShape(4.dp))) {
                        Column() {
                            AsyncImage(model = "https://www.sanjayjangam.com/wp-content/uploads/2021/10/good-morning-images.jpg", contentDescription ="" )
                            Text(text = "Description of image")
                        }
                    }
                }
            })
    }
}
