package com.example.studentprofileeditor.bab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

/**
 * Bab 3 (Bagian 4 dan 4.1) — Umur State dengan remember
 *
 * remember menyimpan nilai di dalam composition. Selama composition itu ada dan
 * composable-nya masih dipanggil, nilainya tetap tersedia. Tiga situasi menentukan
 * nasib nilai tersebut:
 *
 *   1. Recomposition — Compose menjalankan ulang composable yang terpengaruh, dan
 *      remember mengembalikan objek state yang sama. Nilai tetap.
 *   2. Activity dibuat ulang (rotasi, mode gelap, ganti bahasa) — composition baru
 *      dibangun dari awal, dan nilai yang hanya disimpan dengan remember hilang.
 *   3. Composable keluar dari composition — semua yang diingatnya dilupakan; bila
 *      muncul lagi, ia mulai dari awal.
 *
 * BIO DRAFT TEST — percobaan sekali pakai Bagian 4.1.
 *
 * Langkah percobaan dari modul:
 *   1. Prediksi: ketik bio singkat, lalu tuliskan dugaan isi field setelah rotasi.
 *   2. Jalankan: putar emulator dengan tombol rotasi di toolbar (auto-rotate aktif).
 *   3. Catat: bandingkan hasil sebenarnya dengan prediksi.
 *
 * Hasil yang diharapkan pada proyek Empty Activity default: field kembali KOSONG setelah
 * rotasi, karena Activity dibuat ulang dan nilai remember hilang. Untuk draf yang sedang
 * diketik pengguna, perilaku itu tidak diharapkan — solusinya ada di Bab 4
 * (rememberSaveable).
 *
 * Untuk mencoba: ganti sementara konten Scaffold di MainActivity dengan
 *   BioDraftTest(Modifier.padding(innerPadding))
 */
@Composable
fun BioDraftTest(modifier: Modifier = Modifier) {
    var draftBio by remember { mutableStateOf("") }
    OutlinedTextField(
        value = draftBio,
        onValueChange = { draftBio = it },
        label = { Text("Draft (remember)") },
        modifier = modifier.fillMaxWidth()
    )
}
