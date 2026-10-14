package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bab 1 (Bagian 2) — Dari Layar Profil ke Editor Profil
 *
 * Titik awal modul ini adalah layar Student Profile dari modul sebelumnya. Satu-satunya
 * kode lama yang diulang di modul ini adalah dua baris state sederhana berikut:
 *
 *   var name by remember { mutableStateOf("") }
 *   OutlinedTextField(value = name, onValueChange = { name = it })
 *
 * Kode itu cukup untuk satu nilai dan satu aksi. Editor bio membutuhkan lebih dari itu,
 * dengan kebutuhan fitur sebagai berikut (daftar dari modul):
 *
 *   1. Edit membuka editor bio.
 *   2. Draf bio berubah selama pengguna mengetik, sedangkan bio tersimpan hanya berubah
 *      setelah Save.
 *   3. Save hanya boleh dilakukan jika teks setelah di-trim berisi 1 sampai 80 karakter.
 *   4. Cancel membuang perubahan yang belum disimpan.
 *   5. Rotasi biasa tidak boleh menghilangkan draf maupun mode edit.
 *
 * Dua kata kunci pada kebutuhan itu — draf dan tersimpan — yang membuat editor bio
 * membutuhkan lebih dari satu state.
 */
@Composable
fun ContohSatuNilaiSatuAksi(modifier: Modifier = Modifier) {
    // Potongan dari modul Compose Layouts and UI Components: satu state, satu aksi.
    var name by remember { mutableStateOf("") }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Text("Hello, ${name.trim().ifEmpty { "guest" }}!")
    }
}
