package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofileeditor.ui.theme.StudentProfileEditorTheme

/**
 * Bab 9 (Bagian 10) — Membangun Student Profile Editor
 *
 * Fitur dibangun dalam empat langkah mengikuti implementasi acuan modul. Scaffold,
 * TopAppBar, dan tema dari modul sebelumnya tidak diubah.
 *
 *   Langkah 1 — Induk memiliki state (savedBio, draftBio, isEditing) dengan
 *               rememberSaveable agar editor bertahan saat rotasi, plus dua derived
 *               value normalizedBio dan canSave.
 *   Langkah 2 — Lambda Edit, Save, Cancel diteruskan induk ke composable anak.
 *   Langkah 3 — UI dipecah menjadi dua composable stateless: BioReadOnly dan BioEditor.
 *   Langkah 4 — Validasi dan umpan balik: isError, penghitung panjang mentah, dan pesan
 *               kesalahan. Input TIDAK dipotong diam-diam pada karakter ke-80.
 *
 * Tiga transisi:
 *   Edit   : salin savedBio ke draftBio, lalu buka editor.
 *   Save   : periksa canSave sekali lagi (penjaga kedua), simpan normalizedBio, tutup.
 *   Cancel : kembalikan savedBio ke draftBio, tutup tanpa mengubah savedBio.
 *
 * Mengapa draf disalin ulang saat Edit padahal Cancel sudah mengembalikannya? Karena Save
 * menyimpan teks yang sudah di-trim, sementara draf tetap berisi teks mentah. Draf
 * "  Hi  " disimpan sebagai "Hi", jadi tanpa penyalinan saat Edit editor akan terbuka
 * dengan "  Hi  ", bukan bio yang benar-benar tersimpan. Contoh kasus itu dapat dilihat
 * pada Latihan 6.
 */

/** Bagian 10.1 dan 10.2 — induk stateful yang memegang seluruh state editor. */
@Composable
fun StudentProfileEditor(modifier: Modifier = Modifier) {
    // Langkah 1: induk memiliki tiga state.
    var savedBio by rememberSaveable { mutableStateOf("Informatics student") }
    var draftBio by rememberSaveable { mutableStateOf(savedBio) }
    var isEditing by rememberSaveable { mutableStateOf(false) }

    // Derived value: dihitung ulang pada setiap recomposition.
    val normalizedBio = draftBio.trim()
    val canSave = normalizedBio.isNotEmpty() && normalizedBio.length <= 80

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Gunakan kembali ProfileCard() dari pertemuan sebelumnya jika diinginkan.
        Text("Student bio")

        if (isEditing) {
            // Langkah 2 dan 4: event ke atas, validasi diteruskan ke editor stateless.
            BioEditor(
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
            BioReadOnly(
                savedBio = savedBio,
                onEdit = {
                    draftBio = savedBio
                    isEditing = true
                }
            )
        }
    }
}

/** Bagian 10.3 — stateless: hanya menampilkan bio tersimpan dan memanggil onEdit. */
@Composable
fun BioReadOnly(savedBio: String, onEdit: () -> Unit) {
    Text(savedBio)
    Button(onClick = onEdit) { Text("Edit bio") }
}

/** Bagian 10.3, 10.4, 10.5 — stateless: UI saja, tanpa state. */
@Composable
fun BioEditor(
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

/**
 * Preview opsional dari modul: keadaan tidak valid ditampilkan tanpa menjalankan
 * aplikasi, cukup dengan nilai tetap — salah satu keuntungan composable stateless.
 */
@Preview(showBackground = true)
@Composable
fun BioEditorInvalidPreview() {
    StudentProfileEditorTheme {
        BioEditor(
            draftBio = "",
            canSave = false,
            onDraftChange = {},
            onSave = {},
            onCancel = {}
        )
    }
}
