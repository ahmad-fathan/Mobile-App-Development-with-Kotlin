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
import com.example.tasks.JudulBab
import com.example.tasks.KodeBlok
import com.example.tasks.KotakAlur
import com.example.tasks.PanahBawah
import com.example.tasks.Tabel
import com.example.tasks.konsepPertanyaan
import com.example.tasks.langkahTrace

/**
 * Bab 3 — Unidirectional Data Flow dan Hubungannya dengan MVVM
 *
 * Bagian modul: "Unidirectional Data Flow", "UDF and MVVM Work Together", "Add a Task",
 * "Change Completion and Filter", "Render State and Send Actions",
 * "Render a List with Stable Identity", "Trace One Complete Interaction".
 *
 * Arah yang harus dijaga: state TURUN ke UI, aksi NAIK ke pemilik state. MVVM menjawab "siapa
 * bertanggung jawab", UDF menjawab "ke arah mana nilai bergerak" — MVVM tidak otomatis
 * menjamin UDF.
 */

@Composable
fun UdFlowScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Unidirectional data flow",
            keterangan = "Satu pemilik state; setiap perubahan selalu berawal dari pemanggilan method-nya."
        )

        KotakAlur("Compose (View)", "Menggambar state yang diberikan; tidak menyimpan salinan draf.")
        PanahBawah("state turun: TasksUiState → collectAsStateWithLifecycle()")
        KotakAlur("TasksViewModel (state holder)", "Memproses aksi, memvalidasi input, menerbitkan snapshot baru.")
        PanahBawah("aksi naik: callback → onAddTask(), onDeleteTask(), ...")
        KotakAlur("Data layer (belum ada di modul ini)", "Tempat data permanen nanti; ViewModel tidak menulis UI.")

        Tabel(
            "MVVM dan UDF menjawab pertanyaan berbeda",
            konsepPertanyaan.map { it.konsep to it.pertanyaan }
        )

        Text("Empat aksi aplikasi latihan", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            viewModel.onDraftTitleChanged(title)              // 1. input berubah
            viewModel.onAddTask()                             // 2. tambah tugas (validasi di sini)
            viewModel.onTaskCompletionChanged(id, completed)  // 3. status selesai berubah
            viewModel.onIncompleteFilterChanged(enabled)      // 4. hanya tampilkan yang belum selesai
            viewModel.onDeleteTask(id)                        // Independent Practice

            // Sealed event class bersifat OPSIONAL. Untuk aplikasi sekecil ini, method bernama
            // membuat aksinya lebih jelas dibaca.
            """
        )

        Text("Render state dan kirim aksi", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            OutlinedTextField(
                value = uiState.draftTitle,                 // state turun
                onValueChange = onDraftTitleChanged,        // aksi naik
                isError = uiState.inputError != null
            )
            Button(onClick = onAddTask) { Text("Add task") }

            LazyColumn {
                items(uiState.visibleTasks, key = { it.id }) { task ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { checked ->
                                onTaskCompletionChanged(task.id, checked)  // ID, bukan indeks
                            }
                        )
                        Text(task.title, modifier = Modifier.weight(1f))
                        TextButton(onClick = { onDeleteTask(task.id) }) { Text("Delete") }
                    }
                }
            }
            // Jangan menambahkan remember kedua untuk draf: layar dan ViewModel akan berbeda pendapat.
            // Jangan memanggil onAddTask() di badan Composable: badan dapat berjalan berulang kali.
            """
        )

        Text("Satu interaksi lengkap, enam langkah", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Pengguna mencentang tugas (filter mati, satu tugas belum selesai)",
            langkahTrace.map { "Langkah ${it.nomor}: ${it.kejadian}" to it.arah }
        )
        Text(
            "Langkah 1–2: aksi naik. Langkah 3–4: pemilik state. Langkah 5–6: state turun. " +
                "Ketika filter incomplete-only aktif, tugas yang selesai hilang dari daftar terlihat " +
                "tetapi tetap ada di daftar lengkap — itulah sebabnya ringkasan tidak berubah menjadi 0 of 0.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UdFlowPreview() {
    UdFlowScreen()
}
