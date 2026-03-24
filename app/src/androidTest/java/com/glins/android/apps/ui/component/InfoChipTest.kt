package com.glins.android.apps.ui.component

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.glins.android.apps.R
import com.glins.android.ui.component.InfoChip
import org.junit.Rule
import org.junit.Test

class InfoChipTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun should_display_text_when_info_chip_is_rendered() {
        val text = "52439"

        composeTestRule.setContent {
            InfoChip(
                iconId = R.drawable.ic_star,
                text = text
            )
        }

        composeTestRule
            .onNodeWithText(text)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_icon_when_content_description_is_provided() {
        val description = "Star icon"

        composeTestRule.setContent {
            InfoChip(
                iconId = R.drawable.ic_star,
                text = "52439",
                contentDescription = description
            )
        }

        composeTestRule
            .onNodeWithContentDescription(description)
            .assertIsDisplayed()
    }

    @Test
    fun should_display_text_and_icon_together() {
        val text = "52439"
        val description = "Star icon"

        composeTestRule.setContent {
            InfoChip(
                iconId = R.drawable.ic_star,
                text = text,
                contentDescription = description
            )
        }

        composeTestRule
            .onNodeWithText(text)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription(description)
            .assertIsDisplayed()
    }
}
