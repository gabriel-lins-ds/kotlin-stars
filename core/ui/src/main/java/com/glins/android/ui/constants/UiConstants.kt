package com.glins.android.ui.constants

import com.glins.android.common.constants.CommonConstants.NETWORK_PAGE_SIZE


object UiConstants {

    const val FAB_VISIBILITY_THRESHOLD = NETWORK_PAGE_SIZE / 4
    const val AVATAR_SIZE = 48
    const val AVATAR_SIZE_DETAILS = 96
    const val SCREEN_TRANSITION_ANIMATION_DURATION = 500
    const val GITHUB_URL_AVATAR_SIZE_SUFFIX = "&s=${AVATAR_SIZE}"
    const val GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX = "&s=${AVATAR_SIZE_DETAILS}"
    const val REFRESH_TIME_THRESHOLD = 10 * 1000L // 10 seconds
}