package com.example.tasks.bab

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasks.KodeBlok
import com.example.tasks.PESAN_BELUM_ADA_TUGAS
import com.example.tasks.PESAN_JUDUL_KOSONG
import com.example.tasks.Task
import com.example.tasks.TasksUiState
import com.example.tasks.Tabel
import com.example.tasks.butirStateEvent
import com.example.tasks.judulTersimpan
import com.example.tasks.keputusanPemilik

/**
 * Bab 1 — Apa Itu UI State, Model, Nilai Turunan, dan Update Immutable
 *
 * Bagian modul: "The Problem: A Screen with Conflicting Values", "What Is UI State?",
 * "State and Events Are Different", "Decide Who Owns Each Value", "Model a Task",
 * "Model the Screen as One State Object", "Derive Values Instead of Duplicating Them",
 * "Immutable Updates with copy()".
 *
 * Inti bab ini: satu layar = satu object snapshot. Tidak ada nilai turunan yang disimpan, sehingga
 * ringkasan tidak mungkin berbeda dari daftar (masalah "3 of 5" pada modul).
 */

/** Potret state yang dipakai seluruh bab, supaya contoh konsisten. */
val contohStateBab1: TasksUiState = TasksUiState(
    tasks = listOf(
        Task("1", "Review StateFlow", isCompleted = true),
        Task("2", "Implement UDF")
    ),
    draftTitle = "Read chapter 2"
)

@Composable
fun UiStateScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        com.example.tasks.JudulBab(
            teks = "UI state: satu potret untuk satu layar",
            keterangan = "UI = render(current UI state)"
        )

        Text(
            "Masalah awalnya bukan tampilan, melainkan dua sumber kebenaran: daftar tugas, jumlah " +
                "selesai, dan jumlah total disimpan terpisah. Ketika satu tugas dihapus, angka-angka " +
                "itu tidak ikut berubah dan layar menampilkan \"3 of 5\" padahal hanya tersisa 2 tugas.",
            style = MaterialTheme.typography.bodyMedium
        )
        KodeBlok(
            """
            // Cara yang bermasalah: tiga nilai yang harus dijaga tetap cocok
            var tasks = listOf<Task>()
            var completedCount = 3
            var totalCount = 5

            // Cara modul ini: satu snapshot, sisanya diturunkan
            data class TasksUiState(
                val tasks: List<Task> = emptyList(),
                val draftTitle: String = "",
                val showIncompleteOnly: Boolean = false,
                val inputError: String? = null
            ) {
                val completedCount: Int get() = tasks.count { it.isCompleted }
                val visibleTasks: List<Task> get() = if (showIncompleteOnly)
                    tasks.filter { !it.isCompleted } else tasks
            }
            """
        )

        Text("State dan event", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            // Event   : pengguna menekan checkbox
            // State   : tugas berstatus selesai
            // Event TIDAK selalu mengubah state. Menekan Add dengan input kosong saat pesan
            // "Enter a task title." sudah tampil menghasilkan state yang sama.
            """
        )
        Tabel(
            "Tabel — state dan event",
            butirStateEvent.map { "${it.contoh}" to "${it.kategori} — ${it.alasan}" }
        )

        Tabel(
            "Siapa pemilik nilai ini?",
            keputusanPemilik.map { "${it.nilai}" to "${it.pemilik} — ${it.alasan}" }
        )
        Text(
            "StateFlow tidak menggantikan semua pemakaian remember/rememberSaveable. Pilih pemilik " +
                "dari cara nilai dipakai dan berapa lama ia harus bertahan.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Model satu tugas", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            data class Task(
                val id: String,
                val title: String,
                val isCompleted: Boolean = false
            )
            // id tetap mengenali tugas walau daftar terlihat disaring atau posisinya berubah.
            // Semua val: perubahan dibuat dengan copy(), bukan dengan menulis ulang field.
            """
        )

        Text("Nilai turunan pada state contoh", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "TasksUiState(tasks = 2 tugas, 1 selesai, filter mati, draf terisi)",
            listOf(
                "completedCount" to "${contohStateBab1.completedCount}",
                "summaryText" to contohStateBab1.summaryText,
                "visibleTasks" to "${contohStateBab1.visibleTasks.size} tugas",
                "draftTitle" to contohStateBab1.draftTitle,
                "judulTersimpan(draftTitle)" to "${judulTersimpan(contohStateBab1.draftTitle)}",
                "showsEmptyMessage" to "${contohStateBab1.showsEmptyMessage}"
            )
        )

        Text("Update immutable dengan copy()", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            val before = TasksUiState(draftTitle = "Read")
            val after  = before.copy(draftTitle = "Read chapter 2")

            // before tetap "Read"; after berisi "Read chapter 2";
            // property yang tidak disebut copy() mempertahankan nilainya.
            // copy() dangkal: jangan mengubah object bersarang atau collection bersama.
            """
        )

        Text(
            "Dua pesan yang dipakai aplikasi: \"$PESAN_JUDUL_KOSONG\" (validasi) dan " +
                "\"$PESAN_BELUM_ADA_TUGAS\" (empty state). Keduanya konstanta di TasksUiState.kt, " +
                "sehingga layar dan verifikasi.kt memakai teks yang sama.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UiStatePreview() {
    UiStateScreen()
}
