package com.example.jetzone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.example.jetzone.presentation.navigation.MainNavigation
import com.example.jetzone.ui.theme.JetZoneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            JetZoneTheme {
                MainNavigation()
            }
        }
    }
}