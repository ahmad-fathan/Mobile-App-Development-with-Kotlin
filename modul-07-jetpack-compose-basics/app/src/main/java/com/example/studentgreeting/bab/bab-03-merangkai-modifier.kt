package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 5 (berkas ke-3) — Merangkai Modifier
 *
 * Modifier memberi tahu sebuah composable bagaimana ia ditata, diberi ukuran,
 * atau dihias. Tiga modifier yang dipakai di modul ini:
 *
 *   padding(16.dp)   → menambahkan ruang kosong di sekeliling elemen
 *   fillMaxWidth()   → elemen selebar ruang yang tersedia; tingginya tidak berubah
 *   size(120.dp)     → lebar dan tinggi tetap, misalnya untuk gambar
 *
 * fillMaxWidth() hanya memengaruhi lebar, berbeda dengan fillMaxSize() yang
 * memenuhi lebar dan tinggi sekaligus.
 *
 * Beberapa modifier dirangkai dengan tanda titik, satu modifier per baris agar
 * mudah dibaca. Modifier diterapkan berurutan dari atas ke bawah, sehingga
 * modifier yang ditulis lebih dulu memengaruhi modifier sesudahnya.
 */

/** Satu modifier saja: ruang kosong 16 dp di setiap sisi teks. */
@Composable
fun TeksDenganPadding() {
    Text(
        text = "Hello Compose",
        modifier = Modifier.padding(16.dp)
    )
}

/**
 * Dua modifier dirangkai.
 *
 * Urutannya: teks dibuat selebar layar lebih dulu, lalu diberi ruang kosong 16 dp
 * di dalam area selebar itu. Hasilnya sama dengan urutan terbalik untuk padding,
 * tetapi urutannya menjadi penting begitu digabung dengan ukuran atau warna latar.
 */
@Composable
fun TeksSelebarLayar() {
    Text(
        text = "Hello Compose",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

/** size() memberi lebar dan tinggi tetap — dipakai juga untuk membatasi gambar. */
@Composable
fun TeksDenganUkuranTetap() {
    Text(
        text = "Hello Compose",
        modifier = Modifier.size(120.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun TeksDenganPaddingPreview() {
    StudentGreetingTheme {
        TeksDenganPadding()
    }
}

@Preview(showBackground = true)
@Composable
fun TeksSelebarLayarPreview() {
    StudentGreetingTheme {
        TeksSelebarLayar()
    }
}
