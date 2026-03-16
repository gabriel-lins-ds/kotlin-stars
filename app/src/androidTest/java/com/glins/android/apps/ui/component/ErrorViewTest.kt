package com.glins.android.apps.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.glins.android.apps.domain.error.DomainError
import com.glins.android.apps.ui.testtag.TestTags
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ErrorViewTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_default_error_message_when_error_is_null() {
        val defaultMessage = "Default error"

        composeTestRule.setContent {
            ErrorView(
                error = null,
                defaultErrorMessage = defaultMessage
            )
        }

        composeTestRule
            .onNodeWithText(defaultMessage)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_domain_error_message_when_error_is_provided() {
        val error = DomainError.GithubRequestLimit

        composeTestRule.setContent {
            ErrorView(
                error = error,
                defaultErrorMessage = "Fallback message"
            )
        }

        val expectedMessage =
            InstrumentationRegistry.getInstrumentation().targetContext.getString(error.stringRes)

        composeTestRule
            .onNodeWithText(expectedMessage)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_retry_button() {
        composeTestRule.setContent {
            ErrorView(defaultErrorMessage = "Error")
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_RETRY_BUTTON)
            .assertIsDisplayed()
    }

    @Test
    fun should_call_onRetry_when_retry_button_is_clicked() {
        var retryCalled = false

        composeTestRule.setContent {
            ErrorView(
                defaultErrorMessage = "Error",
                onRetry = { retryCalled = true }
            )
        }

        composeTestRule
            .onNodeWithTag(TestTags.ERROR_RETRY_BUTTON)
            .performClick()

        assertTrue(retryCalled)
    }
}
