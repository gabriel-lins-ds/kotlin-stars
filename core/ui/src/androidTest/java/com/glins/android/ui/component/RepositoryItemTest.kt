package com.glins.android.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.glins.android.testing.createRepository
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RepositoryItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_repository_name() {
        val repository = createRepository()

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText(repository.name)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_author_name() {
        val repository = createRepository()

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("by ${repository.author.name}")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_description_when_present() {
        val repository = createRepository(description = "Some description")

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertIsDisplayed()
    }

    @Test
    fun should_not_display_description_when_null() {
        val repository = createRepository(description = null)

        composeTestRule.setContent {
            RepositoryItem(repository = repository)
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertDoesNotExist()
    }

    @Test
    fun should_trigger_onClick_when_item_is_clicked() {
        val repository = createRepository()
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
