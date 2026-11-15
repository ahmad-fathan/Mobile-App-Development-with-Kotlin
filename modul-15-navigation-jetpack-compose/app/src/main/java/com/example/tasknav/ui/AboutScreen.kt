package com.example.tasknav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar About (Latihan 3 modul).
 *
 * Sengaja dibuat ringkas: satu judul, nomor versi, dan dua-tiga kalimat penjelasan. Tidak ada
 * tabel maupun potongan kode — penjelasan konsep ada di berkas `bab/`, dan layar ini hanya
 * memperkenalkan aplikasinya.
 *
 * Route-nya `object About` karena tidak membutuhkan data. Layar ini menerima `onBack`, bukan
 * NavController, sehingga dapat ditampilkan di Preview dan diuji sendirian.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("About") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("TaskNav", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Version 1.0",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                "TaskNav adalah aplikasi contoh Modul 15 — Navigation with Jetpack Compose. " +
                    "Aplikasi ini menunjukkan perpindahan dari daftar tugas ke detail dengan route " +
                    "type-safe: hanya ID tugas yang dikirim, data diambil di layar tujuan. " +
                    "Seluruh keputusan navigasi berada di AppNavHost, sedangkan setiap layar " +
                    "hanya menerima lambda event.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    TaskNavTheme { AboutScreen(onBack = {}) }
}
