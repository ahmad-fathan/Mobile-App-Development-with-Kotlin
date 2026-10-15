package com.example.studenttaskcatalog

/**
 * Model data contoh Student Task Catalog (Bagian 2 modul).
 *
 * Deklarasi StudentTask, sampleTasks, dan taskCategories berada di berkas ini, pada paket
 * akar aplikasi, karena dipakai ulang oleh seluruh bab, latihan, dan pemeriksaan otomatis
 * (satu source set Kotlin hanya boleh mendeklarasikan satu nama).
 *
 * Satu objek StudentTask = satu item di layar; List<StudentTask> = banyak item dengan desain
 * sama dan isi berbeda. Untuk modul ini data disimpan di memori dengan listOf(...): belum ada
 * database, API, atau ViewModel, karena fokusnya cara menampilkan kumpulan data, bukan cara
 * memperolehnya.
 *
 * Tiga aturan data contoh:
 *   1. Cukup banyak — 10 sampai 12 tugas agar daftar benar-benar perlu digulir.
 *   2. Id unik — id dipakai sebagai key (Bagian 6).
 *   3. Teks singkat — judul dua atau tiga kata agar kartu tetap terbaca di dalam grid.
 */

/** Satu tugas kuliah: enam informasi, menjadi satu item di layar. */
data class StudentTask(
    val id: Int,
    val title: String,
    val course: String,
    val category: String,
    val dueDate: String,
    val isCompleted: Boolean
)

/** Data contoh di memori: 12 id unik, kategori dan status bervariasi. */
val sampleTasks = listOf(
    StudentTask(1, "Compose UI Practice", "Mobile Development", "Assignment", "18 Oct", false),
    StudentTask(2, "ML Quiz", "Machine Learning", "Quiz", "20 Oct", true),
    StudentTask(3, "Data Visualization", "Data Science", "Project", "23 Oct", false),
    StudentTask(4, "ERD Revision", "Database Systems", "Assignment", "21 Oct", true),
    StudentTask(5, "Sorting Quiz", "Algorithms", "Quiz", "22 Oct", false),
    StudentTask(6, "Network Lab Report", "Computer Networks", "Assignment", "24 Oct", true),
    StudentTask(7, "UI Wireframes", "Interaction Design", "Project", "26 Oct", false),
    StudentTask(8, "Ethics Essay", "Professional Ethics", "Assignment", "27 Oct", false),
    StudentTask(9, "SQL Quiz", "Database Systems", "Quiz", "28 Oct", true),
    StudentTask(10, "Kotlin Lists Exercise", "Mobile Development", "Assignment", "29 Oct", false),
    StudentTask(11, "App Proposal", "Mobile Development", "Project", "31 Oct", false),
    StudentTask(12, "Regression Project", "Machine Learning", "Project", "3 Nov", false)
)

/** Nama kategori untuk strip horizontal (Bagian 7). "All" selalu ada di depan. */
val taskCategories = listOf("All", "Assignment", "Quiz", "Project")
