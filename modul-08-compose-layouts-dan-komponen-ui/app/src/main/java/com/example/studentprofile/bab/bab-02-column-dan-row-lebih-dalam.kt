package com.example.studentprofile.bab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.R
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 2 (Bagian 3) — Column dan Row Lebih Dalam
 *
 * Empat hal yang ditambahkan di bab ini: layout bersarang, ruang yang tersedia,
 * jarak eksplisit, dan pembagian ruang dengan weight.
 *
 * Ruang yang tersedia: Compose mengukur layar dari induk ke anak. Induk memberi
 * ukuran terbesar yang boleh dipakai (constraints), anak memilih ukurannya sendiri
 * di dalam batas itu. Akibatnya:
 *   - Column/Row tanpa modifier ukuran hanya sebesar isinya (wrap content);
 *   - fillMaxWidth()/fillMaxSize() meminta ukuran maksimum yang diizinkan induk;
 *   - ukuran tetap yang lebih besar dari batas induk dipotong ke batas itu
 *     (mis. Modifier.size(500.dp) pada layar selebar sekitar 360 dp).
 */

/** 3.1 — Row yang anak keduanya adalah Column: layout bersarang. */
@Composable
fun HeaderBersarang() {
    Row {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
            modifier = Modifier.size(56.dp)
        )
        Column {
            Text("Budi Santoso")
            Text("Informatics student")
        }
    }
}

/** 3.3 cara 1 — jarak di posisi tertentu dengan Spacer. */
@Composable
fun HeaderDenganSpacer() {
    Row {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
            modifier = Modifier.size(56.dp)
        )
        Spacer(Modifier.size(16.dp))
        Column {
            Text("Budi Santoso")
            Text("Informatics student")
        }
    }
}

/** 3.3 cara 2 — jarak sama di antara semua anak dengan spacedBy. */
@Composable
fun HeaderDenganSpacedBy() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Profile picture",
            modifier = Modifier.size(56.dp)
        )
        Column {
            Text("Budi Santoso")
            Text("Informatics student")
        }
    }
}

/**
 * 3.4 — dua tombol tanpa weight: tiap tombol hanya selebar teksnya dan sisa ruang
 * menganggur di sebelah kanan. Bandingkan dengan DuaTombolDenganWeight.
 */
@Composable
fun DuaTombolTanpaWeight() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = { }, modifier = Modifier.background(Color.Transparent)) {
            Text("Show Greeting")
        }
        OutlinedButton(onClick = { }) {
            Text("Clear")
        }
    }
}

/**
 * 3.4 — Modifier.weight membagi sisa ruang di antara anak yang diberi bobot.
 * Bobot 1f dan 1f membuat lebar baris terbagi dua sama besar; 2f dan 1f memberi
 * dua pertiga untuk tombol pertama.
 *
 * Dua hal yang perlu diingat: weight hanya tersedia untuk anak LANGSUNG Row/Column
 * (RowScope/ColumnScope), dan Row membutuhkan fillMaxWidth() agar ada lebar yang
 * dapat dibagi.
 */
@Composable
fun DuaTombolDenganWeight() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = { }, modifier = Modifier.weight(1f)) {
            Text("Show Greeting")
        }
        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) {
            Text("Clear")
        }
    }
}

/** Bobot 2f dan 1f: tombol pertama dua pertiga, tombol kedua sepertiga. */
@Composable
fun DuaTombolWeightDuaSatu() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(onClick = { }, modifier = Modifier.weight(2f)) {
            Text("Show Greeting")
        }
        OutlinedButton(onClick = { }, modifier = Modifier.weight(1f)) {
            Text("Clear")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderBersarangPreview() {
    StudentProfileTheme {
        HeaderBersarang()
    }
}

@Preview(showBackground = true)
@Composable
fun DuaTombolTanpaWeightPreview() {
    StudentProfileTheme {
        DuaTombolTanpaWeight()
    }
}

@Preview(showBackground = true)
@Composable
fun DuaTombolDenganWeightPreview() {
    StudentProfileTheme {
        DuaTombolDenganWeight()
    }
}
