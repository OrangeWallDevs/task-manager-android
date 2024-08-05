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
import dev.orangewall.taskmanager.ui.task.TaskListScreen
import dev.orangewall.taskmanager.ui.task.TaskListViewModel

enum class Routes {
    TaskList
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.TaskList.name, modifier = modifier) {
        composable(route = Routes.TaskList.name) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry(Routes.TaskList.name)
            }
            val viewModel: TaskListViewModel = hiltViewModel(parentEntry)
            TaskListScreen(viewModel = viewModel)
        }
    }
}