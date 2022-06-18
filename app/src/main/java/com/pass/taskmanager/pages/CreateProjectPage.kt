package com.pass.taskmanager.pages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.rounded.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pass.taskmanager.views.EditTextField

@Preview
@Composable
fun CreateProjectPage() {
    val imageData: MutableState<Uri?> = remember { mutableStateOf(null) }
    var description by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                imageData.value = it
            }
        }
    Scaffold(
        topBar = { TopBar() },
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().padding(bottom = 15.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {
                    launcher.launch(
                        "image/*"
                    )
                }, content = {
                    if (imageData.value == null)
                        Text(text = "Add Profile Image")
                    else
                        Text(text = "Change Profile Image")
                })
                DisplayImage(imageData, LocalContext.current)
                EditTextField(
                    value = title,
                    placeholder = "Enter the tile",
                    label = "Project Title",
                    onChange = {
                        title = it
                    }
                )
                EditTextField(
                    value = description,
                    placeholder = "Enter the description",
                    label = "Project Description",
                    onChange = {
                        description = it
                    }
                )
            }
            Button(onClick = {}, content = {
                Text(text = "Icon Button", fontSize = 20.sp)
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = null,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            )
        }
    }
}

@Composable
fun TopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Create Project",
                Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Left,
                fontSize = 20.sp,
            )
        }
    )
}

@Composable
fun DisplayImage(imageData: MutableState<Uri?>, context: Context) {
    imageData.let {
        val bitmap: MutableState<Bitmap?> = remember { mutableStateOf(null) }
        val uri = it.value
        if (uri != null) {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, uri)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver, uri)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .background(color = Color.Gray)
                    .size(200.dp, 200.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = "Add images",
                    modifier = Modifier.fillMaxSize(0.7f)
                )
            }
        }
    }
}
