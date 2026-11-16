package com.example.basicnavigation.bab

/**
 * Bab 4 — Mengirim argumen antarlayar (Bagian 5 modul).
 *
 * Dua pendekatan:
 * - traditional: route string berplaceholder (`details/{name}/{qty}`), argumen diambil manual dari
 *   `backStackEntry.arguments`. Nama dan tipe ditulis di tiga tempat -> rawan salah ketik.
 * - type-safe: route berupa data class `@Serializable`, diambil dengan `toRoute<T>()`. Kesalahan
 *   tipe dan nama terdeteksi saat kompilasi.
 *
 * Modul memakai pendekatan type-safe pada Praktik 2. Route type-safe ada di
 * `navigation/Routes.kt` ([com.example.basicnavigation.navigation.OrderDetails]).
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
import com.example.basicnavigation.navigation.OrderDetails
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Tabel 2 modul: perbandingan kedua pendekatan. */
fun tabelPerbandinganBab4(): List<String> = listOf(
    "Traditional — route string, argumen diambil manual dari arguments. " +
        "Sederhana tetapi rapuh: nama dan tipe argumen ditangani sendiri.",
    "Type-safe — route data class, argumen diambil dengan toRoute(). " +
        "Lebih andal: kesalahan tipe dan nama terdeteksi saat kompilasi."
)

/** Contoh objek route yang benar-benar dikirim Praktik 2. */
fun contohRouteBab4(): String = OrderDetails(productName = "Keyboard", quantity = 2).toString()

@Composable
fun Bab4ArgumenNavigasi() {
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Bab 4 — Argumen Antarlayar", style = MaterialTheme.typography.headlineSmall)
        tabelPerbandinganBab4().forEachIndexed { i, baris ->
            Text(baris, modifier = Modifier.padding(top = if (i == 0) 12.dp else 8.dp))
        }
        Text(
            "Start destination Praktik 2 adalah object Home; route berargumen adalah data class " +
                "OrderDetails. Keduanya sama-sama ditandai @Serializable.",
            modifier = Modifier.padding(top = 16.dp)
        )
        Text("Object Home: Home (tanpa argumen, diakses sebagai Home)", modifier = Modifier.padding(top = 8.dp))
        Text("Data class : ${contohRouteBab4()}", modifier = Modifier.padding(top = 4.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauBab4() {
    BasicNavigationTheme { Bab4ArgumenNavigasi() }
}
