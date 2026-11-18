package com.example.minicampus.model

/**
 * Bentuk data satu mata kuliah (Bagian 9.1 modul).
 *
 * `id` adalah satu-satunya data yang dikirim antarlayar; judul dan deskripsi selalu dibaca ulang
 * dari [com.example.minicampus.viewmodel.CourseViewModel] di layar tujuan.
 */
data class Course(
    val id: Int,
    val title: String,
    val description: String
)
