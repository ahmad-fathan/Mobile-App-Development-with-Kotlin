package com.example.basicnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/**
 * Bagian 6.4 + Latihan 2 — halaman detail pesanan.
 *
 * Tombol Back memanggil `popBackStack()`, sehingga entri OrderDetails dibuang dari back stack dan
 * halaman formulir di bawahnya tampil kembali. Formulir itu **tidak dibuat ulang** selama masih di
 * back stack, tetapi isian disimpan dengan `rememberSaveable` agar tetap ada bila dibuat ulang.
 */
@Composable
fun OrderDetailsScreen(
    navController: NavController,
    productName: String,
    quantity: Int
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Order Details", style = MaterialTheme.typography.headlineSmall)
        Text("Product: $productName", modifier = Modifier.padding(top = 16.dp))
        Text("Quantity: $quantity", modifier = Modifier.padding(top = 8.dp))
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauOrderDetailsScreen() {
    BasicNavigationTheme {
        OrderDetailsScreen(
            navController = rememberNavController(),
            productName = "Keyboard",
            quantity = 2
        )
    }
}
