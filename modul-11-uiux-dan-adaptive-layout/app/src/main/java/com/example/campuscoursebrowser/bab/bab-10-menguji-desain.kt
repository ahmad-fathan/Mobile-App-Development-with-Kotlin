package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campuscoursebrowser.KasusUjiDesain
import com.example.campuscoursebrowser.harapanDuaPanel
import com.example.campuscoursebrowser.matriksUjiCourseBrowser

/**
 * Bab 10 (Bagian 11) — Menguji Desain
 *
 * 11.1 Preview desain, lalu uji perilaku.
 *   Karena CourseBrowser menerima Boolean, setiap kebijakan layout dapat dibuat preview-nya.
 *   Preview berguna untuk hierarki, perataan, jarak, dan pembungkusan teks. Tetapi preview
 *   MENYUNTIKKAN kebijakan secara langsung dan tidak memverifikasi perhitungan window size
 *   class: perilaku sebenarnya tetap diuji dengan menjalankan aplikasi, mengubah ukuran jendela,
 *   serta mencoba TalkBack dan tombol Back.
 *
 * 11.2 Matriks uji (Tabel 10). Ukuran pada tabel adalah ukuran jendela aplikasi dalam dp, bukan
 *   piksel tangkapan layar. Uji juga tinggi jendela di sekitar 480 dp, dan catat hasil yang
 *   benar-benar terjadi — jangan menganggap preview sudah membuktikan perilaku aplikasi.
 *
 * 11.3 Mengamati pengguna lain: minta seorang teman menyelesaikan tugas "Find Text Analytics,
 *   open its details, then return to the list" tanpa diberi petunjuk. Catat penyelesaian,
 *   keraguan, ketukan salah, dan permintaan bantuan. Ini pemeriksaan formatif kecil, bukan studi
 *   usability yang representatif.
 *
 * 11.4 Meninjau hasil terhadap requirement di Bab 3 (Tabel 11).
 *
 * KasusUjiDesain, matriksUjiCourseBrowser, dan harapanDuaPanel dideklarasikan di paket akar
 * (verifikasi.kt) supaya tabel di layar dan pemeriksaan otomatis memakai data yang sama.
 */

/** Tabel 11 — kriteria tinjauan dan bukti yang diperiksa. */
val kriteriaTinjauan: List<Pair<String, String>> = listOf(
    "Kebutuhan pengguna" to "Requirement menjelaskan tugas dan konteks",
    "Hierarki" to "Judul, konten pendukung, dan aksi memiliki peran yang jelas",
    "Konsistensi" to "Komponen yang berulang memakai struktur dan label yang sama",
    "Aksesibilitas" to "Teks terbaca, target dapat dipakai, pembacaan screen reader bermakna",
    "Adaptasi" to "Layout sempit dan lebar mempertahankan tugas dan pilihan"
)

/** Tugas observasi 11.3; dibacakan apa adanya agar hasil tiap pengujian dapat dibandingkan. */
const val TUGAS_OBSERVASI = "Find Text Analytics, open its details, then return to the list."

/** Langkah observasi berbasis tugas (11.3), berurutan. */
val langkahObservasi: List<String> = listOf(
    "Biarkan teman mencoba tugas sebelum memberi petunjuk",
    "Catat apakah tugas selesai, keraguan, ketukan yang salah, dan permintaan bantuan",
    "Tanyakan apa yang ia pahami dari label aksi",
    "Tentukan satu masalah dan satu revisi desain"
)

/** Pertanyaan penutup 11.4. */
const val PERTANYAAN_PENUTUP =
    "Revisi mana yang paling meningkatkan penyelesaian tugas, dan bukti apa yang mendukungnya?"

@Composable
fun TabelMatriksUji(daftar: List<KasusUjiDesain> = matriksUjiCourseBrowser) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        daftar.forEach { kasus ->
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(kasus.pengujian, style = MaterialTheme.typography.titleSmall)
                Text(kasus.buktiDiharapkan, style = MaterialTheme.typography.bodyMedium)
                val duaPanel = harapanDuaPanel(kasus)
                if (duaPanel != null) {
                    Text(
                        if (duaPanel) "Kebijakan: dua panel" else "Kebijakan: satu panel",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 520)
@Composable
private fun TabelMatriksUjiPreview() {
    Column(Modifier.padding(16.dp)) { TabelMatriksUji() }
}
