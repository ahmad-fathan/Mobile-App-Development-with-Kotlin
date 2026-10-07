package com.example.studentgreeting.bab

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 7 (berkas ke-5) — Button dan Event Handler
 *
 * Button adalah komponen pertama yang menerima aksi pengguna. Ia terdiri atas dua
 * bagian penting:
 *
 *   onClick → lambda (blok kode) yang dijalankan setiap kali tombol ditekan
 *   blok { ... } setelah tanda kurung tutup → konten yang tampil di dalam tombol
 *
 * Kode di dalam onClick disebut event handler. Pada contoh ini setiap klik
 * mencetak pesan ke Logcat Android Studio, bukan ke layar ponsel.
 *
 * Setelah dicoba di emulator, tombol dapat ditekan dan pesan muncul di Logcat,
 * tetapi tampilan di layar tidak berubah. Agar tampilan berubah, event handler
 * harus mengubah data yang dibaca oleh tampilan — data itulah yang disebut state
 * (Bagian 8, berkas bab-06).
 */
@Composable
fun TombolSederhana() {
    Button(
        onClick = {
            println("Button clicked")
        }
    ) {
        Text("Click Me")
    }
}

@Preview(showBackground = true)
@Composable
fun TombolSederhanaPreview() {
    StudentGreetingTheme {
        TombolSederhana()
    }
}
