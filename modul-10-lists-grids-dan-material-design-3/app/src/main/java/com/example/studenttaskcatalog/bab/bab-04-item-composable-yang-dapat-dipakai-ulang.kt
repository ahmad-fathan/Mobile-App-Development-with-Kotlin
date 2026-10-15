package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.StudentTask
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 4 (Bagian 5) — Item Composable yang Dapat Dipakai Ulang
 *
 * Satu tugas perlu menampilkan judul, mata kuliah, dan tanggal tenggat. Daripada menulis
 * susunan itu berulang kali, kita membuatnya satu kali sebagai composable bernama TaskCard,
 * lalu memanggilnya untuk setiap data.
 *
 * Dua kebiasaan baik pada TaskCard:
 *   - TaskCard menerima SELURUH objek StudentTask, sehingga pemanggilnya cukup menulis
 *     TaskCard(task).
 *   - TaskCard menerima parameter modifier dengan nilai bawaan, sehingga induknya (list
 *     atau grid) dapat mengatur ukuran dan posisinya. Jangan memasang lebar tetap seperti
 *     Modifier.width(300.dp) di dalam kartu.
 *
 * Setiap teks memakai PERAN tipografi (titleMedium, bodyMedium, labelMedium), bukan ukuran
 * huruf tertentu, sehingga semua kartu tampil seragam dan mengikuti tema aplikasi.
 * Sesuai Tabel 4 (Bagian 10): judul → titleMedium, mata kuliah → bodyMedium dengan
 * onSurfaceVariant, kategori dan tenggat → labelMedium. Label status ditambahkan di
 * Bagian 10.1 dan dideklarasikan pada Bab 9 (StatusLabel).
 *
 * 5.1 Daftar Vertikal yang Lengkap
 *
 * Dua parameter lazy container yang baru muncul di sini:
 *   - contentPadding menambahkan ruang di sekeliling SELURUH isi, DI DALAM area gulir.
 *     Kartu pertama tidak menempel ke TopAppBar dan kartu terakhir tidak menempel ke tepi
 *     bawah layar, tetapi saat digulir kartu dapat melewati area padding tanpa terpotong.
 *   - verticalArrangement = Arrangement.spacedBy(12.dp) memberi jarak yang sama di antara
 *     item.
 *
 * Mengapa tidak Modifier.padding saja? Modifier.padding pada LazyColumn memperkecil area
 * gulirnya, sehingga kartu terpotong di tepi yang diberi padding. contentPadding
 * mempertahankan seluruh area tetap dapat digulir.
 */

/** Satu kartu tugas; dipakai ulang oleh daftar (Bab ini) dan grid (Bab 7). */
@Composable
fun TaskCard(task: StudentTask, modifier: Modifier = Modifier) {
    // Card memakai warna kontainer bawaan, yang sudah berasal dari tema.
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.course,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${task.category} · Due ${task.dueDate}",
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(4.dp))
            StatusLabel(isCompleted = task.isCompleted)
        }
    }
}

/** Daftar vertikal lengkap: kartu yang sama untuk setiap data, ber-key pada id. */
@Composable
fun TaskList(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    StudentTaskCatalogTheme { TaskCard(task = sampleTasks[0]) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun TaskListPreview() {
    StudentTaskCatalogTheme { TaskList(tasks = sampleTasks) }
}
