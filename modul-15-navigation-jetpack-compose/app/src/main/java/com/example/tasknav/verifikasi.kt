package com.example.tasknav

/**
 * Aturan, tabel, dan model back stack modul 15 (Navigation with Jetpack Compose)
 * beserta pemeriksaannya.
 *
 * Berkas ini memakai Kotlin stdlib saja — tanpa Compose dan tanpa Android — sehingga aturan
 * navigasi dapat diuji tanpa emulator:
 *
 *   kotlinc verifikasi.kt -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * dari root repositori: `./jalankan.sh 15 perilaku`.
 *
 * Inti yang diuji: urutan entri back stack (push/pop), efek `popUpTo` + `inclusive` +
 * `launchSingleTop`, dan aturan "kirim ID, bukan objek".
 */

// ---------------------------------------------------------------------------
// 0. Penghitung pemeriksaan (jangan menulis total manual)
// ---------------------------------------------------------------------------

var jumlahCekDijalankan: Int = 0
private var gagal: Int = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCekDijalankan++
    if (kondisi) {
        println("OK    $deskripsi")
    } else {
        gagal++
        println("GAGAL $deskripsi")
    }
}

// ---------------------------------------------------------------------------
// 1. Model back stack (Bagian 4 dan 10 modul)
// ---------------------------------------------------------------------------

/** Satu entri back stack: identitas route + argumennya (disimpan sebagai teks, seperti saved state). */
data class Entri(val route: String, val argumen: Map<String, String> = emptyMap()) {
    /** Label entri, mis. `TaskDetail(taskId=2)` — dipakai tabel dan layar bab. */
    val label: String
        get() = if (argumen.isEmpty()) route else "$route(${argumen.entries.joinToString(", ") { "${it.key}=${it.value}" }})"
}

/** Layar pertama yang tampil saat aplikasi dibuka = entri paling bawah. */
fun backStackAwal(start: Entri): List<Entri> = listOf(start)

/** `navigate()` = push: entri baru di atas tumpukan. */
fun push(stack: List<Entri>, baru: Entri): List<Entri> = stack + baru

/** Tombol Back = pop: entri teratas dibuang. Stack kosong berarti aplikasi ditutup. */
fun pop(stack: List<Entri>): List<Entri> = stack.dropLast(1)

/**
 * `navigate(route) { launchSingleTop = true }`: salinan kedua tidak ditambahkan bila destination
 * yang sama sudah berada di puncak (mencegah ketukan ganda menumpuk dua layar identik).
 */
fun pushSingleTop(stack: List<Entri>, baru: Entri): List<Entri> =
    if (stack.lastOrNull()?.route == baru.route) stack else stack + baru

/**
 * `navigate(route) { popUpTo<Target> { inclusive = ... } }`.
 *
 * `inclusive = false` mempertahankan Target, `inclusive = true` ikut membuangnya.
 */
fun pushDenganPopUpTo(
    stack: List<Entri>,
    baru: Entri,
    target: String,
    inclusive: Boolean
): List<Entri> {
    val indeks = stack.indexOfLast { it.route == target }
    if (indeks < 0) return stack + baru
    val sisa = stack.take(if (inclusive) indeks else indeks + 1)
    return sisa + baru
}

/** Dipakai tabel: label seluruh entri dari bawah ke atas. */
fun labelBackStack(stack: List<Entri>): String = stack.joinToString(" → ") { it.label }

// ---------------------------------------------------------------------------
// 2. Tabel 1 — lima komponen dasar
// ---------------------------------------------------------------------------

data class Komponen(val istilah: String, val pengertian: String, val analogi: String)

val komponenDasar: List<Komponen> = listOf(
    Komponen("Destination", "Satu layar yang dapat dituju, berupa sebuah composable", "Sebuah ruangan"),
    Komponen("Route", "Identitas unik sebuah destination", "Nomor ruangan"),
    Komponen("Argument", "Data yang ikut dikirim bersama route", "Catatan \"bawa berkas nomor 2\""),
    Komponen("NavHost", "Composable wadah yang menampilkan destination aktif", "Pintu untuk melihat ruangan"),
    Komponen("NavController", "Objek yang melakukan navigasi dan menyimpan back stack", "Pemandu yang mengingat jalur")
)

