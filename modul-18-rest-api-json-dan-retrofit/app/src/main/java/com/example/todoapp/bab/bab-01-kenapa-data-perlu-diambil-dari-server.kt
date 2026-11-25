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
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel

/**
 * Bab 1 — Kenapa Data Perlu Diambil dari Server? (berkas 01 = Bagian 2)
 *
 * Bagian modul: "Kenapa Data Perlu Diambil dari Server?" (Bagian 2).
 */

private val tabelMasalah = listOf(
    "Data ditulis di dalam kode" to "Setiap pemasangan aplikasi memakai daftar yang sama persis.",
    "Tidak pernah berubah" to "Task baru di HP lain tidak pernah muncul di HP Anda.",
    "Mengubah data = mengedit kode" to "Satu-satunya cara memperbarui data adalah memasang ulang aplikasi.",
    "Solusinya: server" to "Data disimpan di satu tempat bersama; semua HP mengambilnya dari sana."
)

@Composable
fun KenapaDataServerScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 1 — Kenapa data perlu diambil dari server?",
            keterangan = "Data yang ditulis di dalam kode tidak pernah sama antar pengguna dan tidak pernah berubah."
        )

        KodeBlok(
            kode = """
            val todos = listOf(
                Todo(1, 1, "Read chapter 3", false),
                Todo(1, 2, "Submit lab report", true)
            )
            """
        )

        Tabel("Masalah data yang ditulis di dalam kode", tabelMasalah)
    }
}
