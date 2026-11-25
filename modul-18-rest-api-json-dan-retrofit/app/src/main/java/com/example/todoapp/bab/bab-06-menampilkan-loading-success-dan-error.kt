package com.example.todoapp.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapp.JudulBab
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel
import com.example.todoapp.api.RetrofitClient
import com.example.todoapp.api.TodoApiPalsu
import com.example.todoapp.ui.ErrorView
import com.example.todoapp.ui.LoadingView
import com.example.todoapp.ui.TodoList
import com.example.todoapp.ui.TodoUiState
import com.example.todoapp.ui.TodoViewModel
import com.example.todoapp.ui.TodoScreen

/**
 * Bab 6 — Menampilkan Loading, Success, dan Error (berkas 06 = Bagian 7)
 *
 * Bagian modul: "Tiga State UI", "ViewModel: Memanggil API di Dalam Coroutine",
 * "Screen: Menggambar Setiap State", "Menghubungkan ke MainActivity" (Tabel 6).
 *
 * Layar ini memakai `TodoApiPalsu`, sehingga ketiga state dapat dicoba tanpa internet.
 * Aplikasi sebenarnya memakai `TodoScreen()` di `MainActivity`, bukan layar ini.
 */

private val tabelSkenario = listOf(
    "1" to "Jalankan seperti biasa → Loading sebentar, lalu Success: daftar 200 task.",
    "2" to "Aktifkan mode pesawat, tekan Retry → Error: \"No internet connection\".",
    "3" to "Ubah @GET(\"todos\") menjadi @GET(\"todoss\") → Error: \"Server error: 404\"."
)

@Composable
fun TigaStateScreen(modifier: Modifier = Modifier) {
    var hasil by remember { mutableStateOf(TodoApiPalsu.Hasil.Berhasil) }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 6 — Loading, success, dan error",
            keterangan = "Ambil data dari internet bisa gagal, jadi layar harus siap menghadapi tiga kemungkinan."
        )

        KodeBlok(
            kode = """
            sealed interface TodoUiState {
                data object Loading : TodoUiState
                data class Success(val todos: List<Todo>) : TodoUiState
                data class Error(val message: String) : TodoUiState
            }
            """
        )

        KodeBlok(
            kode = """
            fun loadTodos() {
                _uiState.value = TodoUiState.Loading
                viewModelScope.launch {
                    try {
                        val todos = RetrofitClient.api.getTodos()
                        _uiState.value = TodoUiState.Success(todos)
                    } catch (e: IOException) {
                        _uiState.value = TodoUiState.Error("No internet connection")
                    } catch (e: HttpException) {
                        _uiState.value = TodoUiState.Error("Server error: " + e.code())
                    }
                }
            }
            """
        )

        Text(
            text = "Coba ketiga state tanpa internet — pilih sumber data palsu di bawah:",
            style = MaterialTheme.typography.bodyMedium
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            TodoApiPalsu.Hasil.entries.forEach { pilihan ->
                FilterChip(
                    selected = hasil == pilihan,
                    onClick = { hasil = pilihan },
                    label = { Text(pilihan.name) }
                )
            }
        }

        // Satu ViewModel per pilihan: key berubah → ViewModel dibuat baru → init memuat ulang.
        val pabrik = remember(hasil) {
            viewModelFactory {
                initializer { TodoViewModel(TodoApiPalsu(hasil)) }
            }
        }
        val tampilan: TodoViewModel = viewModel(key = hasil.name, factory = pabrik)
        val state by tampilan.uiState.collectAsStateWithLifecycle()

        Card(modifier = Modifier.fillMaxWidth().height(260.dp)) {
            when (val s = state) {
                TodoUiState.Loading -> LoadingView()
                is TodoUiState.Success -> TodoList(s.todos)
                is TodoUiState.Error -> ErrorView(s.message, onRetry = tampilan::loadTodos)
            }
        }

        Tabel("Tabel 7 — Skenario pengujian aplikasi", tabelSkenario)

        Text(
            text = "Aplikasi sebenarnya memakai TodoScreen() yang memanggil RetrofitClient.api " +
                "(base URL " + RetrofitClient.BASE_URL + ").",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Layar aplikasi yang sesungguhnya: TodoScreen() dengan Retrofit dan jaringan sungguhan. */
@Composable
fun TodoAppScreen(modifier: Modifier = Modifier) {
    TodoScreen(modifier = modifier)
}
