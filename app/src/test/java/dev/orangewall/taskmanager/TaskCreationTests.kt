package dev.orangewall.taskmanager

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isOff
import androidx.compose.ui.test.isToggleable
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.orangewall.taskmanager.data.task.Task
import dev.orangewall.taskmanager.util.formatDateToISO
import io.github.serpro69.kfaker.Faker
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.shadows.ShadowLog
import java.util.Date
import java.util.UUID

@RunWith(AndroidJUnit4::class)
class TaskCreationTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val faker = Faker()

    @Before
    fun setUp() {
        ShadowLog.stream = System.out
    }

    @Test
    fun `add a new task`() {
        val addTaskButtonLabel = composeTestRule.activity.getString(R.string.add_task)
        composeTestRule.onNodeWithText(addTaskButtonLabel)
            .assertExists()
            .performClick()

        val taskTitleFieldContentDescription = composeTestRule.activity.getString(R.string.task_title_field)
        val taskDescriptionFieldContentDescription = composeTestRule.activity.getString(R.string.task_description_field)
        val taskDueDateFieldContentDescription = composeTestRule.activity.getString(R.string.task_due_date_field)

        val task = Task(
            id = UUID.randomUUID().toString(),
            title = faker.lorem.words(),
            description = faker.lorem.words(),
            createdDate = Date(),
            dueDate = Date(),
            isCompleted = faker.random.nextBoolean(),
        )

        composeTestRule.onNodeWithContentDescription(taskTitleFieldContentDescription)
            .performTextInput(task.title)
        composeTestRule.onNodeWithContentDescription(taskDescriptionFieldContentDescription)
            .performTextInput(task.description)
        composeTestRule.onNodeWithContentDescription(taskDueDateFieldContentDescription)
            .performTextInput(task.dueDate.toString())

        val saveButtonLabel = composeTestRule.activity.getString(R.string.save_task_button)
        composeTestRule.onNodeWithText(saveButtonLabel)
            .assertExists()
            .performClick()

        val taskItemContentDescription = composeTestRule.activity.getString(R.string.task_item)
        val newTaskItem = composeTestRule.onAllNodesWithContentDescription(taskItemContentDescription)
            .filterToOne(hasText(text = task.title, substring = true, ignoreCase = true))
        newTaskItem.assertExists()
        newTaskItem.assertTextContains(value = task.description, substring = true, ignoreCase = true)
        newTaskItem.assertTextContains(value = formatDateToISO(task.dueDate), substring = true, ignoreCase = true)
        newTaskItem.onChildren().filterToOne(isToggleable()).assertIsOff()
    }
}