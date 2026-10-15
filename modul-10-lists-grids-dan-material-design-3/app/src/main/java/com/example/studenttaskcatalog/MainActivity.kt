package com.example.studenttaskcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.studenttaskcatalog.bab.StudentTaskCatalogScreen
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bagian 12.2 — Layar dan Integrasi dengan Scaffold
 *
 * Tema aplikasi dan satu Scaffold dari pertemuan sebelumnya tetap seperti semula. Di dalam
 * lambda konten Scaffold, layar katalog dipanggil SATU KALI dan innerPadding diteruskan SATU
 * KALI:
 *
 *   Scaffold(topBar = { TopAppBar(title = { Text("Student Tasks") }) }) { innerPadding ->
 *       StudentTaskCatalogScreen(modifier = Modifier.padding(innerPadding))
 *   }
 *
 * Jangan menambahkan Scaffold kedua di dalam layar, dan jangan menerapkan padding dua kali.
 * Tidak ada dependensi Gradle baru: proyek Compose Material 3 standar sudah memuat library
 * foundation (lazy layout) dan material3 (Card serta chip).
 *
 * Untuk mencoba versi pengayaan Bagian 12.4 (filter kategori + empty state), ganti
 * StudentTaskCatalogScreen(...) dengan StudentTaskCatalogFilterScreen(...).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentTaskCatalogTheme {
                StudentTaskCatalogApp()
            }
        }
    }
}

/** Satu-satunya Scaffold: app bar + konten layar. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentTaskCatalogApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Student Tasks") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // innerPadding diterapkan sekali, di batas konten.
        StudentTaskCatalogScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 720)
@Composable
fun StudentTaskCatalogAppPreview() {
    StudentTaskCatalogTheme { StudentTaskCatalogApp() }
}
