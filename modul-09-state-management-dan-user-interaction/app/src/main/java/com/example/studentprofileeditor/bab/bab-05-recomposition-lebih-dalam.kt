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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bab 5 (Bagian 6) — Recomposition Lebih Dalam
 *
 * Initial composition: Compose menjalankan composable kita untuk membangun pohon UI
 * pertama kali. Setelah itu, setiap kali nilai state berubah, Compose mencari composable
 * yang MEMBACA nilai tersebut dan menjadwalkan hanya bagian itu untuk dijalankan ulang.
 *
 * Tiga hal yang sering dianggap sama oleh pemula, padahal berbeda:
 *   - recomposition menjalankan ulang composable yang membaca state yang berubah;
 *   - menggambar ulang seluruh layar BUKAN arti recomposition;
 *   - restart Activity (rotasi) jauh lebih besar: seluruh composition dibuang.
 *
 * Contoh dari modul, satu ketikan yang mengubah draf dari "Mobile dev" (10 karakter)
 * menjadi "Mobile deve" (11 karakter):
 *
 *   Yang tampil di layar   Sebelum   Sesudah
 *   Teks penghitung        10/80     11/80      ← membaca draftBio, jadi berubah
 *   canSave                true      true       ← dihitung ulang, nilainya tetap
 *   Tombol Save            aktif     aktif      ← parameter sama, dapat dilewati Compose
 *
 * Jika draf menjadi kosong, canSave berubah menjadi false dan Button HARUS direkomposisi
 * untuk menampilkan keadaan nonaktif. Keputusan melewati sebuah composable dibuat oleh
 * runtime Compose dan bergantung pada banyak hal — tulislah kode yang benar tanpa
 * mengandalkan lompatan tersebut dan anggap saja itu bonus optimasi.
 */
@Composable
fun PelacakPembacaanState(modifier: Modifier = Modifier) {
    // Ubah nilai awal menjadi "" untuk melihat cabang canSave = false, atau ketik lebih
    // dari 80 karakter untuk melihat tombol Save kembali nonaktif.
    var draftBio by rememberSaveable { mutableStateOf("Mobile dev") }

    val normalizedBio = draftBio.trim()
    val canSave = normalizedBio.isNotEmpty() && normalizedBio.length <= 80

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = { draftBio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth()
        )
        // Membaca draftBio → ikut direkomposisi setiap kali draf berubah.
        Text("${draftBio.length}/80 characters")
        // Menerima canSave → hanya direkomposisi bila nilainya benar-benar berubah.
        Button(onClick = { }, enabled = canSave) {
            Text("Save")
        }
    }
}
