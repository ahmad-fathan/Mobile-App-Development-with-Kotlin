package com.example.basicnavigation.navigation

import kotlinx.serialization.Serializable

/**
 * Bagian 4.3 — daftar route Praktik 1 (pendekatan traditional).
 *
 * Dengan `sealed class`, daftar route menjadi tertutup dan terpusat: salah ketik
 * `Routes.Profil` ditolak compiler, sedangkan salah ketik `"profil"` baru ketahuan saat aplikasi
 * berjalan.
 */
sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Profile : Routes("profile")
    object Detail : Routes("details")
}

/**
 * Bagian 6.3 — route Praktik 2 (type-safe).
 *
 * [Home] berupa `object` karena tidak membawa argumen. [OrderDetails] berupa `data class` karena
 * membawa dua argumen; library Navigation yang mengurus encoding/decoding dan pemeriksaan tipe.
 *
 * Catatan: di modul, route formulir juga bernama `Home`. Di sini namanya tetap `Home`, sedangkan
 * layarnya bernama [com.example.basicnavigation.ui.OrderFormScreen] supaya tidak bentrok dengan
 * `HomeScreen` Praktik 1.
 */
@Serializable
object Home

@Serializable
data class OrderDetails(
    val productName: String,
    val quantity: Int
)
