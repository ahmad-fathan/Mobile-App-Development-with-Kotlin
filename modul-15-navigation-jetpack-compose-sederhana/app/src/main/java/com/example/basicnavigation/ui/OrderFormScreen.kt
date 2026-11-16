package com.example.basicnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/**
 * Bagian 6.4 + Latihan 1 — halaman formulir pesanan.
 *
 * Di modul, layar ini bernama `HomeScreen(onNavigateToDetails: (String, Int) -> Unit)`. Namanya
 * diubah menjadi [OrderFormScreen] karena `HomeScreen` Praktik 1 sudah ada di proyek yang sama.
 *
 * Aturan validasi (dipakai bersama [IzinPesanan] di `verifikasi.kt`):
 * tombol Submit hanya aktif bila nama produk tidak kosong **dan** jumlah dapat diubah dengan
 * `toIntOrNull()`.
 */
@Composable
fun OrderFormScreen(onNavigateToDetails: (String, Int) -> Unit) {
    var productName by rememberSaveable { mutableStateOf("") }
    var quantity by rememberSaveable { mutableStateOf("") }

    val jumlah = quantity.toIntOrNull()
    val bolehSubmit = productName.isNotBlank() && jumlah != null

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Order Form", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Product Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        )
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Button(
            onClick = {
                productName = ""
                quantity = ""
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Clear")
        }
        Button(
            onClick = { onNavigateToDetails(productName, jumlah ?: 0) },
            enabled = bolehSubmit,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauOrderFormScreen() {
    BasicNavigationTheme {
        OrderFormScreen(onNavigateToDetails = { _, _ -> })
    }
}
