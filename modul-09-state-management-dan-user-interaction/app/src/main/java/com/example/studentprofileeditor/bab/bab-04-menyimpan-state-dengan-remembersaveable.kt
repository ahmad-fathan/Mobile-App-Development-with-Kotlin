package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bab 4 (Bagian 5) — Menyimpan State UI dengan rememberSaveable
 *
 * rememberSaveable berperilaku sama seperti remember selama recomposition. Bedanya,
 * nilainya juga disimpan melalui mekanisme saved instance state milik Android, sehingga
 * dipulihkan ketika Activity dibuat ulang. Perubahan kodenya hanya satu kata:
 *
 *   var draftBio by rememberSaveable { mutableStateOf("") }
 *   var isEditing by rememberSaveable { mutableStateOf(false) }
 *
 * Import-nya dari paket androidx.compose.runtime.saveable. Sebelum Activity lama
 * dihancurkan, nilai ditulis ke sebuah Bundle; composition baru memulihkannya.
 *
 *   Situasi                                   remember   rememberSaveable
 *   Recomposition                             Tetap      Tetap
 *   Activity dibuat ulang (rotasi/dark mode)  Hilang     Dipulihkan
 *   Proses dihentikan sistem di latar, lalu   Hilang     Dipulihkan
 *   pengguna kembali
 *   Aplikasi digeser dari Recents / force     Hilang     Tidak dipulihkan
 *   stop
 *   Composable keluar dari composition        Dilupakan  Dilupakan
 *
 * Yang cocok disimpan: nilai kecil seperti String, Boolean, dan Int (draf teks, mode
 * edit, tab terpilih). Class buatan sendiri butuh @Parcelize atau Saver. Objek besar dan
 * daftar panjang TIDAK cocok: ukuran Bundle terbatas dan dapat memicu
 * TransactionTooLargeException. rememberSaveable bukan basis data.
 *
 * Draf bio yang sedang diketik layak memakai rememberSaveable; flag yang hanya
 * menggerakkan animasi singkat cukup memakai remember.
 */
@Composable
fun BioDraftSaveableTest(modifier: Modifier = Modifier) {
    var draftBio by rememberSaveable { mutableStateOf("") }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = { draftBio = it },
            label = { Text("Draft (rememberSaveable)") },
            modifier = Modifier.fillMaxWidth()
        )
        Checkbox(
            checked = isEditing,
            onCheckedChange = { isEditing = it }
        )
        Text("isEditing: $isEditing (dipulihkan setelah rotasi)")
    }
}
