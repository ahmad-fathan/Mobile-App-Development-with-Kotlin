package com.example.coroutinedemo

import com.example.coroutinedemo.data.Task
import com.example.coroutinedemo.ui.main.TasksUiState
import java.io.IOException

/**
 * Aturan pemrosesan murni yang dipakai bersama oleh TasksViewModel, latihan, dan verifikasi.kt.
 *
 * Semua fungsi di sini bebas Android dan bebas coroutine, sehingga dapat diuji tanpa emulator
 * (`./jalankan.sh 17 perilaku`). Karena ViewModel memakai fungsi yang sama, hasil uji tidak
 * mungkin berbeda dari perilaku aplikasi.
 */

/** State saat pekerjaan dimulai: indikator berputar, pesan error lama dibersihkan. */
fun TasksUiState.mulaiMemuat(): TasksUiState = copy(isLoading = true, errorMessage = null)

/** State saat kedua hasil tiba: data masuk sekaligus, indikator berhenti. */
fun TasksUiState.selesaiMemuat(tasks: List<Task>, announcement: String): TasksUiState =
    copy(isLoading = false, tasks = tasks, announcement = announcement)

/** State saat gagal: indikator berhenti, pesan error diisi, data lama tidak dihapus. */
fun TasksUiState.gagalMemuat(pesan: String): TasksUiState =
    copy(isLoading = false, errorMessage = pesan)

/** State saat pengguna menekan Cancel. */
fun TasksUiState.dibatalkan(): TasksUiState =
    copy(isLoading = false, errorMessage = PESAN_DIBATALKAN)

/** Penghitung ketukan — bukti layar tetap merespons selama coroutine menunggu. */
fun TasksUiState.setelahKetuk(): TasksUiState = copy(tapCount = tapCount + 1)

const val PESAN_DIBATALKAN = "Loading cancelled"

/** Pesan error yang dilihat pengguna (Tabel 7 baris ketiga). */
fun pesanGagalMemuat(e: IOException): String = "Failed to load: ${e.message}"

/** Waktu total bila dua pekerjaan dipanggil satu per satu: dijumlahkan. */
fun totalWaktuSekuensial(pertamaMs: Long, keduaMs: Long): Long = pertamaMs + keduaMs

/** Waktu total bila dijalankan bersamaan: sepanjang pekerjaan yang paling lama. */
fun totalWaktuKonkuren(pertamaMs: Long, keduaMs: Long): Long = maxOf(pertamaMs, keduaMs)

/**
 * Latihan 6 — mengurutkan daftar berdasarkan judul (case-insensitive), tanpa mengubah daftar asal.
 * Di aplikasi, pemanggilan fungsi ini dibungkus `withContext(Dispatchers.Default)`.
 */
fun urutkanJudul(tasks: List<Task>): List<Task> =
    tasks.sortedBy { it.title.lowercase() }
