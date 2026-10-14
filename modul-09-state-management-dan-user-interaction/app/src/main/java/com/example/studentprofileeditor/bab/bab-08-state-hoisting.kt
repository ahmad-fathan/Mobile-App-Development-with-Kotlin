package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
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
 * Bab 8 (Bagian 9) — State Hoisting
 *
 * Masalah dua salinan: bila induk menyimpan bio sementara editor menyimpan salinan bionya
 * sendiri, setelah pengguna mengetik ada dua bio yang berbeda, dan pertanyaan Save/Cancel
 * menjadi sulit dijawab. Solusinya state hoisting: draf dipindahkan ke INDUK sehingga
 * hanya ada satu pemilik (single source of truth).
 *
 *   Composable stateful    : membuat dan memiliki state.
 *   Composable stateless   : tidak memiliki state; semua yang ditampilkan datang sebagai
 *                            parameter, semua aksi dikirim keluar sebagai callback.
 *
 * Fungsi type yang dipakai: (String) -> Unit untuk menerima teks baru, dan () -> Unit
 * untuk event tanpa muatan.
 *
 * Callback diberi nama menurut NIAT pengguna, bukan perubahan yang dilakukan:
 *   onEdit(), onDraftChange(newText), onSave(), onCancel().
 *
 * Pola state turun dan event naik inilah unidirectional data flow (UDF). Ikuti alurnya
 * saat draf "Hi" menjadi "Hi!":
 *   1. pengguna mengetik tanda seru, field memanggil onDraftChange("Hi!");
 *   2. induk menjalankan bioDraft = "Hi!";
 *   3. Compose melakukan recomposition dan editor menerima bioDraft = "Hi!";
 *   4. field menampilkan "Hi!" dan penghitung menampilkan 3/80.
 *
 * Semua nama di bab ini diberi awalan "Hoist" supaya tidak bentrok dengan komponen editor
 * lengkap pada Bab 9 dan latihan modul.
 */

/** Stateless: semua nilai masuk lewat parameter, semua aksi keluar lewat callback. */
@Composable
fun HoistBioEditor(
    draftBio: String,
    canSave: Boolean,
    onDraftChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = onDraftChange,
            label = { Text("Bio") },
            minLines = 2,
            maxLines = 4,
            isError = !canSave,
            modifier = Modifier.fillMaxWidth()
        )
        Text("${draftBio.length}/80 characters")
        if (!canSave) {
            Text("Bio must contain 1–80 characters after trimming spaces.")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onSave, enabled = canSave) { Text("Save") }
            OutlinedButton(onClick = onCancel) { Text("Cancel") }
        }
    }
}

/** Stateful: memiliki state dan meneruskannya ke editor stateless di atas. */
@Composable
fun HoistEditorDemo(modifier: Modifier = Modifier) {
    var savedBio by rememberSaveable { mutableStateOf("Informatics student") }
    var draftBio by rememberSaveable { mutableStateOf(savedBio) }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    val normalizedBio = draftBio.trim()
    val canSave = normalizedBio.isNotEmpty() && normalizedBio.length <= 80

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (isEditing) {
            HoistBioEditor(
                draftBio = draftBio,
                canSave = canSave,
                onDraftChange = { draftBio = it },
                onSave = {
                    if (canSave) {
                        savedBio = normalizedBio
                        isEditing = false
                    }
                },
                onCancel = {
                    draftBio = savedBio
                    isEditing = false
                }
            )
        } else {
            Text(savedBio)
            Button(onClick = { draftBio = savedBio; isEditing = true }) { Text("Edit bio") }
        }
    }
}
