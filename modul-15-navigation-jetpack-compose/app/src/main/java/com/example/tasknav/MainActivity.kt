package com.example.tasknav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.tasknav.navigation.AppNavHost
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Titik masuk aplikasi. Isinya hanya [AppNavHost] di dalam tema.
 *
 * Semua perpindahan layar terjadi di dalam NavHost; Activity tidak mengetahui destination apa pun.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskNavTheme {
                AppNavHost()
            }
        }
    }
}
