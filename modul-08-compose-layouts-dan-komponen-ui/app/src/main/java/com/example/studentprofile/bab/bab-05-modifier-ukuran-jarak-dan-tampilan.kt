package com.example.studentprofile.bab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 5 (Bagian 6) — Modifier untuk Ukuran, Jarak, dan Tampilan
 *
 * Empat modifier yang dibahas di sini:
 *   - width(x) dan height(x) mengatur satu dimensi; size(x) mengatur keduanya;
 *   - background(color) mewarnai area yang dimiliki elemen PADA TITIK ITU dalam
 *     rantai modifier;
 *   - clip(shape) memotong semua gambar sesudahnya mengikuti sebuah bentuk,
 *     misalnya CircleShape atau RoundedCornerShape(12.dp).
 *
 * Gunakan dp untuk ukuran dan jarak layout, dan sp untuk ukuran teks (sp mengikuti
 * pengaturan ukuran huruf pengguna).
 */

/** width/height/clip/background dipakai bersama. */
@Composable
fun KotakSudutMembulat() {
    Box(
        modifier = Modifier
            .width(120.dp)
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEADDFF))
    )
}

/**
 * 6.2 — tiga jenis ruang kosong yang sering tertukar:
 *   padding     → ruang DI DALAM elemen, antara tepinya dan isinya
 *   jarak anak  → ruang di antara anak bersebelahan (spacedBy atau Spacer)
 *   arrangement → cara membagi ruang SISA di dalam kontainer
 *
 * Nilai yang diulang-ulang membuat layar terlihat rapi: 16 dp di tepi layar dan di
 * dalam kartu, 16 dp antar-blok utama, 12 dp di antara dua tombol, 4 dp antara nama
 * dan deskripsi.
 */
@Composable
fun TigaJenisRuangKosong() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("padding 16 dp pada Column ini")
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Text("anak 1")
            Text("anak 2")
        }
        Spacer(Modifier.size(16.dp))
        Text("Spacer 16 dp di atas ini")
    }
}

/**
 * 6.3 — urutan modifier menentukan hasil.
 *
 * Rantai A: background lalu padding → background mewarnai SELURUH area elemen,
 * lalu padding mengecilkan ruang untuk teks; warna kuning mengisi area padding.
 *
 * Rantai B: padding lalu background → padding lebih dulu menambah ruang kosong
 * 16 dp, lalu background hanya mewarnai area di dalamnya; padding tetap
 * transparan. Inilah perilaku yang menyerupai "margin".
 *
 * Compose tidak memiliki modifier margin, karena padding sebelum background sudah
 * berperan sebagai margin.
 */
@Composable
fun UrutanBackgroundLaluPadding() {
    Text(
        text = "Hello",
        modifier = Modifier
            .background(Color.Yellow)
            .padding(16.dp)
    )
}

@Composable
fun UrutanPaddingLaluBackground() {
    Text(
        text = "Hello",
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Yellow)
    )
}

@Preview(showBackground = true)
@Composable
fun KotakSudutMembulatPreview() {
    StudentProfileTheme {
        KotakSudutMembulat()
    }
}

@Preview(showBackground = true)
@Composable
fun UrutanBackgroundLaluPaddingPreview() {
    StudentProfileTheme {
        UrutanBackgroundLaluPadding()
    }
}

@Preview(showBackground = true)
@Composable
fun UrutanPaddingLaluBackgroundPreview() {
    StudentProfileTheme {
        UrutanPaddingLaluBackground()
    }
}
