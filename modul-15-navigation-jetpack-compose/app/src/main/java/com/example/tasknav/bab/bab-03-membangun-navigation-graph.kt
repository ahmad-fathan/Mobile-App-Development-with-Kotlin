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
import com.example.tasknav.caraKembali

/**
 * Bab 3 — Membangun Navigation Graph
 *
 * Bagian modul: 7 (7.1 Mendefinisikan Route, 7.2 NavController dan NavHost,
 * 7.3 Navigasi Dipicu oleh Event, 7.4 Kembali ke Layar Sebelumnya).
 *
 * Empat langkah: definisikan route, buat NavController, susun NavHost, picu dari event.
 * Setiap cuplikan di bab ini juga berupa kode yang benar-benar dipakai aplikasi
 * (`navigation/Routes.kt`, `navigation/AppNavHost.kt`, `ui/TaskListScreen.kt`).
 */

@Composable
fun MembangunGraphScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Membangun navigation graph",
            keterangan = "Route → NavController → NavHost → event"
        )

        Text("Langkah 1 — definisikan route", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            package com.example.tasknav.navigation

            import kotlinx.serialization.Serializable

            @Serializable
            object TaskList                 // destination tanpa argumen

            @Serializable
            data class TaskDetail(val taskId: Int)   // tiap properti = satu argumen
            """
        )
        Text(
            "Kesalahan paling sering: lupa @Serializable. Gejalanya, kompilasi berhasil tetapi " +
                "aplikasi crash saat NavHost pertama kali dibangun.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Langkah 2 dan 3 — NavController dan NavHost", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            @Composable
            fun AppNavHost(navController: NavHostController = rememberNavController()) {
                NavHost(navController = navController, startDestination = TaskList) {
                    composable<TaskList> {
                        TaskListScreen(
                            tasks = TaskRepository.getTasks(),
                            onTaskClick = { id -> navController.navigate(TaskDetail(taskId = id)) }
                        )
                    }
                    composable<TaskDetail> { backStackEntry ->
                        val route: TaskDetail = backStackEntry.toRoute()
                        TaskDetailScreen(
                            taskId = route.taskId,
                            onBack = { navController.navigateUp() }
                        )
                    }
                }
            }
            """
        )
        Tabel(
            "Empat elemen dan perannya",
            listOf(
                "rememberNavController()" to "membuat dan mengingat NavController",
                "startDestination" to "menentukan entri paling bawah pada back stack",
                "composable<TaskDetail> { }" to "mendaftarkan satu destination ke graph",
                "toRoute()" to "membaca argumen dari entri back stack sebagai objek bertipe"
            )
        )

        Text("Langkah 4 — navigasi dipicu oleh event", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            // Screen hanya menerima lambda, tidak menerima NavController
            @Composable
            fun TaskListScreen(tasks: List<Task>, onTaskClick: (Int) -> Unit) {
                ListItem(modifier = Modifier.clickable { onTaskClick(task.id) })
            }

            // Pemegang NavController yang menentukan arti event
            onTaskClick = { id -> navController.navigate(TaskDetail(taskId = id)) }
            """
        )
        Tabel(
            "Tiga keuntungan pola ini",
            listOf(
                "Preview" to "screen dapat ditampilkan tanpa NavController",
                "Uji" to "cukup periksa apakah lambda dipanggil dengan nilai yang benar",
                "Daur ulang" to "screen yang sama dapat dipakai di alur navigasi lain"
            )
        )
        Text(
            "navigate() hanya boleh dipanggil di dalam callback event. Bila dipanggil di badan " +
                "composable, ia berjalan pada setiap recomposition dan layar terus ditumpuk.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error
        )

        Text("7.4 Kembali ke layar sebelumnya", style = MaterialTheme.typography.titleSmall)
        Tabel("Tabel 4 — cara kembali", caraKembali.map { it.aksi to "${it.kode} — ${it.efek}" })
        Text(
            "Gestur Back sistem sudah bekerja tanpa kode. Tombol Up di app bar dihubungkan manual: " +
                "screen menerima lambda onBack, NavHost memanggil navigateUp().",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MembangunGraphPreview() {
    com.example.tasknav.ui.theme.TaskNavTheme { MembangunGraphScreen() }
}
