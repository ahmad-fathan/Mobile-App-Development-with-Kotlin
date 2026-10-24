package com.example.studytasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

/**
 * Daftar tugas (Bagian 7.12) — fokus pada tampilan.
 *
 * `key = { it.id }` memakai ID tugas yang stabil, sesuai rekomendasi pada pertemuan list dan grid.
 * Ketika pengguna menekan checkbox, baris tersebut HANYA melaporkan ID tugasnya. Checkbox tidak
 * berubah dengan sendirinya: nilai `checked` baru berubah ketika state yang sudah diperbarui
 * kembali dari ViewModel. Inilah siklus unidirectional data flow dalam skala terkecil.
 *
 * Perhatikan juga bahwa baris tidak menerima ViewModel, hanya data baris dan callback
 * (Tabel 9 baris 6).
 */
@Composable
fun TasksList(
    tasks: List<StudyTask>,
    onToggleTask: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (tasks.isEmpty()) {
        Text(
            text = "No tasks yet. Add your first task above.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = modifier.padding(16.dp)
        )
        return
    }

    LazyColumn(modifier = modifier) {
        items(tasks, key = { it.id }) { task ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = task.isDone,
                    onCheckedChange = { onToggleTask(task.id) }
                )
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (task.isDone) TextDecoration.LineThrough else null
                )
            }
        }
    }
}
