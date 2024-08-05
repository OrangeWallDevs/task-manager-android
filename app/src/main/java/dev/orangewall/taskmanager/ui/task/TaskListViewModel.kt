package dev.orangewall.taskmanager.ui.task

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.orangewall.taskmanager.data.task.Task
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor() : ViewModel() {
    private val _tasks = mutableStateListOf<Task>()
    val tasks: List<Task> get() = _tasks

    fun addTask(task: Task) {
        _tasks.add(task)
    }

    fun toggleTaskCompletion(taskId: String) {
        val task = _tasks.find { it.id == taskId } ?: return
        val updatedTask = Task(
            id = task.id,
            title = task.title,
            description = task.description,
            createdDate = task.createdDate,
            dueDate = task.dueDate,
            isCompleted = !task.isCompleted
        )
        _tasks[_tasks.indexOf(task)] = updatedTask
    }
}