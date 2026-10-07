package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 8 (berkas ke-6) — State dengan remember dan mutableStateOf
 *
 * Siklus event → state → recomposition: event mengubah state, Compose mendeteksi
 * bahwa state yang dibaca sebuah composable telah berubah, lalu memanggil ulang
 * composable itu. Pemanggilan ulang itulah yang disebut recomposition.
 *
 * Baris var count by remember { mutableStateOf(0) } tersusun atas tiga bagian:
 *
 *   mutableStateOf(0) → membuat nilai yang diawasi Compose, nilai awal 0
 *   remember { ... }  → menyimpan nilai itu agar tidak hilang saat fungsi
 *                       dipanggil ulang oleh recomposition
 *   by                → delegasi properti Kotlin, sehingga kita menulis count
 *                       dan bukan count.value
 *
 * Agar by dapat dipakai, berkas memerlukan import getValue, mutableStateOf,
 * remember, dan setValue. getValue dan setValue kadang perlu ditambahkan sendiri
 * karena tidak terlihat langsung di kode.
 *
 * Mengapa remember diperlukan: recomposition menjalankan kembali isi fungsi dari
 * baris pertama. Tanpa remember, mutableStateOf(0) juga ikut dijalankan ulang dan
 * membuat state baru bernilai 0, sehingga penghitung tidak pernah bertambah.
 * Android Studio akan memberi peringatan bila mutableStateOf dipakai tanpa remember.
 *
 * Nilai yang disimpan remember bertahan selama composable masih tampil. Bila layar
 * diputar, activity dibuat ulang dan nilai state kembali ke awal; untuk kasus itu
 * Compose menyediakan rememberSaveable (di luar cakupan modul ini).
 *
 * Catatan: untuk nilai angka bulat, Android Studio mungkin menyarankan
 * mutableIntStateOf(0). Perilakunya sama dan versi khusus Int sedikit lebih
 * efisien, tetapi modul ini memakai mutableStateOf agar satu bentuk dapat dipakai
 * untuk semua tipe data, termasuk String pada bab berikutnya.
 */

/** Tombol yang mencatat berapa kali ia ditekan. */
@Composable
fun CounterExample() {
    var count by remember {
        mutableStateOf(0)
    }

    Button(
        onClick = { count++ }
    ) {
        Text("Clicked $count times")
    }
}

/** Dua state sederhana yang berdampingan: angka dan teks. */
@Composable
fun DuaStateSederhana() {
    var count by remember { mutableStateOf(0) }
    var label by remember { mutableStateOf("Belum diklik") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {
                count++
                label = "Sudah diklik $count kali"
            }
        ) {
            Text("Klik saya")
        }
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
fun CounterExamplePreview() {
    StudentGreetingTheme {
        CounterExample()
    }
}
