package com.example.studentgreeting.latihan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgreeting.R
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Latihan Akhir Modul 7 — enam latihan pada proyek StudentGreeting.
 *
 * Untuk mencoba di aplikasi, ubah isi setContent di MainActivity menjadi nama
 * fungsi latihan yang diinginkan. Setiap fungsi punya @Preview sendiri.
 */

/**
 * Latihan 1 — Mengubah teks.
 * Text("Hello Compose") menjadi sapaan yang memuat nama, fontSize 28.sp, dan
 * fontWeight FontWeight.Bold.
 */
@Composable
fun Latihan1UbahTeks() {
    Text(
        text = "Hello, Ahmad Fathan Hidayatullah!",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )
}

/**
 * Latihan 2 — Modifier.
 * Teks pada Latihan 1 diberi padding 24.dp dan dibuat selebar ruang yang tersedia.
 *
 * Urutan penerapan: fillMaxWidth() lebih dulu membuat teks selebar layar, lalu
 * padding(24.dp) menambahkan ruang kosong di dalam area itu sehingga teks bergeser
 * 24 dp dari tepi. Bila urutannya dibalik (padding lalu fillMaxWidth), ruang kosong
 * dihitung lebih dulu lalu sisanya dibuat selebar layar — jarak tepi tetap 24 dp,
 * tetapi area yang dipakai fillMaxWidth() lebih sempit.
 */
@Composable
fun Latihan2Modifier() {
    Text(
        text = "Hello, Ahmad Fathan Hidayatullah!",
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    )
}

/**
 * Latihan 3 — Tombol pengubah teks.
 * Column berisi satu teks "Hello" dan sebuah tombol. Ketika tombol ditekan, teks
 * berubah menjadi "Welcome to Jetpack Compose". Teks disimpan dalam state String.
 */
@Composable
fun Latihan3TombolPengubahTeks() {
    var teks by remember { mutableStateOf("Hello") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(teks)
        Button(onClick = { teks = "Welcome to Jetpack Compose" }) {
            Text("Ubah Teks")
        }
    }
}

/**
 * Latihan 4 — Sapaan langsung.
 * Kolom isian nama dengan Text di bawahnya yang menampilkan "Hello, <nama>"
 * mengikuti ketikan. Ketika kolom kosong, tampilkan "Hello, guest".
 */
@Composable
fun Latihan4SapaanLangsung() {
    var name by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") }
        )
        // trim() agar spasi saja tetap dianggap kosong.
        Text(if (name.trim().isEmpty()) "Hello, guest" else "Hello, $name")
    }
}

/**
 * Latihan 5 — Kartu profil mahasiswa.
 * Pengembangan Student Greeting App menjadi Student Profile Card: kolom isian
 * nama, teks sapaan, tombol, gambar dengan contentDescription yang sesuai, dan
 * jarak antarelemen yang rapi. Row menempatkan gambar dan nama berdampingan.
 */
@Composable
fun Latihan5KartuProfilMahasiswa(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Student Profile Card", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Button(onClick = { greeting = "Hello, $name!" }) {
            Text("Say Hello")
        }
        Text(text = greeting, fontSize = 20.sp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.student),
                contentDescription = "Student illustration",
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = if (name.trim().isEmpty()) "Guest" else name,
                fontSize = 20.sp
            )
        }
    }
}

/**
 * Latihan 6 — Menelusuri recomposition.
 * Bila remember dihapus dari CounterExample, isi fungsi dijalankan kembali dari
 * baris pertama setiap recomposition, sehingga mutableStateOf(0) membuat state
 * baru bernilai 0 setiap kali. count++ mengubah nilai state yang baru dibuat itu,
 * lalu recomposition berikutnya membuat state baru lagi — penghitung selalu
 * kembali menampilkan "Clicked 0 times" dan tidak pernah bertambah.
 */
fun latihan6PenelusuranRecomposition(): String =
    "Tanpa remember, state dibuat ulang bernilai 0 pada setiap recomposition, " +
        "sehingga penghitung tidak pernah bertambah."

@Preview(showBackground = true)
@Composable
fun Latihan1Preview() {
    StudentGreetingTheme {
        Latihan1UbahTeks()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan2Preview() {
    StudentGreetingTheme {
        Latihan2Modifier()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan3Preview() {
    StudentGreetingTheme {
        Latihan3TombolPengubahTeks()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan4Preview() {
    StudentGreetingTheme {
        Latihan4SapaanLangsung()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan5Preview() {
    StudentGreetingTheme {
        Latihan5KartuProfilMahasiswa()
    }
}
