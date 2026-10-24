package com.example.studytasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * Route (Bagian 7.10): satu-satunya tempat yang memperoleh ViewModel.
 *
 * Tugasnya hanya tiga: meminta ViewModel kepada provider yang sadar lifecycle, mengumpulkan
 * state-nya menjadi state Compose, lalu meneruskan nilai dan callback ke layar.
 *
 * - `viewModel(factory = TasksViewModel.Factory)` mengembalikan instance yang sudah ada bila
 *   tersedia, dan baru memakai factory bila belum ada;
 * - `collectAsStateWithLifecycle()` mengumpulkan StateFlow selama lifecycle induk minimal
 *   STARTED, dan menjeda pengumpulan di bawah state tersebut (mis. saat aplikasi di latar
 *   belakang). Producer di dalam ViewModel TIDAK ikut berhenti;
 * - `viewModel::addTask` adalah function reference, setara `{ viewModel.addTask() }`.
 */
@Composable
fun TasksRoute(
    viewModel: TasksViewModel = viewModel(
        factory = TasksViewModel.Factory
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState,
        onDraftChange = viewModel::onDraftChange,
        onAddTask = viewModel::addTask,
        onToggleTask = viewModel::toggleTask
    )
}
