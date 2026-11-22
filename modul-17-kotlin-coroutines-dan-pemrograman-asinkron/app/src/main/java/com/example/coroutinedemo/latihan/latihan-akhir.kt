package com.example.coroutinedemo.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coroutinedemo.JudulBab
import com.example.coroutinedemo.KodeBlok
import com.example.coroutinedemo.Tabel
import com.example.coroutinedemo.data.Task
import com.example.coroutinedemo.data.TaskRepository
import com.example.coroutinedemo.totalWaktuKonkuren
import com.example.coroutinedemo.totalWaktuSekuensial
import com.example.coroutinedemo.urutkanJudul
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.system.measureTimeMillis

/**
 * Latihan akhir modul: enam soal (Bagian 9).
 *
 * Soal 1 dijalankan sungguhan di layar ini (hasil milidetiknya diukur, bukan dikarang), soal 6
 * diuji sebagai fungsi murni di verifikasi.kt, dan soal 2–5 ditampilkan sebagai jawaban karena
 * berupa perubahan perilaku aplikasi / pertanyaan konsep.
 */

/** Latihan 1 — permintaan ketiga, 1,5 detik. */
suspend fun getCourseCount(): Int = withContext(Dispatchers.IO) {
    kotlinx.coroutines.delay(1500L)
    2
}

/** Menjalankan ketiga permintaan bersamaan, seperti `loadData()` setelah latihan 1. */
suspend fun ukurPemuatanTigaPermintaan(sekuensial: Boolean = false): Long =
    measureTimeMillis {
        if (sekuensial) {
            TaskRepository().getTasks()
            TaskRepository().getAnnouncement()
            getCourseCount()
        } else {
            coroutineScope {
                val tasks = async { TaskRepository().getTasks() }
                val announcement = async { TaskRepository().getAnnouncement() }
                val courses = async { getCourseCount() }
                tasks.await()
                announcement.await()
                courses.await()
            }
        }
    }

private val tabelJawaban = listOf(
    "1. Permintaan ketiga" to "Masih ±2 detik. Ketiga async berjalan bersamaan, jadi total = yang paling lama (2000 ms), bukan 2000+1000+1500 = 4500 ms.",
    "2. Tombol Retry" to "Tampilkan `if (state.errorMessage != null) { OutlinedButton(onClick = onLoad) { Text(\"Retry\") } }` — hanya muncul saat error.",
    "3. catch (e: Exception)" to "Setelah Cancel, pesan menjadi \"Failed to load: StandaloneCoroutine was cancelled\". CancellationException kini ikut tertangkap, padahal pembatalan bukan kegagalan.",
    "4. Thread.sleep di dalam withContext(IO)" to "Layar TIDAK macet: sleep menahan thread IO, bukan main thread. Kalau withContext(Dispatchers.IO) dihapus, sleep berjalan di main thread dan layar membeku 2 detik.",
    "5a. launch vs async" to "launch: Job, tanpa nilai balik. async: Deferred<T>, nilainya diambil dengan await().",
    "5b. GlobalScope" to "Tidak punya parent, jadi tidak pernah dibatalkan otomatis dan pekerjaannya bisa bocor setelah layar ditutup.",
    "5c. getAnnouncement saat getTasks gagal" to "Ikut dibatalkan oleh coroutineScope (aturan ketiga structured concurrency), sehingga tidak pernah selesai; error diteruskan ke pemanggil.",
    "6. Tombol Sort" to "withContext(Dispatchers.Default) { urutkanJudul(tasks) } lalu `_uiState.update { it.copy(tasks = hasil) }`."
)

@Composable
fun LatihanScreen(modifier: Modifier = Modifier) {
    var hasilKonkuren by remember { mutableStateOf<Long?>(null) }
    var hasilSekuensial by remember { mutableStateOf<Long?>(null) }

    LaunchedEffect(Unit) {
        hasilKonkuren = ukurPemuatanTigaPermintaan(sekuensial = false)
        hasilSekuensial = ukurPemuatanTigaPermintaan(sekuensial = true)
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Latihan akhir — enam soal Bagian 9",
            keterangan = "Soal 1 dan 6 dijalankan/uji sungguhan; soal 2–5 berupa jawaban singkat."
        )

        Text(
            "Soal 1 diukur di layar ini: bersamaan ${hasilKonkuren ?: "…"} ms, " +
                "satu per satu ${hasilSekuensial ?: "…"} ms.",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            "Perkiraan bila dihitung: konkuren ${totalWaktuKonkuren(2000L, 1000L)} ms " +
                "(bertambah 1500 ms tidak mengubahnya), sekuensial ${totalWaktuSekuensial(2000L, 1000L) + 1500L} ms.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        val contoh = listOf(
            Task(1, "Write lab report", "Mobile App Development"),
            Task(2, "read chapter 5", "Data Science"),
            Task(3, "Prepare quiz", "Research Methodology")
        )
        Tabel(
            "Soal 6 — hasil urutkanJudul (Dispatchers.Default)",
            urutkanJudul(contoh).map { it.title to it.course }
        )

        Tabel("Jawaban soal 1–6", tabelJawaban)

        KodeBlok(
            kode = """
            // Soal 1: async ketiga di dalam coroutineScope yang sama
            val tasks = async { repository.getTasks(shouldFail) }
            val announcement = async { repository.getAnnouncement() }
            val courses = async { getCourseCount() }
            tasks.await(); announcement.await(); courses.await()

            // Soal 6: pengurutan di Default, hasil masuk ke state
            fun sortTasks() {
                viewModelScope.launch {
                    val hasil = withContext(Dispatchers.Default) {
                        urutkanJudul(_uiState.value.tasks)
                    }
                    _uiState.update { it.copy(tasks = hasil) }
                }
            }
            """.trimIndent()
        )
    }
}
