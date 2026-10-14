package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
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
 * Bab 2 (Bagian 3 dan 3.1) — State dan Event
 *
 * State = data yang menggambarkan apa yang ditampilkan UI saat ini (savedBio, draftBio,
 * isEditing). Event = sesuatu yang terjadi, seperti ketukan atau ketikan, yang meminta UI
 * berubah (Edit, Type, Save, Cancel).
 *
 *   Aspek    State                            Event
 *   Contoh   savedBio, draftBio, isEditing    Edit, Type, Save, Cancel
 *   Peran    Menggambarkan UI saat ini        Meminta sebuah transisi
 *   Umur     Selama pemiliknya menyimpannya   Sesaat: satu ketukan atau ketikan
 *
 * Mengapa menekan Save bukan state? Ketukan Save adalah event; kode memeriksa canSave,
 * lalu memperbarui savedBio dan mengubah isEditing menjadi false. Yang disimpan adalah
 * HASIL dari ketukan itu, bukan fakta bahwa tombolnya sudah ditekan.
 *
 * Transisi yang dimodelkan pada Gambar 2 (empat aturan):
 *   - dari mode baca, Edit membuka mode edit;
 *   - di mode edit, Type mengubah draf dan tetap di mode edit;
 *   - Save dengan draf valid kembali ke mode baca dengan bio baru;
 *   - Cancel kembali ke mode baca dengan bio lama.
 * Save dengan draf kosong sengaja tidak punya panah: tombolnya dinonaktifkan.
 */
@Composable
fun KetukanSaveAdalahEvent(modifier: Modifier = Modifier) {
    // Mode baca dan mode edit disatukan di satu layar agar perbedaannya terlihat jelas:
    // selama belum di-Save, "Draft" berubah dan "Saved" tetap.
    var savedBio by remember { mutableStateOf("Informatics student") }
    var draftBio by remember { mutableStateOf(savedBio) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = { draftBio = it }, // event Type: hanya draf yang berubah
            label = { Text("Draft") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            // event Save: hasil ketukan disimpan di sini, ketukannya sendiri tidak.
            onClick = { if (draftBio.trim().isNotEmpty()) savedBio = draftBio.trim() }
        ) {
            Text("Save")
        }
        Text("Saved: $savedBio")
        Text("Draft: $draftBio")
    }
}
