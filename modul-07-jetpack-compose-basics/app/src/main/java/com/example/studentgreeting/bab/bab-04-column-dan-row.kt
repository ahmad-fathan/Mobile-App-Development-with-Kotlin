package com.example.studentgreeting.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentgreeting.ui.theme.StudentGreetingTheme

/**
 * Bagian 6 (berkas ke-4) — Menyusun Elemen dengan Column dan Row
 *
 * Jika dua Text ditulis berurutan tanpa wadah tata letak, keduanya tergambar
 * bertumpuk di posisi yang sama. Compose menyediakan wadah tata letak:
 *
 *   Column → menyusun anak secara vertikal, dari atas ke bawah
 *   Row    → menyusun anak secara horizontal, dari kiri ke kanan
 *
 * Secara bawaan anak-anak Column saling menempel tanpa jarak. Parameter
 * verticalArrangement = Arrangement.spacedBy(12.dp) menyisipkan jarak yang sama di
 * antara anak. Pada Row, parameter yang setara bernama horizontalArrangement.
 */

@Composable
fun ColumnDuaBaris() {
    Column {
        Text("Student Name")
        Text("Mobile App Development")
    }
}

@Composable
fun RowNama() {
    Row {
        Text("Name: ")
        Text("Ahmad")
    }
}

@Composable
fun ColumnDenganJarak() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Student Name")
        Text("Mobile App Development")
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnDuaBarisPreview() {
    StudentGreetingTheme {
        ColumnDuaBaris()
    }
}

@Preview(showBackground = true)
@Composable
fun RowNamaPreview() {
    StudentGreetingTheme {
        RowNama()
    }
}
