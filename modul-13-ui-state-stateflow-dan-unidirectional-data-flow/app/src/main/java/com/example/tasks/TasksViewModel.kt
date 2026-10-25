package com.example.tasks

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Pemilik state layar Study Tasks (lihat modul "UI State, StateFlow, and Unidirectional Data Flow").
 *
 * Hanya class ini yang boleh MENULIS state. UI membaca lewat [uiState] dan meminta perubahan lewat
 * method bernama — itulah Unidirectional Data Flow:
 *
 *   state  : ViewModel ──► StateFlow ──► collectAsStateWithLifecycle() ──► Compose
 *   action : Compose ──► callback ──► method di class ini ──► state baru
 *
 * Tugas disimpan di memori saja pada modul ini; data layer akan menyusul. Karena itu state bertahan
 * saat rotasi (ViewModel dipertahankan) tetapi hilang saat process death.
 *
 * Catatan desain penting: pembuatan ID dan `trim()` dilakukan DI LUAR lambda `update`, karena
 * transformasi di dalam `update` dapat dievaluasi lebih dari sekali ketika ada pembaruan bersamaan.
 * Transformasi itu harus bebas efek samping.
 */
class TasksViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())

    /** Tampilan read-only. UI tidak dapat mengubah nilai di balik ini. */
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    /** Aksi 1 — pengguna mengetik. Pesan kesalahan dikosongkan karena input sedang diperbaiki. */
    fun onDraftTitleChanged(title: String) {
        _uiState.update { current ->
            current.copy(
                draftTitle = title,
                inputError = null
            )
        }
    }

    /** Aksi 2 — menambah tugas. Validasi milik pemilik state, bukan milik Composable. */
    fun onAddTask() {
        val newId = idTugasBaru()
        _uiState.update { current ->
            val title = current.draftTitle.trim()
            if (title.isBlank()) {
                current.copy(inputError = PESAN_JUDUL_KOSONG)
            } else {
                current.copy(
                    tasks = current.tasks + Task(newId, title),
                    draftTitle = "",
                    inputError = null
                )
            }
        }
    }

    /**
     * Aksi 3 — mengubah status selesai.
     *
     * Memakai ID tugas, bukan posisi di daftar yang terlihat: filter dapat mengubah posisi.
     * Nilai `completed` yang diminta ditulis apa adanya, sehingga aksi yang sama berulang aman.
     */
    fun onTaskCompletionChanged(id: String, completed: Boolean) {
        _uiState.update { current ->
            current.copy(
                tasks = current.tasks.map { task ->
                    if (task.id == id) task.copy(isCompleted = completed) else task
                }
            )
        }
    }

    /** Aksi 4 — mengubah filter daftar. Filter adalah state layar, jadi pemiliknya ViewModel. */
    fun onIncompleteFilterChanged(enabled: Boolean) {
        _uiState.update { current ->
            current.copy(showIncompleteOnly = enabled)
        }
    }

    /**
     * Independent Practice — menghapus tugas.
     *
     * Jalur UDF-nya sama persis dengan aksi lain: layar mengirim ID, ViewModel membuat daftar baru
     * dengan `filterNot`, lalu menerbitkan snapshot baru. Karena memakai ID, penghapusan tetap benar
     * walaupun filter "Show incomplete only" sedang aktif (posisi di daftar terlihat berbeda dari
     * posisi di daftar lengkap).
     */
    fun onDeleteTask(id: String) {
        _uiState.update { current ->
            current.copy(
                tasks = current.tasks.filterNot { it.id == id }
            )
        }
    }

    /**
     * Latihan tambahan "Clear completed" — jalur UDF yang sama dengan [onDeleteTask], hanya
     * predikatnya berbeda: yang dibuang adalah semua tugas selesai.
     */
    fun onClearCompleted() {
        _uiState.update { current ->
            current.copy(
                tasks = current.tasks.filterNot { it.isCompleted }
            )
        }
    }
}
