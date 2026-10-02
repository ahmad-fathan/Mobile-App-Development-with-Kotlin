package com.example.myfirstcomposeapp.bab

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Bab 1 — Composable Pertama
 *
 * Composable adalah fungsi Kotlin biasa dengan tiga perbedaan: diawali anotasi
 * @Composable, namanya diawali huruf kapital, dan tidak mengembalikan nilai apa pun.
 * Fungsi ini tidak menggambar piksel langkah demi langkah; ia mendeskripsikan apa
 * yang seharusnya tampil, lalu Compose yang menampilkannya.
 */

/** Bentuk paling sederhana: satu composable yang memanggil Text. */
@Composable
fun GreetingSederhana() {
    Text(text = "Hello, Android!")
}

/**
 * Composable boleh menerima parameter. Teks dibentuk dengan string template $name,
 * pola yang sudah kita pakai di modul-modul sebelumnya.
 *
 * Parameter modifier adalah parameter opsional pertama (lihat Bab 4).
 */
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

/**
 * Latihan Bagian: mengubah teks template agar memperkenalkan diri sendiri.
 * Ganti isi text menjadi "Hi, my name is $name!" lalu periksa di tampilan Design.
 */
@Composable
fun GreetingPerkenalan(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hi, my name is $name!",
        modifier = modifier
    )
}

/** Beberapa composable dapat ditumpuk dalam satu Column (dibahas di Bab 2). */
@Composable
fun ContohComposableBertingkat() {
    Column {
        GreetingSederhana()
        Greeting("Android")
        GreetingPerkenalan("Alya")
    }
}
