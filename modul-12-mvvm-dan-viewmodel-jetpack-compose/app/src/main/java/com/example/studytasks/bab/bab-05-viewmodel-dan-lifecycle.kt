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
import com.example.studytasks.NasibViewModel
import com.example.studytasks.Tabel
import com.example.studytasks.peristiwaViewModelTasks
import com.example.studytasks.tigaLifecycleTasks
import com.example.studytasks.viewModelBertahanSaat

/**
 * Bab 5 (Bagian 5) — ViewModel dan Lifecycle-nya
 *
 * 5.1 ViewModel dari AndroidX adalah class yang dirancang sebagai state holder tingkat layar. Ia
 *     menyediakan state yang dibutuhkan layar, menangani aksi dan mengoordinasikan pemanggilan
 *     repository, serta mempertahankan state saat perubahan konfigurasi selama masih dalam
 *     scope-nya.
 *
 *     ViewModel TIDAK boleh menyimpan referensi ke Activity, View, atau object lain yang terikat
 *     pada lifecycle layar. Alasannya: ViewModel dapat hidup lebih lama daripada Activity yang
 *     membuatnya, sehingga referensi seperti itu menahan layar yang sudah dihancurkan di memori.
 *
 * 5.2 Ada tiga lifecycle berbeda: Composition (masuk, recomposition berkali-kali, keluar),
 *     Activity (created → started → resumed → stopped → destroyed), dan ViewModel (dipertahankan
 *     ViewModelStoreOwner sampai scope pemilik berakhir). Recomposition BUKAN pembuatan ulang
 *     Activity: selama recomposition, Activity dan ViewModel tetap object yang sama.
 *
 * 5.3 Perubahan konfigurasi (rotasi) umumnya menghancurkan Activity lalu membuat Activity baru,
 *     tetapi ViewModel dalam scope-nya tetap dipertahankan. Process death menghancurkan segalanya:
 *     instance ViewModel hilang. Menyimpan object di memori berbeda dengan menyimpan datanya
 *     secara permanen.
 *
 * Data Tabel 4 dan 5 berasal dari paket akar (verifikasi.kt), sehingga aturan "Activity dibuat
 * ulang → ViewModel tetap" diuji, bukan sekadar ditulis.
 */

@Composable
fun ViewModelDanLifecycleScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 5 — ViewModel dan lifecycle-nya",
            keterangan = "State holder tingkat layar, dan tiga umur yang harus dibedakan."
        )
        Tabel(
            "Tabel 4. Lifecycle Composition, Activity, dan ViewModel",
            tigaLifecycleTasks.map { it.nama to it.penjelasan }
        )
        Tabel(
            "Tabel 5. Dampak berbagai peristiwa terhadap ViewModel",
            peristiwaViewModelTasks.map { it.peristiwa to it.dampak }
        )
        Tabel(
            "Ringkasan yang diuji oleh verifikasi.kt",
            peristiwaViewModelTasks.map { baris ->
                baris.peristiwa to when (baris.nasib) {
                    NasibViewModel.TETAP -> "ViewModel TETAP (instance sama)"
                    NasibViewModel.HILANG -> "ViewModel HILANG; dibuat ulang saat pengguna kembali"
                    NasibViewModel.DIBERSIHKAN -> "ViewModel DIBERSIHKAN; onCleared() dipanggil"
                }
            }
        )
        Tabel(
            "Gambar 5 — garis waktu saat rotasi",
            listOf(
                "Activity baru" to "remember dimulai lagi dari awal",
                "ViewModel" to "instance yang sama dipertahankan, state tidak hilang",
                "Process death" to "keduanya hilang; ViewModel baru dibuat saat aplikasi dibuka lagi"
            )
        )
        Tabel(
            "Yang tidak boleh disimpan di ViewModel",
            listOf(
                "Activity / Context" to "Menahan layar yang sudah dihancurkan → memory leak",
                "View / Composable" to "ViewModel hidup lebih lama daripada Composition",
                "Animasi & detail layout" to "Tidak ada bagian lain yang membutuhkannya (Bagian 6.1)"
            )
        )
        val rotasiBertahan = viewModelBertahanSaat(
            "Activity dibuat ulang karena perubahan konfigurasi"
        )
        Tabel(
            "Kesimpulan untuk aplikasi Study Tasks",
            listOf(
                "Rotasi" to (if (rotasiBertahan) "draf dan daftar tetap ada" else "state hilang"),
                "Process death" to "keduanya hilang (data hanya di memori)"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ViewModelDanLifecyclePreview() {
    ViewModelDanLifecycleScreen()
}
