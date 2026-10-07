package com.example.studentgreeting

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 11 — Membangun Student Greeting App
 *
 * Aplikasi dibangun dalam sembilan langkah kecil, dan setiap langkah dapat
 * diperiksa di Preview atau emulator sebelum melanjutkan:
 *   1–3  teks, Column, Modifier          → StudentGreetingScreen (versi awal,
 *                                          lihat KDoc di bawah)
 *   4–5  TextField dan penyimpanan input di state
 *   6–8  Button, penanganan onClick, dan tampilan sapaan
 *   9    Image
 *
 * Langkah 1–8 masing-masing sudah dibahas di bab/bab-01 sampai bab/bab-08; berkas
 * ini memuat hasil akhir sembilan langkah tersebut.
 *
 * Tiga hal yang perlu diperhatikan:
 *   - MainActivity memanggil StudentGreetingScreen di dalam Scaffold dan
 *     meneruskan Modifier.padding(innerPadding), agar isi layar tidak tertutup
 *     status bar.
 *   - Nama tema StudentGreetingTheme dibuat otomatis dari nama proyek, jadi dapat
 *     berbeda di proyek Anda.
 *   - Fungsi preview membungkus layar dengan tema yang sama agar warna dan huruf di
 *     Preview sesuai dengan aplikasi.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentGreetingTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    StudentGreetingScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

/**
 * Parameter modifier bernilai bawaan Modifier sesuai praktik yang dianjurkan codelab.
 * Dengan parameter ini pemanggil fungsi dapat menambahkan modifier sendiri, misalnya
 * padding dari Scaffold, dan modifier itu diteruskan ke Column sebagai composable
 * pertama, lalu dirangkai dengan fillMaxWidth() dan padding(16.dp).
 *
 * Dua pola sapaan sama-sama benar: membaca name langsung (sapaan berubah setiap
 * ketikan, lihat bab-07) atau membaca greeting (berubah hanya saat tombol ditekan,
 * dipakai di sini). Pilihan bergantung pada perilaku yang diinginkan.
 */
@Composable
fun StudentGreetingScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Enter your name:", fontSize = 20.sp)
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
        Button(onClick = { greeting = "Hello, $name!" }) {
            Text("Say Hello")
        }
        Text(text = greeting, fontSize = 24.sp)
        Image(
            painter = painterResource(R.drawable.student),
            contentDescription = "Student illustration",
            modifier = Modifier.size(120.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StudentGreetingScreenPreview() {
    StudentGreetingTheme {
        StudentGreetingScreen()
    }
}
