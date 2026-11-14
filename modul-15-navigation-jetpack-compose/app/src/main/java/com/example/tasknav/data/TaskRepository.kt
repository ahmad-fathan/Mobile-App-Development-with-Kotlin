package com.example.tasknav.data

/**
 * Model satu tugas (Bagian 9.1 modul).
 *
 * `id` adalah satu-satunya nilai yang dikirim antar-screen; sisanya diambil dari repository,
 * sehingga layar detail selalu menampilkan data terbaru (satu sumber kebenaran).
 */
data class Task(
    val id: Int,
    val title: String,
    val course: String,
    val dueDate: String,
    val isDone: Boolean = false
)

/**
 * Sumber data di memori untuk praktik navigasi.
 *
 * Hanya tiga tugas supaya fokus modul tetap pada perpindahan layar, bukan pada data layer.
 * `getTask()` mengembalikan `null` bila ID tidak dikenal, sehingga layar detail wajib
 * menangani kasus itu (tidak boleh crash).
 */
object TaskRepository {
    private val tasks = listOf(
        Task(1, "Read Navigation docs", "Mobile App Dev", "16 Nov"),
        Task(2, "Submit lab report", "Computer Networks", "18 Nov"),
        Task(3, "Quiz: SQL joins", "Databases", "20 Nov", isDone = true)
    )

    fun getTasks(): List<Task> = tasks

    fun getTask(id: Int): Task? = tasks.find { it.id == id }

    /** Dipakai Latihan 4: daftar tugas pada satu mata kuliah. */
    fun getTasksByCourse(course: String): List<Task> = tasks.filter { it.course == course }
}
