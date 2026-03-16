package com.glins.android.apps.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RepositoryItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeRepository(
        description: String? = "Repository description"
    ) = Repository(
        id = 1,
        index = 1,
        name = "kotlin",
        description = description,
        url = "https://github.com/JetBrains/kotlin",
        stars = 52439,
        forks = 6050,
        author = RepositoryAuthor(
            name = "JetBrains",
            iconUrl = "",
            profileUrl = "https://github.com/JetBrains"
        )
    )

    @Test
    fun should_display_repository_name() {
        val repository = fakeRepository()

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText(repository.name)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_author_name() {
        val repository = fakeRepository()

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("by ${repository.author.name}")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_description_when_present() {
        val repository = fakeRepository("Some description")

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertIsDisplayed()
    }

    @Test
    fun should_not_display_description_when_null() {
        val repository = fakeRepository(null)

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertDoesNotExist()
    }

    @Test
    fun should_trigger_onClick_when_item_is_clicked() {
        val repository = fakeRepository()
        var clicked = false

        composeTestRule.setContent {
            RepositoryItem(
                repository = repository,
                onClick = { clicked = true }
            )
        }

        composeTestRule
            .onNodeWithText(repository.name)
            .performClick()

        assertTrue(clicked)
    }
}