// ---------------------------------------------------------------------------
// 3. Tabel 2 — route berbasis string vs type-safe
// ---------------------------------------------------------------------------

data class PerbandinganRoute(val aspek: String, val stringRoute: String, val typeSafe: String)

val perbandinganRoute: List<PerbandinganRoute> = listOf(
    PerbandinganRoute("Bentuk route", "\"detail/{taskId}\"", "TaskDetail"),
    PerbandinganRoute("Deklarasi argumen", "Daftar navArgument", "Properti data class"),
    PerbandinganRoute("Membaca argumen", "arguments?.getInt(\"taskId\")", "toRoute()"),
    PerbandinganRoute("Kesalahan ketik", "Baru terlihat saat aplikasi berjalan", "Error kompilasi")
)

// ---------------------------------------------------------------------------
// 4. Tabel 5 — tipe argumen yang didukung
// ---------------------------------------------------------------------------

/** Tipe yang langsung dapat dipakai sebagai argumen navigasi. */
val tipeArgumenLangsung: Set<String> = setOf("Int", "Long", "Float", "Double", "Boolean", "String")

/** Tipe yang membutuhkan kerja tambahan. */
val tipeArgumenTambahan: Set<String> = setOf("Kelas buatan sendiri", "Enum", "Nullable dari tipe dasar", "List")

fun didukungLangsung(tipe: String): Boolean = tipe in tipeArgumenLangsung

// ---------------------------------------------------------------------------
// 5. Tabel 4 — cara kembali ke layar sebelumnya
// ---------------------------------------------------------------------------

data class CaraKembali(val aksi: String, val kode: String, val efek: String)

val caraKembali: List<CaraKembali> = listOf(
    CaraKembali("Gestur Back sistem", "(tidak perlu kode)", "NavHost membuang entri teratas"),
    CaraKembali("Tombol Up di app bar", "navController.navigateUp()", "Kembali ke layar sebelumnya"),
    CaraKembali("Membuang entri eksplisit", "navController.popBackStack()", "Membuang entri teratas")
)

// ---------------------------------------------------------------------------
// 6. Tabel 6 — opsi pengendali back stack
// ---------------------------------------------------------------------------

data class OpsiBackStack(val opsi: String, val efek: String)

val opsiBackStack: List<OpsiBackStack> = listOf(
    OpsiBackStack("popUpTo<T>", "Membuang entri di atas T sebelum menambah entri baru"),
    OpsiBackStack("inclusive = true", "Ikut membuang entri T sendiri"),
    OpsiBackStack("launchSingleTop = true", "Tidak menambah salinan bila destination sudah di puncak")
)

// ---------------------------------------------------------------------------
// 7. Tabel 7 — skenario pengujian
// ---------------------------------------------------------------------------

data class SkenarioUji(val nomor: Int, val langkah: String, val harapan: String)

val skenarioUji: List<SkenarioUji> = listOf(
    SkenarioUji(1, "Jalankan aplikasi", "Layar My Tasks menampilkan tiga tugas"),
    SkenarioUji(2, "Ketuk \"Submit lab report\"", "Detail berjudul Task #2 dengan Computer Networks"),
    SkenarioUji(3, "Ketuk Back di app bar", "Kembali ke My Tasks"),
    SkenarioUji(4, "Buka tugas 3, lalu putar emulator", "Tetap menampilkan Quiz: SQL joins, Status: done"),
    SkenarioUji(5, "Gestur Back sistem di layar daftar", "Aplikasi tertutup")
)

// ---------------------------------------------------------------------------
// 8. Tabel 8 — kesalahan umum
// ---------------------------------------------------------------------------

data class KesalahanPerbaikan(val kasus: String, val gejala: String, val perbaikan: String)

