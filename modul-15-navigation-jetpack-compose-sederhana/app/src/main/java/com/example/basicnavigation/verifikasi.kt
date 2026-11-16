package com.example.basicnavigation

/**
 * Aturan navigasi modul 15 yang bisa dihitung tanpa Android, beserta pemeriksaannya.
 *
 * Berkas ini hanya memakai Kotlin stdlib — tanpa Compose dan tanpa AndroidX — sehingga dapat
 * diuji tanpa emulator:
 *
 *   kotlinc verifikasi.kt -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * dari root repo: `./jalankan.sh 01 perilaku` (modul 01 = modul 15, lihat README).
 *
 * Layar bab dan layar aplikasi MENGIMPOR model di berkas ini, jadi yang diuji benar-benar aturan
 * yang dipakai aplikasi.
 */

// ---------------------------------------------------------------------------
// 1. Model back stack (Bagian 2 dan 3)
// ---------------------------------------------------------------------------

/** Satu entri back stack: route + argumennya (disimpan sebagai teks, seperti saved state). */
data class Entri(
    val route: String,
    val argumen: Map<String, String> = emptyMap()
) {
    /** Label entri, mis. `details(productName=Keyboard, quantity=2)`. */
    val label: String
        get() = if (argumen.isEmpty()) {
            route
        } else {
            "$route(${argumen.entries.joinToString(", ") { "${it.key}=${it.value}" }})"
        }
}

/** Aplikasi dibuka: hanya start destination yang ada di tumpukan. */
fun backStackAwal(start: Entri): List<Entri> = listOf(start)

/** `navigate()` = push: layar baru diletakkan di atas tumpukan. */
fun push(stack: List<Entri>, baru: Entri): List<Entri> = stack + baru

/** Tombol Back = pop: entri teratas dibuang. Tumpukan kosong berarti aplikasi ditutup. */
fun pop(stack: List<Entri>): List<Entri> = stack.dropLast(1)

/**
 * `navigate(route) { popUpTo(target) { inclusive = ... } }`.
 *
 * Buang semua entri di atas [target]; bila [inclusive] true, [target] ikut dibuang.
 * Bila [target] tidak ada di tumpukan, tidak ada yang dibuang (sama seperti NavController).
 */
fun pushDenganPopUpTo(
    stack: List<Entri>,
    baru: Entri,
    target: String,
    inclusive: Boolean = false
): List<Entri> {
    val posisi = stack.indexOfLast { it.route == target }
    val sisa = if (posisi < 0) stack else stack.take(if (inclusive) posisi else posisi + 1)
    return sisa + baru
}

/** `launchSingleTop = true`: entri identik di puncak tidak ditambahkan lagi. */
fun pushSingleTop(stack: List<Entri>, baru: Entri): List<Entri> =
    if (stack.lastOrNull()?.route == baru.route) stack else stack + baru

// ---------------------------------------------------------------------------
// 2. Aturan validasi formulir pesanan (Bagian 6.4 + Latihan 1)
// ---------------------------------------------------------------------------

/** Hasil pembacaan isian formulir pesanan. */
data class IzinPesanan(val productName: String, val quantity: String) {
    /** `toIntOrNull()` — null berarti isian jumlah bukan bilangan bulat. */
    val jumlah: Int? get() = quantity.toIntOrNull()

    /** Submit hanya boleh bila nama tidak kosong DAN jumlah berupa bilangan bulat. */
    val bolehSubmit: Boolean
        get() = productName.isNotBlank() && jumlah != null

    /** Pesan yang ditampilkan di bawah tombol Submit. */
    val pesanGalat: String
        get() = when {
            !bolehSubmit && productName.isBlank() -> "Product name cannot be empty"
            !bolehSubmit -> "Quantity must be a whole number"
            else -> ""
        }
}

// ---------------------------------------------------------------------------
// 3. Pemeriksaan
// ---------------------------------------------------------------------------

private var jumlahCek: Int = 0
private var gagal: Int = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCek++
    if (kondisi) {
        println("OK    $deskripsi")
    } else {
        gagal++
        println("GAGAL $deskripsi")
    }
}

