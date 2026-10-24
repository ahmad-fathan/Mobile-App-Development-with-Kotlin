package com.example.studytasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * Layar Study Tasks (Bagian 7.11) — Composable stateless.
 *
 * Bandingkan dengan `TaskScreenBefore` di `bab/bab-01-satu-composable-mengerjakan-semua.kt`
 * (versi sebelum refactoring, Bagian 2). Di sini tidak ada `remember`, aturan validasi, maupun
 * manipulasi list:
 *
 * - text field menampilkan [TasksUiState.draft] dan melaporkan ketikan lewat [onDraftChange];
 * - `isError` membuat field berwarna merah ketika ada pesan kesalahan;
 * - `errorMessage?.let { Text(it) }` menampilkan pesan hanya bila nilainya tidak null;
 * - `Modifier.weight(1f)` memberi sisa tinggi Column kepada daftar, syarat lazy list;
 * - layar ini tidak memiliki akses ke repository dan tidak memperoleh ViewModel. Ia hanya
 *   menerima nilai biasa dan callback, sehingga dapat di-preview dengan state apa pun.
 */
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
        Text(
            "Study tasks",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.semantics { heading() }
        )
        OutlinedTextField(
            value = uiState.draft,
            onValueChange = onDraftChange,
            label = { Text("Task title") },
            isError = uiState.errorMessage != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }
        Button(onClick = onAddTask) { Text("Add task") }
        TasksList(
            tasks = uiState.tasks,
            onToggleTask = onToggleTask,
            modifier = Modifier.weight(1f)
        )
    }
}
