package com.example.basicnavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.latihan.latihan1ValidasiFormulir
import com.example.basicnavigation.latihan.latihan2BackDariDetail
import com.example.basicnavigation.latihan.latihan3RouteTypesafe
import com.example.basicnavigation.latihan.latihan4TotalHarga
import com.example.basicnavigation.latihan.latihan5NewOrderBersih
import com.example.basicnavigation.latihan.latihan6ArgumenBertipe
import com.example.basicnavigation.latihan.latihan7SingleTop

/** Menampilkan hasil tujuh latihan akhir modul. */
@Composable
fun LatihanScreen() {
    val bagian = listOf(
        "Latihan 1 — Validasi formulir pesanan" to latihan1ValidasiFormulir(),
        "Latihan 2 — Back dari halaman detail" to latihan2BackDariDetail(),
        "Latihan 3 — Praktik 1 versi type-safe" to latihan3RouteTypesafe(),
        "Latihan 4 — Argumen price dan total harga" to latihan4TotalHarga(),
        "Latihan 5 — New Order dengan back stack bersih" to latihan5NewOrderBersih(),
        "Latihan 6 (+) — Argumen bertipe" to latihan6ArgumenBertipe(),
        "Latihan 7 (+) — launchSingleTop" to latihan7SingleTop()
    )

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp)) {
        Text("Latihan Akhir", style = MaterialTheme.typography.headlineSmall)
        bagian.forEachIndexed { i, (judul, baris) ->
            Text(judul, modifier = Modifier.padding(top = if (i == 0) 16.dp else 24.dp))
            if (i > 0) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(top = 4.dp))
            }
            baris.forEach { teks ->
                Text(teks, modifier = Modifier.padding(top = 6.dp))
            }
        }
        Text("", modifier = Modifier.padding(bottom = 24.dp))
    }
}
