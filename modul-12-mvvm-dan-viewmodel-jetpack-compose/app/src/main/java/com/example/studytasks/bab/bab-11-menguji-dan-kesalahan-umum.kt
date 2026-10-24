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
import com.example.studytasks.Tabel
import com.example.studytasks.kesalahanUmumMvvm
import com.example.studytasks.pemeriksaanAplikasiTasks

/**
 * Bab 11 (Bagian 12–13) — Menguji Perilaku dan Kesalahan Umum
 *
 * Bagian 12: jalankan aplikasi dan periksa setiap perilaku pada Tabel 8. Setiap baris menguji apa
 * yang dilihat pengguna SEKALIGUS komponen mana yang bertanggung jawab. Dua catatan penting:
 *   • pemeriksaan rotasi hanya membuktikan sesuatu jika Activity benar-benar dibuat ulang;
 *   • pemeriksaan ini tidak menguji process death sama sekali.
 * Sebagai pembanding, lakukan rotasi yang sama pada TaskScreenBefore: draf dan daftarnya hilang.
 *
 * Bagian 13: Tabel 9 memuat kesalahan yang sering terjadi saat pertama kali membangun pola ini.
 * Berkas verifikasi.kt di paket akar menguji aturan data layer (repository) dan isi tabel-tabel
 * modul ini, sehingga tabel di layar dan yang diperiksa tidak dapat berbeda diam-diam.
 *
 * Verifikasi dijalankan dari terminal:
 *
 *   ./jalankan.sh 12 perilaku
 */

@Composable
fun MengujiDanKesalahanUmumScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 12–13 — Menguji perilaku dan kesalahan umum",
            keterangan = "Setiap perilaku punya pemilik jawaban; setiap kesalahan punya perbaikan."
        )
        Tabel(
            "Tabel 8. Pemeriksaan untuk aplikasi Study Tasks",
            pemeriksaanAplikasiTasks.map { it.aksi to "${it.harapan} — ${it.pemilikJawaban}" }
        )
        Text(
            "Bandingkan dengan TaskScreenBefore: setelah rotasi, draf dan daftarnya hilang karena " +
                "remember hanya hidup selama Composition.",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurfaceVariant
        )
        Tabel(
            "Tabel 9. Kesalahan umum saat menggunakan ViewModel dengan Compose",
            kesalahanUmumMvvm.map { it.kesalahan to "${it.perbaikan} (${it.bagian})" }
        )
        Tabel(
            "Cara memeriksa modul ini",
            listOf(
                "./jalankan.sh 12 perilaku" to "Menjalankan verifikasi.kt tanpa emulator",
                "Isi verifikasi" to "Aturan data layer, MVVM, dan matriks ketahanan state (Tabel 2–9)",
                "Lulus" to "76 pemeriksaan, dijalankan dari terminal — bukan dari layar ini"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MengujiDanKesalahanUmumPreview() {
    MengujiDanKesalahanUmumScreen()
}
