package com.glins.android.apps.ui.repositorylist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.glins.android.apps.domain.model.Repository
import com.glins.android.apps.domain.model.RepositoryAuthor
import com.glins.android.apps.ui.testtag.TestTags
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class RepositoryListContentTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeRepositories(): List<Repository> {
        return listOf(
            Repository(
                id = 1,
                index = 1,
                name = "kotlin",
                description = "Kotlin language",
                url = "https://github.com/JetBrains/kotlin",
                stars = 100,
                forks = 20,
                author = RepositoryAuthor(
                    name = "JetBrains",
                    iconUrl = "",
                    profileUrl = "https://github.com/JetBrains"
                )
            ),
            Repository(
                id = 2,
                index = 2,
                name = "ktor",
                description = "Ktor framework",
                url = "https://github.com/ktorio/ktor",
                stars = 200,
                forks = 30,
                author = RepositoryAuthor(
                    name = "JetBrains",
                    iconUrl = "",
                    profileUrl = "https://github.com/JetBrains"
                )
            )
        )
    }

    private fun manyRepositories(): List<Repository> {
        return (1..40).map {
            Repository(
                id = it.toLong(),
                index = it,
                name = "repo $it",
                description = "description $it",
                url = "url",
                stars = it * 10,
                forks = it,
                author = RepositoryAuthor(
                    name = "author",
                    iconUrl = "",
                    profileUrl = ""
                )
            )
        }
    }

    @Test
    fun should_display_repository_items_in_list() {
        val flow = flowOf(PagingData.from(fakeRepositories()))

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = {},
                onRetry = {}
            )
        }

        composeTestRule
            .onNodeWithText("kotlin")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("ktor")
            .assertIsDisplayed()
    }

    @Test
    fun should_trigger_onRepositoryClick_when_repository_item_is_clicked() {
        val flow = flowOf(PagingData.from(fakeRepositories()))
        var clicked = false

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = { clicked = true },
                onRetry = {}
            )
        }

        composeTestRule
            .onNodeWithText("kotlin")
            .performClick()

        assertTrue(clicked)
    }

    @Test
    fun should_not_display_scroll_to_top_fab_when_list_is_at_top() {
        val flow = flowOf(PagingData.from(manyRepositories()))

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = {},
                onRetry = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LIST_SCROLL_TO_TOP_FAB)
            .assertDoesNotExist()
    }

    @Test
    fun should_display_scroll_to_top_fab_when_list_is_scrolled() {
        val flow = flowOf(PagingData.from(manyRepositories()))

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = {},
                onRetry = {},
                fabVisibilityThreshold = lazyPagingItems.itemCount / 4
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LIST_LAZY_LAYOUT)
            .performScrollToIndex(20)

        composeTestRule
            .onNodeWithTag(TestTags.LIST_SCROLL_TO_TOP_FAB)
            .assertIsDisplayed()
    }

    @Test
    fun should_scroll_list_to_top_when_scroll_to_top_fab_is_clicked() {
        val flow = flowOf(PagingData.from(manyRepositories()))

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = {},
                onRetry = {},
                fabVisibilityThreshold = lazyPagingItems.itemCount / 4
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LIST_LAZY_LAYOUT)
            .performScrollToIndex(25)

        composeTestRule
            .onNodeWithTag(TestTags.LIST_SCROLL_TO_TOP_FAB)
            .performClick()

        composeTestRule
            .onNodeWithText("repo 1")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_pull_to_refresh_container() {
        val flow = flowOf(PagingData.from(fakeRepositories()))

        composeTestRule.setContent {
            val lazyPagingItems = flow.collectAsLazyPagingItems()

            RepositoryListContent(
                repositories = lazyPagingItems,
                onRepositoryClick = {},
                onRetry = {}
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.LIST_PULL_TO_REFRESH_BOX)
            .assertIsDisplayed()
    }
}