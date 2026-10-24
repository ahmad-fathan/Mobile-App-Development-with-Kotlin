package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.KotakAlur
import com.example.studytasks.PanahBawah
import com.example.studytasks.Tabel
import com.example.studytasks.urusanLayerTasks

/**
 * Bab 3 (Bagian 3) — Separation of Concerns dan Lapisan Aplikasi
 *
 * 3.1 Separation of concerns berarti memberikan tanggung jawab yang jelas kepada setiap bagian
 *     aplikasi, sehingga perubahan pada satu urusan menyentuh sesedikit mungkin bagian lain.
 *     Pemisahan ini BUKAN soal memperbanyak file: file baru layak dibuat hanya jika file itu
 *     memperjelas sebuah tanggung jawab. Setiap file di modul ini menjawab satu pertanyaan —
 *     siapa yang menggambar layar, siapa yang menentukan isi layar, siapa yang memiliki data.
 *
 * 3.2 UI layer menyajikan informasi dan menangani interaksi (Composable + state holder tingkat
 *     layar seperti ViewModel). Data layer memiliki data aplikasi dan menyediakan operasi
 *     terhadapnya melalui repository; repository dapat memakai data source seperti database atau
 *     web service. Domain layer OPSIONAL dan tidak dipakai pada contoh pengantar ini.
 *
 * Arah dependensi hanya ke bawah: layar → ViewModel → repository. Repository tidak pernah
 * bergantung pada ViewModel ataupun Composable. Karena itu layar dapat diganti, atau repository
 * diuji, tanpa menyentuh sisi lainnya.
 *
 * Tabel urusan diambil dari [urusanLayerTasks] di paket akar (satu sumber, dipakai juga oleh
 * verifikasi.kt) — bukan disalin ke berkas ini.
 */

@Composable
fun SeparationOfConcernsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 3 — Separation of concerns dan lapisan aplikasi",
            keterangan = "Satu urusan, satu pemilik."
        )
        Tabel(
            "Tabel 2. Urusan aplikasi Study Tasks dan komponen yang bertanggung jawab",
            urusanLayerTasks.map { it.urusan to it.komponen }
        )
        Text("Gambar 2 — dua lapisan, arah dependensi ke bawah")
        KotakAlur("UI layer", "TasksScreen (Composable) · TasksRoute · TasksViewModel")
        PanahBawah("memakai")
        KotakAlur("Data layer", "TaskRepository (interface) · InMemoryTaskRepository")
        PanahBawah("kelak memakai")
        KotakAlur("Data source (opsional)", "Database atau web service")
        Tabel(
            "Yang TIDAK boleh terjadi",
            listOf(
                "Repository → ViewModel" to "Repository tidak boleh tahu siapa yang memakainya",
                "ViewModel → Composable" to "ViewModel tidak boleh menyimpan referensi UI/lifecycle",
                "Screen → Repository" to "Layar tidak pernah mengubah data secara langsung"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SeparationOfConcernsPreview() {
    SeparationOfConcernsScreen()
}
