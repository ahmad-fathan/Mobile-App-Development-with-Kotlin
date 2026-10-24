package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.InMemoryTaskRepository
import com.example.studytasks.JudulBab
import com.example.studytasks.KodeBlok
import com.example.studytasks.StudyTask
import com.example.studytasks.Tabel
import com.example.studytasks.TaskRepository

/**
 * Bab 7 (Bagian 7.1–7.3) — Model: StudyTask dan Repository
 *
 * 7.1 Model pertama mendeskripsikan satu tugas: `id` mengidentifikasi tugas meskipun posisinya
 *     berubah, `title` menyimpan teks, `isDone` mencatat apakah sudah selesai. Semua property
 *     `val`, sehingga menandai selesai berarti membuat pengganti dengan `copy()`. Membuat nilai
 *     baru alih-alih mengubah nilai lama inilah yang memungkinkan Compose dan StateFlow
 *     mendeteksi perubahan.
 *
 * 7.2 Repository adalah pintu masuk data tugas dan operasi terhadapnya. Kita mendeskripsikan
 *     kemampuannya dengan interface; ViewModel bergantung pada interface, bukan class konkret.
 *     Fungsi di sini sinkron hanya karena pekerjaannya kecil dan ada di memori.
 *
 * 7.3 Implementasi pertama menyimpan tugas dalam list di dalam object. Empat hal penting:
 *     `nextId++` memberi ID berbeda untuk judul yang sama; `require(...)` menegakkan aturan data;
 *     `getTasks()` mengembalikan salinan; `toggleTask` membangun list baru dengan `map` + `copy()`.
 *
 * Kode di berkas ini adalah deklarasi di paket akar — berkas bab ini menampilkannya dan
 * MEMAKAINYA lewat [contohRepositoryBerjalan], sehingga yang tampil di layar benar-benar berjalan.
 */

val kodeStudyTask: String = """
    data class StudyTask(
        val id: Long,
        val title: String,
        val isDone: Boolean = false
    )
"""

val kodeTaskRepository: String = """
    interface TaskRepository {
        fun getTasks(): List<StudyTask>
        fun addTask(title: String)
        fun toggleTask(id: Long)
    }
"""

val kodeInMemoryRepository: String = """
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
"""

/** Menjalankan repository sungguhan lalu mengembalikan ringkasan hasilnya sebagai teks. */
fun contohRepositoryBerjalan(): List<Pair<String, String>> {
    val repository: TaskRepository = InMemoryTaskRepository()
    repository.addTask("  Review Kotlin  ")
    repository.addTask("Review Kotlin")
    repository.toggleTask(1L)
    val tugasSetelahToggle: List<StudyTask> = repository.getTasks()
    return listOf(
        "Dua judul sama" to tugasSetelahToggle.joinToString { "${it.id}:${it.title}" },
        "ID berbeda" to (tugasSetelahToggle.map { it.id }.toSet().size == 2).toString(),
        "Judul di-trim" to tugasSetelahToggle.first().title,
        "Tugas 1 selesai" to tugasSetelahToggle.first().isDone.toString(),
        "Tugas 2 masih terbuka" to tugasSetelahToggle[1].isDone.toString()
    )
}

@Composable
fun ModelDanRepositoryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 7.1–7.3 — Model: StudyTask dan repository",
            keterangan = "Data layer dimiliki repository; ViewModel hanya memakainya."
        )
        KodeBlok(kodeStudyTask)
        Tabel(
            "Peran setiap property",
            listOf(
                "id" to "Identitas stabil meskipun posisi di dalam daftar berubah",
                "title" to "Teks tugas",
                "isDone" to "Status selesai; diubah lewat copy(), bukan mutasi"
            )
        )
        KodeBlok(kodeTaskRepository)
        Tabel(
            "Mengapa interface, bukan class konkret",
            listOf(
                "ViewModel" to "Bergantung pada TaskRepository; implementasi dapat diganti",
                "Pengujian" to "Repository palsu dapat diberikan tanpa Android",
                "Kelak" to "Implementasi database diberikan tanpa mengubah ViewModel"
            )
        )
        KodeBlok(kodeInMemoryRepository)
        Text("Hasil pemanggilan repository sungguhan:")
        Tabel("Repository berjalan", contohRepositoryBerjalan())
    }
}

@Preview(showBackground = true)
@Composable
fun ModelDanRepositoryPreview() {
    ModelDanRepositoryScreen()
}
