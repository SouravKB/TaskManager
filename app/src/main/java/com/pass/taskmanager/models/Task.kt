package com.pass.taskmanager.models

import com.google.firebase.firestore.Exclude
import java.util.*

data class Task(
    @get: Exclude var tid: String,
    var description: String,
    var status: String,
    var members: List<Person>,
    var deadline: Date,
)
