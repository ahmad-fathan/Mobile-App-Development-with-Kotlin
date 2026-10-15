package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
 * Bab 8 (Bagian 9) — Merancang Kartu untuk List dan Grid
 *
 * TaskCard yang sama hidup di dua lebar yang sangat berbeda: sekitar 380 dp di dalam daftar
 * dan sekitar 180 dp di dalam sel grid. Desain kartu perlu tetap rapi di kedua kondisi.
 *
 * Pedoman praktis:
 *   - Gunakan label singkat, dua atau tiga kata.
 *   - Susun metadata secara VERTIKAL. Row yang menaruh mata kuliah di kiri dan tanggal di
 *     kanan cocok untuk daftar, tetapi terhimpit di dalam sel grid.
 *   - Biarkan judul panjang turun baris, tetapi batasi dengan maxLines agar satu judul tidak
 *     membuat sel sangat tinggi.
 *   - Hindari lebar tetap seperti Modifier.width(300.dp) di dalam kartu; biarkan induknya
 *     menentukan lebar melalui parameter modifier.
 *
 * Berkas ini membandingkan langsung susunan yang SALAH (metadata mendatar, seperti yang
 * terlihat rapi pada daftar) dengan susunan yang BENAR (metadata vertikal, aman di kedua
 * lebar). Perilaku serupa juga dapat diperiksa lewat pemeriksaan otomatis: setiap kartu
 * contoh pada modul ini diawali judul, lalu mata kuliah, lalu kategori dan tenggat.
 */

/** SALAH untuk grid: metadata di kiri-kanan terhimpit pada sel selebar sekitar 180 dp. */
@Composable
fun ContohKartuMetadataMendatar(task: StudentTask, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(task.course, style = MaterialTheme.typography.bodyMedium)
                Text(task.dueDate, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

/** BENAR: metadata vertikal, aman baik pada lebar daftar maupun lebar sel grid. */
@Composable
fun ContohKartuMetadataVertikal(task: StudentTask, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
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
        }
    }
}

@Preview(showBackground = true, widthDp = 180)
@Composable
fun KartuMendatarSempitPreview() {
    StudentTaskCatalogTheme { ContohKartuMetadataMendatar(task = sampleTasks[0]) }
}

@Preview(showBackground = true, widthDp = 180)
@Composable
fun KartuVertikalSempitPreview() {
    StudentTaskCatalogTheme { ContohKartuMetadataVertikal(task = sampleTasks[0]) }
}
