package com.example.tasknav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.tasknav.KodeBlok
import com.example.tasknav.Tabel
import com.example.tasknav.VerifikasiRingkas
import com.example.tasknav.caraKembali
import com.example.tasknav.komponenDasar
import com.example.tasknav.perbandinganRoute
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar About (Latihan 3 modul).
 *
 * Route-nya `object About` karena tidak membutuhkan data. Layar ini tetap menerima `onBack`,
 * bukan NavController, sehingga dapat ditampilkan di Preview dan diuji sendirian.
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
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("TaskNav", style = MaterialTheme.typography.headlineSmall)
            Text(
                "Aplikasi latihan Modul 15 — Navigation with Jetpack Compose. " +
                    "Tiga tugas contoh disimpan di memori; seluruh keputusan navigasi ada di AppNavHost.",
                style = MaterialTheme.typography.bodyMedium
            )
            KodeBlok(
                """
                @Serializable object About      // tanpa argumen
                composable<About> { AboutScreen(onBack = { navController.navigateUp() }) }
                navController.navigate(About)
                """
            )
            Tabel(
                "Lima komponen dasar navigasi",
                komponenDasar.map { it.istilah to "${it.pengertian} — ${it.analogi}" }
            )
            Tabel(
                "Route string vs type-safe",
                perbandinganRoute.map { it.aspek to "${it.stringRoute}  →  ${it.typeSafe}" }
            )
            Tabel("Cara kembali ke layar sebelumnya", caraKembali.map { it.aksi to "${it.kode} — ${it.efek}" })
            VerifikasiRingkas()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview() {
    TaskNavTheme { AboutScreen(onBack = {}) }
}
