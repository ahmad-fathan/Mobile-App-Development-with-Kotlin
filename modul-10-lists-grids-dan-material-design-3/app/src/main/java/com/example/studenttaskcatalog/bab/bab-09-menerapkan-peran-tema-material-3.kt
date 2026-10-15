package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 9 (Bagian 10) — Menerapkan Peran Tema Material 3
 *
 * MaterialTheme.colorScheme dan MaterialTheme.typography sudah dipelajari pada pertemuan
 * sebelumnya. Hal baru di sini adalah memakainya dengan cara yang SAMA pada setiap item yang
 * diulang. Tabel 4 modul menjadi panduan gaya untuk TaskCard:
 *
 *   Elemen kartu              Peran tema
 *   Judul tugas               typography.titleMedium
 *   Mata kuliah               typography.bodyMedium + colorScheme.onSurfaceVariant
 *   Kategori dan tenggat      typography.labelMedium
 *   Label status              labelMedium di atas primaryContainer / onPrimaryContainer
 *   Latar kartu               warna kontainer bawaan Card (dari tema)
 *   Chip filter terpilih      FilterChip(selected = true) memakai warna terpilih
 *
 * 10.1 Label Status dengan Pasangan Warna
 *
 * Warna latar dan warna teks selalu dipilih BERPASANGAN: primaryContainer dengan
 * onPrimaryContainer, surface dengan onSurface. Tema mendefinisikan kedua anggota pasangan
 * untuk mode terang dan mode gelap, sehingga kontras teks tetap terjaga di kedua mode. Warna
 * hijau yang ditulis langsung, misalnya Color(0xFF00C853), mungkin terbaca di atas latar
 * putih tetapi sulit terbaca di atas permukaan gelap.
 *
 * Status belum selesai memakai GARIS TEPI, bukan warna isi, sehingga kedua status berbeda
 * bentuk dan juga warna. Warna tidak pernah menjadi satu-satunya penanda: label selalu
 * bertuliskan "Completed" atau "Pending". BorderStroke diimpor dari
 * androidx.compose.foundation.BorderStroke.
 *
 * 10.2 Status yang Berasal dari Data
 *
 * Teks pada label dibaca dari field isCompleted setiap data. Karena TaskCard dipanggil sekali
 * untuk setiap tugas, menambahkan StatusLabel(isCompleted = task.isCompleted) di dalam
 * TaskCard langsung mengubah semua kartu di daftar dan grid sekaligus — tidak ada kartu yang
 * diubah satu per satu.
 *
 * isCompleted di sini adalah DATA BIASA di dalam daftar, bukan mutableStateOf. Mengubah status
 * secara interaktif, misalnya dengan checkbox, akan menggabungkan materi ini dengan state
 * hoisting dari pertemuan sebelumnya, dan itu menjadi pengembangan lanjutan.
 */

/** Label status; dipakai oleh TaskCard pada Bab 4. */
@Composable
fun StatusLabel(isCompleted: Boolean, modifier: Modifier = Modifier) {
    // Makna status dibawa oleh teks; peran warna hanya mendukungnya.
    val container = if (isCompleted) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surface
    val content = if (isCompleted) MaterialTheme.colorScheme.onPrimaryContainer
                  else MaterialTheme.colorScheme.onSurface
    Surface(
        modifier = modifier,
        color = container,
        contentColor = content,
        shape = MaterialTheme.shapes.small,
        border = if (isCompleted) null
                 else BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Text(
            text = if (isCompleted) "Completed" else "Pending",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

/** Kedua status berdampingan, untuk memeriksa kontras di tema terang dan tema gelap. */
@Composable
fun ContohKeduaStatus(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Status dari data (Bab 10.2):", style = MaterialTheme.typography.titleSmall)
        StatusLabel(isCompleted = true)
        StatusLabel(isCompleted = false)
    }
}

/** Warna yang ditulis langsung tidak mengikuti tema — bandingkan pada mode gelap. */
@Composable
fun ContohWarnaLangsungYangSalah(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = androidx.compose.ui.graphics.Color(0xFF00C853),
        contentColor = androidx.compose.ui.graphics.Color.White,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = "Completed",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusLabelPreview() {
    StudentTaskCatalogTheme { ContohKeduaStatus() }
}
