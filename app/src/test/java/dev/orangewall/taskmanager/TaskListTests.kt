package dev.orangewall.taskmanager

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.orangewall.taskmanager.data.task.Task
import dev.orangewall.taskmanager.ui.task.TaskListViewModel
import io.github.serpro69.kfaker.Faker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog
import java.util.Date
import java.util.UUID
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class TaskListTests {


    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val faker = Faker()
    private lateinit var taskListViewModel: TaskListViewModel

    @Before
    @Throws(Exception::class)
    fun setUp() {
        ShadowLog.stream = System.out
        taskListViewModel =
            (composeTestRule.activity.application as TaskManagerApplication).appContainer.taskListViewModel
    }

    @Test
    fun `when there are no tasks, show no tasks message`() {
        val noTasksMessage = composeTestRule.activity.getString(R.string.no_tasks)
        composeTestRule.onNodeWithText(noTasksMessage).assertExists()
    }

    @Test
    fun `when there are tasks, list them`() {
        val tasks = List(5) {
            Task(
                id = UUID.randomUUID().toString(),
                title = faker.lorem.words(),
                description = faker.lorem.words(),
                createdDate = Date(),
                dueDate = Date(),
                isCompleted = faker.random.nextBoolean(),
            )
        }
        composeTestRule.activity.runOnUiThread {
            tasks.forEach { task ->
                taskListViewModel.addTask(task)
            }
        }
        tasks.forEach { task ->
            composeTestRule.onNodeWithText(task.title).assertExists()
        }
    }
}