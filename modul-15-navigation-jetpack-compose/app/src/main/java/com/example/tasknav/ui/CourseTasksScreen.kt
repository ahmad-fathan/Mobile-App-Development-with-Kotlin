package com.example.tasknav.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tasknav.data.TaskRepository
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar daftar tugas satu mata kuliah (Latihan 4 modul).
 *
 * Route-nya `CourseTasks(val course: String)`: satu argumen String, sesuai aturan "kirim nilai
 * kecil". Judul memuat nama mata kuliah dan jumlah tugas, mis. "Databases (1 task)".
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseTasksScreen(
    course: String,
    onTaskClick: (Int) -> Unit,
    onBack: () -> Unit
) {
    val tasks = TaskRepository.getTasksByCourse(course)
    val satuan = if (tasks.size == 1) "task" else "tasks"
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$course (${tasks.size} $satuan)") },
                navigationIcon = { TextButton(onClick = onBack) { Text("Back") } }
            )
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(tasks, key = { it.id }) { task ->
                ListItem(
                    headlineContent = { Text(task.title) },
                    supportingContent = { Text("due ${task.dueDate}") },
                    trailingContent = { Text(if (task.isDone) "done" else "open") },
                    modifier = Modifier.clickable { onTaskClick(task.id) }
                )
                HorizontalDivider()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseTasksScreenPreview() {
    TaskNavTheme { CourseTasksScreen(course = "Databases", onTaskClick = {}, onBack = {}) }
}
