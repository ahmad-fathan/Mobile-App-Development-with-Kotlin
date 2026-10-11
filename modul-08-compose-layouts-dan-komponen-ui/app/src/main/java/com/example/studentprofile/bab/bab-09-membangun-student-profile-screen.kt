package com.example.studentprofile.bab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 9 (Bagian 9) — Membangun Student Profile / Greeting Screen, Tahap demi Tahap
 *
 * Modul membangun layar akhir dalam tujuh tahap, dan setiap tahap dapat diperiksa
 * di Preview sebelum melanjutkan. Fungsi Tahap1…Tahap6 di bawah adalah potret
 * setiap tahap; hasil akhir seluruh tahap ada di MainActivity.kt
 * (StudentProfileApp, StudentProfileScreen, ProfileCard, ProfileAvatar).
 *
 * Sebelum mulai, modul meminta mengganti nama StudentGreetingScreen menjadi
 * StudentProfileScreen lewat klik kanan, Refactor, Rename.
 */

/** 9.1 Tahap 1 — Header dengan Row dan Column. Kotak abu-abu pengganti gambar. */
@Composable
fun Tahap1Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(Modifier.size(72.dp).background(Color.LightGray))
        Column {
            Text("Budi Santoso")
            Text("Informatics student")
        }
    }
}

/**
 * 9.2 Tahap 2 — Avatar dengan Box. Dua anak ditumpuk di dalam Box 72 dp: lingkaran
 * berisi inisial, dan label "Online" yang digambar di atasnya.
 *
 * Warna masih memakai nilai tetap (LightGray dan Yellow) dengan sengaja; pada
 * Tahap 6 warna-warna ini diganti dengan peran tema.
 */
@Composable
fun Tahap2Avatar(initials: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(72.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) { Text(initials) }
        Text(
            text = "Online",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .background(Color.Yellow)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun Tahap2Header(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Tahap2Avatar(initials = "BS")
        Column {
            Text("Budi Santoso")
            Text("Informatics student")
        }
    }
}

/** 9.3 Tahap 3 — header dikelompokkan dalam Card; padding pindah ke dalam kartu. */
@Composable
fun Tahap3Card(modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Tahap2Avatar(initials = "BS")
            Column {
                Text("Budi Santoso")
                Text("Informatics student")
            }
        }
    }
}

/**
 * 9.4 Tahap 4 — kolom input dan dua tombol. Pada tahap ini tombol belum melakukan
 * apa-apa; isi onClick dilengkapi pada Tahap 5.
 *
 * 9.5 Tahap 5 — Show Greeting memeriksa apakah nama kosong. isBlank() bernilai benar
 * untuk teks kosong maupun teks yang hanya berisi spasi, sedangkan trim() membuang
 * spasi di awal dan akhir nama. Karena name dan greeting adalah state, menekan Clear
 * langsung mengosongkan kolom input dan sapaan melalui recomposition.
 *
 * Tiga hal yang perlu diuji di emulator atau interactive Preview:
 *   1. ketik "Budi" lalu Show Greeting → "Hello, Budi! Welcome to class."
 *   2. kosongkan kolom lalu Show Greeting → "Hello, guest! Welcome to class."
 *   3. Clear mengosongkan kolom input dan sapaan
 */
@Composable
fun Tahap4Dan5Layar(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Tahap3Card()

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    greeting = if (name.isBlank()) {
                        "Hello, guest! Welcome to class."
                    } else {
                        "Hello, ${name.trim()}! Welcome to class."
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Show Greeting")
            }
            OutlinedButton(
                onClick = {
                    name = ""
                    greeting = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }

        Text(text = greeting)
    }
}

/**
 * 9.6 Tahap 6 — warna, teks, dan bentuk yang konsisten. Tata letaknya tidak berubah;
 * yang diganti hanya nilai tetap dan gaya bawaan dengan peran tema:
 *   - Card memakai shape = MaterialTheme.shapes.large dan warna surfaceVariant;
 *   - Column di dalam kartu memakai spacedBy(4.dp); nama titleMedium, deskripsi
 *     bodyMedium dengan warna onSurfaceVariant;
 *   - lingkaran avatar memakai latar primary, inisial titleLarge warna onPrimary;
 *   - label status memakai clip(MaterialTheme.shapes.small), latar tertiary, teks
 *     labelSmall warna onTertiary;
 *   - sapaan memakai titleLarge warna primary.
 */
@Composable
fun Tahap6AvatarBertema(initials: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.size(72.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = "Online",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Composable
fun Tahap6CardBertema(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Tahap6AvatarBertema(initials = "BS")
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Budi Santoso",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Informatics student",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Tahap1HeaderPreview() {
    StudentProfileTheme {
        Tahap1Header()
    }
}

@Preview(showBackground = true)
@Composable
fun Tahap2HeaderPreview() {
    StudentProfileTheme {
        Tahap2Header()
    }
}

@Preview(showBackground = true)
@Composable
fun Tahap3CardPreview() {
    StudentProfileTheme {
        Tahap3Card()
    }
}

@Preview(showBackground = true)
@Composable
fun Tahap4Dan5LayarPreview() {
    StudentProfileTheme {
        Tahap4Dan5Layar()
    }
}

@Preview(showBackground = true)
@Composable
fun Tahap6CardBertemaPreview() {
    StudentProfileTheme {
        Tahap6CardBertema()
    }
}
