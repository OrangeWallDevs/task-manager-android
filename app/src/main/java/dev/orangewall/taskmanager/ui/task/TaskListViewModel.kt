package dev.orangewall.taskmanager.ui.task

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dev.orangewall.taskmanager.data.task.Task

class TaskListViewModel : ViewModel() {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(task: Task) {
        _tasks.add(task)
    }
}