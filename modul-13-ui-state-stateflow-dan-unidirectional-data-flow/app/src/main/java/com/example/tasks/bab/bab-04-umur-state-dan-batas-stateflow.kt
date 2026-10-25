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
import com.example.tasks.Tabel
import com.example.tasks.langkahAntreanState
import com.example.tasks.perilakuKehidupanState

/**
 * Bab 4 — Umur State: Recomposition, Rotasi, Process Death, dan Batas StateFlow
 *
 * Bagian modul: "Recomposition, Rotation, and Process Death", "StateFlow Is Not an Event Queue".
 *
 * Dua batas yang harus dipahami:
 *   1. StateFlow adalah state yang dapat diamati, BUKAN penyimpanan permanen.
 *   2. StateFlow bukan antrean kejadian: nilai yang sama tidak diterbitkan dua kali, dan collector
 *      lambat dapat melewatkan nilai tengah.
 */

@Composable
fun KehidupanStateScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Umur state dan batas StateFlow",
            keterangan = "Tiga peristiwa, tiga dampak berbeda terhadap ViewModel."
        )

        KodeBlok(
            """
            // Composition : Composable masuk, boleh recompose berkali-kali, lalu keluar
            // Activity    : dibuat → dimulai → dilanjutkan → dihentikan → dihancurkan
            // ViewModel   : dipertahankan ViewModelStoreOwner sampai scope pemilik berakhir

            // Recomposition BUKAN pembuatan ulang Activity. Activity tetap object yang sama dan
            // ViewModel dari owner+key yang sama tetap instance yang sama.
            """
        )

        Tabel(
            "Peristiwa dan dampaknya pada ViewModel",
            perilakuKehidupanState.map { it.situasi to it.perilaku }
        )
        Text(
            "Praktiknya: rotasi membuktikan sesuatu hanya bila Activity benar-benar dibuat ulang. " +
                "Process death tidak diuji sama sekali oleh rotasi — ia butuh simulator/emulator dengan " +
                "\"Don't keep activities\" atau perintah adb untuk membunuh proses.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("StateFlow bukan antrean event", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            // Nilai berubah cepat: A → B → C
            // Collector lambat dapat melihat: A, lalu C. B dilewati.

            // Konsekuensi desain:
            // • jangan pakai Boolean di StateFlow sebagai pengantar event sekali jalan;
            // • inputError adalah STATE yang terlihat, bukan event: ia dibersihkan ketika pengguna
            //   mengedit draf atau berhasil menambah tugas;
            // • navigasi, notifikasi, dan efek lain perlu penanganan tersendiri.
            """
        )
        Tabel(
            "Nilai yang diterima collector lambat",
            langkahAntreanState.map { it.nilai to if (it.diterimaCollectorLambat) "diterima" else "dilewati (nilai lebih baru sudah tersedia)" }
        )

        Text("Apa yang perlu ditambahkan bila state harus bertahan lebih lama", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Ketahanan nilai",
            listOf(
                "Recomposition" to "ViewModel yang sama tetap ada; state tidak diinisialisasi ulang",
                "Rotasi (konfigurasi berubah)" to "ViewModel bertahan dalam scope owner-nya",
                "Owner dihapus permanen" to "ViewModel dibersihkan; state di memori hilang",
                "Process death" to "ViewModel + StateFlow hilang; perlu SavedStateHandle (nilai kecil) atau data layer (data awet)"
            )
        )
        KodeBlok(
            """
            // Contoh pemulihan kecil dengan SavedStateHandle (modul berikutnya memakai data layer)
            class DraftViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {
                val draft: StateFlow<String> = savedStateHandle.getStateFlow("draft", "")
                fun onDraftChange(value: String) { savedStateHandle["draft"] = value }
            }
            // StateFlow tetap bukan database: daftar tugas sendiri memerlukan penyimpanan permanen.
            """
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KehidupanStatePreview() {
    KehidupanStateScreen()
}
