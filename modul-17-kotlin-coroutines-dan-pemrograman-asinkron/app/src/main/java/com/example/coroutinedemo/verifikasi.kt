package com.example.coroutinedemo

import com.example.coroutinedemo.data.Task
import com.example.coroutinedemo.data.TaskRepository
import com.example.coroutinedemo.ui.main.TasksUiState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.system.measureTimeMillis
import kotlin.system.exitProcess

/**
 * Verifikasi modul 17 — dijalankan lewat `./jalankan.sh 17 perilaku`, atau dengan kotlinc: kompilasi
 * verifikasi.kt bersama AturanPemrosesan.kt, data/Task.kt, data/TaskRepository.kt, dan
 * ui/main/TasksUiState.kt, lalu jalankan jar-nya (lihat README modul).
 *
 * Berkas ini TIDAK memakai Compose atau AndroidX: hanya Kotlin stdlib + kotlinx-coroutines-core,
 * sehingga aturan modul (waktu sekuensial vs konkuren, pembatalan, structured concurrency, state
 * transitions) dapat dibuktikan tanpa emulator. Fungsi state yang diuji adalah fungsi yang SAMA
 * dengan yang dipakai TasksViewModel.
 */

private var jumlahCekDijalankan = 0
private var jumlahGagal = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCekDijalankan++
    if (kondisi) {
        println("  OK    ${deskripsi}")
    } else {
        jumlahGagal++
        println("  GAGAL ${deskripsi}")
    }
}

// --- kecil: tiruan suspend function yang lambat -------------------------------------------------

private suspend fun muatTugas(ms: Long = 2000L): Int {
    delay(ms)
    return 3
}

private suspend fun muatPengumuman(ms: Long = 1000L): String {
    delay(ms)
    return "Quiz on Monday"
}

private suspend fun gagalSetelah(ms: Long): Int {
    delay(ms)
    throw IOException("No connection")
}

