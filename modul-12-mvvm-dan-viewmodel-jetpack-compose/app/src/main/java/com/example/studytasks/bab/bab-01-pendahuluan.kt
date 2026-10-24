package com.example.studytasks.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studytasks.JudulBab
import com.example.studytasks.Tabel

/**
 * Bab 1 (Bagian 1) — Pendahuluan
 *
 * Sampai pertemuan sebelumnya, layar Compose menyimpan state-nya sendiri dengan `remember`,
 * `mutableStateOf`, `rememberSaveable`, dan state hoisting. Cara itu cukup untuk layar kecil.
 * Ketika aplikasi makin besar, satu fungsi Composable mulai memikul terlalu banyak tugas:
 * menggambar antarmuka, menyimpan data, memeriksa input, dan mengubah data.
 *
 * Modul ini membagi tugas tersebut kepada pemilik yang jelas: pola Model-View-ViewModel (MVVM)
 * bersama class `ViewModel` dari AndroidX. State hoisting dibawa satu langkah lebih jauh — keluar
 * dari Composition, menuju ViewModel di tingkat layar.
 *
 * Tabel 1 di bawah menghubungkan konsep yang sudah dikenal dengan hal yang ditambahkan modul ini.
 * Aplikasi berjalan yang dipakai sepanjang modul adalah Study Tasks: ketik judul, tekan Add task,
 * lihat tugas dalam daftar ber-checkbox; judul kosong ditolak dengan pesan.
 */

/** Tabel 1 — konsep pertemuan sebelumnya dan kaitannya dengan modul ini. */
val tabelKonsepSebelumnya: List<Pair<String, String>> = listOf(
    "remember dan mutableStateOf" to "Menyimpan state yang dapat diamati di dekat UI",
    "State hoisting" to "Memindahkan state ke pemilik yang tepat",
    "Event dan callback" to "Melaporkan aksi pengguna kepada pemilik tersebut",
    "LazyColumn dan Material 3" to "Menampilkan layar dari data yang diberikan",
    "Adaptive layout" to "Menampilkan state layar yang sama dengan susunan yang berbeda"
)

/** Tujuan pembelajaran modul (Bagian 1). */
val tujuanPembelajaranModul: List<String> = listOf(
    "menjelaskan separation of concerns serta tanggung jawab UI layer dan data layer;",
    "memetakan Model, View, dan ViewModel ke bagian-bagian aplikasi Compose;",
    "membedakan recomposition, pembuatan ulang Activity, dan process death;",
    "menyediakan state layar dari ViewModel dan mengirimkan aksi pengguna kepadanya;",
    "melakukan refactoring sebuah Composable menjadi layar yang menerima state dan event."
)

@Composable
fun PendahuluanScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bagian 1 — Pendahuluan",
            keterangan = "Dari layar yang menyimpan state-nya sendiri menuju pemilik state " +
                "tingkat layar: ViewModel."
        )
        Tabel("Tabel 1. Konsep sebelumnya dan kaitannya dengan modul ini", tabelKonsepSebelumnya)
        Tabel(
            "Setelah modul ini, mahasiswa mampu",
            tujuanPembelajaranModul.mapIndexed { i, t -> "${i + 1}. " to t }
        )
        Tabel(
            "Aplikasi berjalan: Study Tasks",
            listOf(
                "Ketik judul" to "OutlinedTextField menampilkan uiState.draft",
                "Tekan Add task" to "ViewModel memvalidasi lalu memanggil repository",
                "Lihat daftar" to "LazyColumn dengan key = id dan Checkbox",
                "Judul kosong" to "Pesan \"Enter a task title.\"; tidak ada tugas dibuat"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PendahuluanPreview() {
    PendahuluanScreen()
}
