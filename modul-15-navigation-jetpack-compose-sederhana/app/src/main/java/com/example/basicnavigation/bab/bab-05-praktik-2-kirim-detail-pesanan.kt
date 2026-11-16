package com.example.basicnavigation.bab

/**
 * Bab 5 — Praktik 2: mengirim detail pesanan antarhalaman (Bagian 6 modul).
 *
 * Alur data satu kalimat: formulir memanggil `navController.navigate(OrderDetails(...))`, lalu
 * halaman detail membaca argumen tersebut dengan `toRoute<OrderDetails>()` dan menampilkannya.
 *
 * Layar: [com.example.basicnavigation.ui.OrderFormScreen] (form) dan
 * [com.example.basicnavigation.ui.OrderDetailsScreen] (detail).
 * Penghubung: [com.example.basicnavigation.navigation.Praktik2App].
 * Aturan validasi Submit diuji lewat [com.example.basicnavigation.IzinPesanan].
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
import com.example.basicnavigation.IzinPesanan
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Contoh isian dan keputusan tombol Submit. */
fun contohValidasiBab5(): List<String> = listOf(
    IzinPesanan("Keyboard", "2"),
    IzinPesanan("", "2"),
    IzinPesanan("Keyboard", "dua")
).map { isian ->
    val status = if (isian.bolehSubmit) "Submit aktif" else "Submit mati: ${isian.pesanGalat}"
    "Product Name = \"${isian.productName}\", Quantity = \"${isian.quantity}\" -> $status"
}

@Composable
fun Bab5Praktik2() {
    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
        Text("Bab 5 — Praktik 2", style = MaterialTheme.typography.headlineSmall)
        Text(
            "Formulir berisi Product Name, Quantity, tombol Clear, dan tombol Submit. Submit hanya " +
                "aktif bila nama tidak kosong dan jumlah berupa bilangan bulat (toIntOrNull()).",
            modifier = Modifier.padding(top = 8.dp)
        )
        contohValidasiBab5().forEach { Text(it, modifier = Modifier.padding(top = 8.dp)) }
        Text(
            "Setelah Submit, halaman detail menampilkan nama produk dan jumlah. Tombol Back " +
                "memanggil popBackStack() sehingga formulir tampil kembali.",
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauBab5() {
    BasicNavigationTheme { Bab5Praktik2() }
}
