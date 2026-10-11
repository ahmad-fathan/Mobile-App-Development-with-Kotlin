package com.example.studentprofile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
// Ganti dengan tema proyek Anda bila namanya berbeda (lihat Subbagian 9.8 modul).
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bagian 9.8 — Kode Lengkap Student Profile / Greeting Screen
 *
 * Hubungan seluruh bagian aplikasi, dibaca dari luar ke dalam (Subbagian 8.3):
 *   1. MainActivity.onCreate memanggil enableEdgeToEdge() dan setContent; semua yang
 *      berada di dalam setContent adalah Compose.
 *   2. Fungsi tema (StudentProfileTheme) menyediakan warna, tipografi, dan bentuk.
 *   3. StudentProfileApp memuat satu-satunya Scaffold: slot topBar dan konten.
 *   4. StudentProfileScreen adalah konten: Column berisi kartu, kolom input, tombol,
 *      dan sapaan.
 *   5. ProfileCard dan ProfileAvatar adalah bagian kecil yang dapat dipakai ulang.
 *
 * Kode ini tidak memerlukan gambar maupun dependensi tambahan. Perubahan nama
 * composable dan tema dilakukan lewat Refactor ▸ Rename, bukan diketik manual, agar
 * semua pemanggilnya ikut berubah.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentProfileTheme {
                StudentProfileApp()
            }
        }
    }
}

/** Satu-satunya Scaffold: app bar + konten layar. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Student Profile") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        // innerPadding diterapkan sekali, di batas konten.
        StudentProfileScreen(modifier = Modifier.padding(innerPadding))
    }
}

/** Layar: kartu profil, input nama, dua tombol, sapaan. */
@Composable
fun StudentProfileScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileCard()

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

        Text(
            text = greeting,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/** Informasi profil dikelompokkan dalam Card Material 3. */
@Composable
fun ProfileCard(modifier: Modifier = Modifier) {
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
            ProfileAvatar(initials = "BS")
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

/**
 * Avatar tanpa berkas gambar: lingkaran berisi inisial dan label status yang
 * ditumpuk di pojok kanan bawah.
 *
 * Parameter modifier diteruskan ke layout terluar (Box), sehingga pemanggil dapat
 * memperbesar avatar, misalnya Modifier.size(96.dp) pada Latihan 5.
 */
@Composable
fun ProfileAvatar(initials: String, modifier: Modifier = Modifier) {
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

@Preview(showBackground = true)
@Composable
fun StudentProfileAppPreview() {
    StudentProfileTheme {
        StudentProfileApp()
    }
}
