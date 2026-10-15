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
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 5 (Bagian 6) — Key yang Stabil
 *
 * Tanpa key, lazy layout mengenali setiap item berdasarkan POSISINYA. Selama daftar tidak
 * pernah berubah, cara ini cukup. Masalah muncul ketika data disisipkan, dihapus, atau
 * diurutkan ulang.
 *
 * Contoh: kartu ketiga sedang terbuka (state yang di-remember di dalam item), lalu tugas
 * baru disisipkan di posisi paling atas. Tanpa key, semua data bergeser satu posisi ke
 * bawah, tetapi state yang tersimpan tetap berada di posisi 3 — keadaan terbuka itu kini
 * menempel pada tugas yang salah. Dengan key = { it.id }, Compose mengaitkan setiap item
 * dengan DATANYA, sehingga state ikut berpindah bersama tugas yang benar. Key juga membantu
 * Compose mempertahankan posisi gulir dan menganimasikan perubahan dengan benar.
 *
 * Aturan memilih key:
 *   - Unik    — dua item dengan key sama di satu daftar membuat aplikasi crash.
 *   - Stabil  — data yang sama selalu memiliki key yang sama. id ideal; indeks daftar tidak,
 *               karena indeks berubah setiap kali daftar berubah.
 *   - Dapat disimpan — di Android tipe key harus dapat disimpan dalam Bundle. Int dan String
 *               memenuhi syarat ini.
 *
 * Perhatikan juga bentuk items(tasks.size) { index -> ... } (varian berbasis indeks): varian
 * itu tidak menerima key, sehingga tidak boleh dipakai untuk daftar yang dapat berubah.
 */

/** Benar: state item mengikuti data, karena key = id. */
@Composable
fun DaftarDenganKey(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleTasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

/** Salah: varian berbasis indeks tidak menerima key, jadi state menempel pada posisi. */
@Composable
fun DaftarTanpaKey(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleTasks.size) { index ->
            val task = sampleTasks[index]
            Text(
                text = "${index + 1}. ${task.title}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

/** Key tidak harus id: tipe apa pun yang unik, stabil, dan dapat disimpan boleh dipakai. */
@Composable
fun ContohKeyBerupaString(modifier: Modifier = Modifier) {
    val judulUnik = sampleTasks.map { it.title }
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(16.dp)) {
        items(judulUnik, key = { it }) { judul ->
            Text(judul, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun DaftarTanpaKeyPreview() {
    StudentTaskCatalogTheme { DaftarTanpaKey() }
}
