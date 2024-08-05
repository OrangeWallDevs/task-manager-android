package dev.orangewall.taskmanager.ui.task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.orangewall.taskmanager.R
import dev.orangewall.taskmanager.data.task.Task
import dev.orangewall.taskmanager.util.formatDateToISO


@Composable
fun TaskListScreen(
    viewModel: TaskListViewModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                )
        ) {
            TaskList(tasks = viewModel.tasks, completeTask = viewModel::toggleTaskCompletion)
        }
    }
}

@Composable
fun TaskList(
    tasks: List<Task>,
    completeTask: (taskId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val taskItemContentDescription = stringResource(id = R.string.task_item)
    if (tasks.isEmpty()) {
        Text(text = stringResource(id = R.string.no_tasks))
    } else {
        LazyColumn(modifier = modifier) {
            items(tasks) { task ->
                Row(
                    modifier = Modifier.semantics {
                        contentDescription = taskItemContentDescription
                    }
                ) {
                    Checkbox(
                        checked = task.isCompleted,
                        onCheckedChange = { completeTask(task.id) }
                    )
                    Text(text = task.title, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    Text(text = task.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    Text(text = formatDateToISO(task.dueDate))
                }
            }
        }
    }
}