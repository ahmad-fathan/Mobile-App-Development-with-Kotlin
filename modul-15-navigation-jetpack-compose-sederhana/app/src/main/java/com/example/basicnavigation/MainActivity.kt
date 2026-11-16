package com.example.basicnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.navigation.BasicNavigationApp
import com.example.basicnavigation.navigation.Praktik2App
import com.example.basicnavigation.ui.LatihanScreen
import com.example.basicnavigation.ui.MateriScreen
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/**
 * Titik masuk aplikasi.
 *
 * Modul ini memuat DUA praktik yang di modul aslinya adalah dua proyek terpisah. Supaya cukup satu
 * proyek Android, [MenuPraktik] menjadi pemilih: Praktik 1 (navigasi dasar), Praktik 2 (kirim
 * argumen), ringkasan materi bab 1-5, dan latihan akhir. Tombol Back dari dalam salah satu layar
 * kembali ke menu, bukan menutup aplikasi.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicNavigationTheme {
                MenuPraktik()
            }
        }
    }
}

@Composable
fun MenuPraktik() {
    var layar by rememberSaveable { mutableIntStateOf(0) }
    BackHandler(enabled = layar != 0) { layar = 0 }

    when (layar) {
        1 -> BasicNavigationApp()
        2 -> Praktik2App()
        3 -> MateriScreen()
        4 -> LatihanScreen()
        else -> Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Modul 15 — Navigation", style = MaterialTheme.typography.headlineSmall)
            Text("Jetpack Compose", style = MaterialTheme.typography.bodyMedium)
            TombolMenu("Praktik 1 — Navigasi Dasar") { layar = 1 }
            TombolMenu("Praktik 2 — Kirim Argumen") { layar = 2 }
            TombolMenu("Materi — Bab 1 s.d. 5") { layar = 3 }
            TombolMenu("Latihan Akhir") { layar = 4 }
        }
    }
}

@Composable
private fun TombolMenu(teks: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
        Text(teks)
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauMenuPraktik() {
    BasicNavigationTheme {
        MenuPraktik()
    }
}
