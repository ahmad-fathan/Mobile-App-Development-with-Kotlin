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
import com.example.campuscoursebrowser.MasalahUmumAdaptif
import com.example.campuscoursebrowser.masalahUmumAdaptif
import com.example.campuscoursebrowser.semuaMasalahPunyaSolusi

/**
 * Bab 11 (Bagian 12) — Mengatasi Masalah Umum
 *
 * Tabel 12 merangkum masalah yang sering muncul pada contoh pertemuan ini. Tiga yang paling
 * sering terjadi:
 *
 *   - `currentWindowAdaptiveInfoV2` tidak dikenali → dependensi adaptive lebih lama dari 1.3.0,
 *     atau import salah. Tambahkan adaptive:1.3.0 lalu Gradle sync.
 *   - `isWidthAtLeastBreakpoint` tidak ditemukan → WindowSizeClass masih dari API lama
 *     `androidx.compose.material3.windowsizeclass`. Pakai
 *     `androidx.window.core.layout.WindowSizeClass`.
 *   - Layout tidak pernah dua panel → lebar < 840 dp ATAU tinggi < 480 dp. Periksa ukuran jendela
 *     dalam dp, bukan piksel; ponsel landscape umumnya di bawah 480 dp.
 *
 * Daftar masalahnya ada di MasalahUmumAdaptif (paket akar, verifikasi.kt) agar layar dan
 * pemeriksaan otomatis memakai sumber yang sama.
 */

@Composable
fun TabelMasalahUmum(daftar: List<MasalahUmumAdaptif> = masalahUmumAdaptif) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        daftar.forEach { baris ->
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(baris.masalah, style = MaterialTheme.typography.titleSmall)
                Text("Penyebab: ${baris.penyebab}", style = MaterialTheme.typography.bodySmall)
                Text("Solusi: ${baris.solusi}", style = MaterialTheme.typography.bodySmall)
            }
        }
        Text(
            "Semua ${daftar.size} masalah punya penyebab dan solusi: " +
                "${semuaMasalahPunyaSolusi(daftar)}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 560)
@Composable
private fun TabelMasalahUmumPreview() {
    Column(Modifier.padding(16.dp)) { TabelMasalahUmum() }
}
