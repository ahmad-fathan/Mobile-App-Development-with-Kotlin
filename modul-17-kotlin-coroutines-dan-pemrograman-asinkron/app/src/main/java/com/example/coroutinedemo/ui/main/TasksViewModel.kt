package com.example.coroutinedemo.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinedemo.data.TaskRepository
import com.example.coroutinedemo.dibatalkan
import com.example.coroutinedemo.gagalMemuat
import com.example.coroutinedemo.mulaiMemuat
import com.example.coroutinedemo.pesanGagalMemuat
import com.example.coroutinedemo.selesaiMemuat
import com.example.coroutinedemo.setelahKetuk
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

/**
 * ViewModel aplikasi Coroutine Demo (Subbagian 7.7). Di sinilah semua konsep modul bertemu:
 * `viewModelScope`, `launch` + Job, `async` di dalam `coroutineScope`, dan `try/catch` yang
 * mengubah kegagalan menjadi pesan di UI state.
 */
class TasksViewModel(
    private val repository: TaskRepository = TaskRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(TasksUiState())
    val uiState: StateFlow<TasksUiState> = _uiState.asStateFlow()

    /** Job pemuatan terakhir, dipakai tombol Cancel dan untuk membatalkan pemuatan sebelumnya. */
    private var loadJob: Job? = null

    fun loadData(shouldFail: Boolean = false) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _uiState.update { it.mulaiMemuat() }
            try {
                coroutineScope {
                    val tasks = async { repository.getTasks(shouldFail) }
                    val announcement = async { repository.getAnnouncement() }
                    val loadedTasks = tasks.await()
                    val loadedAnnouncement = announcement.await()
                    _uiState.update { it.selesaiMemuat(loadedTasks, loadedAnnouncement) }
                }
            } catch (e: IOException) {
                // coroutineScope menampung kegagalan child, jadi IOException sampai ke sini
                // sebagai exception biasa. CancellationException sengaja TIDAK ditangkap.
                _uiState.update { it.gagalMemuat(pesanGagalMemuat(e)) }
            }
        }
    }

    fun cancelLoading() {
        loadJob?.cancel()
        _uiState.update { it.dibatalkan() }
    }

    fun onTap() {
        _uiState.update { it.setelahKetuk() }
    }

    /** Contoh buruk yang sengaja: memblokir main thread selama 3 detik (Subbagian 7.1). */
    fun blockMainThread() {
        Thread.sleep(3000L)
    }
}
