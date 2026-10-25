package com.example.tasks

/**
 * Satu potret (snapshot) seluruh nilai yang dibutuhkan untuk menampilkan layar Study Tasks.
 *
 * Default-nya mendeskripsikan layar awal: belum ada tugas, input kosong, filter mati, tanpa pesan.
 * Setiap perubahan menghasilkan object baru lewat [copy] — tidak ada nilai yang diubah di tempat.
 *
 * Dua nilai TURUNAN ([completedCount] dan [visibleTasks]) sengaja dihitung dari [tasks], bukan
 * disimpan sebagai property terpisah. Nilai turunan tidak dapat melenceng dari daftar sumbernya —
 * inilah masalah "3 of 5" pada Bagian 2 modul.
 */
data class TasksUiState(
    val tasks: List<Task> = emptyList(),
    val draftTitle: String = "",
    val showIncompleteOnly: Boolean = false,
    val inputError: String? = null
) {
    /** Ringkasan selalu memakai daftar LENGKAP, bukan daftar yang sudah disaring. */
    val completedCount: Int
        get() = tasks.count { it.isCompleted }

    /** Satu-satunya tempat aturan filter ditulis. */
    val visibleTasks: List<Task>
        get() = if (showIncompleteOnly) {
            tasks.filter { !it.isCompleted }
        } else {
            tasks
        }

    /** "2 of 3 tasks completed" — teks ringkasan yang dipakai layar. */
    val summaryText: String
        get() = "$completedCount of ${tasks.size} tasks completed"

    /** Layar kosong: tidak ada yang tampil di daftar, baik karena belum ada tugas maupun karena filter. */
    val showsEmptyMessage: Boolean
        get() = visibleTasks.isEmpty()
}

/** Teks empty state — dibedakan agar pengguna tahu apakah datanya kosong atau hanya tersaring. */
const val PESAN_BELUM_ADA_TUGAS = "No tasks yet. Add your first task."
const val PESAN_SEMUA_SELESAI = "All tasks are completed. Nothing left to do."

/** Bagian "Render State" — pesan validasi yang dilihat pengguna saat judul kosong. */
const val PESAN_JUDUL_KOSONG = "Enter a task title."
