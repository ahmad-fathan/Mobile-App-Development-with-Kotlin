package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 1 (Bagian 2) — Dari Kumpulan Data ke Item Tampilan
 *
 * Sebelum menggambar apa pun, tentukan bentuk datanya. Setiap tugas kuliah memiliki enam
 * informasi: nomor identitas, judul, mata kuliah, kategori, tanggal tenggat, dan status
 * selesai. Keenamnya dikumpulkan pada satu data class StudentTask.
 *
 * Deklarasi StudentTask, sampleTasks, dan taskCategories berada di paket akar aplikasi
 * (berkas StudentTask.kt), karena dipakai ulang oleh seluruh bab, latihan, dan pemeriksaan
 * otomatis — satu source set Kotlin hanya boleh mendeklarasikan satu nama.
 *
 * Satu objek StudentTask = satu item di layar; List<StudentTask> = banyak item dengan desain
 * sama dan isi berbeda. Fungsi di bawah menunjukkan hubungan itu: satu kartu untuk setiap
 * objek di dalam daftar.
 *
 * Untuk modul ini data disimpan di memori dengan listOf(...): belum ada database, API, atau
 * ViewModel, karena fokus pertemuan ini cara MENAMPILKAN kumpulan data, bukan cara
 * memperolehnya. Tiga aturan data contoh: cukup banyak (10–12 tugas agar daftar perlu digulir),
 * id unik (id dipakai sebagai key), dan teks singkat (judul dua atau tiga kata).
 */

/** Satu contoh: tiga data pertama digambar sebagai tiga kartu, satu kartu per objek. */
@Composable
fun ContohKumpulanDataKeItem(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "${sampleTasks.size} tasks in sampleTasks",
            style = MaterialTheme.typography.titleSmall
        )
        sampleTasks.take(3).forEach { task ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${task.id}. ${task.title}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContohKumpulanDataKeItemPreview() {
    StudentTaskCatalogTheme { ContohKumpulanDataKeItem() }
}
