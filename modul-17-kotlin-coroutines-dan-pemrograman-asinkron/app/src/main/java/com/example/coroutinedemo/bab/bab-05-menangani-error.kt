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
import com.example.coroutinedemo.Tabel

/**
 * Bab 6 — Menangani Error (berkas 05 = Bagian 6)
 *
 * Berkas bab-05 = Bagian 6 modul.
 *
 * Bagian modul: "try/catch pada Suspend Function", "Error dari async Perlu Dibungkus
 * coroutineScope", "Jangan Ikut Menangkap CancellationException", "Menampilkan Error Lewat
 * UI State".
 */

private val tabelPola = listOf(
    "Berisiko" to "async langsung di dalam launch: error tertangkap di await(), tetapi async yang gagal tetap membatalkan parent → bisa crash.",
    "Aman" to "async dibungkus coroutineScope, try/catch dipasang di LUAR coroutineScope.",
    "Aturan" to "Bungkus pekerjaan paralel dengan coroutineScope, lalu pasang try/catch di luarnya.",
    "Spesifik" to "Tangkap IOException, bukan Exception, agar CancellationException tidak ikut tertangkap.",
    "Pemilik error" to "ViewModel menangkap error, layar hanya membaca errorMessage dari UI state — layar tanpa try/catch."
)

@Composable
fun PenangananErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 6 — Menangani error",
            keterangan = "Suspend function bisa melempar exception seperti fungsi biasa; yang penting adalah di mana ia ditangkap."
        )

        KodeBlok(
            kode = """
            // Berisiko
            viewModelScope.launch {
                try {
                    val tasks = async { repository.getTasks() }
                    show(tasks.await())
                } catch (e: IOException) {
                    showError(e)
                }
            }

            // Aman
            viewModelScope.launch {
                try {
                    coroutineScope {
                        val tasks = async { repository.getTasks() }
                        show(tasks.await())
                    }
                } catch (e: IOException) {
                    showError(e)
                }
            }
            """.trimIndent()
        )

        KodeBlok(
            kode = """
            // Kalau terpaksa menangkap Exception, lempar ulang pembatalan lebih dulu
            try {
                repository.getTasks()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                showError(e)
            }
            """.trimIndent()
        )

        Tabel("Ringkasan pola penanganan error", tabelPola)

        KodeBlok(
            kode = """
            // Di aplikasi praktik: error berakhir sebagai field UI state, bukan di layar
            catch (e: IOException) {
                _uiState.update { it.gagalMemuat("Failed to load: ${'$'}{e.message}") }
            }
            """.trimIndent()
        )
    }
}
