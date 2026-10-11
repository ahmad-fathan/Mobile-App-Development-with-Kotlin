package com.example.studentprofile.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 3 (Bagian 4) — Arrangement dan Alignment
 *
 * Setiap Row dan Column memiliki dua arah:
 *   sumbu utama (main axis)  → arah penempatan anak; horizontal untuk Row,
 *                              vertikal untuk Column; diatur Arrangement
 *   sumbu silang (cross axis) → arah lainnya; diatur Alignment
 *
 * Pola penamaannya konsisten: parameter arrangement dinamai menurut sumbu utama,
 * parameter alignment dinamai menurut sumbu silang.
 *
 *   Column → verticalArrangement   (Top, Center, Bottom, SpaceBetween, spacedBy)
 *            horizontalAlignment   (Start, CenterHorizontally, End)
 *   Row    → horizontalArrangement (Start, Center, End, SpaceBetween, spacedBy)
 *            verticalAlignment     (Top, CenterVertically, Bottom)
 *   Box    → tidak punya sumbu utama; contentAlignment (TopStart … BottomEnd)
 *
 * Nilai Start dan End (bukan kiri/kanan) mengikuti arah baca bahasa, sehingga
 * dicerminkan pada bahasa yang ditulis dari kanan ke kiri.
 *
 * Semua perbedaan di bawah hanya terlihat karena kontainer memakai fillMaxSize()
 * atau fillMaxWidth() — tanpa itu tinggi/lebar kontainer sama dengan isinya.
 */

/** 4.2 — arrangement pada Column: sumbu utama vertikal. */
@Composable
fun ColumnArrangementCenter() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("A")
        Text("B")
        Text("C")
    }
}

@Composable
fun ColumnArrangementSpaceBetween() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        Text("A")
        Text("B")
        Text("C")
    }
}

@Composable
fun ColumnArrangementSpacedBy() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.End
    ) {
        Text("A")
        Text("B")
        Text("C")
    }
}

/** 4.3 — alignment pada Row: sumbu silang vertikal. */
@Composable
fun RowSpaceBetween() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

/**
 * Row yang sama tanpa fillMaxWidth(): Row hanya selebar isinya, tidak ada ruang
 * tersisa, sehingga SpaceBetween tampak sama dengan Start.
 */
@Composable
fun RowSpaceBetweenTanpaFillMaxWidth() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

/**
 * Row yang sama dengan Arrangement.End: kedua anak berpindah ke ujung kanan dan
 * tetap bersebelahan — yang berubah hanya posisi kelompok anak di sepanjang sumbu
 * utama, bukan jarak antar-anaknya.
 */
@Composable
fun RowArrangementEnd() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnArrangementCenterPreview() {
    StudentProfileTheme {
        Box(Modifier.fillMaxSize()) { ColumnArrangementCenter() }
    }
}

@Preview(showBackground = true)
@Composable
fun RowSpaceBetweenPreview() {
    StudentProfileTheme {
        RowSpaceBetween()
    }
}
