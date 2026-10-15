package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 2 (Bagian 3) — Mengapa Lazy Layout?
 *
 * Column meng-compose SELURUH anaknya sekaligus, baik yang terlihat maupun yang jauh di
 * luar layar. Untuk formulir lima elemen itu tidak masalah; untuk 500 tugas berarti 500
 * item dibuat padahal layar hanya menampilkan sekitar enam.
 *
 * LazyColumn meng-compose dan menata letak item sesuai kebutuhan di sekitar viewport, yaitu
 * area yang sedang tampil. Saat pengguna menggulir, item yang mendekati viewport
 * di-compose, sedangkan item yang sudah jauh dapat dilepas atau dipakai ulang.
 *
 * Perhatikan: lazy layout tidak menjamin hanya item yang terlihat saja yang ada — Compose
 * dapat menyiapkan beberapa item di luar tepi viewport agar guliran mulus, dan jumlah
 * pastinya adalah detail implementasi. Yang penting: biaya tampilan bergantung pada apa
 * yang berada di dekat layar, bukan pada panjang seluruh daftar.
 */

/** Cara lama: seluruh 12 tugas di-compose sekaligus, tanpa memperhatikan viewport. */
@Composable
fun ContohColumnSeluruhDaftar(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        sampleTasks.forEach { task ->
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

/** Cara lazy: item dibuat sesuai kebutuhan di sekitar viewport. */
@Composable
fun ContohLazyColumn(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleTasks, key = { it.id }) { task ->
            Text(text = task.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ContohLazyColumnPreview() {
    StudentTaskCatalogTheme { ContohLazyColumn() }
}
