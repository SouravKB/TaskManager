package com.pass.taskmanager.models

data class Task(
    val name: String,
    val status: String,
    val members: List<Person>,
)
