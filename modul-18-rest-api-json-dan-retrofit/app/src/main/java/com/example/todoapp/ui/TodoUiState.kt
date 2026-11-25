package com.example.todoapp.ui

import com.example.todoapp.api.Todo

/**
 * Bagian 7.1 — tiga kemungkinan yang harus siap digambar layar.
 *
 * `sealed interface` membuat compiler tahu state hanya ada tiga jenis. Kalau satu cabang
 * `when` terlewat, kode tidak mau dikompilasi.
 */
sealed interface TodoUiState {
    data object Loading : TodoUiState
    data class Success(val todos: List<Todo>) : TodoUiState
    data class Error(val message: String) : TodoUiState
}
