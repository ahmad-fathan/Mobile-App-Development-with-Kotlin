package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Bab 2 (Bagian 3) — Memulai dari Pengguna, Tugas, dan Konteks
 *
 * Sebelum menggambar layar, jawab tiga pertanyaan: siapa penggunanya, apa yang ingin dicapai,
 * dan di mana serta bagaimana aplikasi dipakai. Jawaban ketiga sudah memberi petunjuk untuk
 * layout adaptif: pengguna yang sama dapat memakai aplikasi pada ruang layar yang sangat
 * berbeda (ponsel sambil berpindah tempat, atau jendela tablet yang diubah ukurannya).
 *
 * Ketiga jawaban diringkas menjadi user story: peran, keinginan, alasan.
 */

/** Tiga jawaban yang mendahului wireframe. */
data class RingkasanPengguna(
    val siapa: String,
    val apa: String,
    val konteks: String
)

val ringkasanPenggunaCourseBrowser = RingkasanPengguna(
    siapa = "Mahasiswa yang mencari informasi tentang sebuah mata kuliah",
    apa = "Menemukan mata kuliah dan membaca deskripsinya",
    konteks = "Di ponsel sambil berpindah tempat, atau di jendela tablet yang ukurannya diubah saat belajar"
)

/**
 * User story satu kalimat: "Sebagai <peran>, saya ingin <keinginan> agar <alasan>."
 * Skenario ini adalah skenario kelas, bukan hasil penelitian: pada proyek nyata asumsi harus
 * diperiksa dengan wawancara atau observasi pengguna sebenarnya.
 */
fun userStory(siapa: String, keinginan: String, alasan: String): String =
    "Sebagai $siapa, saya ingin $keinginan agar $alasan."

val userStoryCourseBrowser: String = userStory(
    siapa = "mahasiswa",
    keinginan = "membuka detail mata kuliah",
    alasan = "saya dapat memutuskan mata kuliah mana yang ingin saya pelajari lebih lanjut"
)

/** User story ditampilkan sebagai kartu, bukan disembunyikan di dokumen terpisah. */
@Composable
fun KartuUserStory(cerita: String = userStoryCourseBrowser) {
    Card {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("User story", style = MaterialTheme.typography.titleMedium)
            Text(cerita, style = MaterialTheme.typography.bodyLarge)
            Text(
                "Konteks: ${ringkasanPenggunaCourseBrowser.konteks}.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 240)
@Composable
private fun KartuUserStoryPreview() {
    KartuUserStory()
}
