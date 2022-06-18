package com.pass.taskmanager.models

import android.graphics.Bitmap
import android.net.Uri

data class Person(
    val name: String,
    val email: String,
    val avatar: Uri,
)
