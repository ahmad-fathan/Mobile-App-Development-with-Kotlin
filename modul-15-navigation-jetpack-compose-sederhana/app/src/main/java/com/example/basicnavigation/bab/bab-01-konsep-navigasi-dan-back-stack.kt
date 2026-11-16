package com.example.basicnavigation.bab

/**
 * Bab 1 — Konsep navigasi dan back stack (Bagian 2 modul).
 *
 * Navigasi = cara pengguna berpindah layar. Satu layar yang bisa dituju disebut *destination*.
 * Komponen navigasi menangani tiga hal: back stack, deep link (opsional), dan passing arguments.
 *
 * Back stack bekerja seperti tumpukan piring: layar baru diletakkan di atas, tombol Back mengambil
 * layar teratas. Model dan hitungannya ada di `verifikasi.kt` paket akar ([Entri], [push], [pop],
 * [pushDenganPopUpTo]) supaya angka pada tabel di layar tidak perlu dihafal.
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.Entri
import com.example.basicnavigation.backStackAwal
import com.example.basicnavigation.pop
import com.example.basicnavigation.push
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Urutan back stack untuk Gambar 2 modul: Home -> Profile -> Detail -> Back. */
fun contohBackStackBab1(): List<String> {
    val home = Entri("home")
    val profile = Entri("profile")
    val detail = Entri("details")
    return listOf(
        "Buka aplikasi   : ${backStackAwal(home).map { it.route }}",
        "Ke Profile      : ${push(backStackAwal(home), profile).map { it.route }}",
        "Ke Detail       : ${push(push(backStackAwal(home), profile), detail).map { it.route }}",
        "Tekan Back      : ${pop(push(push(backStackAwal(home), profile), detail)).map { it.route }}"
    )
}

@Composable
fun Bab1KonsepNavigasi() {
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Bab 1 — Konsep Navigasi", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Destination adalah satu layar yang bisa dituju. Back stack menyimpan riwayat " +
                "perpindahan; setiap navigate() menambah entri, setiap Back membuang entri teratas.",
            modifier = Modifier.padding(top = 8.dp)
        )
        contohBackStackBab1().forEachIndexed { i, baris ->
            Text(baris, modifier = Modifier.padding(top = if (i == 0) 16.dp else 4.dp))
        }
        Text(
            "Kesimpulan: layar yang dibuka belakangan selalu berada di atas, sehingga Back " +
                "mengembalikan layar sebelumnya, bukan start destination.",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauBab1() {
    BasicNavigationTheme { Bab1KonsepNavigasi() }
}
