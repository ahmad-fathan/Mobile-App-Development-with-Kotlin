package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.KotakAlur
import com.example.studytasks.PanahBawah
import com.example.studytasks.Tabel
import com.example.studytasks.kepemilikanStateTasks
import com.example.studytasks.langkahAksiAddTask
import com.example.studytasks.peranMvvmTasks

/**
 * Bab 4 (Bagian 4) — Pola MVVM
 *
 * 4.1 Model bukan sekadar data class di Kotlin: Model mencakup data DAN operasi terhadapnya —
 *     dalam aplikasi kita berarti repository beserta object StudyTask yang dikelolanya.
 *     MVVM dan lapisan Android juga menjelaskan hal berbeda: View dan ViewModel sama-sama berada
 *     di UI layer. MVVM menjelaskan hubungan keduanya dengan Model, bukan mengharuskan tiga
 *     lapisan Android yang terpisah.
 *
 * 4.2 Satu aksi pengguna mengikuti lima langkah: View melaporkan klik → ViewModel memeriksa draf →
 *     repository menambahkan tugas → ViewModel menerbitkan state baru → View menampilkan daftar
 *     baru.
 *
 * 4.3 Unidirectional data flow (UDF): state mengalir ke bawah menuju UI, event mengalir ke atas
 *     menuju pemilik state. Hanya satu pemilik yang dapat mengubah state, sehingga setiap
 *     perubahan pada layar selalu berawal dari pemanggilan salah satu fungsi milik pemilik state.
 *
 * Tabel 3, urutan langkah, dan pembagian kepemilikan diambil dari deklarasi di paket akar
 * (verifikasi.kt) supaya tabel di layar dan yang diperiksa tidak dapat berbeda.
 */

@Composable
fun PolaMvvmScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 4 — Pola MVVM",
            keterangan = "Model · View · ViewModel, lalu satu aksi yang melewati ketiganya."
        )
        Tabel("Tabel 3. Tiga bagian MVVM dalam aplikasi Study Tasks",
            peranMvvmTasks.map { it.bagian to it.makna })

        Text("Gambar 3 — satu aksi Add task melewati View, ViewModel, dan repository")
        KotakAlur("1. View", "Klik tombol Add task dilaporkan ke ViewModel")
        PanahBawah("onAddTask()")
        KotakAlur("2. ViewModel", "Memeriksa draf saat ini; kosong → pesan validasi, selesai")
        PanahBawah("repository.addTask(title)")
        KotakAlur("3. Repository", "Membuat StudyTask dengan nextId++ dan menyimpannya")
        PanahBawah("getTasks()")
        KotakAlur("4. ViewModel", "Menerbitkan TasksUiState baru (draf kosong, daftar baru)")
        PanahBawah("StateFlow<TasksUiState>")
        KotakAlur("5. View", "Recomposition menampilkan daftar yang baru")

        Tabel(
            "Urutan yang sama dalam bentuk tabel",
            langkahAksiAddTask.map { "${it.urutan}. ${it.pelaku}" to it.kegiatan }
        )
        Tabel(
            "Bagian 4.3 — UDF: siapa memiliki apa",
            kepemilikanStateTasks
        )
        Tabel(
            "Layar tidak pernah…",
            listOf(
                "…mengubah repository" to "Layar hanya memanggil event handler",
                "…mengubah state milik ViewModel" to "Layar menerima hasilnya sebagai state baru",
                "…menyimpan MutableStateFlow" to "Hanya StateFlow read-only yang terlihat dari luar"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PolaMvvmPreview() {
    PolaMvvmScreen()
}
