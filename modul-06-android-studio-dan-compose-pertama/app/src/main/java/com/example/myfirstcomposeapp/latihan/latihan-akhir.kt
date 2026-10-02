package com.example.myfirstcomposeapp.latihan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstcomposeapp.R
import com.example.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme

/**
 * Latihan Akhir Modul 6 — tiga latihan pada proyek MyFirstComposeApp.
 *
 * Untuk mencoba di aplikasi, ubah isi setContent di MainActivity menjadi
 * Latihan1PersonalisasiLayar() atau Latihan2GambarSendiri().
 */

/**
 * Latihan 1 — Personalisasi layar.
 * Baris pertama menjadi sapaan sendiri, baris kedua menjadi deskripsi singkat
 * ide aplikasi. Refresh Preview, jalankan di emulator atau ponsel, lalu bandingkan
 * hasil Preview dengan aplikasi yang terpasang.
 *
 * Latihan dianggap selesai jika kedua baris teks dan gambar launcher tampil di
 * aplikasi yang berjalan.
 */
@Composable
fun Latihan1PersonalisasiLayar() {
    Surface(color = Color(0xFFB2EBF2)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(text = "Halo, saya Ahmad Fathan Hidayatullah!")
            Text(text = "Aplikasi ini mencatat jadwal kuliah mahasiswa.")
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App launcher icon"
            )
        }
    }
}

/**
 * Latihan 2 — Gambar milik sendiri.
 *
 * File PNG/WebP kecil bernama my_picture.png diletakkan di
 * app/src/main/res/drawable/. Proses build memberi resource itu ID, dan ID itulah
 * yang dipanggil dari kode sebagai R.drawable.my_picture.
 */
@Composable
fun Latihan2GambarSendiri() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(text = "Halo, saya Ahmad Fathan Hidayatullah!")
        Text(text = "Aplikasi ini mencatat jadwal kuliah mahasiswa.")
        Image(
            painter = painterResource(id = R.drawable.my_picture),
            contentDescription = "Gambar contoh milik sendiri dari folder drawable"
        )
    }
}

/**
 * Latihan 3 — Menelusuri proyek.
 *
 * (a) File yang berisi MainActivity
 *     app/src/main/java/com/example/myfirstcomposeapp/MainActivity.kt
 *     Berisi activity pertama yang dibuka saat aplikasi diluncurkan; onCreate()
 *     adalah titik masuk yang memasang layar melalui setContent { }.
 *
 * (b) File Gradle yang mengonfigurasi modul app
 *     app/build.gradle.kts
 *     Menentukan namespace, compileSdk/targetSdk/minSdk, dan daftar dependensi
 *     modul app — termasuk Jetpack Compose.
 *
 * (c) Baris kode yang merujuk gambar launcher
 *     painter = painterResource(id = R.mipmap.ic_launcher)
 *     Memuat ikon launcher (res/mipmap/ic_launcher) sebagai gambar di layar;
 *     ID-nya berasal dari kelas R yang dibuat otomatis saat build.
 */
@Composable
fun Latihan3RingkasanPenelusuran(): String =
    "MainActivity.kt + app/build.gradle.kts + painterResource(id = R.mipmap.ic_launcher)"

@Preview(showBackground = true)
@Composable
fun Latihan1Preview() {
    MyFirstComposeAppTheme {
        Latihan1PersonalisasiLayar()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan2Preview() {
    MyFirstComposeAppTheme {
        Latihan2GambarSendiri()
    }
}
