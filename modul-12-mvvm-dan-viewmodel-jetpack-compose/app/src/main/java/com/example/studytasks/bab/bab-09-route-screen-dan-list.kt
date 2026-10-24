package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.KodeBlok
import com.example.studytasks.Tabel
import com.example.studytasks.TasksRoute
import com.example.studytasks.TasksScreen
import com.example.studytasks.TasksUiState
import com.example.studytasks.StudyTask

/**
 * Bab 9 (Bagian 7.10–7.13, 9, 10) — Route, Screen, List, dan scope ViewModel
 *
 * 7.10 Route adalah Composable kecil yang tugasnya HANYA tiga: memperoleh ViewModel,
 *      mengumpulkan state-nya, meneruskan nilai + callback ke layar. Tiga hal baru:
 *      `viewModel(factory = ...)` meminta instance kepada provider lifecycle-aware (mengembalikan
 *      yang sudah ada bila tersedia, baru memakai factory bila belum);
 *      `collectAsStateWithLifecycle()` mengumpulkan StateFlow selama lifecycle induk minimal
 *      STARTED dan menjeda di bawahnya; `viewModel::addTask` adalah function reference.
 *      Menjeda pengumpulan TIDAK menghentikan producer/coroutine di dalam ViewModel — yang
 *      berhenti hanya penerimaan nilai oleh layar ini.
 *
 * 7.11 TasksScreen stateless: menerima seluruh UI state dan satu callback untuk setiap aksi.
 *      Tidak ada remember, validasi, maupun manipulasi list. `isError` membuat field merah;
 *      `Modifier.weight(1f)` memberi sisa tinggi Column kepada daftar (syarat lazy list).
 *
 * 7.12 TasksList memakai `key = { it.id }`. Checkbox TIDAK berubah sendiri: nilai `checked` baru
 *      berubah ketika state yang sudah diperbarui kembali dari ViewModel. Baris hanya menerima
 *      data baris + callback, bukan ViewModel.
 *
 * 7.13 Route diletakkan di dalam Activity dengan Scaffold + innerPadding.
 *
 * 9    Karena TasksScreen menerima nilai biasa, layar dapat di-preview dengan state apa pun —
 *      lihat TasksPreview.kt (tiga preview: normal, pesan validasi, kosong).
 *
 * 10   Scope: viewModel() mencari ViewModelStoreOwner terdekat dan memakai key. Tanpa navigasi,
 *      Activity adalah owner-nya. Mengeluarkan layar dari Composition TIDAK membersihkan
 *      ViewModel; menghapus owner secara permanen akan memanggil onCleared(). Karena itu ViewModel
 *      tidak boleh dibuat dengan constructor-nya di dalam Composable.
 */

val kodeTasksRoute: String = """
    @Composable
    fun TasksRoute(
        viewModel: TasksViewModel = viewModel(
            factory = TasksViewModel.Factory
        )
    ) {
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        TasksScreen(
            uiState = uiState,
            onDraftChange = viewModel::onDraftChange,
            onAddTask = viewModel::addTask,
            onToggleTask = viewModel::toggleTask
        )
    }
"""

val kodeTasksScreen: String = """
    @Composable
    fun TasksScreen(
        uiState: TasksUiState,
        onDraftChange: (String) -> Unit,
        onAddTask: () -> Unit,
        onToggleTask: (Long) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Study tasks")
            OutlinedTextField(
                value = uiState.draft,
                onValueChange = onDraftChange,
                label = { Text("Task title") },
                isError = uiState.errorMessage != null
            )
            uiState.errorMessage?.let { Text(it) }
            Button(onClick = onAddTask) { Text("Add task") }
            TasksList(
                tasks = uiState.tasks,
                onToggleTask = onToggleTask,
                modifier = Modifier.weight(1f)
            )
        }
    }
"""

val kodeTasksList: String = """
    @Composable
    fun TasksList(
        tasks: List<StudyTask>,
        onToggleTask: (Long) -> Unit,
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
                    Text(task.title)
                }
            }
        }
    }
"""

