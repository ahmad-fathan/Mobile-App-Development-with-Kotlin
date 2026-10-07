package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 3 (berkas ke-1) — Mengulang Singkat: Composable dan Preview
 *
 * Fungsi composable adalah fungsi Kotlin bertanda @Composable yang mendeskripsikan
 * sebagian antarmuka. Composable boleh memanggil composable lain, seperti
 * WelcomeScreen yang memanggil Text di bawah ini.
 *
 * @Preview membuat Android Studio dapat menampilkan composable di tampilan Split
 * atau Design tanpa menjalankan aplikasi. Mulai Bagian 7 interaksi seperti klik dan
 * ketikan diuji dengan menjalankan aplikasi di emulator, atau dengan mengaktifkan
 * mode interaktif pada panel Preview (letak tombolnya dapat berbeda antarversi
 * Android Studio).
 */

/** Bentuk paling sederhana: satu composable yang memanggil Text. */
@Composable
fun WelcomeScreen() {
    Text("Welcome to Jetpack Compose")
}

/** Beberapa composable dapat dipanggil berurutan di dalam sebuah wadah (lihat Bab 4). */
@Composable
fun ContohComposableBertingkat() {
    Column {
        WelcomeScreen()
        WelcomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    StudentGreetingTheme {
        WelcomeScreen()
    }
}
