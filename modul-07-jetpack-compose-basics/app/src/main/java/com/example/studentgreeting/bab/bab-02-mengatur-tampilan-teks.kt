package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 4 (berkas ke-2) — Mengatur Tampilan Teks
 *
 * Text menerima parameter bernama (named argument) untuk mengatur tampilannya.
 * Menuliskan nama parameter membuat kode lebih mudah dibaca ketika jumlah
 * parameternya bertambah.
 *
 * Satuan ukuran Android:
 *   dp (density-independent pixels) → ukuran elemen, jarak, dan padding
 *   sp (scale-independent pixels)   → ukuran huruf
 *
 * Keduanya menghasilkan ukuran fisik yang sama di layar dengan kerapatan piksel
 * berbeda. Bedanya, sp juga mengikuti pengaturan ukuran huruf pengguna, sehingga
 * teks tetap nyaman dibaca. Karena itu ukuran huruf selalu memakai sp.
 *
 * Jika sp atau FontWeight berwarna merah, letakkan kursor pada kata tersebut lalu
 * tekan Alt+Enter (Mac: Option+Enter) untuk menambahkan import yang sesuai.
 */

/** Bentuk paling sederhana: hanya teks yang ditampilkan. */
@Composable
fun TeksSederhana() {
    Text("Hello Compose")
}

/** Ukuran dan ketebalan huruf diatur lewat parameter bernama. */
@Composable
fun TeksBesarTebal() {
    Text(
        text = "Hello Compose",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun TeksSederhanaPreview() {
    StudentGreetingTheme {
        TeksSederhana()
    }
}

@Preview(showBackground = true)
@Composable
fun TeksBesarTebalPreview() {
    StudentGreetingTheme {
        TeksBesarTebal()
    }
}
