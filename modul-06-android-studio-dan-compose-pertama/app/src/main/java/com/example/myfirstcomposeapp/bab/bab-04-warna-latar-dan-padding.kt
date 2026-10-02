package com.example.myfirstcomposeapp.bab

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
 * Bab 4 — Mempercantik Tampilan: Warna Latar dan Padding
 *
 * Surface adalah wadah yang mewakili satu bagian antarmuka yang tampilannya dapat
 * diubah, misalnya warna latar atau garis tepinya. Modifier mengatur ukuran dan
 * jarak sebuah composable:
 *
 *   fillMaxSize()   → Column memakai seluruh ruang layar yang tersedia
 *   padding(24.dp)  → jarak 24 dp dari tepi layar; satuan dp (density-independent
 *                     pixel) membuat jarak terlihat serupa di layar dengan
 *                     kerapatan piksel berbeda
 *
 * Karena Column memakai fillMaxSize(), Surface yang membungkusnya ikut memenuhi
 * layar, sehingga seluruh area latar menjadi berwarna.
 *
 * Ikon launcher ditampilkan lewat R.drawable.ic_launcher_foreground (VectorDrawable),
 * bukan R.mipmap.ic_launcher — lihat catatan di bab-03-menampilkan-gambar.kt.
 */
@Composable
fun MyFirstScreen() {
    Surface(color = Color.Cyan) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(text = "Hello, Android!")
            Text(text = "This is my first Compose screen.")
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "App launcher icon"
            )
        }
    }
}

/**
 * Bagian 13.2 — perbedaan letak modifier.
 *
 * Di sini padding diterapkan pada Text dan Surface hanya membungkus teks, sehingga
 * warna cyan muncul di sekitar teks dengan jarak 24 dp, bukan di seluruh layar.
 * Bandingkan dengan MyFirstScreen yang memberi padding pada Column berukuran penuh.
 */
@Composable
fun GreetingBerwarnaLatar(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Cyan) {
        Text(
            text = "Hi, my name is $name!",
            modifier = modifier.padding(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyFirstScreenPreview() {
    MyFirstComposeAppTheme {
        MyFirstScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingBerwarnaLatarPreview() {
    MyFirstComposeAppTheme {
        GreetingBerwarnaLatar("Android")
    }
}