val kesalahanUmumNavigasi: List<KesalahanPerbaikan> = listOf(
    KesalahanPerbaikan("Route tanpa @Serializable", "Crash saat NavHost dibangun", "Tambahkan anotasi pada route"),
    KesalahanPerbaikan("Plugin serialization belum dipasang", "Error build atau error serializer", "Tambahkan kotlin.serialization, lalu sync"),
    KesalahanPerbaikan("navigate() di badan composable", "Layar terus ditumpuk", "Panggil hanya di dalam lambda event"),
    KesalahanPerbaikan("NavController dikirim ke screen", "Screen sulit di-preview dan diuji", "Kirim lambda event"),
    KesalahanPerbaikan("Objek utuh sebagai argumen", "Data usang, batas saved state", "Kirim ID, cari data di tujuan")
)

// ---------------------------------------------------------------------------
// 9. Langkah selanjutnya — Navigation 3
// ---------------------------------------------------------------------------

data class PerbedaanNav3(val aspek: String, val navCompose: String, val nav3: String)

val perbedaanNavigation3: List<PerbedaanNav3> = listOf(
    PerbedaanNav3("Pemilik back stack", "NavController", "Daftar key di state Compose aplikasi"),
    PerbedaanNav3("Wadah destination", "NavHost", "NavDisplay"),
    PerbedaanNav3("Pendaftaran destination", "composable<Route>", "entryProvider")
)

// ---------------------------------------------------------------------------
// 10. Pemeriksaan
// ---------------------------------------------------------------------------

