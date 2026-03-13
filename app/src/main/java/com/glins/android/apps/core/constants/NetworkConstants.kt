package com.glins.android.apps.core.constants

import com.glins.android.apps.core.constants.UiConstants.AVATAR_SIZE
import com.glins.android.apps.core.constants.UiConstants.AVATAR_SIZE_DETAILS

object NetworkConstants {
    const val NETWORK_PAGE_SIZE = 100
    const val GITHUB_URL_AVATAR_SIZE_SUFFIX = "&s=$AVATAR_SIZE"
    const val GITHUB_URL_AVATAR_SIZE_DETAILS_SUFFIX = "&s=$AVATAR_SIZE_DETAILS"
    const val CACHE_TIMEOUT = 2 * 60 * 60 * 1000L // 2h

}