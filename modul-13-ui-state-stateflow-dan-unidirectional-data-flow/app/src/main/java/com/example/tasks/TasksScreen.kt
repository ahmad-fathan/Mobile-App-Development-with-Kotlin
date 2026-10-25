package com.example.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Route — satu-satunya tempat ViewModel dan Compose bertemu.
 *
 * Tugasnya tiga: memperoleh ViewModel, mengumpulkan state, lalu meneruskan nilai + callback ke
 * [TasksScreen]. Karena `collectAsStateWithLifecycle()` berhenti mengumpulkan di bawah STARTED,
 * layar tidak menerima pembaruan saat aplikasi di latar belakang; ketika layar terlihat lagi,
 * StateFlow memberi nilai TERKINI.
 */
@Composable
fun TasksRoute(viewModel: TasksViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState,
        onDraftTitleChanged = viewModel::onDraftTitleChanged,
        onAddTask = viewModel::onAddTask,
        onTaskCompletionChanged = viewModel::onTaskCompletionChanged,
        onIncompleteFilterChanged = viewModel::onIncompleteFilterChanged,
        onDeleteTask = viewModel::onDeleteTask,
        onClearCompleted = viewModel::onClearCompleted
    )
}

/**
 * Layar stateless: menerima state dan melaporkan aksi. Tidak ada `remember`, tidak ada aturan
 * validasi, dan tidak ada ViewModel di sini — sehingga layar dapat dipreview dengan state apa pun.
 *
 * Perhatikan bahwa `draftTitle` diambil dari `uiState`, bukan dari `remember` kedua di layar:
 * dua salinan draf akan membuat layar dan ViewModel dapat berbeda pendapat.
 */
@Composable
fun TasksScreen(
    uiState: TasksUiState,
    onDraftTitleChanged: (String) -> Unit,
    onAddTask: () -> Unit,
    onTaskCompletionChanged: (String, Boolean) -> Unit,
    onIncompleteFilterChanged: (Boolean) -> Unit,
    onDeleteTask: (String) -> Unit,
    onClearCompleted: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("My tasks", style = MaterialTheme.typography.headlineSmall)
        Text(uiState.summaryText, style = MaterialTheme.typography.bodyMedium)

        OutlinedTextField(
            value = uiState.draftTitle,
            onValueChange = onDraftTitleChanged,
            label = { Text("Task title") },
            isError = uiState.inputError != null,
            modifier = Modifier.fillMaxWidth()
        )
        uiState.inputError?.let { pesan ->
            Text(
                text = pesan,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
        Button(onClick = onAddTask) { Text("Add task") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = uiState.showIncompleteOnly,
                    role = Role.Checkbox,
                    onValueChange = onIncompleteFilterChanged
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // onCheckedChange = null: baris induk yang menangani klik, tombol tidak memanggil dua kali.
            Checkbox(checked = uiState.showIncompleteOnly, onCheckedChange = null)
            Text("Show incomplete only", modifier = Modifier.padding(start = 8.dp))
        }

        TextButton(onClick = onClearCompleted) { Text("Clear completed") }

        TasksList(
            uiState = uiState,
            onTaskCompletionChanged = onTaskCompletionChanged,
            onDeleteTask = onDeleteTask,
            modifier = Modifier.weight(1f)
        )
    }
}

/**
 * Daftar tugas. `key = { it.id }` memakai identitas yang stabil, sehingga Compose tetap melacak
 * baris yang sama saat daftar terlihat berubah karena filter atau penghapusan.
 */
@Composable
fun TasksList(
    uiState: TasksUiState,
    onTaskCompletionChanged: (String, Boolean) -> Unit,
    onDeleteTask: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        if (uiState.showsEmptyMessage) {
            item(key = "empty-state") {
                Text(
                    text = if (uiState.tasks.isEmpty()) PESAN_BELUM_ADA_TUGAS else PESAN_SEMUA_SELESAI,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        items(uiState.visibleTasks, key = { it.id }) { task ->
            Row(
                modifier = Modifier.fillMaxWidth().clickable { onTaskCompletionChanged(task.id, !task.isCompleted) },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = { checked -> onTaskCompletionChanged(task.id, checked) }
                )
                Text(
                    text = task.title,
                    modifier = Modifier.weight(1f),
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null
                )
                TextButton(onClick = { onDeleteTask(task.id) }) { Text("Delete") }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Preview — layar tidak membutuhkan ViewModel, jadi setiap state dapat digambar
// ---------------------------------------------------------------------------

private val contohTugas = listOf(
    Task("1", "Review StateFlow", isCompleted = true),
    Task("2", "Implement UDF"),
    Task("3", "Type the draft", isCompleted = false)
)

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    MaterialTheme {
        TasksScreen(
            uiState = TasksUiState(
                tasks = contohTugas,
                draftTitle = "Read chapter 2"
            ),
            onDraftTitleChanged = {},
            onAddTask = {},
            onTaskCompletionChanged = { _, _ -> },
            onIncompleteFilterChanged = {},
            onDeleteTask = {},
            onClearCompleted = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenErrorPreview() {
    MaterialTheme {
        TasksScreen(
            uiState = TasksUiState(inputError = PESAN_JUDUL_KOSONG),
            onDraftTitleChanged = {},
            onAddTask = {},
            onTaskCompletionChanged = { _, _ -> },
            onIncompleteFilterChanged = {},
            onDeleteTask = {},
            onClearCompleted = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenFilteredPreview() {
    MaterialTheme {
        TasksScreen(
            uiState = TasksUiState(
                tasks = listOf(
                    Task("1", "Review StateFlow", isCompleted = true),
                    Task("2", "Implement UDF", isCompleted = true)
                ),
                showIncompleteOnly = true
            ),
            onDraftTitleChanged = {},
            onAddTask = {},
            onTaskCompletionChanged = { _, _ -> },
            onIncompleteFilterChanged = {},
            onDeleteTask = {},
            onClearCompleted = {}
        )
    }
}
