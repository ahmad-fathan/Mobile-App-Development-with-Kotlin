package com.example.studentprofile.bab

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 8 (Bagian 8) — Scaffold: Struktur Sebuah Layar
 *
 * Scaffold menyusun kerangka layar sesuai pedoman Material Design. Ia TIDAK
 * menggantikan Row, Column, atau Box; ia hanya menempatkan bagian besar layar,
 * sedangkan layout buatan kita tetap berada di dalam kontennya.
 *
 * Slot yang perlu dikenal:
 *   - topBar : app bar di bagian atas, diisi TopAppBar
 *   - lambda konten (trailing lambda) : isi layar, menerima innerPadding
 *   - bottomBar dan floatingActionButton : slot lain yang tidak dipakai modul ini
 *
 * innerPadding bertipe PaddingValues dan berisi ruang yang dipakai app bar serta
 * bilah sistem (status bar dan navigation bar). Scaffold meneruskan nilai ini ke
 * konten tetapi TIDAK menerapkannya — tugas menerapkannya ada pada kita. Bila
 * diabaikan, konten digambar mulai dari bagian paling atas jendela sehingga kartu
 * profil tertutup app bar.
 *
 * innerPadding diteruskan SEKALI ke layout terluar layar. Ada dua padding dengan
 * tugas berbeda: innerPadding menjaga konten agar tidak tertutup bilah, sedangkan
 * 16.dp adalah jarak desain dari tepi layar yang kita pilih sendiri. Karena itu
 * hindari Scaffold kedua di dalam layar dan jangan menambahkan padding bilah sistem
 * sendiri, supaya padding tidak berlebihan.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContohScaffold() {
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
        Text(
            text = "Isi layar ditulis di sini.",
            modifier = Modifier.padding(innerPadding).padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ContohScaffoldPreview() {
    StudentProfileTheme {
        ContohScaffold()
    }
}
