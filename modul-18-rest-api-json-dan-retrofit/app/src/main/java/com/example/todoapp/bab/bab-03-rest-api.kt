package com.example.todoapp.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.JudulBab
import com.example.todoapp.Tabel

/**
 * Bab 3 — REST API (berkas 03 = Bagian 4)
 *
 * Bagian modul: "REST API" (Tabel 4).
 */

private val tabelRest = listOf(
    "GET /todos" to "Berikan semua task.",
    "GET /todos/1" to "Berikan task nomor 1.",
    "DELETE /todos/1" to "Hapus task nomor 1."
)

@Composable
fun RestApiScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 3 — REST API",
            keterangan = "URL adalah kata benda (apa datanya), method adalah kata kerja (mau diapakan)."
        )

        Tabel("Tabel 4 — Membaca request REST seperti kalimat", tabelRest)

        Tabel(
            "Ciri khas REST",
            listOf(
                "Endpoint tanpa kata kerja" to "Bukan getAllTodos atau deleteTodo; kata kerja sudah diwakili method.",
                "Resource di URL" to "Data disajikan sebagai resource, mis. /todos dan /users.",
                "Method menentukan tindakan" to "GET membaca, POST menambah, PUT mengubah, DELETE menghapus."
            )
        )
    }
}
