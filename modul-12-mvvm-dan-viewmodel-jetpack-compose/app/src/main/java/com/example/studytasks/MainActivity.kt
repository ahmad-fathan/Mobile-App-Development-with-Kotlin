package com.example.studytasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.studytasks.ui.theme.StudyTasksTheme

/**
 * Bagian 7.13 — Menghubungkan layar dengan Activity.
 *
 * `MainActivity`, `setContent`, dan theme bawaan proyek dipertahankan; hanya isinya berubah.
 * `innerPadding` menjaga konten agar tidak tertutup area sistem yang ditangani oleh Scaffold.
 *
 * Untuk mencoba contoh bab lain tanpa mengubah logika ViewModel, ganti `TasksRoute()` dengan
 * composable bertanda @Preview di berkas bab:
 *
 *   TaskScreenBefore()                bab-01  (Bagian 2 — versi sebelum refactoring)
 *   LayarTigaLifecycle()              bab-02  (Bagian 5.2 — Tabel 4)
 *   TabelDampakPeristiwa()            bab-03  (Bagian 5.3 — Tabel 5)
 *   TabelTempatMenyimpanState()       bab-04  (Bagian 6.1 — Tabel 6)
 *   TabelKetahananNilai()             bab-05  (Bagian 6.2 — Tabel 7)
 *   TabelKesalahanUmum()              bab-09  (Bagian 13 — Tabel 9)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        TasksRoute()
                    }
                }
            }
        }
    }
}
