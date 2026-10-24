package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.KodeBlok
import com.example.studytasks.Tabel

/**
 * Bab 2 (Bagian 2) — Ketika Satu Composable Mengerjakan Semuanya
 *
 * Sebelum refactoring, layar daftar tugas memikul semua tugas sekaligus:
 *   • menampilkan text field, tombol, dan daftar tugas;
 *   • menyimpan draf yang sedang diketik dan daftar tugas;
 *   • memeriksa apakah judul yang dikirim kosong;
 *   • memberikan ID tugas dan mengubah data aplikasi;
 *   • kelak, membaca data dari database atau web service ketika aplikasi berkembang.
 *
 * [TaskScreenBefore] di bawah adalah versi ringkasnya, persis seperti pada modul. Kode ini
 * BERJALAN dengan baik dan untuk percobaan singkat masih wajar — itulah sebabnya ia disimpan di
 * sini sebagai pembanding, bukan dihapus.
 *
 * Yang perlu diperhatikan:
 *   1. tampilan, penanganan input, aturan validasi, dan perubahan data berada di satu blok;
 *   2. jika aturan penyimpanan berubah (mis. ke database), kode layout terpaksa ikut diubah;
 *   3. aturan validasinya tidak dapat dipakai ulang di layar kedua;
 *   4. layar tidak dapat di-preview dengan daftar berbeda tanpa menekan tombolnya;
 *   5. `remember` hilang ketika Activity dibuat ulang (rotasi), sehingga draf DAN daftar hilang.
 *
 * Bandingkan dengan `TasksScreen` di paket akar: layar itu tidak punya keduanya.
 */

/** Layar sebelum refactoring: semuanya di dalam satu fungsi, dengan state sendiri. */
@Composable
fun TaskScreenBefore() {
    var draft by remember { mutableStateOf("") }
    var titles by remember { mutableStateOf(listOf<String>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = draft,
            onValueChange = { draft = it },
            label = { Text("Task title") }
        )
        Button(onClick = {
            if (draft.isNotBlank()) {
                titles = titles + draft.trim()
                draft = ""
            }
        }) { Text("Add task") }
        LazyColumn {
            items(titles, key = { it }) { title -> Text(title) }
        }
    }
}

/** Kode sumber [TaskScreenBefore] apa adanya, untuk ditampilkan di layar bab. */
val kodeTaskScreenBefore: String = """
    @Composable
    fun TaskScreenBefore() {
        var draft by remember { mutableStateOf("") }
        var titles by remember { mutableStateOf(listOf<String>()) }

        Column {
            OutlinedTextField(
                value = draft,
                onValueChange = { draft = it }
            )
            Button(onClick = {
                if (draft.isNotBlank()) {
                    titles = titles + draft.trim()
                    draft = ""
                }
            }) { Text("Add task") }
            titles.forEach { title -> Text(title) }
        }
    }
"""

@Composable
fun SatuComposableMengerjakanSemuaScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 2 — Ketika satu Composable mengerjakan semuanya",
            keterangan = "Kode pembanding sebelum refactoring."
        )
        KodeBlok(kodeTaskScreenBefore)
        Tabel(
            "Lima tanggung jawab yang bercampur di satu fungsi",
            listOf(
                "Tampilan" to "Column, OutlinedTextField, Button, Text",
                "Input" to "onValueChange = { draft = it }",
                "Validasi" to "isNotBlank() di dalam onClick",
                "Perubahan data" to "titles + draft.trim() dan draft = \"\"",
                "Identitas data" to "tidak ada ID — nanti LazyColumn memakai judul sebagai key"
            )
        )
        Tabel(
            "Dua akibat yang langsung terasa",
            listOf(
                "Masa hidup" to "remember hilang saat Activity dibuat ulang (rotasi): draf " +
                    "dan seluruh daftar tugas hilang",
                "Uji ulang" to "aturan validasi tidak dapat dipakai layar kedua dan layar tidak " +
                    "dapat di-preview dengan daftar lain"
            )
        )
        Text(
            "Coba jalankan layar di bawah, lalu rotasi perangkat: isian dan daftar kembali kosong.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Praktik: layar berjalan untuk membuktikan masalah masa hidup `remember`. */
@Composable
fun PraktikTaskScreenBefore() {
    MaterialTheme {
        TaskScreenBefore()
    }
}

@Preview(showBackground = true)
@Composable
fun SatuComposableMengerjakanSemuaPreview() {
    SatuComposableMengerjakanSemuaScreen()
}
