package com.glins.android.apps.data.constants

import com.glins.android.apps.ui.constants.UiConstants

object NetworkConstants {
    const val NETWORK_PAGE_SIZE = 100
    const val NETWORK_PAGE_PREFETCH_DISTANCE = NETWORK_PAGE_SIZE / 10
    const val GITHUB_URL_AVATAR_SIZE_SUFFIX = "&s=${UiConstants.AVATAR_SIZE}"
    const val GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX = "&s=${UiConstants.AVATAR_SIZE_DETAILS}"
    const val CACHE_TIMEOUT = 2 * 60 * 60 * 1000L // 2h
    const val REFRESH_TIME_THRESHOLD = 10 * 1000L // 10 seconds

}