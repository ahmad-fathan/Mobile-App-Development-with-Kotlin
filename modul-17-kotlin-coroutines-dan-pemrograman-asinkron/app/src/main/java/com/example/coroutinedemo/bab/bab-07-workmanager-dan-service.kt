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
 * Bab 8 — Di Luar Coroutine: WorkManager dan Service (berkas 07 = Bagian 8)
 *
 * Berkas bab-07 = Bagian 8 modul. Satu-satunya bab tanpa layar pendamping, karena isinya
 * membandingkan dua alat penjadwal, bukan mempraktikkan coroutine.
 *
 * Coroutine di viewModelScope berhenti saat pengguna meninggalkan layar. Pekerjaan yang harus
 * tetap selesai ditangani WorkManager atau foreground service — keduanya tetap memakai coroutine
 * di dalamnya.
 */

private val tabelAlat = listOf(
    "WorkManager" to "Pekerjaan yang harus selesai meski aplikasi ditutup atau HP dinyalakan ulang (unggah log, sinkronisasi harian).",
    "Foreground service" to "Pekerjaan panjang yang disadari pengguna dan disertai notifikasi (memutar musik, melacak GPS).",
    "Coroutine" to "Mengatur bagaimana pekerjaan berjalan tanpa membuat aplikasi macet; tidak menjadwalkan pekerjaan.",
    "Pembagian tugas" to "WorkManager/service mengatur kapan dan berapa lama; coroutine mengatur bagaimana."
)

@Composable
fun DiLuarCoroutineScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 8 — Di luar coroutine: WorkManager dan Service",
            keterangan = "viewModelScope berhenti bersama layar; dua alat ini untuk pekerjaan yang harus tetap selesai."
        )

        Tabel("Kapan memakai apa", tabelAlat)

        // Contoh WorkManager hanya ditampilkan sebagai potongan kode: kelas Worker bukan bagian
        // dari aplikasi praktik, sehingga tidak ada dependensi WorkManager di proyek ini.
        KodeBlok(
            kode = """
            class UploadLogWorker(
                context: Context,
                params: WorkerParameters
            ) : CoroutineWorker(context, params) {
                override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
                    uploadLogs()
                    Result.success()
                }
            }
            """.trimIndent()
        )
    }
}
