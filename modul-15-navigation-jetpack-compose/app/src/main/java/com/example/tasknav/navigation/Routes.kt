package com.example.tasknav.navigation

import kotlinx.serialization.Serializable

/**
 * Tipe-tipe route aplikasi TaskNav (Bagian 7.1 modul).
 *
 * Aturan pemilihan bentuk:
 *  - destination yang tidak membutuhkan data  -> `object`
 *  - destination yang membutuhkan data        -> `data class`, tiap properti menjadi argumen
 *
 * Setiap route WAJIB beranotasi `@Serializable`. Tanpa anotasi, aplikasi masih berhasil
 * dikompilasi tetapi crash saat `NavHost` pertama kali dibangun.
 *
 * Nama dan tipe properti data class langsung menjadi nama dan tipe argumen navigasi, jadi
 * tidak ada lagi kunci string yang bisa salah ketik.
 */

/** Layar daftar tugas, tanpa argumen. */
@Serializable
object TaskList

/** Layar detail, membutuhkan ID tugas. */
@Serializable
data class TaskDetail(val taskId: Int)

/** Layar About — Latihan 3: tanpa argumen, jadi `object`. */
@Serializable
object About

/** Layar daftar tugas satu mata kuliah — Latihan 4: butuh nama mata kuliah. */
@Serializable
data class CourseTasks(val course: String)

/** Layar pembuka — Latihan 6: start destination dengan tombol "Get started". */
@Serializable
object Welcome
