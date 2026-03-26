package com.glins.android.apps.ui.repositorydetails

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.glins.android.domain.model.Repository
import com.glins.android.domain.model.RepositoryAuthor
import com.glins.android.apps.ui.testtag.TestTags
import com.glins.android.repository_details.RepositoryDetailsContent
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RepositoryDetailsContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeRepository(
        description: String? = "Repository description"
    ) = Repository(
        id = 1,
        index = 10,
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
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.DETAILS_BODY_REPO_NAME)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_author_name() {
        val repository = fakeRepository()

        composeTestRule.setContent {
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText(repository.author.name)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_description_when_present() {
        val repository = fakeRepository("Some description")

        composeTestRule.setContent {
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Some description")
            .assertIsDisplayed()
    }

    @Test
    fun should_not_display_description_when_null() {
        val repository = fakeRepository(null)

        composeTestRule.setContent {
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = {},
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Description")
            .assertDoesNotExist()
    }

    @Test
    fun should_call_onOpenUrlClick_when_open_repository_button_is_clicked() {
        val repository = fakeRepository()
        var clicked = false

        composeTestRule.setContent {
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = { clicked = true },
                onBackClick = {}
            )
        }

        composeTestRule
            .onNodeWithText("Open on GitHub")
            .performClick()

        assertTrue(clicked)
    }

    @Test
    fun should_call_onBackClick_when_back_icon_is_clicked() {
        val repository = fakeRepository()
        var backClicked = false

        composeTestRule.setContent {
            RepositoryDetailsContent(
                repository = repository,
                onOpenUrlClick = {},
                onBackClick = { backClicked = true }
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Back")
            .performClick()

        assertTrue(backClicked)
    }
}
