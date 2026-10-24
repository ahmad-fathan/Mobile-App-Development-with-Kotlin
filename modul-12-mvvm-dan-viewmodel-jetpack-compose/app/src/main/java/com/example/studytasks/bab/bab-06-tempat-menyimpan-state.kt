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
import com.example.studytasks.PeristiwaNilai
import com.example.studytasks.Tabel
import com.example.studytasks.contohStateTasks
import com.example.studytasks.ketahananNilaiTasks
import com.example.studytasks.nilaiBertahan

/**
 * Bab 6 (Bagian 6) — Menentukan Tempat Menyimpan State
 *
 * 6.1 Tidak semua nilai perlu disimpan di ViewModel. Pedoman state hoisting tetap berlaku:
 *     pindahkan state ke pemilik TERENDAH yang perlu membaca atau mengubahnya. Status buka-tutup
 *     card → state UI lokal; posisi scroll → rememberLazyListState; draf yang dipakai validasi →
 *     ViewModel; data lintas layar → repository; data yang harus awet → penyimpanan permanen.
 *     State animasi dan detail layout tetap di UI: memindahkannya ke ViewModel hanya menambah
 *     kode tanpa manfaat.
 *
 * 6.2 "Mempertahankan nilai" bukan "menyimpan permanen". Tabel 7 membandingkan empat mekanisme
 *     pada tiga peristiwa. Pemulihan saved state bergantung pada mekanisme saved state Android
 *     dan lifecycle task: setelah force stop atau task ditutup dari Recents, nilai tidak kembali.
 *     Aturan praktis: input kecil untuk pemulihan → saved state; data yang harus awet → database
 *     lewat repository.
 *
 * Tabel 6 dan 7 diambil dari paket akar (verifikasi.kt). Angka pada matriks ketahanan dihitung,
 * bukan ditulis ulang di berkas ini.
 */

@Composable
fun TempatMenyimpanStateScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 6 — Menentukan tempat menyimpan state",
            keterangan = "Pemilik terendah yang membutuhkannya; umur nilai menentukan mekanismenya."
        )
        Tabel(
            "Tabel 6. Contoh state dan pemilik yang sesuai",
            contohStateTasks.map { it.contoh to it.keterangan }
        )
        Tabel(
            "Tabel 7. Ketahanan nilai pada setiap mekanisme",
            ketahananNilaiTasks.map { baris ->
                baris.mekanisme.label to PeristiwaNilai.entries.joinToString(" · ") { peristiwa ->
                    val tanda = if (nilaiBertahan(baris.mekanisme, peristiwa)) "ya" else "tidak"
                    "${peristiwa.label}: $tanda"
                }
            }
        )
        Tabel(
            "Aturan praktis",
            listOf(
                "Draf / ID item terpilih" to "saved state (rememberSaveable atau SavedStateHandle)",
                "Daftar tugas itu sendiri" to "database atau penyimpanan permanen lewat repository",
                "Animasi, scroll, buka-tutup card" to "UI; jangan dipindah ke ViewModel"
            )
        )
        Tabel(
            "Batas aplikasi Study Tasks",
            listOf(
                "Bertahan" to "rotasi, karena repository dipertahankan oleh ViewModel",
                "Hilang" to "process death dan saat scope ViewModel berakhir (Activity selesai)"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TempatMenyimpanStatePreview() {
    TempatMenyimpanStateScreen()
}
