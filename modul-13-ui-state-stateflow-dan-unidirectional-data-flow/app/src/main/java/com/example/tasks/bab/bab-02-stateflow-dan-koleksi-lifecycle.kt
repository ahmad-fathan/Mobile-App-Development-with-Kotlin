package com.example.tasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasks.JudulBab
import com.example.tasks.KodeBlok
import com.example.tasks.Tabel
import com.example.tasks.peranApiState
import com.example.tasks.stateSetelahHapus
import com.example.tasks.stateSetelahStatus
import com.example.tasks.stateSetelahTambah
import com.example.tasks.bab.contohStateBab1

/**
 * Bab 2 — StateFlow: Nilai Sekarang yang Dapat Diamati
 *
 * Bagian modul: "What Is StateFlow?", "Flow, StateFlow, and Compose State",
 * "Keep Mutable State Private", "Update the Current State", "Collect State at the Route Boundary",
 * "Why Lifecycle-Aware Collection?".
 *
 * StateFlow menyimpan SATU nilai sekarang dan menerbitkan nilai baru ke collector. Ia bukan riwayat
 * kejadian: collector lambat dapat melewatkan nilai tengah.
 */

@Composable
fun StateFlowScreen(modifier: Modifier = Modifier) {
    val setelahTambah = stateSetelahTambah(contohStateBab1.copy(draftTitle = "  Add a second task  "), "3")
    val setelahCentang = stateSetelahStatus(setelahTambah, "2", true)
    val setelahHapus = stateSetelahHapus(setelahCentang, "1")

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "StateFlow: satu pemilik, satu aliran baca",
            keterangan = "MutableStateFlow ditulis ViewModel; StateFlow dibaca UI."
        )

        KodeBlok(
            """
            class TasksViewModel : ViewModel() {
                private val _uiState = MutableStateFlow(TasksUiState())
                val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()
            }
            // _uiState  : dapat ditulis, hanya di dalam ViewModel
            // uiState   : read-only, diberikan ke UI
            // UI meminta perubahan lewat method, bukan dengan menulis state
            """
        )

        Tabel(
            "Peran setiap API (jangan tertukar)",
            peranApiState.map { it.api to it.peran }
        )

        Text(
            "Rantainya: MutableStateFlow ditulis ViewModel → StateFlow read-only → " +
                "collectAsStateWithLifecycle() → Compose State dibaca layar.",
            style = MaterialTheme.typography.bodyMedium
        )
        KodeBlok(
            """
            // Jalur lengkap di TasksScreen.kt
            @Composable
            fun TasksRoute(viewModel: TasksViewModel = viewModel()) {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                TasksScreen(
                    uiState = uiState,
                    onDraftTitleChanged = viewModel::onDraftTitleChanged,
                    onAddTask = viewModel::onAddTask,
                    onTaskCompletionChanged = viewModel::onTaskCompletionChanged,
                    onIncompleteFilterChanged = viewModel::onIncompleteFilterChanged
                )
            }
            // Layar menerima nilai + callback, bukan ViewModel.
            """
        )

        Text("update() — transformasi atomik, bebas efek samping", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            fun onDraftTitleChanged(title: String) {
                _uiState.update { current ->
                    current.copy(draftTitle = title, inputError = null)
                }
            }

            fun onAddTask() {
                val newId = idTugasBaru()          // ID dibuat di LUAR update
                _uiState.update { current ->
                    val title = current.draftTitle.trim()
                    if (title.isBlank()) current.copy(inputError = PESAN_JUDUL_KOSONG)
                    else current.copy(
                        tasks = current.tasks + Task(newId, title),
                        draftTitle = "",
                        inputError = null
                    )
                }
            }
            // Jangan kirim request, tulis berkas, atau buat ID di dalam lambda update:
            // lambda itu dapat dievaluasi lebih dari sekali saat ada pembaruan bersamaan.
            """
        )

        Text("Koleksi sadar-lifecycle", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "collectAsStateWithLifecycle()",
            listOf(
                "Aplikasi terlihat (≥ STARTED)" to "koleksi berjalan; setiap snapshot baru menjadi Compose State",
                "Aplikasi di latar belakang (< STARTED)" to "koleksi berhenti; layar tidak menerima pembaruan",
                "Terlihat kembali" to "koleksi berlanjut dengan nilai TERKINI dari StateFlow",
                "Producer di ViewModel" to "tidak otomatis berhenti — umurnya tergantung konfigurasi coroutine/flow"
            )
        )

        Text("Telusuri perubahan state pada aplikasi ini", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Tiga snapshot berurutan",
            listOf(
                "awal" to "draf \"\", ${contohStateBab1.tasks.size} tugas, ${contohStateBab1.completedCount} selesai",
                "setelah onAddTask()" to "draf \"\", ${setelahTambah.tasks.size} tugas, ${setelahTambah.completedCount} selesai",
                "setelah checkbox tugas #2" to "draf \"\", ${setelahCentang.tasks.size} tugas, ${setelahCentang.completedCount} selesai",
                "setelah onDeleteTask(\"1\")" to "draf \"\", ${setelahHapus.tasks.size} tugas, ${setelahHapus.completedCount} selesai"
            )
        )
        Text(
            "Setiap baris dihitung dari fungsi murni di verifikasi.kt (stateSetelahTambah, " +
                "stateSetelahStatus, stateSetelahHapus) — aturan yang sama dipakai viewModel dan uji.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StateFlowPreview() {
    StateFlowScreen()
}
