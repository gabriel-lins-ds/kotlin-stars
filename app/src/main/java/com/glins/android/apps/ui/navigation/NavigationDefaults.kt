package com.glins.android.apps.ui.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.ui.unit.IntOffset
import com.glins.android.apps.ui.constants.UiConstants.SCREEN_TRANSITION_ANIMATION_DURATION

object NavigationDefaults {
    val tweenSpec: FiniteAnimationSpec<IntOffset> = tween(
        durationMillis = SCREEN_TRANSITION_ANIMATION_DURATION,
        easing = FastOutSlowInEasing
    )
}