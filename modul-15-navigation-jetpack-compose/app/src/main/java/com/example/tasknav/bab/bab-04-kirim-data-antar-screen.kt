package com.example.tasknav.bab

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
import com.example.tasknav.JudulBab
import com.example.tasknav.KodeBlok
import com.example.tasknav.Tabel
import com.example.tasknav.tipeArgumenTambahan
import com.example.tasknav.tipeArgumenLangsung

/**
 * Bab 4 — Mengirim Data Antar-Screen
 *
 * Bagian modul: 8 (8.1 Argumen Berada di Route Class, 8.2 Mengirim dan Menerima Argumen,
 * 8.3 Kirim ID Bukan Seluruh Objek, 8.4 Argumen Opsional, 8.5 Membaca Argumen di ViewModel).
 *
 * Aturan yang dipegang: argumen adalah alamat, bukan paket.
 */

@Composable
fun KirimDataScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Mengirim data antar-screen",
            keterangan = "Kirim ID kecil, ambil isinya di tujuan"
        )

        Text("8.1 Argumen berada di route class", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            @Serializable data class TaskDetail(val taskId: Int)
            @Serializable data class EditTask(val taskId: Int, val title: String)
            """
        )
        Tabel(
            "Tabel 5 — tipe argumen",
            listOf(
                "Dapat langsung dipakai" to tipeArgumenLangsung.joinToString(", "),
                "Butuh kerja tambahan" to tipeArgumenTambahan.joinToString(", ")
            )
        )

        Text(
            "Enum dapat langsung dipakai sebagai argumen. Beri anotasi @Keep pada deklarasi enum " +
                "agar tidak terhapus ketika build diperkecil (minify).",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("8.2 Mengirim dan menerima argumen", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            // Sisi pengirim
            onTaskClick(2)                                    // layar daftar
            navController.navigate(TaskDetail(2))             // NavHost
            // Entri back stack yang baru menyimpan taskId = 2

            // Sisi penerima
            val route: TaskDetail = backStackEntry.toRoute()  // bongkar nilai
            TaskDetailScreen(taskId = route.taskId)           // Int biasa, bukan route
            """
        )
        Text(
            "Karena argumen disimpan di entri back stack, nilainya tetap ada setelah layar diputar " +
                "dan setelah aplikasi dipulihkan sistem. Screen detail tidak pernah menyentuh API navigasi.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("8.3 Kirim ID, bukan seluruh objek", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Dua pilihan",
            listOf(
                "Dihindari" to "navigate(TaskDetail(task)) — objek disalin ke saved state yang ukurannya terbatas dan salinannya menjadi usang",
                "Dianjurkan" to "navigate(TaskDetail(task.id)) — nilai kecil; layar detail mengambil data terbaru dari satu sumber kebenaran"
            )
        )
        KodeBlok(
            """
            @Composable
            fun TaskDetailScreen(taskId: Int, onBack: () -> Unit) {
                val task = TaskRepository.getTask(taskId)   // cari berdasarkan ID
                // tampilkan tugas, atau "Task not found" jika null
            }
            """
        )

        Text("8.4 Argumen opsional dengan nilai default", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            @Serializable
            data class TaskList(val showDone: Boolean = true)

            navController.navigate(TaskList())                  // showDone = true
            navController.navigate(TaskList(showDone = false))  // nilai diganti
            NavHost(navController, startDestination = TaskList()) { }
            // Tipe nullable juga boleh: val query: String? = null
            """
        )

        Text("8.5 Membaca argumen di ViewModel", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            class TaskDetailViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {
                private val route = savedStateHandle.toRoute<TaskDetail>()
                val task: Task? = TaskRepository.getTask(route.taskId)
            }

            composable<TaskDetail> {
                val viewModel: TaskDetailViewModel = viewModel()
                TaskDetailScreen(task = viewModel.task, onBack = { navController.navigateUp() })
            }
            """
        )
        Text(
            "Pendekatan ViewModel belum dipakai praktik modul ini, tetapi menjadi pola utama ketika " +
                "screen memuat data dari repository yang sesungguhnya.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KirimDataPreview() {
    com.example.tasknav.ui.theme.TaskNavTheme { KirimDataScreen() }
}
