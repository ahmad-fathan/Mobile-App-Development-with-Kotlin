package com.example.minicampus.navigation

import kotlinx.serialization.Serializable

/**
 * Empat type-safe route aplikasi (Bagian 9.3 modul).
 *
 * Aturan pemilihan bentuk:
 *  - destination tanpa argumen -> `object`
 *  - destination dengan argumen -> `data class`, tiap properti menjadi argumen navigasi
 *
 * Tiga menu utama dipakai sebagai top-level destination pada Navigation Bar; [CourseDetail]
 * adalah secondary destination yang hanya dibuka dari layar Courses.
 */

/** Top-level: layar sambutan. */
@Serializable
object Home

/** Top-level: daftar mata kuliah. */
@Serializable
object Courses

/** Top-level: identitas mahasiswa. */
@Serializable
object Profile

/** Secondary: detail satu mata kuliah; hanya membawa ID, bukan seluruh objek. */
@Serializable
data class CourseDetail(val courseId: Int)
