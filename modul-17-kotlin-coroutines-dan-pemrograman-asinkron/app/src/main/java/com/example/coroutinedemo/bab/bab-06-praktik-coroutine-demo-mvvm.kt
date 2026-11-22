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
import com.example.coroutinedemo.totalWaktuKonkuren

/**
 * Bab 7 — Praktik: Membuat Aplikasi Coroutine Demo dengan MVVM (berkas 06 = Bagian 7)
 *
 * Berkas bab-06 = Bagian 7 modul.
 *
 * Bagian modul: "Aplikasi yang Akan Dibuat", "Arsitektur Aplikasi", "Menyiapkan Proyek",
 * "Model Task", "Repository", "UI State", "ViewModel", "Screen", "MainActivity",
 * "Menjalankan dan Mengamati Aplikasi" (Tabel 5, Tabel 6, Tabel 7).
 *
 * Kode yang dijelaskan bab ini adalah kode yang benar-benar berjalan di aplikasi: layar
 * `TasksScreen()` dibuka lewat MainActivity.
 */

private val tabelTombol = listOf(
    "Load" to "Memuat tugas (2 dtk) + pengumuman (1 dtk) bersamaan — launch, async, withContext.",
    "Load (fail)" to "Server pura-pura gagal, pesan error tampil — try/catch, structured concurrency.",
    "Cancel" to "Menghentikan pemuatan — Job.cancel().",
    "Tap me" to "Penghitung untuk membuktikan layar tetap lancar — suspend tidak menghambat thread.",
    "Block UI" to "Main thread tidur 3 dtk — contoh buruk yang sengaja."
)

private val tabelUiState = listOf(
    "isLoading" to "Indikator putar tampil; tombol Load dan Load (fail) dinonaktifkan.",
    "tasks" to "Daftar tugas yang sudah dimuat.",
    "announcement" to "Pengumuman yang sudah dimuat.",
    "errorMessage" to "Pesan error, atau null kalau tidak ada error.",
    "tapCount" to "Penghitung ketukan."
)

private val tabelSkenario = listOf(
    "Load, lalu Tap me berkali-kali" to "Angka terus bertambah; data muncul setelah ±2 dtk — suspend tidak menghambat thread.",
    "Perhatikan lamanya pemuatan" to "±2 dtk, bukan 3 dtk — async berjalan bersamaan.",
    "Load (fail)" to "\"Failed to load: Server is not reachable\" — try/catch + structured concurrency.",
    "Load lalu segera Cancel" to "\"Loading cancelled\" dan data tidak berubah — pembatalan Job.",
    "Block UI lalu Tap me" to "Tidak ada yang bereaksi 3 dtk — main thread terhambat.",
    "Putar layar saat memuat" to "Pemuatan tetap berlanjut — viewModelScope hidup selama ViewModel."
)

@Composable
fun PraktikScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 7 — Praktik: Coroutine Demo (MVVM)",
            keterangan = "Setiap tombol mewakili satu konsep; coroutine hanya ada di ViewModel dan repository."
        )

        Tabel("Tabel 5 — Tombol pada aplikasi Coroutine Demo", tabelTombol)

        KotakAlur(judul = "Compose (ui/main/TasksScreen.kt)", isi = "Membaca state, mengirim event. Tanpa coroutine.")
        PanahBawah("collectAsStateWithLifecycle + callback")
        KotakAlur(judul = "TasksViewModel (viewModelScope)", isi = "launch, coroutineScope, dua async, try/catch → UI state")
        PanahBawah("suspend function (main-safe)")
        KotakAlur(judul = "TaskRepository (Dispatchers.IO)", isi = "withContext + delay — pengganti Retrofit/Room")

        Tabel("Tabel 6 — Field pada TasksUiState", tabelUiState)

        KodeBlok(
            kode = """
            fun loadData(shouldFail: Boolean = false) {
                loadJob?.cancel()
                loadJob = viewModelScope.launch {
                    _uiState.update { it.mulaiMemuat() }
                    try {
                        coroutineScope {
                            val tasks = async { repository.getTasks(shouldFail) }
                            val announcement = async { repository.getAnnouncement() }
                            val loadedTasks = tasks.await()
                            val loadedAnnouncement = announcement.await()
                            _uiState.update { it.selesaiMemuat(loadedTasks, loadedAnnouncement) }
                        }
                    } catch (e: IOException) {
                        _uiState.update { it.gagalMemuat(pesanGagalMemuat(e)) }
                    }
                }
            }
            """.trimIndent()
        )

        Tabel(
            "Lama pemuatan",
            listOf(
                "Kalau sekuensial" to "2000 + 1000 = 3000 ms.",
                "Kalau bersamaan" to "${totalWaktuKonkuren(2000L, 1000L)} ms — yang terlihat di aplikasi."
            )
        )

        JudulBab(teks = "Menyiapkan proyek", keterangan = "Empty Activity → nama CoroutineDemo, paket com.example.coroutinedemo.")

        KodeBlok(
            kode = """
            # gradle/libs.versions.toml
            [libraries.androidx-lifecycle-viewmodel-compose]
            group = "androidx.lifecycle"
            name = "lifecycle-viewmodel-compose"
            version.ref = "lifecycleRuntimeKtx"

            [libraries.androidx-lifecycle-runtime-compose]
            group = "androidx.lifecycle"
            name = "lifecycle-runtime-compose"
            version.ref = "lifecycleRuntimeKtx"

            # app/build.gradle.kts
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            """.trimIndent()
        )

        KodeBlok(
            kode = """
            com.example.coroutinedemo
            ├── data
            │   ├── Task.kt
            │   └── TaskRepository.kt
            ├── ui
            │   ├── main
            │   │   ├── TasksUiState.kt
            │   │   ├── TasksViewModel.kt
            │   │   └── TasksScreen.kt
            │   └── theme
            └── MainActivity.kt
            """.trimIndent()
        )

        Tabel("Tabel 7 — Skenario untuk mengamati aplikasi", tabelSkenario)
    }
}
