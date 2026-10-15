package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.StudentTask
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 3 (Bagian 4) — Mengenal LazyColumn
 *
 * Bentuk paling sederhana: satu Text untuk setiap judul tugas.
 *
 *   LazyColumn {
 *       items(tasks, key = { it.id }) { task -> Text(task.title) }
 *   }
 *
 * items(tasks, ...) membuat satu item untuk setiap elemen dalam tasks dan memberikan
 * elemen tersebut ke lambda sebagai task. Parameter key memberi tahu Compose identitas
 * setiap item.
 *
 * PERIKSA IMPORT: items adalah extension function, dan untuk LazyColumn serta LazyRow
 * asalnya dari androidx.compose.foundation.lazy.items. Android Studio kadang menyarankan
 * import yang lain, jadi periksa baris ini bila muncul error.
 *
 * 4.1 Anatomi LazyColumn
 *
 * Lambda di belakang LazyColumn berbeda dari lambda Column: isinya adalah DSL (bahasa kecil
 * untuk mendeskripsikan item). Di dalamnya kita tidak memanggil Text secara langsung,
 * melainkan memanggil item atau items lalu menaruh composable di dalam keduanya.
 *
 *   Bagian            Peran
 *   LazyColumn { }    kontainer yang menggulir vertikal; lambda-nya adalah DSL pembangun daftar
 *   item { }          menambahkan tepat satu elemen tetap, misalnya judul bagian
 *   items(tasks, ...) menambahkan satu elemen untuk setiap data dalam daftar
 *   key = { it.id }   identitas yang stabil untuk setiap data
 *   task ->           data yang sedang ditampilkan
 *   isi lambda        tampilan satu data di layar
 *
 * Dengan 12 tugas dan satu item judul, daftar itu memiliki 13 item.
 */

/** Daftar paling sederhana: satu Text per tugas, tanpa kartu. */
@Composable
fun TaskTitleList(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(tasks, key = { it.id }) { task ->
            Text(
                text = task.title,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/** Anatomi lengkap: satu item tetap di atas, lalu satu item untuk setiap data. */
@Composable
fun ContohLazyColumnJudulBagian(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("This week", style = MaterialTheme.typography.titleSmall)
        }
        items(sampleTasks, key = { it.id }) { task ->
            Text(task.title, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun TaskTitleListPreview() {
    StudentTaskCatalogTheme { TaskTitleList(sampleTasks) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ContohLazyColumnJudulBagianPreview() {
    StudentTaskCatalogTheme { ContohLazyColumnJudulBagian() }
}
