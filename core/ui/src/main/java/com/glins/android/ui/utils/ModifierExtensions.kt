package com.glins.android.ui.utils

import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role
import com.glins.android.ui.constants.UiConstants.CLICK_DEBOUNCE_THRESHOLD

fun Modifier.clickableDebounced(
    enabled: Boolean = true,
    onClickLabel: String? = null,
    role: Role? = null,
    debounceInterval: Long = CLICK_DEBOUNCE_THRESHOLD,
    onClick: () -> Unit
): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    clickable(
        enabled = enabled,
        onClickLabel = onClickLabel,
        role = role,
        onClick = {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime > debounceInterval) {
                lastClickTime = currentTime
                onClick()
            }
        }
    )
}
