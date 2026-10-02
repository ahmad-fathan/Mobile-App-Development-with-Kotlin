package com.example.myfirstcomposeapp.bab

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme

/**
 * Bab 2 — Menyusun Layar dan Melihatnya dengan Preview
 *
 * Column adalah wadah yang menyusun elemen di dalamnya secara vertikal, sesuai
 * urutan penulisan. Tanpa Column, kedua Text akan saling menumpuk di posisi
 * yang sama. Wadah lain seperti Row dan Box dibahas pada pertemuan berikutnya.
 */
@Composable
fun LayarDuaBaris() {
    Column {
        Text(text = "Hello, Android!")
        Text(text = "This is my first Compose screen.")
    }
}

/**
 * @Preview memberi tahu Android Studio bahwa composable ini perlu ditampilkan di
 * tampilan Design. Fungsi preview juga harus @Composable, karena di dalamnya ia
 * memanggil composable lain, dan tidak boleh memiliki parameter wajib.
 *
 * Preview tidak membangun aplikasi lengkap dan tidak memasang apa pun ke perangkat;
 * ia hanya alat bantu mendesain.
 */
@Preview(showBackground = true)
@Composable
fun LayarDuaBarisPreview() {
    MyFirstComposeAppTheme {
        LayarDuaBaris()
    }
}
