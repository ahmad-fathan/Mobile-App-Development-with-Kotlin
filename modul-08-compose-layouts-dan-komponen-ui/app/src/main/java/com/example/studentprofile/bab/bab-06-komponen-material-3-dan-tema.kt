package com.example.studentprofile.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 6 (Bagian 7) — Komponen Material 3 dan Konsistensi Desain
 *
 * Material 3 adalah design system Google: aturan warna, tipografi, dan bentuk
 * beserta komponen siap pakai. Di Compose semuanya berada di paket
 * androidx.compose.material3. Tiga bagian tema yang dipakai modul ini:
 *
 *   MaterialTheme.colorScheme → peran warna: primary/onPrimary,
 *                               primaryContainer, surfaceVariant, tertiary
 *   MaterialTheme.typography  → titleLarge, titleMedium, bodyMedium, labelSmall
 *   MaterialTheme.shapes      → small, large (CircleShape terpisah dari skala ini)
 *
 * Warna berawalan "on" dipakai untuk isi di atas warna pasangannya, mis. teks
 * onPrimary di atas latar primary, agar kontrasnya tetap terbaca. Peran tema lebih
 * baik daripada warna/ukuran tetap karena perubahan tema langsung berlaku di semua
 * layar dan tema gelap ikut menyesuaikan.
 */

/** 7.1 — membaca peran tema pada Text. */
@Composable
fun TeksDenganPeranTema() {
    Text(
        text = "Budi Santoso",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.primary
    )
}

/**
 * 7.2 — Surface adalah kontainer bertema paling dasar: ia melukis warna latar dan
 * bentuk sekaligus menetapkan warna isi yang sesuai; isinya ditempatkan seperti Box.
 * Card adalah kontainer untuk satu kelompok informasi yang saling berkaitan; isinya
 * disusun dari atas ke bawah seperti Column.
 *
 * Padding diletakkan pada Row DI DALAM kartu, bukan pada modifier Card: padding pada
 * Card menambah ruang di luar kartu, padding pada Row memberi ruang di dalamnya.
 */
@Composable
fun ContohCardTema() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(Modifier.padding(16.dp)) {
            Text("Budi Santoso")
        }
    }
}

/**
 * 7.3 — OutlinedTextField memiliki parameter yang sama dengan TextField
 * (value, onValueChange, label), hanya saja tampil dengan garis tepi.
 * singleLine = true mencegah nama terpecah ke baris baru; fillMaxWidth() membuat
 * kolom input sejajar dengan kartu.
 *
 * Material 3 juga membedakan tingkat kepentingan aksi: Button terisi warna untuk
 * aksi utama (Show Greeting), OutlinedButton hanya bergaris tepi untuk aksi
 * sekunder (Clear).
 */
@Composable
fun ContohOutlinedTextFieldDanTombol() {
    var nama by remember { mutableStateOf("") }

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            label = { Text("Your name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { }, modifier = Modifier.weight(1f)) { Text("Show Greeting") }
            OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) { Text("Clear") }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeksDenganPeranTemaPreview() {
    StudentProfileTheme {
        TeksDenganPeranTema()
    }
}

@Preview(showBackground = true)
@Composable
fun ContohCardTemaPreview() {
    StudentProfileTheme {
        ContohCardTema()
    }
}
