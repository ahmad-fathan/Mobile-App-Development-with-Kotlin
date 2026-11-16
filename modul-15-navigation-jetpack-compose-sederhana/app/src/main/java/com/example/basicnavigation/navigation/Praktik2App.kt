package com.example.basicnavigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.basicnavigation.ui.OrderDetailsScreen
import com.example.basicnavigation.ui.OrderFormScreen

/**
 * Bagian 6.5 — NavHost Praktik 2 (di modul bernama `MainScreen()`).
 *
 * Tiga hal yang perlu diperhatikan:
 * - destination didaftarkan dengan `composable<Home>` / `composable<OrderDetails>`, bukan string;
 * - perpindahan mengirim objek [OrderDetails], bukan path string;
 * - `toRoute<OrderDetails>()` membangun kembali objek itu lengkap dengan tipenya.
 *
 * `OrderFormScreen` hanya melaporkan "Submit ditekan" lewat lambda, jadi keputusan ke mana
 * berpindah tetap dipegang NavHost.
 */
@Composable
fun Praktik2App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            OrderFormScreen(
                onNavigateToDetails = { productName, quantity ->
                    navController.navigate(OrderDetails(productName, quantity))
                }
            )
        }
        composable<OrderDetails> { backStackEntry ->
            val args = backStackEntry.toRoute<OrderDetails>()
            OrderDetailsScreen(
                navController = navController,
                productName = args.productName,
                quantity = args.quantity
            )
        }
    }
}
