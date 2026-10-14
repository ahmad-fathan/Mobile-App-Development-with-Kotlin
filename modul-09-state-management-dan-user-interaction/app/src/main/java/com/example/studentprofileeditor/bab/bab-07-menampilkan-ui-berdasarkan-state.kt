package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bab 7 (Bagian 8) — Menampilkan UI Berdasarkan State
 *
 * Di Compose, menampilkan atau menyembunyikan UI cukup dengan ekspresi if biasa di dalam
 * composable. Tidak ada properti visibility yang perlu diubah:
 *
 *   if (isEditing) {
 *       // editor, validasi, Save, Cancel
 *   } else {
 *       // bio tersimpan dan tombol Edit
 *   }
 *
 * Satu nilai Boolean menentukan composable mana yang berada di dalam composition. Ketika
 * isEditing berubah, satu cabang keluar dari composition dan cabang lain masuk.
 *
 * Dua composable di bawah memperlihatkan akibatnya. Semua state yang dipakai bersama
 * disimpan di INDUK (bioSaved, bioDraft, isEditing), sehingga cabang yang keluar dari
 * composition tidak membawa serta state-nya. Pada Bab 8 (state hoisting) pola yang sama
 * dipakai untuk editor sungguhan.
 */
@Composable
fun PercabanganIfBiarkanIndukMemegangState(modifier: Modifier = Modifier) {
    var bioSaved by rememberSaveable { mutableStateOf("Informatics student") }
    var bioDraft by rememberSaveable { mutableStateOf(bioSaved) }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isEditing) {
            Text("Cabang editor aktif (isEditing = true)")
            Button(onClick = { bioSaved = bioDraft; isEditing = false }) { Text("Save") }
        } else {
            Text(bioSaved)
            Button(onClick = { bioDraft = bioSaved; isEditing = true }) { Text("Edit bio") }
        }
    }
}

/**
 * Bahaya menyimpan state DI DALAM cabang yang bisa keluar dari composition: begitu
 * cabangnya tidak dipanggil lagi, semua yang diingatnya dilupakan. Cabang di bawah ini
 * menyimpan penghitungnya sendiri, jadi nilainya kembali ke 0 setiap kali isEditing
 * dimatikan dan dinyalakan lagi.
 */
@Composable
fun CabangYangMelupakanStateSendiri(modifier: Modifier = Modifier) {
    var isEditing by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = { isEditing = !isEditing }) {
            Text(if (isEditing) "Tutup" else "Buka")
        }
        if (isEditing) {
            CabangEditorDenganStateSendiri()
        }
    }
}

/** State di dalam cabang: hilang setiap kali cabang keluar dari composition. */
@Composable
private fun CabangEditorDenganStateSendiri() {
    var hitunganKetikan by remember { mutableStateOf(0) }
    Button(onClick = { hitunganKetikan++ }) {
        Text("Ketikan dalam cabang: $hitunganKetikan")
    }
}
