package com.example.todoapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.api.RetrofitClient
import com.example.todoapp.api.TodoApiService
import com.example.todoapp.pesanKegagalan
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Bagian 7.2 — pemanggilan API di dalam ViewModel, memakai pola MVVM modul 12–13.
 *
 * Urutan kerja `loadTodos()`:
 * 1. state → Loading, sehingga indikator loading langsung tampil;
 * 2. `viewModelScope.launch` membuka coroutine yang otomatis dibatalkan saat ViewModel dihapus;
 * 3. panggilan `suspend` berhenti sebentar di baris API tanpa membekukan layar;
 * 4. berhasil → Success, gagal → Error.
 *
 * Tidak ada `withContext(Dispatchers.IO)`: fungsi suspend Retrofit sudah main-safe.
 */
class TodoViewModel(
    private val api: TodoApiService = RetrofitClient.api
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    init {
        loadTodos()
    }

    fun loadTodos() {
        _uiState.value = TodoUiState.Loading
        viewModelScope.launch {
            try {
                val todos = api.getTodos()
                _uiState.value = TodoUiState.Success(todos)
            } catch (e: Exception) {
                _uiState.value = TodoUiState.Error(pesanKegagalan(e))
            }
        }
    }
}

/**
 * Bagian per latihan 3 — mengambil **satu** task berdasarkan id.
 *
 * Endpoint `/todos/{id}` mengirim satu object, jadi fungsi interface-nya mengembalikan `Todo`.
 * Id yang tidak ada (mis. 999) membuat server menjawab 404 → `HttpException`.
 */
class TodoDetailViewModel(
    private val api: TodoApiService = RetrofitClient.api
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState.asStateFlow()

    fun muat(id: Int = 5) {
        _uiState.value = TodoUiState.Loading
        viewModelScope.launch {
            try {
                // Satu object tetap dibungkus List agar bentuk state-nya sama dengan daftar.
                _uiState.value = TodoUiState.Success(listOf(api.getTodo(id)))
            } catch (e: Exception) {
                _uiState.value = TodoUiState.Error(pesanKegagalan(e))
            }
        }
    }
}
