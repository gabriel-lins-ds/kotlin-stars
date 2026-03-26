package com.glins.android.apps.navigation

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import com.glins.android.common.navigator.BrowserNavigator

class AndroidBrowserNavigator(private val context: Context) : BrowserNavigator {
    override fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri()).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}
