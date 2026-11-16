package com.example.basicnavigation.latihan

/**
 * Latihan akhir modul 15 (Navigation with Jetpack Compose).
 *
 * Satu fungsi per soal. Lima soal modul, ditambah dua soal pengayaan bertanda (+) yang memakai
 * konsep yang sama (argumen tambahan dan back stack bersih).
 */

import com.example.basicnavigation.Entri
import com.example.basicnavigation.IzinPesanan
import com.example.basicnavigation.pushDenganPopUpTo
import com.example.basicnavigation.push

/**
 * Latihan 1 — melengkapi formulir pesanan.
 *
 * Layar sungguhannya [com.example.basicnavigation.ui.OrderFormScreen]; di sini aturannya saja yang
 * diperiksa, supaya bisa dijalankan tanpa emulator.
 */
fun latihan1ValidasiFormulir(): List<String> = listOf("", "  ", "dua", "2.5", "2").map { jumlah ->
    val isian = IzinPesanan("Keyboard", jumlah)
    "quantity=\"$jumlah\" -> bolehSubmit=${isian.bolehSubmit} (${isian.pesanGalat.ifEmpty { "valid" }})"
}

/** Latihan 2 — isi back stack saat tombol Back di halaman detail ditekan. */
fun latihan2BackDariDetail(): List<String> {
    val formulir = Entri("Home")
    val detail = Entri("OrderDetails", mapOf("productName" to "Keyboard", "quantity" to "2"))
    val stack = push(listOf(formulir), detail)
    val setelahBack = stack.dropLast(1)
    return listOf(
        "Sebelum Back : ${stack.map { it.route }}",
        "Sesudah Back : ${setelahBack.map { it.route }}",
        "Penjelasan   : entri OrderDetails dibuang dari puncak, formulir (Home) yang tersisa tampil lagi"
    )
}

/** Latihan 3 — Praktik 1 versi type-safe: route berupa object @Serializable, bukan string. */
fun latihan3RouteTypesafe(): List<String> = listOf(
    "@Serializable object Home / Profile / Detail",
    "composable<Home> { HomeScreen(navController) }",
    "navController.navigate(Profile)  // bukan navigate(\"profile\")",
    "Keuntungan: salah nama route ditolak compiler, bukan saat aplikasi berjalan"
)

/**
 * Latihan 4 — menambah argumen `price: Double` pada OrderDetails.
 *
 * Total = price * quantity. Karena route memakai data class, menambah argumen cukup di satu tempat:
 * deklarasi data class-nya, lalu pengiriman dan pembacaannya ikut menyesuaikan.
 */
data class OrderDetailsBerharga(val productName: String, val quantity: Int, val price: Double) {
    val total: Double get() = price * quantity
}

fun latihan4TotalHarga(): List<String> {
    val pesanan = OrderDetailsBerharga("Keyboard", 2, 250_000.0)
    return listOf(
        "Satuan : Rp${pesanan.price}",
        "Jumlah : ${pesanan.quantity}",
        "Total  : Rp${pesanan.total}"
    )
}

/**
 * Latihan 5 — tombol New Order kembali ke formulir dengan back stack bersih.
 *
 * `popBackStack()` saja tidak cukup: ia hanya membuang layar teratas, sehingga formulir masih
 * menyimpan entri lama dan tombol Back sistem akan kembali ke detail pesanan yang sudah selesai.
 * `popUpTo(Home) { inclusive = true }` membuang entri Home lama, lalu formulir baru diletakkan
 * sebagai satu-satunya entri sehingga Back langsung menutup aplikasi.
 */
fun latihan5NewOrderBersih(): List<String> {
    val home = Entri("Home")
    val detail = Entri("OrderDetails", mapOf("productName" to "Keyboard", "quantity" to "2"))
    val stack = push(listOf(home), detail)
    val popSaja = stack.dropLast(1)
    val bersih = pushDenganPopUpTo(stack, Entri("Home"), "Home", inclusive = true)
    return listOf(
        "Sebelum New Order : ${stack.map { it.route }}",
        "popBackStack()    : ${popSaja.map { it.route }} -> Back masih bisa membuka detail lama",
        "popUpTo(inclusive): ${bersih.map { it.route }} -> Back langsung menutup aplikasi"
    )
}

/** Pengayaan (+) — argumen bertipe salah ditolak compiler, bukan saat aplikasi berjalan. */
fun latihan6ArgumenBertipe(): List<String> = listOf(
    "navController.navigate(OrderDetails(\"Keyboard\", 2))   // benar",
    "navController.navigate(OrderDetails(\"Keyboard\", \"2\"))  // ditolak compiler: Intvs String",
    "toRoute<OrderDetails>() mengembalikan objek lengkap dengan tipenya"
)

/** Pengayaan (+) — mengapa launchSingleTop mencegah layar ganda saat tombol ditekan dua kali. */
fun latihan7SingleTop(): List<String> = listOf(
    "Tanpa singleTop  : [Home, Profile, Profile] -> Back terasa \"tidak berfungsi\"",
    "Dengan singleTop : [Home, Profile] -> Back langsung kembali ke Home"
)
