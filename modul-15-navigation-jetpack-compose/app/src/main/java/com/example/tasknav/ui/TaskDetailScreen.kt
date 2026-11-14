package com.example.tasknav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasknav.data.Task
import com.example.tasknav.data.TaskRepository
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar detail satu tugas (Bagian 9.3 modul).
 *
 * Layar menerima ID dan event Back, lalu mencari tugasnya sendiri di repository — ingat aturan
 * "argumen adalah alamat, bukan paket": ID yang dikirim, isinya diambil di tujuan.
 *
 * `task` boleh `null` (ID tidak dikenal) dan kasus itu ditampilkan sebagai pesan, bukan crash.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    taskId: Int,
    onBack: () -> Unit,
    onOpenCourse: (String) -> Unit = {}
) {
    val task = TaskRepository.getTask(taskId)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task #$taskId") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Back") }
                }
            )
        }
    ) { innerPadding ->
        TaskDetailContent(task, Modifier.padding(innerPadding), onOpenCourse)
    }
}

/** Isi layar dipisah agar fungsi utamanya tetap pendek dan mudah di-preview. */
@Composable
private fun TaskDetailContent(
    task: Task?,
    modifier: Modifier = Modifier,
    onOpenCourse: (String) -> Unit = {}
) {
    Column(
        modifier = modifier.padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (task == null) {
            Text("Task not found") // ID tidak dikenal
        } else {
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineSmall
            )
            Text("Course: ${task.course}")
            Text("Due: ${task.dueDate}")
            Text("Status: ${if (task.isDone) "done" else "open"}")
            // Latihan 4: buka daftar semua tugas pada mata kuliah yang sama.
            Button(onClick = { onOpenCourse(task.course) }) {
                Text("All tasks in ${task.course}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskDetailScreenPreview() {
    TaskNavTheme {
        TaskDetailScreen(taskId = 2, onBack = {})
    }
}

@Preview(showBackground = true, name = "Task not found")
@Composable
fun TaskDetailNotFoundPreview() {
    TaskNavTheme {
        TaskDetailScreen(taskId = 99, onBack = {})
    }
}
