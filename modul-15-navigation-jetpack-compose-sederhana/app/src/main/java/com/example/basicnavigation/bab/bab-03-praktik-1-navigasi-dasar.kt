package com.example.basicnavigation.bab

/**
 * Bab 3 — Praktik 1: aplikasi tiga layar (Bagian 4 modul).
 *
 * Layar sungguhannya ada di paket `ui`:
 * [com.example.basicnavigation.ui.HomeScreen],
 * [com.example.basicnavigation.ui.ProfileScreen],
 * [com.example.basicnavigation.ui.DetailScreen];
 * penghubungnya [com.example.basicnavigation.navigation.BasicNavigationApp].
 *
 * Isi berkas ini hanya ringkasan langkah praktik supaya mahasiswa bisa membacanya di aplikasi
 * sambil mencocokkan kode.
 */

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.navigation.Routes
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Route Praktik 1 diambil langsung dari [Routes], bukan ditulis ulang sebagai string. */
fun daftarRouteBab3(): List<String> = listOf(
    "Routes.Home.route    = ${Routes.Home.route}",
    "Routes.Profile.route = ${Routes.Profile.route}",
    "Routes.Detail.route  = ${Routes.Detail.route}"
)

@Composable
fun Bab3Praktik1() {
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Bab 3 — Praktik 1", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Tambahkan dependensi navigation-compose, buat Routes (sealed class), buat tiga layar, " +
                "lalu pasang NavHost di BasicNavigationApp dengan startDestination Routes.Home.",
            modifier = Modifier.padding(top = 8.dp)
        )
        daftarRouteBab3().forEach { Text(it, modifier = Modifier.padding(top = 8.dp)) }
        Text(
            "Uji back stack: Home -> Profile -> Detail, lalu Go Back dua kali. Hasilnya kembali ke " +
                "Home. Tombol Back sistem memberi perilaku yang sama dengan popBackStack().",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauBab3() {
    BasicNavigationTheme { Bab3Praktik1() }
}