fun jalankanVerifikasiModul17(): Boolean {
    jumlahCekDijalankan = 0
    jumlahGagal = 0
    println("Verifikasi modul 17 — Kotlin coroutines dan pemrograman asinkron")
    println()

    // ---------------------------------------------------------------- Bagian 3: dasar coroutine
    println("Bagian 3 — dasar coroutine")

    val sekuensial = runBlocking {
        measureTimeMillis {
            muatTugas()
            muatPengumuman()
        }
    }
    cek("sekuensial 2000+1000 ms terukur >= 2950 ms (hasil: $sekuensial ms)", sekuensial >= 2950L)

    val konkuren = runBlocking {
        measureTimeMillis {
            coroutineScope {
                val tugas = async { muatTugas() }
                val catatan = async { muatPengumuman() }
                "${tugas.await()} tasks, ${catatan.await()}"
            }
        }
    }
    cek("konkuren 2000||1000 ms terukur < 2500 ms (hasil: $konkuren ms)", konkuren < 2500L)
    cek("konkuren benar-benar lebih cepat dari sekuensial", konkuren < sekuensial)
    cek(
        "rumus waktu: sekuensial = jumlah, konkuren = paling lama",
        totalWaktuSekuensial(2000L, 1000L) == 3000L && totalWaktuKonkuren(2000L, 1000L) == 2000L
    )

    val asyncHasil = runBlocking { async { delay(10L); 42 }.await() }
    cek("async mengembalikan nilai lewat await() (42)", asyncHasil == 42)

    var selesai = false
    runBlocking {
        val job = launch { delay(10L); selesai = true }
        job.join()
    }
    cek("launch menghasilkan Job yang bisa ditunggu (join)", selesai)

    // ------------------------------------------------------- Bagian 4: pembatalan dan dispatcher
    println()
    println("Bagian 4 — pembatalan dan dispatcher")

    val dicetak = mutableListOf<Int>()
    runBlocking {
        val job: Job = launch {
            repeat(5) { i ->
                dicetak += i + 1
                delay(300L)
            }
        }
        delay(700L)
        job.cancel()
    }
    cek("job.cancel() menghentikan pekerjaan (dicetak: $dicetak)", dicetak == listOf(1, 2, 3))

    var pembatalanTertangkap: Throwable? = null
    runBlocking {
        val job = launch {
            try {
                delay(1000L)
            } catch (e: CancellationException) {
                pembatalanTertangkap = e
                throw e
            }
        }
        delay(100L)
        job.cancel()
        job.join()
    }
    cek("CancellationException dilempar saat coroutine dibatalkan", pembatalanTertangkap is CancellationException)

    val threadIO = runBlocking {
        withContext(Dispatchers.IO) { Thread.currentThread().name }
    }
    val threadUtama = Thread.currentThread().name
    cek("withContext(Dispatchers.IO) berjalan di thread lain ($threadIO vs $threadUtama)", threadIO != threadUtama)

    // ------------------------------------------------------ Bagian 5: structured concurrency
    println()
    println("Bagian 5 — structured concurrency")

    val saudaraDibatalkan = runBlocking {
        var pengumumanSelesai = false
        try {
            coroutineScope {
                val tugas = async { gagalSetelah(200L) }
                val catatan = async {
                    delay(2000L)
                    pengumumanSelesai = true
                }
                tugas.await()
                catatan.await()
            }
            false
        } catch (e: IOException) {
            !pengumumanSelesai
        }
    }
    cek("child yang gagal membatalkan sibling-nya dan error diteruskan", saudaraDibatalkan)

    val pesanGagal = runBlocking {
        try {
            coroutineScope { async { gagalSetelah(50L) }.await() }
            ""
        } catch (e: IOException) {
            e.message ?: ""
        }
    }
    cek("IOException dari child sampai ke pemanggil (\"No connection\")", pesanGagal == "No connection")

    // --------------------------------------------------------------- Bagian 6: menangani error
    println()
    println("Bagian 6 — menangani error")

    cek(
        "pesan error UI memakai message exception",
        pesanGagalMemuat(IOException("Server is not reachable")) == "Failed to load: Server is not reachable"
    )

    val errorJadiPesan = runBlocking {
        try {
            coroutineScope { async { gagalSetelah(50L) }.await() }
            ""
        } catch (e: IOException) {
            pesanGagalMemuat(e)
        }
    }
    cek(
        "catch (IOException) mengubah kegagalan async menjadi pesan UI",
        errorJadiPesan == "Failed to load: No connection"
    )

    // ------------------------------------------------------------- Bagian 7: UI state + repository
    println()
    println("Bagian 7 — UI state dan repository")

    val awal = TasksUiState()
    cek("state awal: tidak loading, tanpa data, tanpa error", !awal.isLoading && awal.tasks.isEmpty() && awal.errorMessage == null)
    cek("state awal: penghitung ketukan 0", awal.tapCount == 0)

    val loading = awal.mulaiMemuat()
    cek("mulaiMemuat menyalakan isLoading", loading.isLoading)

    val gagal = loading.gagalMemuat(pesanGagalMemuat(IOException("Server is not reachable")))
    cek("gagalMemuat mematikan isLoading dan mengisi pesan", !gagal.isLoading && gagal.errorMessage == "Failed to load: Server is not reachable")

    val berhasil = gagal.mulaiMemuat().selesaiMemuat(
        listOf(Task(1, "Write lab report", "Mobile App Development")),
        "Quiz 2 is next Monday"
    )
    cek("selesaiMemuat mengisi data dan membersihkan indikator", !berhasil.isLoading && berhasil.tasks.size == 1)
    cek("mulaiMemuat kedua membersihkan pesan error lama", berhasil.errorMessage == null)

    val dibatalkan = loading.dibatalkan()
    cek("dibatalkan: indikator berhenti dan pesan \"Loading cancelled\"", !dibatalkan.isLoading && dibatalkan.errorMessage == "Loading cancelled")

    val setelahKetuk = awal.setelahKetuk().setelahKetuk()
    cek("setelahKetuk menambah penghitung (2)", setelahKetuk.tapCount == 2)

    val repositori = TaskRepository()
    val isiTugas = runBlocking { repositori.getTasks() }
    cek("repository mengembalikan 3 tugas", isiTugas.size == 3)
    cek("tugas pertama: id 1, \"Write lab report\"", isiTugas.first().id == 1 && isiTugas.first().title == "Write lab report")
    cek("pengumuman repository benar", runBlocking { repositori.getAnnouncement() } == "Quiz 2 is next Monday")

    val gagalRepository = runBlocking {
        try {
            repositori.getTasks(shouldFail = true)
            false
        } catch (e: IOException) {
            e.message == "Server is not reachable"
        }
    }
    cek("shouldFail = true melempar IOException dengan pesan yang benar", gagalRepository)

    // ------------------------------------------------------------------------ Latihan: soal 1 & 6
    println()
    println("Latihan — soal 1 dan soal 6")

    val tigaPermintaan = runBlocking {
        measureTimeMillis {
            coroutineScope {
                val tugas = async { muatTugas() }
                val catatan = async { muatPengumuman() }
                val jumlahMatkul = async { delay(1500L); 2 }
                tugas.await(); catatan.await(); jumlahMatkul.await()
            }
        }
    }
    cek("soal 1: async ketiga (1500 ms) tidak menambah total ±2 dtk (hasil: $tigaPermintaan ms)", tigaPermintaan < 2500L)

    val contoh = listOf(
        Task(1, "Write lab report", "Mobile App Development"),
        Task(2, "read chapter 5", "Data Science"),
        Task(3, "Prepare quiz", "Research Methodology")
    )
    val terurut = urutkanJudul(contoh)
    cek(
        "soal 6: urutkanJudul mengurutkan tanpa peduli huruf besar/kecil",
        terurut.map { it.title } == listOf("Prepare quiz", "read chapter 5", "Write lab report")
    )
    cek("soal 6: daftar asal tidak berubah (immutable)", contoh.map { it.title } == listOf("Write lab report", "read chapter 5", "Prepare quiz"))

    // ----------------------------------------------------------------------------------- ringkasan
    println()
    println("---------------------------------------------")
    println(if (jumlahGagal == 0) "SEMUA OK: $jumlahCekDijalankan/$jumlahCekDijalankan cek lulus" else "GAGAL: $jumlahGagal dari $jumlahCekDijalankan cek")
    return jumlahGagal == 0
}

/** Titik masuk konsol: `java -jar verifikasi.jar` — keluar non-nol bila ada cek gagal. */
fun main() {
    val sukses = jalankanVerifikasiModul17()
    if (!sukses) exitProcess(1)
}
