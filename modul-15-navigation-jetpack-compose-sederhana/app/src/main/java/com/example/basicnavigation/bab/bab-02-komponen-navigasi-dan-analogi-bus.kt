package com.example.basicnavigation.bab

/**
 * Bab 2 — Komponen navigasi (Bagian 3 modul).
 *
 * Tiga komponen inti: NavController (menjalankan perintah + menyimpan riwayat), NavHost (wadah
 * layar yang tampil), NavGraph (peta destination). Analogi perjalanan bus pada Tabel 1 modul.
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
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Tabel 1 modul: komponen -> analogi -> peran. */
data class BarisAnalogi(val komponen: String, val analogi: String, val peran: String)

fun tabelAnalogiBab2(): List<BarisAnalogi> = listOf(
    BarisAnalogi("NavController", "Sopir", "Menjalankan perpindahan dan menyimpan riwayat"),
    BarisAnalogi("NavHost", "Jendela bus", "Menampilkan halte (layar) yang sedang disinggahi"),
    BarisAnalogi("NavGraph", "Peta rute", "Menentukan rute dan sub-rute yang boleh dilalui"),
    BarisAnalogi("Destination", "Halte", "Satu layar di dalam aplikasi")
)

@Composable
fun Bab2KomponenNavigasi() {
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Bab 2 — Komponen Navigasi", style = MaterialTheme.typography.headlineSmall)
        tabelAnalogiBab2().forEachIndexed { i, baris ->
            Text(
                "${baris.komponen} = ${baris.analogi} — ${baris.peran}",
                modifier = Modifier.padding(top = if (i == 0) 12.dp else 6.dp)
            )
        }
        Text(
            "NavController dibuat dengan rememberNavController() di tingkat paling atas hierarki " +
                "composable agar semua layar memakai controller yang sama.",
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            "NavHost wajib diberi startDestination. Kumpulan destination di dalam NavHost itulah " +
                "NavGraph; navigation(...) membuat graph bersarang untuk mengelompokkan layar.",
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauBab2() {
    BasicNavigationTheme { Bab2KomponenNavigasi() }
}
