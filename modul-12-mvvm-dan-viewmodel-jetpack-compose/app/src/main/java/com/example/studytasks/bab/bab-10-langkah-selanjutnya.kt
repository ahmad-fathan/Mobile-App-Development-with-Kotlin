package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.KodeBlok
import com.example.studytasks.Tabel

/**
 * Bab 10 (Bagian 11) — Langkah Selanjutnya
 *
 * Aplikasi Study Tasks sengaja menghindari dua hal yang dibutuhkan aplikasi sungguhan. Bagian ini
 * memperkenalkan keduanya supaya Anda dapat mengenalinya ketika muncul pada pertemuan berikutnya.
 *
 * 11.1 Pekerjaan asinkron. Repository sungguhan membaca dari database atau jaringan, dan itu
 *      membutuhkan waktu, sehingga menyediakan fungsi suspend atau aliran data yang dapat
 *      diamati — bukan fungsi sinkron seperti contoh kita. ViewModel menjalankannya di
 *      `viewModelScope`. Aturan yang menyertainya:
 *        • viewModelScope dibatalkan otomatis ketika ViewModel dibersihkan;
 *        • menjalankan coroutine TIDAK dengan sendirinya memindahkan pekerjaan yang memblokir
 *          keluar dari main thread;
 *        • karena itu repository sebaiknya menyediakan operasi yang aman dipanggil dari main
 *          thread;
 *        • pekerjaan panjang yang harus tetap berjalan setelah layar ditutup memerlukan owner
 *          atau mekanisme lain.
 *      Ketika repository dipakai bersama atau dapat diperbarui dari tempat lain, ViewModel
 *      sebaiknya MENGAMATI aliran data dari repository, bukan hanya memperbarui potret data
 *      setelah tombolnya sendiri ditekan.
 *
 * 11.2 SavedStateHandle memulihkan nilai kecil setelah process death. `getStateFlow("draft", "")`
 *      mengembalikan StateFlow yang didukung saved state; menulis `savedStateHandle["draft"] =
 *      value` sekaligus memperbarui flow dan menyimpan nilainya. Contoh ini ilustrasi, bukan
 *      pengganti TasksViewModel: data tugasnya sendiri tetap memerlukan penyimpanan permanen.
 */

val kodeViewModelScope: String = """
    // Perluasan konseptual, bukan bagian dari API repository saat ini.
    viewModelScope.launch {
        repository.refreshTasks()
    }
"""

val kodeDraftViewModel: String = """
    class DraftViewModel(
        private val savedStateHandle: SavedStateHandle
    ) : ViewModel() {
        val draft: StateFlow<String> =
            savedStateHandle.getStateFlow("draft", "")

        fun onDraftChange(value: String) {
            savedStateHandle["draft"] = value
        }
    }
"""

@Composable
fun LangkahSelanjutnyaScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 11 — Langkah selanjutnya",
            keterangan = "Dua hal yang belum ada di contoh: pekerjaan asinkron dan pemulihan draf."
        )
        KodeBlok(kodeViewModelScope)
        Tabel(
            "Bagian 11.1 — empat aturan pekerjaan asinkron",
            listOf(
                "viewModelScope" to "Dibatalkan otomatis ketika ViewModel dibersihkan",
                "Coroutine ≠ thread" to "Tidak memindahkan pekerjaan yang memblokir dari main thread",
                "Repository" to "Menyediakan operasi yang aman dipanggil dari main thread",
                "Pekerjaan panjang" to "Perlu owner/mekanisme lain bila harus bertahan setelah layar ditutup"
            )
        )
        Tabel(
            "Mengamati vs memperbarui potret",
            listOf(
                "Sekarang" to "getTasks() dipanggil setelah aksi ViewModel sendiri",
                "Dua layar" to "Cara ini tidak lagi cukup; ViewModel harus mengamati aliran repository",
                "Usulan" to "val tasks: StateFlow<List<StudyTask>> pada TaskRepository"
            )
        )
        KodeBlok(kodeDraftViewModel)
        Tabel(
            "Bagian 11.2 — apa yang ditambahkan SavedStateHandle",
            listOf(
                "getStateFlow(key, default)" to "StateFlow yang didukung saved state",
                "savedStateHandle[key] = value" to "Memperbarui flow sekaligus menyimpan nilainya",
                "Setelah pemulihan" to "Instance baru dimulai dengan draf yang tersimpan",
                "Batasnya" to "Hanya untuk nilai kecil; daftar tugas tetap butuh penyimpanan permanen"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LangkahSelanjutnyaPreview() {
    LangkahSelanjutnyaScreen()
}
