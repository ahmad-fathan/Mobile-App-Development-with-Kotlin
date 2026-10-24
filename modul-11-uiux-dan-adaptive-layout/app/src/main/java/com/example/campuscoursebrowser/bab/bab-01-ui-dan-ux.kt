package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Bab 1 (Bagian 2) — UI dan UX
 *
 * UI adalah apa yang dilihat dan disentuh pengguna; UX adalah seberapa baik pengguna mencapai
 * tujuannya. Layar yang tampak rapi masih bisa sulit dipakai: kartu yang menarik tanpa jalan
 * kembali yang jelas adalah masalah alur tugas (UX), bukan masalah tampilan kartu (UI).
 *
 * Karena itu setiap desain dinilai dari dua sisi: antarmukanya dan alur tugasnya. Bandingkan
 * `perbandinganUiUx` dengan Tabel 1 modul.
 */

/** Satu baris Tabel 1: konsep, pertanyaan utamanya, dan contohnya pada Course Browser. */
data class AspekAntarmuka(
    val nama: String,
    val pertanyaanUtama: String,
    val contohCourseBrowser: String
)

/** Tabel 1 — perbedaan UI dan UX pada contoh Campus Course Browser. */
val perbandinganUiUx: List<AspekAntarmuka> = listOf(
    AspekAntarmuka(
        nama = "User interface (UI)",
        pertanyaanUtama = "Apa yang dilihat dan digunakan pengguna untuk berinteraksi?",
        contohCourseBrowser = "Kartu mata kuliah, label, tombol, jarak antarelemen"
    ),
    AspekAntarmuka(
        nama = "User experience (UX)",
        pertanyaanUtama = "Seberapa baik pengguna dapat mencapai tujuannya?",
        contohCourseBrowser = "Dapatkah pengguna menemukan mata kuliah, memahami detailnya, " +
            "lalu kembali tanpa bingung?"
    )
)

/**
 * Contoh layar yang tampak rapi tetapi buntu: kartu terbaca, tetapi tidak ada aksi apa pun.
 * Semua teks memakai gaya yang sama sehingga mata tidak punya titik awal (lihat Bab 5).
 */
@Composable
fun LayarRapiTanpaAlur() {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text("Text Analytics", style = MaterialTheme.typography.bodyMedium)
        Text("Find useful patterns in text data.", style = MaterialTheme.typography.bodyMedium)
        Text("Mobile App Development", style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 320)
@Composable
private fun LayarRapiTanpaAlurPreview() {
    LayarRapiTanpaAlur()
}
