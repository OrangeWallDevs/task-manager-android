package dev.orangewall.taskmanager.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.orangewall.taskmanager.data.task.Task
import dev.orangewall.taskmanager.ui.components.DatePickerModal
import dev.orangewall.taskmanager.ui.theme.TaskManagerTheme
import dev.orangewall.taskmanager.util.formatDateToISO
import java.util.Date
import java.util.UUID

@Composable
fun TaskCreationScreen(
    onNavigateToTaskList: () -> Unit,
    viewModel: TaskListViewModel,
    modifier: Modifier = Modifier
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf<Long?>(null) }
    var errors by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = taskTitle,
            onValueChange = { taskTitle = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        OutlinedTextField(
            value = taskDescription,
            onValueChange = { taskDescription = it },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary
            )
        )
        Row {
            if (taskDueDate != null) {
                val date = Date(taskDueDate!!)
                val formattedDate = formatDateToISO(date)
                Text("Due Date: $formattedDate")
            } else {
                Text("No due date selected")
            }
            Button(onClick = { showDatePicker = true }) {
                Text("Select Due Date")
            }
        }
        if (showDatePicker) {
            DatePickerModal(
                onDateSelected = {
                    taskDueDate = it
                    showDatePicker = false
                },
                onDismiss = { showDatePicker = false }
            )
        }
        Button(onClick = {
            validateTask(taskTitle, taskDescription, taskDueDate) {
                errors = it
            }
            if (errors.isEmpty()) {
                viewModel.addTask(
                    Task(
                        UUID.randomUUID().toString(),
                        taskTitle,
                        taskDescription,
                        Date(),
                        Date(taskDueDate!!)
                    )
                )
                onNavigateToTaskList()
            }
        },
            colors = ButtonDefaults.buttonColors()) {
            Text("Add Task", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

fun validateTask(
    title: String,
    description: String,
    dueDate: Long?,
    updateErrors: (String) -> Unit
) {
    var formErrors = ""
    if (title.isEmpty()) {
        formErrors += "Title is required\n"
    }
    if (description.isEmpty()) {
        formErrors += "Description is required\n"
    }
    if (dueDate == null) {
        formErrors += "Due date is required"
    }
    updateErrors(formErrors)
}


