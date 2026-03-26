package com.glins.android.apps.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.glins.android.ui.component.GithubAuthorImage
import org.junit.Rule
import org.junit.Test

class GithubAuthorImageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_avatar_image_when_composable_is_rendered() {
        composeTestRule.setContent {
            GithubAuthorImage()
        }

        composeTestRule
            .onNodeWithContentDescription("Owner avatar")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_avatar_when_url_is_null() {
        composeTestRule.setContent {
            GithubAuthorImage(url = null)
        }

        composeTestRule
            .onNodeWithContentDescription("Owner avatar")
            .assertIsDisplayed()
    }

    @Test
    fun should_display_avatar_when_url_is_provided() {
        composeTestRule.setContent {
            GithubAuthorImage(
                url = "https://avatars.githubusercontent.com/u/1?v=4"
            )
        }

        composeTestRule
            .onNodeWithContentDescription("Owner avatar")
            .assertIsDisplayed()
    }
}