val kodeActivity: String = """
    setContent {
        StudyTasksTheme {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Column(modifier = Modifier.padding(innerPadding)) {
                    TasksRoute()
                }
            }
        }
    }
"""

val kodeDependensi: String = """
    # gradle/libs.versions.toml
    androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle",
        name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
    androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle",
        name = "lifecycle-runtime-compose", version.ref = "lifecycleRuntimeKtx" }

    # app/build.gradle.kts
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
"""

val kodeSalahConstructor: String = """
    // Tidak memakai retensi yang dikelola lifecycle:
    val vm = TasksViewModel(InMemoryTaskRepository())
"""

@Composable
fun RouteScreenDanListScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 7.10–7.13, 9, 10 — Route, screen, list, dan scope",
            keterangan = "Satu tempat memperoleh ViewModel; sisanya hanya menerima nilai."
        )
        KodeBlok(kodeTasksRoute)
        Tabel(
            "Tiga hal baru di route",
            listOf(
                "viewModel(factory = …)" to "Provider lifecycle-aware: instance lama dipakai bila ada",
                "collectAsStateWithLifecycle()" to "Mengumpulkan StateFlow selama minimal STARTED, " +
                    "dijeda di bawahnya (mis. di latar belakang)",
                "viewModel::addTask" to "Function reference, setara { viewModel.addTask() }"
            )
        )
        KodeBlok(kodeTasksScreen)
        Tabel(
            "Bandingkan dengan TaskScreenBefore",
            listOf(
                "Tidak ada remember" to "State dimiliki ViewModel, bukan Composition",
                "Tidak ada validasi" to "Aturan tinggal di ViewModel dan repository",
                "Tidak ada manipulasi list" to "Layar hanya menampilkan uiState.tasks",
                "Tidak ada ViewModel" to "Layar menerima nilai biasa → dapat di-preview"
            )
        )
        KodeBlok(kodeTasksList)
        Tabel(
            "Bagian 7.12 — siklus UDF pada skala terkecil",
            listOf(
                "Klik checkbox" to "Baris melaporkan task.id lewat onToggleTask",
                "ViewModel" to "repository.toggleTask(id) lalu menerbitkan state baru",
                "Checkbox" to "Nilai checked baru berubah setelah state kembali dari ViewModel"
            )
        )
        KodeBlok(kodeActivity)
        KodeBlok(kodeDependensi)
        Tabel(
            "Bagian 10 — scope ViewModel",
            listOf(
                "Tanpa navigasi" to "Activity adalah ViewModelStoreOwner",
                "Dengan navigasi" to "Owner dapat berupa entri back stack untuk satu destination",
                "Keluar dari Composition" to "TIDAK membersihkan ViewModel",
                "Owner dihapus permanen" to "ViewModel dibersihkan dan onCleared() dipanggil"
            )
        )
        KodeBlok(kodeSalahConstructor)
        Tabel(
            "Mengapa constructor di Composable salah",
            listOf(
                "Dijalankan ulang" to "Setiap recomposition membuat ViewModel baru dengan repository kosong",
                "Dibungkus remember" to "Tidak berjalan ulang saat recomposition, tetapi tetap hilang " +
                    "saat Activity dibuat ulang",
                "Yang benar" to "Hanya provider lewat viewModel() + factory yang memberi retensi"
            )
        )
        Text("Coba langsung: layar di bawah memakai TasksRoute dan ViewModel sungguhan.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
        Box(modifier = Modifier.fillMaxWidth().height(360.dp)) {
            TasksRoute()
        }
    }
}

/** Praktik: screen stateless yang sama, tetapi dengan state buatan sendiri. */
@Composable
fun PraktikTasksScreenTanpaViewModel() {
    TasksScreen(
        uiState = TasksUiState(
            draft = "Read chapter 2",
            tasks = listOf(
                StudyTask(1L, "Review Kotlin", isDone = true),
                StudyTask(2L, "Practice Compose")
            )
        ),
        onDraftChange = {},
        onAddTask = {},
        onToggleTask = {}
    )
}

@Preview(showBackground = true)
@Composable
fun RouteScreenDanListPreview() {
    RouteScreenDanListScreen()
}
