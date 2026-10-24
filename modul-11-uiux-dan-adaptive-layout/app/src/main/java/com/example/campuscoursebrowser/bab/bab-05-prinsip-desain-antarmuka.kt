package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campuscoursebrowser.Course
import com.example.campuscoursebrowser.TeksCourseBrowser

/**
 * Bab 5 (Bagian 6) — Prinsip Desain Antarmuka
 *
 * Tiga prinsip yang paling sering menentukan apakah sebuah layar mudah dipakai:
 * hierarki visual, konsistensi, serta umpan balik dan pemulihan.
 *
 * 6.1 Hierarki Visual
 *   Hierarki adalah urutan layar menarik perhatian. Hierarki dibangun dengan ukuran, posisi,
 *   kontras, jarak, dan penekanan aksi utama. `KartuTanpaHierarki` dan `KartuDenganHierarki`
 *   memuat informasi yang sama persis; yang berbeda hanya peran tipografi dan jaraknya.
 *
 * 6.2 Hierarki dalam Compose
 *   Setiap baris `KartuDenganHierarki` membawa alasan desain:
 *     titleLarge      → tingkat kepentingan judul
 *     bodyMedium      → informasi sekunder
 *     spacedBy(8.dp)  → mengelompokkan gagasan tanpa garis tepi
 *     "View details"  → menjelaskan langkah berikutnya
 *
 * 6.3 Konsistensi
 *   Satu `KartuCourse` yang dipakai ulang dan satu MaterialTheme bersama mencegah tiap layar
 *   menjadi sistem desain sendiri (satu kartu "Open", kartu lain "Details", kartu ketiga "GO").
 *
 * 6.4 Umpan Balik dan Pemulihan
 *   Pengguna perlu tahu apa yang sedang terjadi dan bagaimana keluar dari keadaan yang tidak
 *   diharapkan. Lihat `responsYangMembantu` (Tabel 3). Pada contoh modul hanya penanda pilihan
 *   dan keadaan detail kosong yang diimplementasikan; pencarian dan jaringan hanya contoh desain.
 */

/** Label aksi harus sama untuk aksi yang sama. */
val labelAksiKonsisten: List<String> = listOf("View details")

/** True bila setiap layar memakai label yang sama untuk aksi membuka detail. */
fun labelAksiSeragam(semuaLabel: List<String>): Boolean = semuaLabel.toSet().size == 1

/**
 * Tabel 3 — situasi dan respons yang membantu. Baris 3 dan 4 hanya contoh desain: modul ini
 * tidak mengimplementasikan pencarian maupun jaringan.
 */
val responsYangMembantu: List<Pair<String, String>> = listOf(
    "Sebuah mata kuliah dipilih" to
        "Tampilkan detailnya dan tandai pilihan tersebut pada layout lebar",
    "Belum ada yang dipilih" to "Jelaskan: \u201cSelect a course to read its details\u201d",
    "Pencarian tidak memberi hasil" to "Jelaskan hasilnya dan tawarkan \u201cClear search\u201d",
    "Pemuatan gagal pada versi berjaringan" to
        "Jelaskan masalahnya dan tawarkan percobaan ulang yang bermakna"
)

/** Aturan umum: jangan menampilkan pesan berhasil sebelum operasi benar-benar berhasil. */
fun bolehMenampilkanPesanBerhasil(operasiBerhasil: Boolean): Boolean = operasiBerhasil

/** Kartu dengan informasi yang sama, tetapi semua teks berbobot sama: mata tidak punya titik awal. */
@Composable
fun KartuTanpaHierarki() {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Text Analytics", style = MaterialTheme.typography.bodyMedium)
            Text("Find useful patterns in text data.", style = MaterialTheme.typography.bodyMedium)
            TextButton(onClick = {}) { Text("OPEN") }
        }
    }
}

/** Urutan perhatian 1 → 2 → 3: judul, ringkasan, aksi utama. */
@Composable
fun KartuDenganHierarki(course: Course, onOpen: () -> Unit = {}) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(course.title, style = MaterialTheme.typography.titleLarge)
            Text(course.summary, style = MaterialTheme.typography.bodyMedium)
            Button(onClick = onOpen) { Text(TeksCourseBrowser.LIHAT_DETAIL) }
        }
    }
}

/** Dua kartu berdampingan agar perbedaan hierarki terlihat langsung. */
@Composable
fun PerbandinganHierarki(course: Course) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Tanpa hierarki", style = MaterialTheme.typography.labelLarge)
        KartuTanpaHierarki()
        Text("Dengan hierarki", style = MaterialTheme.typography.labelLarge)
        KartuDenganHierarki(course)
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 420)
@Composable
private fun KartuTanpaHierarkiPreview() {
    Row(Modifier.padding(8.dp)) { KartuTanpaHierarki() }
}
