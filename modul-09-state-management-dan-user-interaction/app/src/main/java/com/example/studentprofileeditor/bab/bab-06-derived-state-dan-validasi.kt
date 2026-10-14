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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Bab 6 (Bagian 7) — Derived State dan Validasi
 *
 * canSave dan jumlah karakter dapat DIHITUNG dari draftBio. Nilai seperti ini disebut
 * derived value. Yang dianjurkan: simpan draf satu kali saja, lalu hitung semua nilai
 * lain darinya dengan val biasa (bukan state):
 *
 *   val normalizedBio = draftBio.trim()
 *   val canSave = normalizedBio.isNotEmpty() && normalizedBio.length <= 80
 *   val count = draftBio.length
 *
 * Karena dihitung ulang pada setiap recomposition, nilainya tidak mungkin berbeda dari
 * draf. derivedStateOf disediakan untuk perhitungan yang mahal; untuk trim() dan
 * pengecekan panjang teks, val biasa sudah cukup.
 *
 * Perhatikan juga beda panjang mentah dan teks yang di-trim: penghitung menampilkan
 * draftBio.length (termasuk spasi), sedangkan validasi memakai normalizedBio. Tiga spasi
 * tampil sebagai 3/80 tetapi tidak dapat disimpan.
 *
 * Empat fungsi murni di bawah ini merangkum aturan validasi editor, dan dipakai ulang oleh
 * seluruh bab serta latihan pada modul ini.
 */

/** Teks yang benar-benar akan disimpan: draf tanpa spasi di awal dan akhir. */
fun bioNormalized(draft: String): String = draft.trim()

/** Valid bila teks setelah di-trim berisi 1 sampai 80 karakter. */
fun bioValid(draft: String): Boolean {
    val normalized = bioNormalized(draft)
    return normalized.isNotEmpty() && normalized.length <= 80
}

/** Panjang mentah yang ditampilkan penghitung karakter, termasuk spasi. */
fun bioCount(draft: String): Int = draft.length

/** Indikator "Unsaved changes" (Latihan 5): draf yang di-trim berbeda dari yang tersimpan. */
fun bioAdaPerubahanBelumDisimpan(savedBio: String, draftBio: String): Boolean =
    bioNormalized(draftBio) != savedBio

/** Pola yang dianjurkan: satu state, sisanya derived value. */
@Composable
fun PolaDerivedYangBenar(modifier: Modifier = Modifier) {
    var draftBio by rememberSaveable { mutableStateOf("") }

    val normalizedBio = draftBio.trim()
    val canSave = bioValid(draftBio)
    val count = bioCount(draftBio)

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
        Text("$count/80 characters")
        Text("normalizedBio = ${normalizedBio}, canSave = ${canSave}")
    }
}

/**
 * Pola yang perlu DIHINDARI: count dan canSave disimpan sebagai state terpisah, sehingga
 * setiap kode yang mengubah draf harus ingat memperbarui keduanya. Di bawah ini canSave
 * sengaja tidak diperbarui (seperti contoh pada modul), akibatnya tombol Save tetap
 * dianggap aktif untuk bio kosong — bug yang mudah muncul ketika kode bertambah.
 */
@Composable
fun PolaStateTerpisahYangSalah(modifier: Modifier = Modifier) {
    var draftBio by remember { mutableStateOf("") }
    var count by remember { mutableStateOf(0) }
    var canSave by remember { mutableStateOf(true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = {
                draftBio = it
                count = it.length
                // lupa memperbarui canSave
            },
            label = { Text("Bio (pola yang dihindari)") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("$count/80 characters, canSave = $canSave (tidak ikut berubah)")
    }
}
