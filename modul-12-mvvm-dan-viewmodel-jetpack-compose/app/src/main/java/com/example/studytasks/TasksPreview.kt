package com.example.studytasks

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/**
 * Preview tanpa ViewModel (Bagian 9).
 *
 * Karena [TasksScreen] menerima nilai biasa, layar ini dapat ditampilkan dengan state apa pun
 * yang kita inginkan — tanpa membuat repository ataupun ViewModel. Lambda kosong `{}` berfungsi
 * sebagai callback pengganti: preview tidak dapat merespons klik, sehingga hanya memerlukan
 * fungsi dengan tipe yang sesuai.
 *
 * Bandingkan dengan `TaskScreenBefore`: layar itu hanya dapat menampilkan state yang dibangunnya
 * sendiri. Inilah manfaat praktis dari refactoring.
 */
@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    MaterialTheme {
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
}

/**
 * Preview kedua: state dengan pesan validasi, yaitu state yang biasanya hanya muncul jika tombol
 * ditekan dengan field kosong.
 */
@Preview(showBackground = true)
@Composable
fun TasksScreenErrorPreview() {
    MaterialTheme {
        TasksScreen(
            uiState = TasksUiState(errorMessage = "Enter a task title."),
            onDraftChange = {},
            onAddTask = {},
            onToggleTask = {}
        )
    }
}

/** Preview keadaan kosong: belum ada tugas sama sekali. */
@Preview(showBackground = true)
@Composable
fun TasksScreenEmptyPreview() {
    MaterialTheme {
        TasksScreen(
            uiState = TasksUiState(),
            onDraftChange = {},
            onAddTask = {},
            onToggleTask = {}
        )
    }
}
