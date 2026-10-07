package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 9 (berkas ke-7) — Menerima Input dengan TextField
 *
 * Berbeda dengan Text, TextField memerlukan state karena isi kolom berubah setiap
 * kali pengguna mengetik. Parameter utamanya:
 *
 *   value         → teks yang ditampilkan kolom, diambil dari state
 *   onValueChange → lambda yang dipanggil setiap kali isi kolom berubah;
 *                   parameter it berisi teks terbaru
 *   label         → teks petunjuk di dalam kolom
 *
 * Hal yang sering membingungkan: TextField tidak menyimpan ketikan dengan
 * sendirinya. Kolom selalu menampilkan isi value, ketikan pengguna diteruskan ke
 * onValueChange, lalu kita yang menyimpannya ke state dengan name = it. Jika baris
 * itu terlupa, kolom tampak tidak bereaksi ketika diketik.
 */

@Composable
fun NameInput() {
    var name by remember {
        mutableStateOf("")
    }

    TextField(
        value = name,
        onValueChange = {
            name = it
        },
        label = {
            Text("Enter your name")
        }
    )
}

/**
 * Karena isi ketikan tersimpan di state, nilai yang sama dapat dipakai komponen
 * lain. Setiap ketikan memicu recomposition, sehingga TextField dan Text
 * diperbarui bersamaan dan sapaan mengikuti isi kolom huruf demi huruf.
 */
@Composable
fun NameInputDenganSapaan() {
    var name by remember { mutableStateOf("") }

    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Enter your name") }
        )
        Text("Hello, $name")
    }
}

@Preview(showBackground = true)
@Composable
fun NameInputPreview() {
    StudentGreetingTheme {
        NameInput()
    }
}

@Preview(showBackground = true)
@Composable
fun NameInputDenganSapaanPreview() {
    StudentGreetingTheme {
        NameInputDenganSapaan()
    }
}
