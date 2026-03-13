package com.glins.android.apps.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.glins.android.apps.presentation.navigation.AppNavigation
import com.glins.android.apps.presentation.theme.KotlinStarsTheme
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