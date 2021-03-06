package com.pass.taskmanager.models

import android.net.Uri
import com.google.firebase.firestore.Exclude


data class Project(
    @get: Exclude var pid: String,
    var name: String,
    var description: String,
    var image: String,
    var adminUID: String,
    var members: List<String>,
) {
    constructor() : this("", "", "", "", "", listOf())
}
