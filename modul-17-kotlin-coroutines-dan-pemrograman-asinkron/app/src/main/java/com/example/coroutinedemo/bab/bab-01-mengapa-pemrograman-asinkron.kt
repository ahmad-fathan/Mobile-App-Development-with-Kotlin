package com.example.coroutinedemo.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coroutinedemo.JudulBab
import com.example.coroutinedemo.KodeBlok
import com.example.coroutinedemo.KotakAlur
import com.example.coroutinedemo.PanahBawah
import com.example.coroutinedemo.Tabel

/**
 * Bab 2 — Kenapa Butuh Pemrograman Asinkron? (berkas 01 = Bagian 2)
 *
 * Berkas bab-01 = Bagian 2 modul.
 *
 * Bagian modul: "Mengenal Main Thread", "Apa yang Terjadi Kalau Main Thread Terhambat?",
 * "Sinkron dan Asinkron" (Gambar 1, Tabel 1).
 */

private val tabelPekerjaanLambat = listOf(
    "Jaringan" to "Mengunduh daftar tugas dari server. Menunggu internet → latar belakang (IO).",
    "Database" to "Menyimpan atau membaca tugas dengan Room. Menunggu memori penyimpanan → IO.",
    "Berkas" to "Membaca lampiran atau mengekspor laporan. Menunggu memori penyimpanan → IO.",
    "Komputasi berat" to "Mengurutkan 100.000 data, membaca JSON besar. CPU sibuk → Default.",
    "Memperbarui tampilan" to "Menampilkan tugas yang sudah dimuat. Cepat, tetapi hanya boleh di main thread."
)

private val tabelGejala = listOf(
    "Jank" to "Animasi atau scroll patah-patah karena frame tidak selesai dalam ±16 ms.",
    "Freeze" to "Tombol tidak bereaksi ketika ditekan.",
    "ANR" to "Dialog Application Not Responding muncul bila input tidak diproses ±5 detik.",
    "Solusi" to "Pekerjaan lambat dipindahkan ke thread latar belakang; main thread tetap bebas menggambar."
)

@Composable
fun MainThreadScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 2 — Kenapa butuh pemrograman asinkron?",
            keterangan = "Satu main thread mengurus gambar layar, input, dan callback lifecycle dari satu antrean."
        )

        KotakAlur(
            judul = "Main thread (UI thread)",
            isi = "Menggambar tampilan · menanggapi sentuhan · menjalankan callback lifecycle seperti onCreate()"
        )
        PanahBawah("semua tugas mengantre di message queue")
        KotakAlur(
            judul = "Selama kode kita berjalan",
            isi = "Frame tidak digambar dan sentuhan diabaikan — 60 fps berarti hanya ±16 ms per frame."
        )

        Tabel("Tabel 1 — Contoh pekerjaan lambat dan tempat menjalankannya", tabelPekerjaanLambat)
        Tabel("Apa yang dirasakan pengguna", tabelGejala)

        JudulBab(teks = "Sinkron vs asinkron", keterangan = "Pesan kopi lalu berdiri di kasir (sinkron) vs memesan lalu duduk menunggu buzzer (asinkron).")

        KodeBlok(
            kode = """
            // Sinkron: thread menunggu, tidak bisa melakukan apa pun
            val data = api.downloadTasks()   // 2 detik main thread membeku

            // Asinkron: coroutine berhenti sebentar, thread bebas
            val data = withContext(Dispatchers.IO) { api.downloadTasks() }
            """.trimIndent()
        )
        KodeBlok(
            kode = """
            // Terbukti di aplikasi praktik (Bab 7): tombol Block UI
            fun blockMainThread() {
                Thread.sleep(3000L)   // sengaja salah: main thread tidur 3 detik
            }
            """.trimIndent()
        )
    }
}