private fun verifikasiPerilakuModul15(): Boolean {
    jumlahCekDijalankan = 0
    gagal = 0

    println("Verifikasi Modul 15 — Navigation with Jetpack Compose")
    println()

    // --- Back stack dasar ---------------------------------------------------
    val daftar = Entri("TaskList")
    val awal = backStackAwal(daftar)
    cek("start destination adalah entri paling bawah", awal.size == 1 && awal.single().route == "TaskList")

    val setelahDetail2 = push(awal, Entri("TaskDetail", mapOf("taskId" to "2")))
    cek("navigate() menambah entri di atas", setelahDetail2.size == 2)
    cek("entri teratas adalah layar yang terlihat",
        setelahDetail2.last().argumen["taskId"] == "2")
    cek("entri bawah tetap start destination", setelahDetail2.first().route == "TaskList")
    cek("label entri memuat argumen", setelahDetail2.last().label == "TaskDetail(taskId=2)")

    val setelahBack = pop(setelahDetail2)
    cek("Back membuang entri teratas", setelahBack.size == 1 && setelahBack.single().route == "TaskList")
    cek("Back pada start destination menutup aplikasi", pop(setelahBack).isEmpty())

    val setelahDetail3 = push(awal, Entri("TaskDetail", mapOf("taskId" to "3")))
    cek("detail 2 dibuang, detail 3 di atas daftar",
        labelBackStack(push(pop(setelahDetail2), Entri("TaskDetail", mapOf("taskId" to "3")))) ==
            labelBackStack(setelahDetail3))

    // Latihan 2: buka 1, Back, buka 2, buka 3 lewat tautan di detail.
    var latihan2 = backStackAwal(daftar)
    latihan2 = push(latihan2, Entri("TaskDetail", mapOf("taskId" to "1")))
    latihan2 = pop(latihan2)
    latihan2 = push(latihan2, Entri("TaskDetail", mapOf("taskId" to "2")))
    latihan2 = push(latihan2, Entri("TaskDetail", mapOf("taskId" to "3")))
    cek("Latihan 2: stack = TaskList, Detail(2), Detail(3)",
        labelBackStack(latihan2) == "TaskList → TaskDetail(taskId=2) → TaskDetail(taskId=3)")
    cek("Back sekali menampilkan Detail(2) — Detail(3) dibuang", pop(latihan2).last().argumen["taskId"] == "2")
    cek("Back kedua menampilkan TaskList", pop(pop(latihan2)).last().route == "TaskList")

    // --- popUpTo dan launchSingleTop (Bagian 10) ---------------------------
    val welcome = backStackAwal(Entri("Welcome"))
    val tanpaPop = push(welcome, Entri("TaskList"))
    cek("tanpa popUpTo, Welcome tetap di bawah TaskList", tanpaPop.size == 2)

    val denganPopInclusive = pushDenganPopUpTo(welcome, Entri("TaskList"), "Welcome", inclusive = true)
    cek("popUpTo inclusive=true menyisakan satu entri", denganPopInclusive.size == 1)
    cek("Back setelah popUpTo inclusive menutup aplikasi", pop(denganPopInclusive).isEmpty())

    val denganPopEksklusif = pushDenganPopUpTo(welcome, Entri("TaskList"), "Welcome", inclusive = false)
    cek("popUpTo tanpa inclusive mempertahankan Welcome",
        labelBackStack(denganPopEksklusif) == "Welcome → TaskList")

    val ganda = pushSingleTop(denganPopInclusive, Entri("TaskList"))
    cek("launchSingleTop mencegah salinan ganda", ganda.size == 1)
    cek("push biasa tetap menambah salinan", push(denganPopInclusive, Entri("TaskList")).size == 2)
    cek("launchSingleTop tidak menghalangi destination berbeda",
        pushSingleTop(denganPopInclusive, Entri("TaskDetail", mapOf("taskId" to "1"))).size == 2)
    cek("popUpTo target yang tidak ada hanya menambah entri",
        pushDenganPopUpTo(awal, Entri("TaskDetail", mapOf("taskId" to "1")), "Welcome", true).size == 2)

    // --- Argumen: kirim ID, bukan objek -----------------------------------
    cek("Int dan String didukung langsung", didukungLangsung("Int") && didukungLangsung("String"))
    cek("kelas buatan sendiri butuh kerja tambahan", !didukungLangsung("Kelas buatan sendiri"))
    cek("List butuh kerja tambahan", !didukungLangsung("List"))
    cek("enam tipe dasar yang didukung", tipeArgumenLangsung.size == 6)
    cek("empat jenis tipe tambahan tercatat", tipeArgumenTambahan.size == 4)

    val routeDenganId = Entri("TaskDetail", mapOf("taskId" to "2"))
    cek("argumen hanya membawa ID, bukan seluruh objek", routeDenganId.argumen.size == 1)
    cek("nilai argumen tetap Int setelah parse", routeDenganId.argumen.getValue("taskId").toInt() == 2)

    // --- Tabel modul -------------------------------------------------------
    cek("lima komponen dasar dijelaskan", komponenDasar.size == 5)
    cek("setiap komponen punya analogi", komponenDasar.all { it.analogi.isNotBlank() })
    cek("perbandingan route punya 4 aspek", perbandinganRoute.size == 4)
    cek("type-safe menyebut toRoute()", perbandinganRoute.any { it.typeSafe.contains("toRoute") })
    cek("tiga cara kembali didokumentasikan", caraKembali.size == 3)
    cek("tiga opsi back stack didokumentasikan", opsiBackStack.size == 3)
    cek("lima skenario pengujian", skenarioUji.size == 5)
    cek("skenario 4 menguji rotasi", skenarioUji.first { it.nomor == 4 }.langkah.contains("putar"))
    cek("lima kesalahan umum navigasi", kesalahanUmumNavigasi.size == 5)
    cek("setiap kesalahan punya perbaikan", kesalahanUmumNavigasi.all { it.perbaikan.isNotBlank() })
    cek("navigate() di badan composable tercatat sebagai kesalahan",
        kesalahanUmumNavigasi.any { it.kode7() })
    cek("Navigation 3 punya 3 perbedaan tercatat", perbedaanNavigation3.size == 3)
    cek("NavDisplay menggantikan NavHost",
        perbedaanNavigation3.first { it.aspek.contains("Wadah") }.nav3 == "NavDisplay")

    println()
    println("Kasus uji: ${jumlahCekDijalankan - gagal}/$jumlahCekDijalankan lulus")
    return gagal == 0
}

/** Aturan modul: `navigate()` hanya boleh dipanggil di dalam lambda event. */
private fun KesalahanPerbaikan.kode7(): Boolean =
    kasus.contains("badan composable") && perbaikan.contains("lambda event")

fun main() {
    val lulus = verifikasiPerilakuModul15()
    if (lulus) {
        println("OK — aturan navigation, back stack, dan argumen lulus")
    } else {
        println("GAGAL — ada pemeriksaan yang tidak lulus")
        kotlin.system.exitProcess(1)
    }
}
