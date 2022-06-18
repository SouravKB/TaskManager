package com.pass.taskmanager.models

import android.graphics.Bitmap
import android.net.Uri
import com.google.firebase.firestore.Exclude

data class Person(
    var name: String,
    var email: String,
    var avatar: Uri,
    var phone_no: String,
    @get:Exclude var uid: String,
)
