package com.example.coroutinedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.coroutinedemo.ui.main.TasksScreen
import com.example.coroutinedemo.ui.theme.CoroutineDemoTheme

/**
 * Titik masuk aplikasi Coroutine Demo (Subbagian 7.9).
 *
 * Untuk mencoba layar bab, ganti `TasksScreen()` dengan salah satu dari `MainThreadScreen()`,
 * `DasarCoroutineScreen()`, `ScopeDispatcherScreen()`, `StructuredConcurrencyScreen()`,
 * `PraktikScreen()`, `DiLuarCoroutineScreen()`, atau `LatihanScreen()`.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TasksScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
