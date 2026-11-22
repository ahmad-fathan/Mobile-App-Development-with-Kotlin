package com.example.coroutinedemo.ui.main

import com.example.coroutinedemo.data.Task

/**
 * Satu potret isi layar Coroutine Demo (Subbagian 7.6, Tabel 6).
 *
 * Immutable: setiap perubahan menghasilkan object baru lewat `copy()`. Status loading, data, dan
 * pesan error berada di kelas yang sama, sehingga layar cukup membaca satu aliran state dan tidak
 * perlu menulis try/catch sendiri.
 */
data class TasksUiState(
    val isLoading: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val announcement: String = "",
    val errorMessage: String? = null,
    val tapCount: Int = 0
)
