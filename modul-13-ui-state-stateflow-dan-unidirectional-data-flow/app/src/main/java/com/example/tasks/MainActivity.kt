package com.example.tasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.tasks.ui.theme.TasksTheme

/**
 * Titik masuk aplikasi. Isinya hanya `TasksRoute()` di dalam tema dan Scaffold.
 *
 * Untuk mencoba layar bab lain tanpa mengubah logika ViewModel, ganti `TasksRoute()` dengan
 * composable bertanda @Preview di berkas bab:
 *
 *   UIStateScreen()                  bab-01  (model, derived state, copy)
 *   StateFlowScreen()                bab-02  (StateFlow, bridging ke Compose State)
 *   UdFlowScreen()                   bab-03  (UDF, MVVM, trace satu interaksi)
 *   KehidupanStateScreen()           bab-04  (rotasi, process death, StateFlow bukan antrean)
 *   KesalahanUmumScreen()            bab-05  (kesalahan umum, langkah praktik, jawaban)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TasksRoute()
                    }
                }
            }
        }
    }
}
