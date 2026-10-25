package com.example.tasks.latihan

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
import com.example.tasks.JudulBab
import com.example.tasks.KodeBlok
import com.example.tasks.Tabel
import com.example.tasks.Task
import com.example.tasks.TasksUiState
import com.example.tasks.sisaBelumSelesai
import com.example.tasks.stateSetelahHapus
import com.example.tasks.stateSetelahHapusSelesai
import com.example.tasks.stateSetelahStatus
import com.example.tasks.stateSetelahTambah

/**
 * Latihan akhir modul 13.
 *
 * Ada dua latihan di modul: "Independent Practice: Delete a Task" (wajib) dan latihan tambahan
 * "Clear completed" yang memakai jalur UDF yang sama. Keduanya sudah dijalankan sebagai fungsi
 * murni di verifikasi.kt, lalu layar ini menampilkan hasilnya supaya jalur UDF dapat ditelusuri
 * tanpa emulator.
 */

private val tigaTugas = TasksUiState(
    tasks = listOf(
        Task("1", "Review StateFlow", isCompleted = true),
        Task("2", "Implement UDF"),
        Task("3", "Type the draft")
    ),
    draftTitle = "  Practice the UDF trace  ",
    showIncompleteOnly = true
)

@Composable
fun LatihanScreen(modifier: Modifier = Modifier) {
    val setelahTambah = stateSetelahTambah(tigaTugas, "4")
    val setelahCentang = stateSetelahStatus(setelahTambah, "2", true)
    val setelahHapus = stateSetelahHapus(setelahCentang, "3")
    val setelahBersih = stateSetelahHapusSelesai(setelahHapus)

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Latihan akhir — Delete a Task dan Clear completed",
            keterangan = "Setiap baris adalah satu snapshot hasil fungsi murni; tidak ada state yang diubah di tempat."
        )

        Text(
            "Jalur UDF penghapusan (tulis dulu, baru kode): layar mengirim ID lewat " +
                "onDeleteTask: (String) -> Unit → route menghubungkan ke viewModel::onDeleteTask → " +
                "ViewModel membuat daftar baru dengan filterNot → snapshot baru diterbitkan → layar " +
                "menggambar daftar dan ringkasan yang baru.",
            style = MaterialTheme.typography.bodyMedium
        )

        KodeBlok(
            """
            // TasksViewModel.kt — penghapusan berdasarkan ID, bukan posisi di daftar tersaring
            fun onDeleteTask(id: String) {
                _uiState.update { current ->
                    current.copy(tasks = current.tasks.filterNot { it.id == id })
                }
            }

            // Latihan tambahan: hapus semua yang sudah selesai, jalur UDF yang sama
            fun onClearCompleted() {
                _uiState.update { current ->
                    current.copy(tasks = current.tasks.filterNot { it.isCompleted })
                }
            }
            """
        )

        Tabel(
            "Simulasi pada state awal: 3 tugas (1 selesai), filter incomplete-only AKTIF, draf terisi",
            listOf(
                "awal" to daftar(tigaTugas),
                "onAddTask()" to daftar(setelahTambah) + " — draf \"${setelahTambah.draftTitle}\"",
                "onTaskCompletionChanged(\"2\", true)" to daftar(setelahCentang) + " — tugas #2 hilang dari daftar tersaring",
                "onDeleteTask(\"3\")" to daftar(setelahHapus),
                "onClearCompleted()" to daftar(setelahBersih) + " — semua selesai tersisa"
            )
        )

        Text(
            "Ringkasan memakai daftar LENGKAP, daftar tampil memakai filter. Sisa yang belum selesai: " +
                "${sisaBelumSelesai(tigaTugas)} dari ${tigaTugas.tasks.size} pada state awal.",
            style = MaterialTheme.typography.bodyMedium
        )

        Text("Pertanyaan yang harus dijawab saat menguji", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Empat pemeriksaan",
            listOf(
                "Hapus tugas di tengah" to "dua ID lainnya tidak berubah",
                "Hapus saat filter aktif" to "tugas tersembunyi tidak ikut terhapus",
                "Ringkasan setelah hapus" to "completed/total dihitung ulang dari daftar lengkap",
                "Preview" to "tetap dikompilasi: callback kosong { } sudah cukup"
            )
        )
    }
}

/** Ringkasan isi daftar lengkap dan daftar terlihat dalam satu baris. */
private fun daftar(state: TasksUiState): String {
    val semua = state.tasks.joinToString { "${it.id}${if (it.isCompleted) "✓" else ""}" }
    val tampil = state.visibleTasks.joinToString { it.id }
    return "semua [$semua] · tampil [$tampil] · ${state.summaryText}"
}

@Preview(showBackground = true)
@Composable
fun LatihanPreview() {
    LatihanScreen()
}
