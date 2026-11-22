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
 * Bab 5 — Structured Concurrency (berkas 04 = Bagian 5)
 *
 * Berkas bab-04 = Bagian 5 modul.
 *
 * Bagian modul: "Hubungan Parent dan Child", "coroutineScope", "Kalau Salah Satu Child Gagal"
 * (Gambar 4). Pohon coroutine di sini adalah pohon yang benar-benar dipakai aplikasi praktik:
 * viewModelScope → launch → coroutineScope → dua async.
 */

private val tigaAturan = listOf(
    "Aturan 1" to "Parent selalu menunggu semua child-nya selesai.",
    "Aturan 2" to "Kalau parent dibatalkan, semua child ikut dibatalkan.",
    "Aturan 3" to "Kalau satu child gagal, parent dan semua sibling ikut dibatalkan; error diteruskan ke atas."
)

private val urutanGagal = listOf(
    "0,5 detik" to "fetchTasks() melempar IOException(\"No connection\").",
    "seketika" to "Scope menghentikan fetchAnnouncement(), sehingga fungsi itu tidak pernah selesai.",
    "lalu" to "coroutineScope meneruskan IOException ke pemanggilnya (loadDashboard / loadData).",
    "akhir" to "Blok try/catch di pemanggil menangkap error dan mengubahnya menjadi pesan UI."
)

@Composable
fun StructuredConcurrencyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 5 — Structured concurrency",
            keterangan = "Setiap coroutine punya parent, sehingga tidak ada pekerjaan yang terlupakan tanpa pemilik."
        )

        KotakAlur(judul = "viewModelScope", isi = "Pemilik pekerjaan layar; berakhir saat ViewModel dihapus")
        PanahBawah("launch → Job disimpan di loadJob")
        KotakAlur(judul = "loadJob (launch)", isi = "Bisa dibatalkan lewat job.cancel()")
        PanahBawah("coroutineScope { }")
        KotakAlur(
            judul = "dua child async",
            isi = "async { getTasks() } · async { getAnnouncement() }"
        )
        PanahBawah("salah satu gagal → sibling dibatalkan, error naik ke parent")

        Tabel("Tiga aturan structured concurrency", tigaAturan)

        KodeBlok(
            kode = """
            suspend fun loadDashboard(): String = coroutineScope {
                val tasks = async { fetchTasks() }
                val note = async { fetchAnnouncement() }
                "${'$'}{tasks.await()} tasks, ${'$'}{note.await()}"
            }

            fun main() = runBlocking {
                println(loadDashboard())   // 3 tasks, Quiz on Monday
            }
            """.trimIndent()
        )

        Tabel("Kalau fetchTasks() gagal setelah 0,5 detik", urutanGagal)

        KodeBlok(
            kode = """
            suspend fun fetchTasks(): Int {
                delay(500L)
                throw IOException("No connection")
            }

            fun main() = runBlocking {
                try {
                    println(loadDashboard())
                } catch (e: IOException) {
                    println("Error: ${'$'}{e.message}")   // Error: No connection
                }
            }
            """.trimIndent()
        )
    }
}
