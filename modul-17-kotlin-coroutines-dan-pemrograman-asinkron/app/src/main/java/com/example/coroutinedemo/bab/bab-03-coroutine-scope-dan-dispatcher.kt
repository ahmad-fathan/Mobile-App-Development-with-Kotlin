package com.example.coroutinedemo.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.coroutinedemo.JudulBab
import com.example.coroutinedemo.KodeBlok
import com.example.coroutinedemo.Tabel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Bab 4 — Coroutine Scope dan Dispatcher (berkas 03 = Bagian 4)
 *
 * Berkas bab-03 = Bagian 4 modul.
 *
 * Bagian modul: "Coroutine Scope", "Scope yang Tersedia di Android", "Coroutine di Dalam
 * Composable", "Dispatcher", "Berpindah Thread dengan withContext", "Job dan Pembatalan"
 * (Tabel 3 dan Tabel 4).
 */

private val tabelScope = listOf(
    "viewModelScope" to "Berakhir saat ViewModel dihapus — pilihan utama MVVM.",
    "lifecycleScope" to "Berakhir saat Activity/Fragment dihancurkan — pekerjaan terikat Activity.",
    "LaunchedEffect" to "Berakhir saat composable hilang atau key-nya berubah.",
    "rememberCoroutineScope()" to "Berakhir saat composable hilang — untuk event klik.",
    "GlobalScope" to "Tidak pernah berakhir — sebaiknya dihindari karena pekerjaan bisa bocor."
)

private val tabelDispatcher = listOf(
    "Dispatchers.Main" to "Main thread — memperbarui UI state; bawaan viewModelScope.",
    "Dispatchers.IO" to "Kumpulan thread untuk pekerjaan yang banyak menunggu: jaringan, Room, berkas.",
    "Dispatchers.Default" to "Sebanyak inti CPU — mengurutkan data besar, JSON besar, olah gambar."
)

@Composable
fun ScopeDispatcherScreen(modifier: Modifier = Modifier) {
    var showHint by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        delay(3000L)
        showHint = false
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 4 — Scope dan dispatcher",
            keterangan = "Scope menjawab siapa pemiliknya, dispatcher menjawab di thread mana ia berjalan."
        )

        Tabel("Tabel 3 — Coroutine scope di Android", tabelScope)
        Tabel("Tabel 4 — Tiga dispatcher yang paling sering dipakai", tabelDispatcher)

        KodeBlok(
            kode = """
            // Repository memindahkan pekerjaan beratnya sendiri → main-safe
            class TaskRepository(private val api: TaskApi) {
                suspend fun getTasks(): List<Task> =
                    withContext(Dispatchers.IO) { api.downloadTasks() }
            }

            // ViewModel dimulai di Main, pindah ke IO di dalam repository, lalu kembali ke Main
            viewModelScope.launch {
                val tasks = repository.getTasks()
                _uiState.update { it.copy(tasks = tasks) }
            }
            """.trimIndent()
        )

        if (showHint) {
            Text(
                "Petunjuk ini hilang setelah 3 detik (LaunchedEffect).",
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            Text(
                "Petunjuk sudah disembunyikan oleh LaunchedEffect.",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // onClick bukan suspend function, jadi butuh rememberCoroutineScope().
        Button(onClick = {
            scope.launch {
                delay(500L)
                showHint = true
            }
        }) {
            Text("Tampilkan petunjuk lagi")
        }

        KodeBlok(
            kode = """
            val job = launch {
                repeat(5) { i ->
                    println("Downloading part ${'$'}{i + 1}")
                    delay(500L)
                }
            }
            delay(1200L)
            job.cancel()          // pembatalan bersifat kooperatif: berlaku di titik suspensi
            println("Download cancelled")
            """.trimIndent()
        )
    }
}
