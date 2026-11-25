package com.example.todoapp.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.ALAMAT_TODO_1
import com.example.todoapp.ALAMAT_TODOS
import com.example.todoapp.JudulBab
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel
import com.example.todoapp.gabungAlamat
import com.example.todoapp.kelompokStatus

/**
 * Bab 2 — Client, Server, dan HTTP (berkas 02 = Bagian 3)
 *
 * Bagian modul: "Client, Server, dan HTTP", "Endpoint: Alamat Data", "HTTP Method",
 * "Status Code" (Tabel 1, 2, 3).
 */

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val tabelEndpoint = listOf(
    "/todos" to "semua task (200 data)",
    "/todos/1" to "task dengan id 1",
    "/users" to "semua pengguna"
)

private val tabelMethod = listOf(
    "GET" to "Membaca data — GET /todos",
    "POST" to "Membuat data baru — POST /todos",
    "PUT" to "Mengubah data yang sudah ada — PUT /todos/1",
    "DELETE" to "Menghapus data — DELETE /todos/1"
)

private val kodeStatus = listOf(200, 201, 404, 500)

@Composable
fun ClientServerHttpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 2 — Client, server, dan HTTP",
            keterangan = "Client mengirim request, server membalas response. Satu request menghasilkan satu response."
        )

        KodeBlok(
            kode = """
            Client (aplikasi Android)  --request-->  Server
            Client (aplikasi Android)  <--response-- Server
            """
        )

        Tabel("Tabel 1 — Endpoint JSONPlaceholder", tabelEndpoint)

        Text(
            text = "Base URL + endpoint = ${gabungAlamat(BASE_URL, "todos")}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Sama dengan alamat modul: ${ALAMAT_TODOS == gabungAlamat(BASE_URL, "todos")} · ${ALAMAT_TODO_1}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Tabel("Tabel 2 — HTTP method yang umum dipakai", tabelMethod)

        Tabel(
            "Tabel 3 — Status code dan kelompoknya",
            kodeStatus.map { "$it" to kelompokStatus(it) }
        )
    }
}
