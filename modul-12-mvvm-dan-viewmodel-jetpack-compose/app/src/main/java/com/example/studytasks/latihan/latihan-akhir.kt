package com.example.studytasks.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.studytasks.InMemoryTaskRepository
import com.example.studytasks.JudulBab
import com.example.studytasks.PemilikState
import com.example.studytasks.StudyTask
import com.example.studytasks.Tabel
import com.example.studytasks.KodeBlok
import com.example.studytasks.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Latihan Akhir Modul 12 — MVVM and ViewModel in Jetpack Compose.
 *
 * Enam latihan dari modul, dengan jawaban tertulis yang dapat dibaca
 * lewat [jawabanLatihanModul12] dan bagian yang meminta kode benar-benar diimplementasikan:
 *
 *   Latihan 1  Menentukan tanggung jawab            → Tabel jawaban (tanpa kode)
 *   Latihan 2  Memprediksi masa hidup               → Tabel jawaban + jalur kode terkecil
 *   Latihan 3  Menghapus tugas                      → [LatihanTaskRepository], [LatihanTasksViewModel],
 *                                                     [LatihanTasksScreen] (tombol Delete)
 *   Latihan 4  Menghitung tugas tersisa             → derived state + Clear completed
 *   Latihan 5  Menemukan masalah                    → empat masalah + versi yang benar
 *   Latihan 6  Berbagi tugas antarlayar            → jawaban tertulis + potongan kode
 *
 * Untuk mencoba hasil latihan di emulator, ganti `TasksRoute()` pada MainActivity.kt dengan
 * `LatihanTasksRoute()`.
 */

// ---------------------------------------------------------------------------
// Latihan 1 — Menentukan tanggung jawab
// ---------------------------------------------------------------------------

/** Satu butir Latihan 1 beserta jawaban dan alasannya. */
data class JawabanTanggungJawab(
    val nomor: Int,
    val butir: String,
    val pemilik: PemilikState,
    val alasan: String
)

val jawabanLatihan1: List<JawabanTanggungJawab> = listOf(
    JawabanTanggungJawab(
        1,
        "Menggambar checkbox untuk sebuah tugas.",
        PemilikState.UI_LOKAL,
        "Checkbox adalah tampilan; yang disimpan hanyalah nilai checked yang datang dari state."
    ),
    JawabanTanggungJawab(
        2,
        "Memeriksa bahwa judul yang dikirim tidak kosong.",
        PemilikState.VIEWMODEL,
        "Aturan ini menghasilkan pesan pada layar dan harus terjadi sebelum repository dipanggil."
    ),
    JawabanTanggungJawab(
        3,
        "Membuat ID untuk tugas baru.",
        PemilikState.REPOSITORY,
        "ID adalah identitas data; repository yang memiliki daftar tugas dan urutan ID-nya."
    ),
    JawabanTanggungJawab(
        4,
        "Mengingat sejauh mana pengguna sudah melakukan scroll pada daftar.",
        PemilikState.STATE_HOLDER_UI,
        "Posisi scroll adalah detail UI; rememberLazyListState menyimpannya tanpa ViewModel."
    ),
    JawabanTanggungJawab(
        5,
        "Menampilkan teks \"Enter a task title.\"",
        PemilikState.UI_LOKAL,
        "Teks itu digambar layar dari errorMessage; isi pesannya ditentukan ViewModel."
    ),
    JawabanTanggungJawab(
        6,
        "Menyimpan tugas yang sudah selesai setelah ponsel dinyalakan ulang.",
        PemilikState.PENYIMPANAN_PERMANEN,
        "Hanya penyimpanan permanen yang bertahan setelah process death; diakses lewat repository."
    ),
    JawabanTanggungJawab(
        7,
        "Mengingat apakah card tugas sedang terbuka untuk menampilkan detail.",
        PemilikState.UI_LOKAL,
        "Status buka-tutup sementara milik UI; rememberSaveable cukup dan tidak mengotori ViewModel."
    ),
    JawabanTanggungJawab(
        8,
        "Mengosongkan text field setelah tugas ditambahkan.",
        PemilikState.VIEWMODEL,
        "ViewModel yang menerbitkan draft = \"\" setelah repository menambahkan tugas."
    )
)

// ---------------------------------------------------------------------------
// Latihan 2 — Memprediksi masa hidup
// ---------------------------------------------------------------------------

data class JawabanMasaHidup(
    val peristiwa: String,
    val before: String,
    val denganViewModel: String,
    val alasan: String
)

