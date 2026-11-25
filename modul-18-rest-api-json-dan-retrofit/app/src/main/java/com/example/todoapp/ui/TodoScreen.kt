package com.example.todoapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.api.Todo
import com.example.todoapp.ringkasanSelesai

/**
 * Bagian 7.3 — layar hanya membaca state terbaru lalu menggambarnya.
 *
 * `collectAsStateWithLifecycle()` menggambar ulang setiap kali state berubah, dan `when`
 * memilih satu tampilan untuk setiap state. Karena `TodoUiState` sealed, ketiga cabang wajib
 * ada — kalau satu terhapus, compiler menolak.
 */
@Composable
fun TodoScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = modifier.fillMaxSize()) {
        when (val s = state) {
            TodoUiState.Loading -> LoadingView()
            is TodoUiState.Success -> TodoList(s.todos)
            is TodoUiState.Error -> ErrorView(s.message, onRetry = viewModel::loadTodos)
        }
    }
}

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

/**
 * Daftar task. Latihan 4 menambahkan teks ringkasan di atas daftar; jumlah yang selesai
 * dihitung dari properti `completed`, bukan ditulis tetap.
 */
@Composable
fun TodoList(todos: List<Todo>, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = ringkasanSelesai(todos),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyColumn(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            items(todos) { todo ->
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Checkbox(checked = todo.completed, onCheckedChange = null)
                    Text(text = todo.title, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

@Composable
fun ErrorView(message: String, onRetry: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message)
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
