package com.example.studentprofile.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 1 (Bagian 2) — Layar sebagai Susunan Kontainer
 *
 * Ada dua pertanyaan yang perlu dijawab untuk setiap bagian layar:
 *   1. Di mana sebuah elemen ditempatkan dan berapa ruang yang didapatnya?
 *      Dijawab oleh layout: Column, Row, Box, Scaffold, dibantu Spacer dan Modifier.
 *   2. Apa elemen itu? Dijawab oleh komponen: Text, Button, OutlinedTextField,
 *      Card, TopAppBar — diatur lewat parameter komponen dan tema Material 3.
 *
 * Komponen kontainer seperti Card TIDAK otomatis menyusun isinya ke samping; isinya
 * disusun dari atas ke bawah seperti Column. Untuk menaruh gambar di samping teks di
 * dalam kartu, tetap diperlukan Row.
 */

/** Card menyusun isinya seperti Column, jadi dua Text ini bertingkat. */
@Composable
fun ContohCardTanpaRow() {
    Card {
        Text("Budi Santoso")
        Text("Informatics student")
    }
}

/** Untuk menyusun isi kartu ke samping, Row dipakai di dalamnya. */
@Composable
fun ContohCardDenganRow() {
    Card {
        Row {
            Column {
                Text("Budi Santoso")
                Text("Informatics student")
            }
            Spacer(Modifier.size(16.dp))
            Text("Aktif")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContohCardTanpaRowPreview() {
    StudentProfileTheme {
        ContohCardTanpaRow()
    }
}