val jawabanLatihan2: List<JawabanMasaHidup> = listOf(
    JawabanMasaHidup(
        "1. Pengguna merotasi perangkat.",
        "Draf DAN daftar hilang.",
        "Draf dan daftar tetap.",
        "Rotasi membuat ulang Activity; remember hidup hanya selama Composition, sedangkan " +
            "ViewModel dipertahankan ViewModelStoreOwner (Tabel 5 & 7)."
    ),
    JawabanMasaHidup(
        "2. Aplikasi ke latar belakang, proses dimatikan sistem, pengguna kembali.",
        "Hilang.",
        "Hilang — ViewModel baru dengan repository kosong.",
        "Process death menghancurkan instance ViewModel (Tabel 5 baris 3); data hanya di memori."
    ),
    JawabanMasaHidup(
        "3. Pengguna menekan Back, lalu membuka aplikasi lagi.",
        "Hilang.",
        "Kosong.",
        "Owner dihapus permanen → ViewModel dibersihkan; instance berikutnya memakai repository baru."
    )
)

/** Perubahan terkecil agar DRAF saja bertahan pada peristiwa 2: pindahkan draf ke saved state. */
const val KODE_DRAF_SAVEDSTATE: String =
    "class LatihanDraftViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {\n" +
        "    val draft: StateFlow<String> = savedStateHandle.getStateFlow(\"draft\", \"\")\n" +
        "    fun onDraftChange(value: String) { savedStateHandle[\"draft\"] = value }\n" +
        "}"

// ---------------------------------------------------------------------------
// Latihan 3 + 4 — delete, hitung tersisa, clear completed
// ---------------------------------------------------------------------------

/** Kontrak repository latihan: tambah deleteTask dan clearCompleted (Latihan 3 & 4). */
interface LatihanTaskRepository {
    fun getTasks(): List<StudyTask>
    fun addTask(title: String)
    fun toggleTask(id: Long)
    fun deleteTask(id: Long)
    fun clearCompleted()
}

/** Implementasi memori dengan ID stabil: menghapus satu tugas tidak mengubah ID tugas lain. */
class LatihanInMemoryTaskRepository : LatihanTaskRepository {
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
            if (task.id == id) task.copy(isDone = !task.isDone) else task
        }
    }

    override fun deleteTask(id: Long) {
        tasks = tasks.filter { it.id != id }
    }

    override fun clearCompleted() {
        tasks = tasks.filter { !it.isDone }
    }
}

/** State layar latihan. [sisaTugas] dan [semuaSelesai] SENGAJA tidak disimpan. */
data class LatihanTasksUiState(
    val draft: String = "",
    val tasks: List<StudyTask> = emptyList(),
    val errorMessage: String? = null
) {
    /**
     * Latihan 4 (1) — derived state: dihitung dari [tasks] saat ditampilkan, bukan property baru
     * di UI state. Alasannya: nilai turunan tidak dapat berbeda dari sumbernya, dan tidak perlu
     * setiap aksi mengingat untuk memperbaruinya. Ini penerapan konsep derived state dari
     * pertemuan state management.
     */
    val sisaTugas: Int get() = tasks.count { !it.isDone }
    val teksSisa: String get() = "$sisaTugas of ${tasks.size} tasks remaining"
    val semuaSelesai: Boolean get() = tasks.isNotEmpty() && tasks.all { it.isDone }
}

/** ViewModel latihan: deleteTask (Latihan 3), hitung tersisa + clearCompleted (Latihan 4). */
class LatihanTasksViewModel(
    private val repository: LatihanTaskRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        LatihanTasksUiState(tasks = repository.getTasks())
    )
    val uiState: StateFlow<LatihanTasksUiState> = _uiState.asStateFlow()

    fun onDraftChange(value: String) {
        _uiState.update { it.copy(draft = value, errorMessage = null) }
    }

    fun addTask() {
        val title = _uiState.value.draft.trim()
        if (title.isEmpty()) {
            _uiState.update { it.copy(errorMessage = "Enter a task title.") }
            return
        }
        repository.addTask(title)
        _uiState.update {
            it.copy(draft = "", tasks = repository.getTasks(), errorMessage = null)
        }
    }

    fun toggleTask(id: Long) {
        repository.toggleTask(id)
        _uiState.update { it.copy(tasks = repository.getTasks()) }
    }

    fun deleteTask(id: Long) {
        repository.deleteTask(id)
        _uiState.update { it.copy(tasks = repository.getTasks()) }
    }

    fun clearCompleted() {
        repository.clearCompleted()
        _uiState.update { it.copy(tasks = repository.getTasks()) }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                LatihanTasksViewModel(repository = LatihanInMemoryTaskRepository())
            }
        }
    }
}

/** Route latihan: satu-satunya tempat yang memperoleh ViewModel. */
@Composable
fun LatihanTasksRoute(
    viewModel: LatihanTasksViewModel = viewModel(factory = LatihanTasksViewModel.Factory)
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    LatihanTasksScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onAddTask = viewModel::addTask,
        onToggleTask = viewModel::toggleTask,
        onDeleteTask = viewModel::deleteTask,
        onClearCompleted = viewModel::clearCompleted
    )
}

