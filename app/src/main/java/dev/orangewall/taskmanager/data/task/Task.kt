package dev.orangewall.taskmanager.data.task

import java.util.Date

data class Task (
    val id: String,
    val title: String,
    val description: String,
    val dueDate: Date,
    val createdDate: Date,
    val isCompleted: Boolean = false
)