package com.glins.android.apps.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.glins.android.apps.presentation.navigation.AppNavigation
import com.glins.android.apps.presentation.theme.KotlinStarsTheme

class KotlinStarsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinStarsTheme {
                AppNavigation()
            }
        }
    }
}