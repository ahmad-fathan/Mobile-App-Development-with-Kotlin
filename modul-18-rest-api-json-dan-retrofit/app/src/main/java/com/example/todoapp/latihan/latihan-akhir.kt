package com.example.todoapp.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapp.JudulBab
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel
import com.example.todoapp.api.JSON_SATU_POST
import com.example.todoapp.api.Todo
import com.example.todoapp.api.bacaSatuPost
import com.example.todoapp.pesanKegagalan
import com.example.todoapp.ringkasanSelesai
import com.example.todoapp.ui.TodoDetailViewModel
import com.example.todoapp.ui.TodoUiState

/**
 * Latihan akhir modul: lima soal (Bagian 9).
 *
 * Soal 1, 2, 3, dan 4 dijalankan sungguhan di layar ini: JSON `/posts/1` dibaca Gson, endpoint
 * `/todos/{id}` dipanggil ke server, dan ringkasan task dihitung dari properti `completed`.
 * Soal 5 berupa jawaban konsep.
 */

private val jawabanSoal1 = listOf(
    "(a) melihat semua pengguna" to "GET /users",
    "(b) pengguna id 3" to "GET /users/3 — diharapkan 200; id 9999 → 404",
    "(c) menghapus task id 10" to "DELETE /todos/10",
    "(d) menambah task baru" to "POST /todos — diharapkan 201"
)

private val contohTigaTask = listOf(
    Todo(1, 1, "Read chapter 3", false),
    Todo(1, 2, "Submit lab report", true),
    Todo(1, 3, "Prepare quiz", false)
)

private val jawabanSoal5 = listOf(
    "IOException" to "Request tidak pernah sampai ke server. Contoh: HP dalam mode pesawat, " +
        "Wi-Fi mati, atau domain server tidak dapat dihubungi.",
    "HttpException" to "Server menjawab, tetapi status code-nya gagal (4xx/5xx). Contoh: " +
        "endpoint /todoss tidak ada → 404; server sedang down → 500.",
    "Cara membedakan" to "IOException = tidak ada jawaban sama sekali; HttpException = ada " +
        "jawaban, dan jawaban itu menyebut kode kegagalan."
)

@Composable
fun LatihanScreen(modifier: Modifier = Modifier) {
    var idDiminta by remember { mutableStateOf(5) }
    val post = remember { bacaSatuPost() }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Latihan akhir — lima soal Bagian 9",
            keterangan = "Soal 1–4 dijalankan di layar ini; soal 5 berupa jawaban singkat."
        )

        Tabel("Soal 1 — method dan endpoint JSONPlaceholder", jawabanSoal1)

        Tabel(
            "Soal 2 — data class Post hasil membaca /posts/1",
            listOf(
                "userId" to "${post.userId}",
                "id" to "${post.id}",
                "title" to post.title,
                "body" to post.body
            )
        )
        KodeBlok(kode = JSON_SATU_POST.trim())
        KodeBlok(
            kode = """
            interface PostApiService {
                @GET("posts")
                suspend fun getPosts(): List<Post>
            }
            """
        )

        Text(
            text = "Soal 3 — ambil satu task dari server. Bandingkan id 5 dengan id 999:",
            style = MaterialTheme.typography.bodyMedium
        )
        Button(onClick = { idDiminta = 5 }) { Text("Muat task id 5") }
        Button(onClick = { idDiminta = 999 }) { Text("Muat task id 999") }
        HasilSatuTask(id = idDiminta)

        Tabel(
            "Soal 4 — ringkasan selesai",
            listOf(
                "Contoh 3 task, 1 selesai" to ringkasanSelesai(contohTigaTask)
            )
        )
        Text(
            text = "TodoList() memakai fungsi yang sama, sehingga teks di atas daftar selalu " +
                "dihitung dari properti completed.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Tabel("Soal 5 — IOException vs HttpException", jawabanSoal5)
    }
}

/** Menampilkan state hasil `getTodo(id)` — dipakai soal 3. */
@Composable
private fun HasilSatuTask(id: Int, modifier: Modifier = Modifier) {
    val tampilan: TodoDetailViewModel = viewModel(key = "detail-$id")
    val state by tampilan.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(id) { tampilan.muat(id) }

    val teks = when (val s = state) {
        TodoUiState.Loading -> "Loading …"
        is TodoUiState.Success -> s.todos.firstOrNull()?.let { "id ${it.id}: ${it.title}" } ?: "kosong"
        is TodoUiState.Error -> "Error: ${s.message}"
    }
    Text(text = "Hasil getTodo($id) → $teks", style = MaterialTheme.typography.bodyMedium, modifier = modifier)
    Text(
        text = "Pesan untuk 404 (id 999): " + pesanKegagalan(com.example.todoapp.galatHttp(404)),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
