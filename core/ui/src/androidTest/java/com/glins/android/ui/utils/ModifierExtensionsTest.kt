package com.glins.android.ui.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ModifierExtensionsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun clickableDebounced_should_trigger_onClick_once_when_clicked_twice_fast() {
        var clickCount = 0
        val debounceInterval = 500L
        val tag = "debounced_box"

        composeTestRule.setContent {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .testTag(tag)
                    .clickableDebounced(debounceInterval = debounceInterval) {
                        clickCount++
                    }
            )
        }

        // Perform two clicks rapidly
        composeTestRule.onNodeWithTag(tag).performClick()
        composeTestRule.onNodeWithTag(tag).performClick()

        // Should only have triggered once because of debounce
        assertEquals(1, clickCount)
    }

    @Test
    fun clickableDebounced_should_trigger_onClick_twice_when_clicked_after_interval() {
        var clickCount = 0
        val debounceInterval = 100L
        val tag = "debounced_box"

        composeTestRule.setContent {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .testTag(tag)
                    .clickableDebounced(debounceInterval = debounceInterval) {
                        clickCount++
                    }
            )
        }

        // First click
        composeTestRule.onNodeWithTag(tag).performClick()
        
        // Wait for interval to pass
        Thread.sleep(debounceInterval + 50)

        // Second click
        composeTestRule.onNodeWithTag(tag).performClick()

        // Should have triggered twice
        assertEquals(2, clickCount)
    }
}
