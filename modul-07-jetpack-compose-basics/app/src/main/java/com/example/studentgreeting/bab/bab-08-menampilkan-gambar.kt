package com.example.studentgreeting.bab

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentgreeting.R
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 10 (berkas ke-8) — Menampilkan Gambar di Aplikasi
 *
 * Image beserta painterResource dan contentDescription sudah dibahas di modul
 * sebelumnya. Bab ini menambahkan dua hal yang dipakai aplikasi akhir:
 *
 *   1. Ukuran. Tanpa modifier, gambar tampil sesuai ukuran aslinya dan dapat
 *      memenuhi layar. Modifier.size(120.dp) membatasinya menjadi kotak
 *      120 dp × 120 dp.
 *   2. contentDescription. Teks ini dibacakan pembaca layar seperti TalkBack untuk
 *      pengguna tunanetra atau dengan gangguan penglihatan, jadi isinya sebaiknya
 *      menjelaskan gambar. Isi dengan null hanya bila gambar murni dekoratif.
 *
 * Salin gambar ke app/src/main/res/drawable/ dengan nama yang hanya berisi huruf
 * kecil, angka, dan garis bawah. Bila belum punya gambar, gunakan
 * R.drawable.ic_launcher_foreground yang tersedia di setiap proyek baru.
 *
 * Hindari R.mipmap.ic_launcher: pada perangkat API 26+ ID itu menunjuk ke
 * mipmap-anydpi-v26/ic_launcher.xml yang berisi <adaptive-icon>, dan
 * painterResource hanya menerima VectorDrawable atau gambar raster (PNG, JPG,
 * WEBP). Pemakaiannya membuat aplikasi berhenti dengan galat
 * "Only VectorDrawables and rasterized asset types are supported".
 */

@Composable
fun GambarBerkuranTetap() {
    Image(
        painter = painterResource(R.drawable.student),
        contentDescription = "Student illustration",
        modifier = Modifier.size(120.dp)
    )
}

/** Gambar berdampingan dengan teks di dalam Column. */
@Composable
fun GambarDenganTeks() {
    Column {
        Text("Student illustration")
        GambarBerkuranTetap()
    }
}

@Preview(showBackground = true)
@Composable
fun GambarBerkuranTetapPreview() {
    StudentGreetingTheme {
        GambarBerkuranTetap()
    }
}
