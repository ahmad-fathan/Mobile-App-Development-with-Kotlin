package com.example.studytasks

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Komponen tampilan bersama untuk seluruh berkas bab.
 *
 * Modul 12 hampir seluruhnya berupa tabel dan potongan kode, jadi tiga komponen kecil ini dipakai
 * ulang di semua bab: [JudulBab], [KodeBlok], dan [Tabel]. Dideklarasikan SEKALI di paket akar —
 * menyalinnya ke setiap bab akan memicu `Conflicting declarations` saat folder dikompilasi
 * sekaligus.
 */

/** Judul sebuah bagian modul, dipakai sebagai kepala setiap layar bab. */
@Composable
fun JudulBab(teks: String, keterangan: String? = null) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = teks,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.semantics { heading() }
        )
        if (keterangan != null) {
            Text(
                text = keterangan,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Potongan kode yang ditampilkan di layar.
 *
 * Memakai `FontFamily.Monospace` lewat `style` tipografi bawaan Compose, dan dapat digulir
 * horizontal supaya baris panjang tidak terpotong pada layar sempit.
 */
@Composable
fun KodeBlok(kode: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Text(
            text = kode.trimIndent(),
            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
            fontSize = 12.sp,
            lineHeight = 18.sp,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .horizontalScroll(rememberScrollState())
                .padding(12.dp)
        )
    }
}

/** Satu baris tabel: label di kiri (tebal), keterangan di kanan. */
@Composable
fun BarisTabel(label: String, keterangan: String) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(
            text = keterangan,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/** Daftar pasangan label–keterangan; dipakai untuk semua tabel dua kolom di modul ini. */
@Composable
fun Tabel(judul: String, baris: List<Pair<String, String>>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = judul,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.semantics { heading() }
        )
        baris.forEach { (label, keterangan) ->
            BarisTabel(label, keterangan)
        }
    }
}

/** Baris horizontal untuk diagram sederhana (View → ViewModel → Repository). */
@Composable
fun KotakAlur(judul: String, isi: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(text = judul, style = MaterialTheme.typography.labelLarge)
            Text(
                text = isi,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Panah bawah antar-kotak pada diagram alur. */
@Composable
fun PanahBawah(keterangan: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "↓  $keterangan",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