/** Layar latihan: stateless, menerima satu callback untuk setiap aksi. */
@Composable
fun LatihanTasksScreen(
    uiState: LatihanTasksUiState,
    onDraftChange: (String) -> Unit,
    onAddTask: () -> Unit,
    onToggleTask: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    onClearCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Study tasks", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(
            value = uiState.draft,
            onValueChange = onDraftChange,
            label = { Text("Task title") },
            isError = uiState.errorMessage != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (uiState.errorMessage != null) {
            Text(uiState.errorMessage, color = MaterialTheme.colorScheme.error)
        }
        Button(onClick = onAddTask) { Text("Add task") }
        Text(uiState.teksSisa, style = MaterialTheme.typography.labelLarge)
        TextButton(onClick = onClearCompleted, enabled = uiState.tasks.any { it.isDone }) {
            Text("Clear completed")
        }
        LatihanTasksList(
            tasks = uiState.tasks,
            onToggleTask = onToggleTask,
            onDeleteTask = onDeleteTask,
            modifier = Modifier.weight(1f)
        )
    }
}

/** Baris latihan: checkbox + judul + tombol Delete, key stabil dari ID. */
@Composable
fun LatihanTasksList(
    tasks: List<StudyTask>,
    onToggleTask: (Long) -> Unit,
    onDeleteTask: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(tasks, key = { it.id }) { task ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { onToggleTask(task.id) }
                )
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f)
                )
                TextButton(onClick = { onDeleteTask(task.id) }) { Text("Delete") }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Latihan 5 — Menemukan masalah
// ---------------------------------------------------------------------------

data class MasalahLatihan5(val masalah: String, val akibat: String, val perbaikan: String)

/** Empat masalah pada kode Latihan 5 beserta akibat dan perbaikannya. */
val jawabanLatihan5: List<MasalahLatihan5> = listOf(
    MasalahLatihan5(
        masalah = "1. `val uiState = MutableStateFlow(...)` disediakan publik.",
        akibat = "Siapa pun dapat menulis state ViewModel dari luar; aliran perubahan tidak lagi " +
            "dapat dilacak ke satu pemilik.",
        perbaikan = "Jadikan `private val _uiState` dan sediakan `val uiState: StateFlow<...> = " +
            "_uiState.asStateFlow()`."
    ),
    MasalahLatihan5(
        masalah = "2. Draf kosong tidak diperiksa; `repository.addTask(it.draft)` dipanggil " +
            "langsung sehingga `require` melempar exception.",
        akibat = "Aplikasi crash, bukan menampilkan pesan validasi.",
        perbaikan = "Trim draf, bila kosong terbitkan errorMessage dan return sebelum repository."
    ),
    MasalahLatihan5(
        masalah = "3. `repository.addTask` dipanggil DI DALAM lambda `update`.",
        akibat = "Lambda dapat dijalankan lebih dari sekali pada pembaruan bersamaan, sehingga " +
            "tugas yang sama dapat ditambahkan dua kali.",
        perbaikan = "Panggil repository lebih dulu, lalu `update { it.copy(...) }`."
    ),
    MasalahLatihan5(
        masalah = "4. `TasksViewModel(InMemoryTaskRepository())` dibuat langsung di dalam " +
            "Composable route.",
        akibat = "Instance baru setiap recomposition (repository kosong), dan seluruh state hilang " +
            "saat Activity dibuat ulang pada rotasi.",
        perbaikan = "Pakai `viewModel(factory = TasksViewModel.Factory)`; `remember` tidak cukup " +
            "karena hanya hidup selama Composition."
    )
)

/** Versi kode yang sudah diperbaiki (Latihan 5), ditampilkan di layar latihan. */
val kodeLatihan5Benar: String = """
    class TasksViewModel(
        private val repository: TaskRepository
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(TasksUiState())
        val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

        fun addTask() {
            val title = _uiState.value.draft.trim()
            if (title.isEmpty()) {
                _uiState.update { it.copy(errorMessage = "Enter a task title.") }
                return
            }
            repository.addTask(title)          // di luar lambda update
            _uiState.update {
                it.copy(draft = "", tasks = repository.getTasks(), errorMessage = null)
            }
        }
    }

    @Composable
    fun TasksRoute(
        viewModel: TasksViewModel = viewModel(factory = TasksViewModel.Factory)
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        TasksScreen(uiState = uiState, /* … */)
    }
"""

// ---------------------------------------------------------------------------
// Latihan 6 — Berbagi tugas antarlayar
// ---------------------------------------------------------------------------

