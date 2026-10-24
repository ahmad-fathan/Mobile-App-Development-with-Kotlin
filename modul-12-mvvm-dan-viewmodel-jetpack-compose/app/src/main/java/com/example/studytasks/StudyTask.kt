package com.example.studytasks

/**
 * Satu tugas dalam aplikasi Study Tasks (Bagian 7.1).
 *
 * Semua property bertipe `val`, sehingga tidak ada pemanggil yang dapat mengubah isi tugas yang
 * sudah ada. Menandai tugas selesai berarti membuat pengganti dengan `copy()`:
 *
 *     task.copy(isDone = true)
 *
 * Membuat nilai baru alih-alih mengubah nilai lama inilah yang membuat Compose dan StateFlow
 * dapat mendeteksi perubahan: nilai lama dan nilai baru dibandingkan dengan `equals`.
 *
 * - [id] mengidentifikasi tugas meskipun posisinya di dalam daftar berubah;
 * - [title] menyimpan teks tugas;
 * - [isDone] mencatat apakah tugas sudah selesai.
 */
data class StudyTask(
    val id: Long,
    val title: String,
    val isDone: Boolean = false
)
