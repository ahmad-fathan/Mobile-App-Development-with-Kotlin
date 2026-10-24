package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campuscoursebrowser.alasanMediumSatuPanel
import com.example.campuscoursebrowser.kebijakanDuaPanel
import com.example.campuscoursebrowser.kelasLebarJendela
import com.example.campuscoursebrowser.kelasTinggiJendela

/**
 * Bab 7 (Bagian 8) — Layout Responsive dan Adaptive
 *
 * 8.1 Dua jenis penyesuaian
 *   Responsive: konten berubah ukuran, membungkus, atau mengalir ulang di dalam ruang yang
 *   tersedia (kartu mengisi parent, grid memuat lebih banyak kolom). Adaptive: susunan atau
 *   struktur interaksi berubah (alur list-detail menjadi dua panel). Keduanya saling melengkapi
 *   dan dapat muncul pada layar yang sama.
 *
 * 8.2 Berpikir dalam jendela aplikasi
 *   Kesalahan umum adalah menentukan layout dari JENIS PERANGKAT ("ponsel satu panel, tablet dua
 *   panel"). Yang menentukan adalah ukuran jendela aplikasi: aplikasi dapat menempati sebagian
 *   layar, rotasi/split-screen/freeform mengubah ruang, tablet dapat menampung jendela sempit,
 *   dan perangkat lipat berubah dimensi saat aplikasi berjalan. Pertanyaan desain yang tepat:
 *   "Berapa banyak ruang yang dimiliki UI ini sekarang?"
 *
 * 8.3 Window size class sebagai breakpoint
 *   Batas yang dipakai pedoman: compact < 600 dp, medium 600–839 dp, expanded 840–1199 dp,
 *   large 1200–1599 dp, extra-large ≥ 1600 dp (Tabel 5). Modul ini TIDAK membuat lima layout
 *   berbeda: semua lebar ≥ 840 dp dengan tinggi ≥ 480 dp memakai satu kebijakan dua panel.
 *
 * 8.4 Pola layout dipilih dari tugas, bukan dari jumlah ruang: list-detail (memilih lalu
 *   memeriksa), feed (menjelajah banyak konten), supporting pane (konten utama + alat pendukung).
 *   Tugas Course Browser = list-detail.
 *
 * 8.5 Kebijakan modul (Tabel 7): compact dan medium tetap satu panel; dua panel hanya bila lebar
 *   ≥ 840 dp DAN tinggi ≥ 480 dp; lebar tetapi pendek tetap satu panel. Kebijakan ini pilihan
 *   untuk contoh kelas, bukan aturan universal.
 *
 * Batas kelas, kebijakanDuaPanel, kelasLebarJendela, kelasTinggiJendela, dan alasanMediumSatuPanel
 * dideklarasikan di paket akar (verifikasi.kt), karena aturan yang sama dipakai tampilan dan
 * pemeriksaan otomatis.
 */

/** Satu baris Tabel 7. */
data class BarisKebijakan(val ruang: String, val keputusan: String)

/** Tabel 7 — kebijakan layout Campus Course Browser. */
val kebijakanLayoutCourseBrowser: List<BarisKebijakan> = listOf(
    BarisKebijakan("Lebar compact (< 600 dp)", "Tampilkan daftar atau detail terpilih, satu per satu"),
    BarisKebijakan("Lebar medium (600 sampai 839 dp)", "Tetap satu panel, dengan konten yang nyaman dibaca"),
    BarisKebijakan(
        "Lebar ≥ 840 dp dan tinggi ≥ 480 dp",
        "Tampilkan panel daftar dan panel detail bersamaan"
    ),
    BarisKebijakan(
        "Lebar tetapi pendek (< 480 dp)",
        "Tetap satu panel: ponsel landscape belum nyaman untuk dua panel"
    )
)

/** Tabel 6 — pola layout dan tugas yang cocok. */
data class PolaLayout(val pola: String, val tugasCocok: String, val contohKuliah: String)

val polaLayoutUmum: List<PolaLayout> = listOf(
    PolaLayout("List-detail", "Memilih sebuah item lalu memeriksanya", "Daftar mata kuliah dan deskripsinya"),
    PolaLayout("Feed", "Menjelajahi banyak item konten", "Kartu sumber belajar"),
    PolaLayout("Supporting pane", "Mengerjakan konten utama dengan alat pendukung", "Materi kuliah beserta catatan")
)

@Composable
fun TabelKebijakanLayout() {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        kebijakanLayoutCourseBrowser.forEach { baris ->
            Text(baris.ruang, style = MaterialTheme.typography.titleSmall)
            Text(baris.keputusan, style = MaterialTheme.typography.bodyMedium)
        }
        Text(
            alasanMediumSatuPanel(700),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/** Contoh kelas untuk satu ukuran jendela, dipakai di menu aplikasi. */
fun contohKelasJendela(lebarDp: Int, tinggiDp: Int): String {
    val panel = if (kebijakanDuaPanel(lebarDp, tinggiDp)) "dua panel" else "satu panel"
    return "$lebarDp x $tinggiDp dp → ${kelasLebarJendela(lebarDp)}/" +
        "${kelasTinggiJendela(tinggiDp)} → $panel"
}

@Preview(showBackground = true, widthDp = 360, heightDp = 380)
@Composable
private fun TabelKebijakanLayoutPreview() {
    TabelKebijakanLayout()
}
