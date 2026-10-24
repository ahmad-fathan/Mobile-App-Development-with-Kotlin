package com.example.studytasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * State holder tingkat layar Study Tasks (Bagian 7.6–7.9).
 *
 * Tiga tanggung jawabnya:
 *   1. menerbitkan [uiState] — potret tunggal yang dibaca layar (Bagian 7.6);
 *   2. menangani aksi pengguna dan mengoordinasikan repository (Bagian 7.7);
 *   3. menyediakan [Factory] agar Android tahu cara membuatnya beserta dependensinya
 *      (Bagian 7.8).
 *
 * Aturan yang tampak di kode ini:
 *   - `_uiState` privat dan dapat diubah; [uiState] publik dan read-only (Tabel 9 baris 2);
 *   - repository dipanggil DI LUAR lambda `update`, karena lambda itu dapat dijalankan lebih dari
 *     sekali bila ada pembaruan lain pada saat yang sama (Tabel 9 baris 5);
 *   - pemeriksaan draf kosong terjadi sebelum `require` di repository, sehingga pengguna melihat
 *     pesan, bukan aplikasi yang crash.
 */
class TasksViewModel(
    private val repository: TaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        TasksUiState(tasks = repository.getTasks())
    )
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    /** Setiap ketikan memperbarui draf dan mengosongkan pesan kesalahan. */
    fun onDraftChange(value: String) {
        _uiState.update {
            it.copy(draft = value, errorMessage = null)
        }
    }

    /**
     * Dua tugas ViewModel secara berdampingan: mengubah input tidak valid menjadi umpan balik pada
     * layar, atau mengoordinasikan repository lalu menerbitkan state baru.
     */
    fun addTask() {
        val title = _uiState.value.draft.trim()
        if (title.isEmpty()) {
            _uiState.update {
                it.copy(errorMessage = "Enter a task title.")
            }
            return
        }
        repository.addTask(title)
        _uiState.update {
            it.copy(
                draft = "",
                tasks = repository.getTasks(),
                errorMessage = null
            )
        }
    }

    fun toggleTask(id: Long) {
        repository.toggleTask(id)
        _uiState.update { it.copy(tasks = repository.getTasks()) }
    }

    /**
     * Factory dijalankan HANYA ketika instance baru diperlukan. Ketika provider mengembalikan
     * ViewModel yang dipertahankan setelah rotasi, blok initializer tidak dijalankan lagi,
     * sehingga repository yang sama beserta tugas-tugasnya tetap dipakai.
     */
    companion object {
        val Factory = viewModelFactory {
            initializer {
                TasksViewModel(
                    repository = InMemoryTaskRepository()
                )
            }
        }
    }
}
