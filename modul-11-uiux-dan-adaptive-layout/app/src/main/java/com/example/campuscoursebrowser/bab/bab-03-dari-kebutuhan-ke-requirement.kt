package com.example.campuscoursebrowser.bab

import com.example.campuscoursebrowser.RequirementUi
import com.example.campuscoursebrowser.requirementCourseBrowser
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Bab 3 (Bagian 4) — Dari Kebutuhan ke Requirement yang Dapat Diamati
 *
 * Kebutuhan pengguna masih umum. Requirement menjadi berguna ketika tim dapat mengamati apakah
 * ia sudah terpenuhi, jadi setiap keputusan UI harus disertai cara memeriksanya.
 *
 * Kolom pertama sengaja ditulis sebagai "kebutuhan yang perlu diselidiki": selama belum
 * dikonfirmasi pengguna, ia masih hipotesis. Baris terakhir diwujudkan pada Bab 8, ketika
 * pilihan mata kuliah harus bertahan saat layout berganti.
 */

/**
 * Catatan: RequirementUi, requirementCourseBrowser, semuaRequirementDapatDiperiksa, dan
 * requirementTentangUkuranJendela dideklarasikan di paket akar (berkas verifikasi.kt) karena
 * dipakai juga oleh berkas lain dan oleh pemeriksaan otomatis. Satu source set Kotlin hanya
 * boleh mendeklarasikan satu nama, jadi bab ini mengimpornya, bukan menyalinnya.
 */


@Composable
fun TabelRequirement(daftar: List<RequirementUi> = requirementCourseBrowser) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        daftar.forEach { baris ->
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(baris.kebutuhan, style = MaterialTheme.typography.titleMedium)
                Text(baris.keputusanUi, style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Cara memeriksa: ${baris.caraMemeriksa}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 480)
@Composable
private fun TabelRequirementPreview() {
    TabelRequirement()
}