val jawabanLatihan6: List<Pair<String, String>> = listOf(
    "1. Mengapa Factory sekarang memberi daftar berbeda" to
        "Setiap initializer membuat InMemoryTaskRepository() baru, dan setiap ViewModel scope-nya " +
        "berbeda — tidak ada data yang dibagi.",
    "2. Di mana repository bersama dibuat" to
        "Di tingkat aplikasi (Application atau container sederhana), hidup selama proses aplikasi; " +
        "kedua factory menerima instance yang sama lewat constructor.",
    "3. Mengapa pembaruan setelah aksi sendiri tidak cukup" to
        "SummaryViewModel tidak pernah memanggil aksi Tasks, jadi potretnya tidak pernah diperbarui. " +
        "Perlu aliran yang dapat diamati dari repository.",
    "4. Agar tugas awet setelah aplikasi ditutup" to
        "Penyimpanan permanen (database/Room) di balik repository, plus operasi asinkron yang aman " +
        "dipanggil dari main thread."
)

val kodeLatihan6: String = """
    interface TaskRepository {
        val tasks: StateFlow<List<StudyTask>>
        fun addTask(title: String)
        fun toggleTask(id: Long)
    }

    class TasksViewModel(repository: TaskRepository) : ViewModel() {
        val uiState: StateFlow<TasksUiState> = combine(
            repository.tasks, draftFlow
        ) { tasks, draft -> TasksUiState(draft, tasks) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), TasksUiState())
    }

    class SummaryViewModel(repository: TaskRepository) : ViewModel() {
        val selesai: StateFlow<Int> = repository.tasks
            .map { daftar -> daftar.count { it.isDone } }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0)
    }
"""

// ---------------------------------------------------------------------------
// Layar latihan
// ---------------------------------------------------------------------------

@Composable
fun LatihanAkhirScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Latihan Akhir — MVVM and ViewModel",
            keterangan = "Enam latihan modul. Latihan 3 dan 4 sudah diimplementasikan dan berjalan."
        )
        Tabel(
            "Latihan 1 — Menentukan tanggung jawab",
            jawabanLatihan1.map { "${it.nomor}. ${it.butir}" to "${it.pemilik.name} — ${it.alasan}" }
        )
        Tabel(
            "Latihan 2 — Memprediksi masa hidup",
            jawabanLatihan2.map { it.peristiwa to "TaskScreenBefore: ${it.before} · Dengan ViewModel: " +
                "${it.denganViewModel} (${it.alasan})" }
        )
        Text("Latihan 2 — perubahan terkecil agar draf bertahan pada peristiwa 2:")
        KodeBlok(KODE_DRAF_SAVEDSTATE)
        Tabel(
            "Latihan 3 & 4 — yang ditambahkan",
            listOf(
                "Latihan 3" to "deleteTask(id) di repository (filter), ViewModel, dan callback; tombol Delete di tiap baris",
                "Latihan 4" to "sisaTugas & teksSisa sebagai derived state; Clear completed lewat repository"
            )
        )
        Tabel(
            "Latihan 5 — empat masalah",
            jawabanLatihan5.map { it.masalah to "Akibat: ${it.akibat} Perbaikan: ${it.perbaikan}" }
        )
        KodeBlok(kodeLatihan5Benar)
        Tabel("Latihan 6 — berbagi tugas antarlayar", jawabanLatihan6)
        KodeBlok(kodeLatihan6)
        Text("Implementasi latihan (berjalan):")
        LatihanTasksRoute()
    }
}

@Preview(showBackground = true)
@Composable
fun LatihanPreview() {
    LatihanTasksScreen(
        uiState = LatihanTasksUiState(
            tasks = listOf(
                StudyTask(1L, "Review Kotlin", isDone = true),
                StudyTask(2L, "Practice Compose"),
                StudyTask(3L, "Read chapter 2")
            )
        ),
        onDraftChange = {},
        onAddTask = {},
        onToggleTask = {},
        onDeleteTask = {},
        onClearCompleted = {}
    )
}

/** Preview keadaan ketika semua tugas sudah selesai (Latihan 4 poin 4). */
@Preview(showBackground = true)
@Composable
fun LatihanSemuaSelesaiPreview() {
    LatihanTasksScreen(
        uiState = LatihanTasksUiState(
            tasks = listOf(
                StudyTask(1L, "Review Kotlin", isDone = true),
                StudyTask(2L, "Practice Compose", isDone = true)
            )
        ),
        onDraftChange = {},
        onAddTask = {},
        onToggleTask = {},
        onDeleteTask = {},
        onClearCompleted = {}
    )
}

/** Preview layar jawaban tanpa komponen aplikasi berjalan. */
@Preview(showBackground = true)
@Composable
fun LatihanJawabanPreview() {
    MaterialTheme {
        Scaffold { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                JudulBab("Latihan Akhir — jawaban tertulis", null)
            }
        }
    }
}
