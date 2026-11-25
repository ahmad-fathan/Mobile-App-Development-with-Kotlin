package com.example.todoapp.bab

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.ALAMAT_TODO_1
import com.example.todoapp.ALAMAT_TODO_999
import com.example.todoapp.JudulBab
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel
import com.example.todoapp.api.JSON_DUA_TODO
import com.example.todoapp.api.JSON_SATU_TODO
import com.example.todoapp.api.bacaDaftarTodo
import com.example.todoapp.api.bacaSatuTodo
import com.example.todoapp.kelompokStatus
import com.example.todoapp.statusDiharapkan

/**
 * Bab 4 — JSON (berkas 04 = Bagian 5)
 *
 * Bagian modul: "JSON Object", "JSON Array", "Mengamati Respons API Secara Langsung",
 * "Memetakan JSON ke Data Class Kotlin" (Tabel 5).
 *
 * JSON di layar ini benar-benar dibaca dengan Gson lalu ditampilkan hasilnya; angka
 * `bacaSatuTodo()` dan `bacaDaftarTodo()` di bawah berasal dari hasil parsing, bukan teks tetap.
 */

private val tabelTipe = listOf(
    "Angka bulat — 1" to "Int",
    "Teks — \"delectus aut autem\"" to "String",
    "Boolean — false" to "Boolean",
    "Array berisi object — [ {...}, {...} ]" to "List<Todo>"
)

private val langkahAmati = listOf(
    "1" to "Buka browser, kunjungi $ALAMAT_TODO_1, lalu baca setiap key dan jenis value-nya.",
    "2" to "Tekan F12 → tab Network → muat ulang → klik request 1 dan cari status code-nya.",
    "3" to "Ganti alamat menjadi $ALAMAT_TODO_999 lalu ulangi langkah 2."
)

@Composable
fun JsonScreen(modifier: Modifier = Modifier) {
    val satu = remember { bacaSatuTodo() }
    val daftar = remember { bacaDaftarTodo() }
    var terlihat by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 4 — JSON",
            keterangan = "JSON adalah teks biasa: { } berarti satu task, [ ] berarti banyak task."
        )

        Tabel(
            "Bagian 5.1 — JSON object",
            listOf(
                "userId" to "${satu.userId}",
                "id" to "${satu.id}",
                "title" to satu.title,
                "completed" to "${satu.completed}"
            )
        )
        KodeBlok(kode = JSON_SATU_TODO.trim())

        Tabel(
            "Bagian 5.2 — JSON array (${daftar.size} task dibaca Gson)",
            daftar.map { "#${it.id}" to it.title }
        )
        KodeBlok(kode = JSON_DUA_TODO.trim())

        Tabel("Bagian 5.3 — Amati respons API di browser", langkahAmati)

        Button(onClick = { terlihat = if (terlihat == 0) 1 else 0 }) {
            Text(if (terlihat == 0) "Tampilkan status yang diharapkan" else "Sembunyikan")
        }
        if (terlihat == 1) {
            listOf(ALAMAT_TODO_1, ALAMAT_TODO_999).forEach { alamat ->
                val kode = statusDiharapkan(alamat)
                Text(
                    text = "$alamat → $kode (${kelompokStatus(kode)})",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Tabel("Tabel 5 — Pemetaan jenis value JSON ke tipe Kotlin", tabelTipe)

        KodeBlok(
            kode = """
            data class Todo(
                val userId: Int,
                val id: Int,
                val title: String,
                val completed: Boolean
            )
            """
        )
    }
}
