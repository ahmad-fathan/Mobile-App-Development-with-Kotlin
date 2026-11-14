package com.example.tasknav.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasknav.JudulBab
import com.example.tasknav.KodeBlok
import com.example.tasknav.Tabel
import com.example.tasknav.komponenDasar

/**
 * Bab 1 — Mengapa Aplikasi Membutuhkan Navigasi dan Lima Komponen Dasarnya
 *
 * Bagian modul: 2 (Mengapa Aplikasi Membutuhkan Navigasi) dan 3 (Lima Komponen Dasar
 * Navigation Compose, termasuk 3.1 Bagaimana Komponen Bekerja Sama).
 *
 * Inti bab: menampilkan dua layar dengan `var showDetail by remember` cepat rusak karena riwayat
 * layar, data tujuan, pemulihan sistem, dan skala harus ditangani sendiri. Navigation Compose
 * menangani keempatnya.
 */

@Composable
fun MengapaNavigasiScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Mengapa aplikasi butuh navigasi",
            keterangan = "Event naik lewat lambda, state baru (back stack) menentukan layar"
        )

        Text(
            "Versi naif memakai satu variabel state lalu memilih composable dengan if. " +
                "Pendekatan itu gagal pada empat hal berikut.",
            style = MaterialTheme.typography.bodyMedium
        )
        KodeBlok(
            """
            // Cepat rusak saat aplikasi membesar
            var showDetail by remember { mutableStateOf(false) }
            if (showDetail) TaskDetailScreen() else TaskListScreen()
            """
        )
        Tabel(
            "Yang harus ditangani sendiri tanpa Navigation Compose",
            listOf(
                "Riwayat layar" to "Tombol Back harus kembali ke layar sebelumnya, bukan menutup aplikasi",
                "Data untuk layar tujuan" to "Layar detail harus tahu tugas mana yang dipilih, dan data bertahan saat diputar",
                "Pemulihan oleh sistem" to "Setelah proses aplikasi dihentikan, posisi dan data harus kembali",
                "Skala" to "Sepuluh layar berarti puluhan kombinasi if yang sulit dirawat"
            )
        )

        Text("Lima komponen dasar", style = MaterialTheme.typography.titleSmall)
        Tabel("Tabel 1 — istilah, pengertian, analogi", komponenDasar.map { it.istilah to "${it.pengertian} — ${it.analogi}" })
        Text(
            "Kumpulan seluruh destination di dalam satu NavHost disebut navigation graph. " +
                "Pada TaskNav graph berisi TaskList, TaskDetail, About, CourseTasks, dan Welcome.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("3.1 Bagaimana komponen bekerja sama", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            1. Pengguna mengetuk baris "Submit lab report".
            2. Screen TIDAK berpindah sendiri; ia memanggil lambda yang diterimanya:
                   onTaskClick(2)
            3. Pemegang NavController menerjemahkan event itu:
                   navController.navigate(TaskDetail(2))
            4. NavController menambah entri ke back stack; NavHost menampilkan
               TaskDetailScreen karena isi back stack berubah.
            """
        )
        Text(
            "Polanya sama dengan UDF: event mengalir ke atas, lalu state baru menentukan apa yang " +
                "digambar. Bedanya, state yang dimaksud adalah back stack.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MengapaNavigasiPreview() {
    com.example.tasknav.ui.theme.TaskNavTheme { MengapaNavigasiScreen() }
}
