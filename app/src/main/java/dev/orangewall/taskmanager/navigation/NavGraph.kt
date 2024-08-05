package dev.orangewall.taskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import dev.orangewall.taskmanager.ui.task.TaskCreationScreen
import dev.orangewall.taskmanager.ui.task.TaskListScreen
import dev.orangewall.taskmanager.ui.task.TaskListViewModel

enum class Routes {
    TaskList,
    TaskCreation
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val viewModel = hiltViewModel<TaskListViewModel>()
    NavHost(navController = navController, startDestination = Routes.TaskList.name, modifier = modifier) {
        composable(route = Routes.TaskList.name) {
            TaskListScreen(
                onNavigateToTaskCreation = { navController.navigate(Routes.TaskCreation.name) },
                viewModel = viewModel
            )
        }
        composable(route = Routes.TaskCreation.name) {
            TaskCreationScreen(
                onNavigateToTaskList = { navController.navigate(Routes.TaskList.name) },
                viewModel = viewModel
            )
        }
    }
}