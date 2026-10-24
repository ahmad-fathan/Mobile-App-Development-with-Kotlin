package com.example.studytasks

/**
 * Implementasi data layer yang menyimpan tugas di dalam memori (Bagian 7.3).
 *
 * Perhatikan empat baris penting di bawah:
 *
 * - `nextId++` memberi nilai [nextId] saat ini kepada tugas baru lalu menaikkannya, sehingga
 *   setiap tugas mendapat ID berbeda meskipun dua judulnya sama;
 * - `require(cleanTitle.isNotEmpty())` menegakkan aturan data: tugas yang tersimpan tidak boleh
 *   memiliki judul kosong;
 * - [getTasks] mengembalikan salinan, sehingga pemanggil tidak dapat mengubah list milik
 *   repository dari luar;
 * - [toggleTask] membangun list baru dengan `map` dan hanya mengganti tugas yang cocok.
 *
 * Implementasi ini mengasumsikan semua pemanggilan berasal dari main thread, tidak permanen, dan
 * tidak dirancang untuk beberapa thread yang menulis bersamaan. Batasan itu masih dapat diterima
 * untuk contoh dengan satu layar.
 */
class InMemoryTaskRepository : TaskRepository {
    private var tasks = listOf<StudyTask>()
    private var nextId = 1L

    override fun getTasks(): List<StudyTask> = tasks.toList()

    override fun addTask(title: String) {
        val cleanTitle = title.trim()
        require(cleanTitle.isNotEmpty())
        tasks = tasks + StudyTask(nextId++, cleanTitle)
    }

    override fun toggleTask(id: Long) {
        tasks = tasks.map { task ->
            if (task.id == id) task.copy(isDone = !task.isDone)
            else task
        }
    }
}