fun main() {
    val home = Entri("home")
    val profile = Entri("profile")
    val detail = Entri("details")

    // --- back stack Praktik 1 (Gambar 2 modul) ---
    val awal = backStackAwal(home)
    cek("Aplikasi dibuka: hanya home di back stack", awal == listOf(home))
    val keProfile = push(awal, profile)
    cek("Home -> Profile: [home, profile]", keProfile.map { it.route } == listOf("home", "profile"))
    val keDetail = push(keProfile, detail)
    cek("Profile -> Detail: [home, profile, details]",
        keDetail.map { it.route } == listOf("home", "profile", "details"))
    cek("Detail ada di puncak tumpukan", keDetail.last() == detail)
    val kembali1 = pop(keDetail)
    cek("Back dari Detail: [home, profile]", kembali1.map { it.route } == listOf("home", "profile"))
    val kembali2 = pop(kembali1)
    cek("Back dari Profile: [home]", kembali2.map { it.route } == listOf("home"))
    cek("Back dari Home mengosongkan tumpukan (aplikasi ditutup)", pop(kembali2).isEmpty())
    cek("pop() pada tumpukan kosong tidak error", pop(emptyList()).isEmpty())

    // --- popUpTo (Bagian 3.1 + Latihan 5) ---
    val homeBaru = Entri("home")
    val bersih = pushDenganPopUpTo(keDetail, homeBaru, "home", inclusive = true)
    cek("popUpTo(home, inclusive = true) menyisakan satu home baru", bersih == listOf(homeBaru))
    cek("Setelah popUpTo, Back langsung menutup aplikasi", pop(bersih).isEmpty())
    val tanpaBuangHome = pushDenganPopUpTo(keDetail, detail, "profile", inclusive = false)
    cek("popUpTo(profile, inclusive = false) mempertahankan home dan profile",
        tanpaBuangHome.map { it.route } == listOf("home", "profile", "details"))
    cek("popUpTo tanpa target yang ada tidak membuang apa pun",
        pushDenganPopUpTo(keDetail, detail, "about").size == 4)

    // --- singleTop ---
    cek("launchSingleTop tidak menumpuk route yang sama",
        pushSingleTop(listOf(home, profile), profile) == listOf(home, profile))

    // --- label entri berargumen (type-safe route) ---
    val pesanan = Entri("OrderDetails", mapOf("productName" to "Keyboard", "quantity" to "2"))
    cek("Label entri memuat argumen",
        pesanan.label == "OrderDetails(productName=Keyboard, quantity=2)")

    // --- validasi formulir (Latihan 1) ---
    cek("Nama kosong ditolak", !IzinPesanan("", "2").bolehSubmit)
    cek("Nama hanya spasi ditolak", !IzinPesanan("   ", "2").bolehSubmit)
    cek("Jumlah kosong ditolak", !IzinPesanan("Keyboard", "").bolehSubmit)
    cek("Jumlah bukan angka ditolak", !IzinPesanan("Keyboard", "dua").bolehSubmit)
    cek("Jumlah desimal ditolak", !IzinPesanan("Keyboard", "2.5").bolehSubmit)
    cek("Isian lengkap dan valid diterima", IzinPesanan("Keyboard", "2").bolehSubmit)
    cek("Jumlah dibaca sebagai bilangan bulat", IzinPesanan("Keyboard", "2").jumlah == 2)
    cek("Pesan galat menyebut nama produk", IzinPesanan("", "2").pesanGalat.contains("name"))
    cek("Pesan galat menyebut jumlah", IzinPesanan("Keyboard", "x").pesanGalat.contains("Quantity"))
    cek("Tidak ada pesan galat saat isian valid", IzinPesanan("Keyboard", "2").pesanGalat.isEmpty())

    println()
    println(if (gagal == 0) "SEMUA OK ($jumlahCek cek)" else "$gagal GAGAL dari $jumlahCek cek")
    if (gagal != 0) {
        kotlin.system.exitProcess(1)
    }
}
