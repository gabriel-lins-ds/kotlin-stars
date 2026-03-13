package com.glins.android.apps.util.constants

import com.glins.android.apps.util.constants.UiConstants.AVATAR_SIZE
import com.glins.android.apps.util.constants.UiConstants.AVATAR_SIZE_DETAILS

object NetworkConstants {
    const val NETWORK_PAGE_SIZE = 100
    const val NETWORK_PAGE_PREFETCH_DISTANCE = NETWORK_PAGE_SIZE / 10
    const val GITHUB_URL_AVATAR_SIZE_SUFFIX = "&s=$AVATAR_SIZE"
    const val GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX = "&s=$AVATAR_SIZE_DETAILS"
    const val CACHE_TIMEOUT = 2 * 60 * 60 * 1000L // 2h
    const val REFRESH_TIME_THRESHOLD = 10 * 1000L // 10 seconds

}