package com.example.tasks

import java.util.UUID

/**
 * Satu tugas pada layar Study Tasks.
 *
 * Seluruh property `val`, sehingga tidak ada pemanggil yang dapat mengubah field pada tugas yang
 * sudah ada. Untuk mengubah tugas, buat penggantinya dengan [copy] — membuat nilai baru inilah yang
 * membuat Compose dan StateFlow dapat melihat perubahan.
 */
data class Task(
    val id: String,
    val title: String,
    val isCompleted: Boolean = false
)

/**
 * Pembuat ID tugas.
 *
 * Dipisahkan ke fungsi kecil supaya ViewModel tetap dapat diuji dan supaya pembuatan ID terjadi
 * SEBELUM `update` (transformasi di dalam `update` harus bebas efek samping dan dapat dijalankan
 * lebih dari sekali).
 */
fun idTugasBaru(): String = UUID.randomUUID().toString()
