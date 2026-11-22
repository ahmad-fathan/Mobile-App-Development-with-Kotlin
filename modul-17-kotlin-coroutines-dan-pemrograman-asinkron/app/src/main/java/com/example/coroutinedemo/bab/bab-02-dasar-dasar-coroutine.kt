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
import com.example.coroutinedemo.totalWaktuKonkuren
import com.example.coroutinedemo.totalWaktuSekuensial

/**
 * Bab 3 — Dasar-Dasar Coroutine (berkas 02 = Bagian 3)
 *
 * Berkas bab-02 = Bagian 3 modul.
 *
 * Bagian modul: "Apa Itu Coroutine?", "Coroutine Pertama", "Suspend Function",
 * "Apa yang Terjadi Saat Coroutine Berhenti Sebentar?", "delay() Berbeda dengan Thread.sleep()",
 * "Memulai Coroutine dengan launch dan async", "Satu per Satu atau Bersamaan?" (Tabel 2).
 */

private val tabelDelayVsSleep = listOf(
    "Yang berhenti" to "delay(): hanya coroutine-nya. Thread.sleep(): seluruh thread.",
    "Thread selama menunggu" to "delay(): bebas mengerjakan hal lain. Thread.sleep(): tidak bisa apa pun.",
    "Bisa dibatalkan?" to "delay(): bisa, meski sedang menunggu. Thread.sleep(): tidak peduli pembatalan.",
    "Di main thread" to "delay(): aplikasi tetap lancar. Thread.sleep(): aplikasi macet."
)

@Composable
fun DasarCoroutineScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 3 — Dasar-dasar coroutine",
            keterangan = "Pekerjaan yang bisa suspend lalu resume, tanpa membuat thread-nya macet."
        )

        KodeBlok(
            kode = """
            import kotlinx.coroutines.*

            fun main() = runBlocking {
                launch {
                    delay(1000L)
                    println("Task finished")
                }
                println("Waiting for the task...")
            }

            // Output
            // Waiting for the task...
            // Task finished
            """.trimIndent()
        )

        KodeBlok(
            kode = """
            suspend fun fetchUserName(): String {
                delay(1000L)          // titik suspensi: coroutine mencatat posisi lalu melepas thread
                return "Aisyah"
            }

            fun main() = runBlocking {
                println("Loading...")
                val name = fetchUserName()    // hanya boleh dari coroutine / suspend function
                println("Hello, ${'$'}name")
            }
            """.trimIndent()
        )

        Tabel("Tabel 2 — Perbedaan delay() dan Thread.sleep()", tabelDelayVsSleep)

        KodeBlok(
            kode = """
            // launch: tanpa nilai balik (Job)
            launch { delay(500L); println("Task saved") }

            // async: menghasilkan Deferred<T>, nilainya diambil dengan await()
            val deferred = async { delay(500L); 42 }
            println("Result: ${'$'}{deferred.await()}")
            """.trimIndent()
        )

        val sekuensial = totalWaktuSekuensial(2000L, 1000L)
        val konkuren = totalWaktuKonkuren(2000L, 1000L)
        Tabel(
            "Satu per satu vs bersamaan (diukur di modul)",
            listOf(
                "Sekuensial" to "fetchTasks() lalu fetchAnnouncement(): ${sekuensial} ms (dijumlahkan).",
                "Konkuren" to "dua async lalu await(): ${konkuren} ms (sepanjang yang paling lama).",
                "Syarat" to "Konkuren hanya untuk pekerjaan yang tidak saling bergantung."
            )
        )
    }
}
