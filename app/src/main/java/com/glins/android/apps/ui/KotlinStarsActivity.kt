package com.glins.android.apps.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.glins.android.apps.ui.navigation.AppNavigation
import com.glins.android.apps.ui.theme.KotlinStarsTheme
import androidx.core.net.toUri

class KotlinStarsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinStarsTheme {
                AppNavigation(onOpenUrlClick = ::onOpenUrlClick)
            }
        }
    }

    private fun onOpenUrlClick(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }
}