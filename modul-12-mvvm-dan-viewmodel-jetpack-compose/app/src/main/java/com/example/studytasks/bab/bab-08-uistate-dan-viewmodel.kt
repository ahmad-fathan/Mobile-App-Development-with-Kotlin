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
import com.example.studytasks.PESAN_JUDUL_KOSONG
import com.example.studytasks.Tabel
import com.example.studytasks.TasksUiState
import com.example.studytasks.TasksViewModel

/**
 * Bab 8 (Bagian 7.4–7.9) — UI state dan ViewModel
 *
 * 7.4 UI state adalah potret seluruh nilai yang dibutuhkan untuk menampilkan layar pada satu saat.
 *     Mengumpulkannya dalam satu class berarti draf, daftar, dan pesan kesalahan selalu berubah
 *     bersama — tidak pernah tampil dalam kombinasi yang tidak konsisten. `errorMessage` nullable:
 *     `null` berarti tidak ada pesan.
 *
 * 7.5 StateFlow adalah penampung nilai terkini yang dapat diamati, bagian dari Kotlin coroutines.
 *     Pola standarnya dua property: `_uiState` (MutableStateFlow, private) dan `uiState`
 *     (StateFlow, read-only). Collector menerima nilai terbaru lebih dulu, lalu nilai berikutnya
 *     yang berbeda. Garis bawah di awal nama adalah konvensi untuk versi private yang dapat
 *     diubah.
 *
 * 7.6–7.7 onDraftChange memakai `update { it.copy(...) }`; addTask memeriksa draf lebih dulu,
 *     memanggil repository DI LUAR lambda `update` (lambda itu dapat dijalankan lebih dari sekali,
 *     sehingga tugas yang sama bisa ditambahkan dua kali), lalu menerbitkan state baru.
 *
 * 7.8 Factory memberi tahu ViewModel provider cara membuat instance beserta dependensinya. Blok
 *     initializer hanya dijalankan ketika instance baru diperlukan — setelah rotasi tidak
 *     dijalankan lagi, sehingga repository yang sama tetap dipakai.
 *
 * 7.9 Gabungan ketiganya ada di TasksViewModel.kt di paket akar.
 */

val kodeTasksUiState: String = """
    data class TasksUiState(
        val draft: String = "",
        val tasks: List<StudyTask> = emptyList(),
        val errorMessage: String? = null
    )
"""

val kodeStateFlow: String = """
    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()
"""

val kodeBagianPembuka: String = """
    class TasksViewModel(
        private val repository: TaskRepository
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(
            TasksUiState(tasks = repository.getTasks())
        )
        val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

        fun onDraftChange(value: String) {
            _uiState.update {
                it.copy(draft = value, errorMessage = null)
            }
        }
"""

val kodeAksiPengguna: String = """
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
"""

val kodeFactory: String = """
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
"""

/**
 * Menjalankan [TasksViewModel] sungguhan (tanpa Android, karena ViewModel() dari AndroidX ada di
 * JVM classpath saat build) lalu menampilkan jejak state-nya.
 */
private fun jejakStateViewModel(): List<Pair<String, String>> {
    val viewModel = TasksViewModel(InMemoryTaskRepository())
    val jejak = mutableListOf<Pair<String, String>>()
    fun catat(label: String) {
        val state: TasksUiState = viewModel.uiState.value
        jejak += label to "draft=\"${state.draft}\" tasks=${state.tasks.size} " +
            "error=${state.errorMessage ?: "null"}"
    }

    catat("State awal")
    viewModel.addTask()
    catat("Add task dengan draf kosong")
    viewModel.onDraftChange("   ")
    viewModel.addTask()
    catat("Add task dengan spasi saja")
    viewModel.onDraftChange("Review Kotlin")
    catat("Setelah mengetik")
    viewModel.addTask()
    catat("Setelah Add task")
    viewModel.toggleTask(viewModel.uiState.value.tasks.first().id)
    catat("Setelah menekan checkbox")
    return jejak
}

@Composable
fun UiStateDanViewModelScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 7.4–7.9 — UI state dan ViewModel",
            keterangan = "Satu potret state, satu pemilik, satu factory."
        )
        KodeBlok(kodeTasksUiState)
        Tabel(
            "Bagian 7.4 — mengapa satu class",
            listOf(
                "Konsisten" to "Draf, daftar, dan pesan berubah bersama",
                "tasks" to "Potret untuk layar; pemilik data tetap repository",
                "errorMessage" to "null berarti tidak ada pesan yang ditampilkan"
            )
        )
        KodeBlok(kodeStateFlow)
        Tabel(
            "Bagian 7.5 — peran setiap bagian StateFlow",
            listOf(
                "_uiState" to "MutableStateFlow: hanya pemiliknya (ViewModel) dapat mengubah",
                "uiState" to "StateFlow read-only: dibaca route dan layar",
                "collector" to "Menerima nilai terbaru, lalu nilai berikutnya yang berbeda",
                "_ (garis bawah)" to "Konvensi nama untuk versi private yang dapat diubah"
            )
        )
        KodeBlok(kodeBagianPembuka)
        KodeBlok(kodeAksiPengguna)
        Tabel(
            "Bagian 7.7 — dua hal yang mudah salah",
            listOf(
                "Pemeriksaan sebelum require" to "Pengguna melihat pesan \"$PESAN_JUDUL_KOSONG\", " +
                    "bukan aplikasi yang crash",
                "Repository di luar update" to "Lambda update dapat dijalankan lebih dari sekali; " +
                    "di dalamnya addTask dapat menambah tugas dua kali"
            )
        )
        KodeBlok(kodeFactory)
        Text("Jejak state dari TasksViewModel yang benar-benar dijalankan:")
        Tabel("Bagian 8 — penelusuran perubahan state", jejakStateViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun UiStateDanViewModelPreview() {
    UiStateDanViewModelScreen()
}
