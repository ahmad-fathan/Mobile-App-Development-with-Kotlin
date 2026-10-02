package com.example.myfirstcomposeapp.bab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myfirstcomposeapp.R
import com.example.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme

/**
 * Bab 3 — Menampilkan Gambar
 *
 * Gambar di Android disimpan sebagai resource di folder res/. Kelas R dibuat
 * otomatis oleh proses build dari isi folder res/: setiap resource mendapat ID,
 * dan ID itulah yang dipanggil dari kode.
 *
 *   res/drawable/my_picture.png        →  R.drawable.my_picture
 *   res/drawable/ic_launcher_foreground →  R.drawable.ic_launcher_foreground
 *
 * Nama resource hanya boleh berisi huruf kecil, angka, dan garis bawah.
 *
 * Catatan penting: R.mipmap.ic_launcher TIDAK dapat dipakai di painterResource.
 * Pada perangkat API 26 ke atas, ID itu menunjuk ke mipmap-anydpi-v26/ic_launcher.xml
 * yang berisi <adaptive-icon>, dan painterResource hanya menerima VectorDrawable
 * (XML <vector>) atau gambar raster (PNG, JPG, WEBP). Pemakaiannya menyebabkan
 * runtime error "Only VectorDrawables and rasterized asset types are supported".
 * Karena itu ikon template yang kita tampilkan di sini adalah
 * R.drawable.ic_launcher_foreground, yaitu VectorDrawable yang juga dipakai
 * sebagai lapisan depan ikon launcher.
 */
@Composable
fun LayarDenganGambar() {
    Column {
        Text(text = "Hello, Android!")
        Text(text = "This is my first Compose screen.")
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App launcher icon"
        )
    }
}

/**
 * painterResource memuat gambar dari resource aplikasi, R.mipmap.ic_launcher
 * merujuk ikon launcher yang dibuat bersama proyek, dan contentDescription
 * mendeskripsikan gambar untuk keperluan aksesibilitas (dibacakan pembaca layar).
 */
@Preview(showBackground = true)
@Composable
fun LayarDenganGambarPreview() {
    MyFirstComposeAppTheme {
        LayarDenganGambar()
    }
}
