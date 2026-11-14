package com.example.tasknav.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
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
import com.example.tasknav.data.Task
import com.example.tasknav.data.TaskRepository
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar daftar tugas (Bagian 9.2 modul).
 *
 * Tidak ada satu baris pun yang berhubungan dengan NavController: layar hanya menerima data dan
 * melaporkan ketukan lewat `onTaskClick(id)`. Karena itu layar ini dapat ditampilkan di Preview
 * dan diuji tanpa navigasi.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    tasks: List<Task>,
    onTaskClick: (Int) -> Unit,
    onAboutClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                // Latihan 3: tombol About di top app bar layar daftar.
                actions = {
                    TextButton(onClick = onAboutClick) { Text("About") }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(contentPadding = innerPadding) {
            items(tasks, key = { it.id }) { task ->
                ListItem(
                    headlineContent = { Text(task.title) },
                    supportingContent = { Text("${task.course}, due ${task.dueDate}") },
                    trailingContent = { Text(if (task.isDone) "done" else "open") },
                    modifier = Modifier.clickable { onTaskClick(task.id) }
                )
                HorizontalDivider()
            }
        }
    }
}

/** Preview memakai data repository langsung — tanpa NavController, tanpa ViewModel. */
@Preview(showBackground = true)
@Composable
fun TaskListScreenPreview() {
    TaskNavTheme {
        TaskListScreen(tasks = TaskRepository.getTasks(), onTaskClick = {})
    }
}
