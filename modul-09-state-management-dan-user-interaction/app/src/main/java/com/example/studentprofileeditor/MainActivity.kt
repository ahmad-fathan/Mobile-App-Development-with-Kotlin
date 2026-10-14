package com.example.studentprofileeditor

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofileeditor.bab.StudentProfileEditor
import com.example.studentprofileeditor.ui.theme.StudentProfileEditorTheme

/**
 * Bagian 10.6 — Memanggil Editor dari Scaffold
 *
 * Pada modul Compose Layouts and UI Components, aplikasi memiliki satu-satunya Scaffold
 * dan kontennya meneruskan Modifier.padding(innerPadding) ke layar. Susunan itu
 * PERTAHANAN: Scaffold yang sama, TopAppBar yang sama, kartu profil yang sama; yang baru
 * hanya editor bio di bawahnya.
 *
 * Ketiga aturan modul saat mengintegrasikan:
 *   1. Jangan menambahkan Scaffold kedua.
 *   2. Letakkan editor di dalam konten yang sudah menerapkan innerPadding, dan jangan
 *      menerapkan padding dua kali.
 *   3. Tambahkan import yang belum ada — terutama rememberSaveable, OutlinedButton, dan
 *      Arrangement.
 *
 * Untuk mencoba percobaan rotasi Bagian 4.1, ganti sementara isi slot konten menjadi
 * BioDraftTest(Modifier.padding(innerPadding)) atau
 * BioDraftSaveableTest(Modifier.padding(innerPadding)); keduanya ada di paket `bab`.
 *
 * Untuk mencoba latihan, ganti StudentProfileScreen(...) dengan
 * Latihan4EditorNamaPanggilan(modifier = Modifier.padding(innerPadding)).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudentProfileEditorTheme {
                StudentProfileEditorApp()
            }
        }
    }
}

/** Satu-satunya Scaffold: app bar + konten layar. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileEditorApp() {
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

/** Layar: kartu profil dari modul sebelumnya, lalu editor bio baru. */
@Composable
fun StudentProfileScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileCard()
        StudentProfileEditor()
    }
}

/** Informasi profil dikelompokkan dalam Card Material 3 (tidak berubah dari modul lalu). */
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

/** Avatar tanpa berkas gambar: lingkaran berisi inisial dan label status di pojok kanan bawah. */
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
fun StudentProfileEditorAppPreview() {
    StudentProfileEditorTheme {
        StudentProfileEditorApp()
    }
}
