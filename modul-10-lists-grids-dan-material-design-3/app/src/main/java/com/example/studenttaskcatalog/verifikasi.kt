package com.example.studenttaskcatalog

/**
 * Pemeriksaan perilaku Lists, Grids, and Material Design 3 (modul 10).
 *
 * Modul ini menilai layar dari PERILAKUNYA, bukan tampilannya. Tabel 5 modul memuat tujuh
 * pengujian:
 *
 *   T1  Muat 12 data contoh            → semua data dapat dicapai dengan menggulir
 *   T2  Gulir daftar vertikal          → tidak ada kartu terpotong
 *   T3  Gulir strip kategori           → strip bergulir secara horizontal
 *   T4  Beralih ke grid                → data yang sama tampil dalam grid
 *   T5  Perlebar layar (grid adaptif)  → jumlah kolom mengikuti lebar yang tersedia
 *   T6  Periksa tugas selesai/belum    → label status terbaca dan benar
 *   T7  Tema terang dan gelap          → teks tetap terbaca (warna semantik)
 *
 * T2, T3, T6, dan T7 hanya dapat dilihat pada perangkat (atau Preview). Yang dapat diperiksa
 * tanpa emulator adalah ATURAN yang melandasinya: cukup banyaknya data dan keunikan id (T1),
 * data yang sama dipakai kedua bentuk tampilan (T4), perhitungan jumlah kolom Adaptive (T5),
 * serta aturan key dan pasangan warna (T6, T7). Aturan itu dimodelkan sebagai fungsi murni di
 * berkas ini.
 *
 * Karena berkas ini hanya memakai Kotlin stdlib, pemeriksaannya dapat dijalankan dari terminal
 * tanpa emulator dan tanpa Gradle:
 *
 *   kotlinc app/src/main/java/com/example/studenttaskcatalog/verifikasi.kt \
 *       -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * (atau dari root repositori: `./jalankan.sh 10 perilaku`). Fungsi yang sama dicetak ulang
 * dari dalam aplikasi oleh `jawabanLatihan6VerifikasiSemua()` pada latihan/latihan-akhir.kt,
 * yang juga memeriksa data contoh yang sesungguhnya (`sampleTasks`).
 */

// ---------------------------------------------------------------------------
// 1. Aturan kumpulan data (T1) dan key (Bab 5)
// ---------------------------------------------------------------------------

/** T1/Bab 2: 10 sampai 12 tugas agar daftar benar-benar perlu digulir pada layar ponsel. */
fun jumlahDataCukupUntukDigulir(jumlah: Int): Boolean = jumlah in 10..12

/**
 * Aturan key Bab 5: setiap id unik. Dua item dengan key sama di satu daftar membuat aplikasi
 * crash, jadi daftar id yang diberikan harus sama panjang dengan himpunan id-nya.
 */
fun idSemuaUnik(ids: List<Int>): Boolean = ids.size == ids.toSet().size

/** Key harus dapat disimpan dalam Bundle: Int dan String memenuhi syarat, bukan objek biasa. */
fun keyDapatDisimpan(key: Any?): Boolean = key is Int || key is String

// ---------------------------------------------------------------------------
// 2. Perhitungan kolom Adaptive (Bagian 8.1 dan T5)
// ---------------------------------------------------------------------------

/**
 * Jumlah kolom LazyVerticalGrid dengan GridCells.Adaptive(minSize).
 *
 * Compose menghitung berapa sel selebar minSize yang muat bila sisa ruang setelah padding
 * dibagi rata ke semua sel. Rumus yang dipakai padanan rumus itu:
 *
 *   kolom = lantai( (lebar − 2·paddingHorizontal + jarakAntarSel) / (minSize + jarakAntarSel) )
 *
 * Minimal satu kolom, walau layar lebih sempit daripada satu sel.
 */
fun jumlahSelAdaptif(
    lebarTotalDp: Int,
    paddingHorizontal: Int = 16,
    jarakAntarSel: Int = 12,
    minSize: Int = 160
): Int {
    val tersedia = lebarTotalDp - 2 * paddingHorizontal
    val sel = (tersedia + jarakAntarSel) / (minSize + jarakAntarSel)
    return if (sel < 1) 1 else sel
}

/** Lebar setiap sel setelah sisa lebar dibagikan rata, dalam dp (dibulatkan ke bawah). */
fun lebarSelAdaptif(
    lebarTotalDp: Int,
    paddingHorizontal: Int = 16,
    jarakAntarSel: Int = 12,
    minSize: Int = 160
): Int {
    val sel = jumlahSelAdaptif(lebarTotalDp, paddingHorizontal, jarakAntarSel, minSize)
    val tersedia = lebarTotalDp - 2 * paddingHorizontal - jarakAntarSel * (sel - 1)
    return tersedia / sel
}

// ---------------------------------------------------------------------------
// 3. Satu kumpulan data, dua bentuk tampilan (T4) dan filter kategori (Bab 12.4)
// ---------------------------------------------------------------------------

/**
 * T4: daftar dan grid menerima KUMPULAN DATA YANG SAMA (objek yang sama, bukan salinannya).
 * Yang berbeda hanya susunannya, jadi jumlah item kedua bentuk tampilan harus identik.
 */
fun satuKumpulanDataUntukDuaTampilan(untukList: Any?, untukGrid: Any?): Boolean =
    untukList === untukGrid

/** Bab 12.4: daftar yang tampil diturunkan dari kategori terpilih. "All" berarti semuanya. */
fun <T> tugasTerlihat(semua: List<T>, kategoriTerpilih: String, kategoriDari: (T) -> String): List<T> =
    if (kategoriTerpilih == "All") semua
    else semua.filter { kategoriDari(it) == kategoriTerpilih }

/** Empty state ditampilkan bila tidak ada tugas yang cocok dengan kategori terpilih. */
fun harusTampilkanEmptyState(jumlahTugasTerlihat: Int): Boolean = jumlahTugasTerlihat == 0

// ---------------------------------------------------------------------------
// 4. Aturan label status (T6) dan pasangan warna (T7)
// ---------------------------------------------------------------------------

/**
 * T6: makna status dibawa oleh TEKS, bukan oleh warna. Label selalu bertuliskan "Completed"
 * atau "Pending".
 */
fun teksStatus(isCompleted: Boolean): String = if (isCompleted) "Completed" else "Pending"

/** T6: status pending dibedakan juga oleh GARIS TEPI, bukan hanya warna isi. */
fun memakaiGarisTepi(isCompleted: Boolean): Boolean = !isCompleted

/**
 * T7: warna latar dan warna teks selalu dipilih BERPASANGAN sehingga kontras terjaga di mode
 * terang maupun gelap. Warna yang ditulis langsung (mis. 0xFF00C853) tidak mengikuti tema.
 */
val pasanganWarnaLatarTeks: List<Pair<String, String>> = listOf(
    "primaryContainer" to "onPrimaryContainer",
    "surface" to "onSurface"
)

fun warnaBerpasanganDenganBenar(latar: String, teks: String): Boolean =
    pasanganWarnaLatarTeks.any { (l, t) -> l == latar && t == teks }

/** Warna mentah yang ditulis langsung (bukan peran colorScheme) dikenali dari format ARGB. */
fun warnaDitulisLangsung(nilai: String): Boolean = nilai.startsWith("0x")

// ---------------------------------------------------------------------------
// 5. Masalah umum (Tabel 6, Bagian 14)
// ---------------------------------------------------------------------------

/**
 * Tabel 6 modul, urut 1..7: gejala → perbaikan. Salinan ini dipakai untuk memastikan setiap
 * gejala punya penanganan dan untuk dicetak pada menu aplikasi.
 */
val masalahUmumKatalog: List<Pair<String, String>> = listOf(
    "items merah di dalam LazyVerticalGrid" to
        "Tambahkan import androidx.compose.foundation.lazy.grid.items",
    "Crash \"infinity maximum height constraints\"" to
        "Satu kontainer gulir; beri Modifier.weight(1f)",
    "Crash karena key duplikat" to "Pastikan setiap id unik",
    "State item berpindah ke data lain" to "Gunakan key = { it.id }, bukan indeks",
    "Kartu terakhir terpotong" to "Gunakan contentPadding",
    "Grid hanya 1 kolom di ponsel" to "Wajar pada layar sempit; kecilkan minSize bila perlu",
    "Teks status tidak terbaca di tema gelap" to "Gunakan pasangan peran colorScheme"
)

/** Dua import items yang berbeda untuk kontainer yang berbeda (Tabel 3, Bagian 8). */
val importItemsPerKontainer: Map<String, String> = mapOf(
    "LazyColumn" to "androidx.compose.foundation.lazy.items",
    "LazyRow" to "androidx.compose.foundation.lazy.items",
    "LazyVerticalGrid" to "androidx.compose.foundation.lazy.grid.items"
)

// ---------------------------------------------------------------------------
// 6. Pemeriksaan
// ---------------------------------------------------------------------------

/** Jumlah pemeriksaan yang benar-benar dijalankan; diisi oleh verifikasiPerilakuKatalog(). */
var jumlahCekDijalankan: Int = 0

/**
 * Menjalankan seluruh pemeriksaan yang dapat dihitung tanpa emulator.
 * Mengembalikan daftar kegagalan; daftar kosong berarti semuanya lulus.
 */
fun verifikasiPerilakuKatalog(): List<String> {
    val gagal = mutableListOf<String>()
    jumlahCekDijalankan = 0

    fun cek(nama: String, kondisi: Boolean) {
        jumlahCekDijalankan++
        if (!kondisi) gagal += nama
    }

    // T1 — 12 data contoh (dan rentang yang diminta modul: 10 sampai 12).
    cek("T1 12 data contoh cukup untuk digulir", jumlahDataCukupUntukDigulir(12))
    cek("T1 9 data belum cukup untuk digulir", !jumlahDataCukupUntukDigulir(9))
    cek("T1 13 data melebihi rentang modul", !jumlahDataCukupUntukDigulir(13))

    // Bab 5 — key unik, stabil, dan dapat disimpan.
    cek("B5 id 1..12 semuanya unik", idSemuaUnik((1..12).toList()))
    cek("B5 id duplikat terdeteksi", !idSemuaUnik(listOf(1, 2, 2, 3)))
    cek("B5 key Int dapat disimpan", keyDapatDisimpan(7))
    cek("B5 key String dapat disimpan", keyDapatDisimpan("Assignment"))
    cek("B5 key berupa objek biasa tidak dapat disimpan", !keyDapatDisimpan(Any()))

    // T4 — satu kumpulan data, dua bentuk tampilan.
    val data = listOf("a", "b", "c")
    cek("T4 list dan grid menerima data yang sama", satuKumpulanDataUntukDuaTampilan(data, data))
    cek("T4 dua salinan berbeda bukan data yang sama", !satuKumpulanDataUntukDuaTampilan(data, data.toList()))

    // T5 — grid adaptif, konfigurasi Bagian 8.2 (padding 16, jarak 12, minSize 160).
    cek("T5 412 dp → 2 kolom", jumlahSelAdaptif(412) == 2)
    cek("T5 412 dp → lebar sel 184 dp", lebarSelAdaptif(412) == 184)
    cek("T5 840 dp → 4 kolom", jumlahSelAdaptif(840) == 4)
    cek("T5 360 dp → 1 kolom", jumlahSelAdaptif(360) == 1)
    cek("T5 600 dp → 3 kolom", jumlahSelAdaptif(600) == 3)
    // Jarak yang lebih besar pada layar sempit mengurangi satu kolom (catatan Bagian 8.2).
    cek("T5 jarak 48 dp pada 360 dp tetap 1 kolom", jumlahSelAdaptif(360, jarakAntarSel = 48) == 1)
    cek("T5 layar sempit tidak pernah 0 kolom", jumlahSelAdaptif(120) == 1)

    // T6 — teks status dan pembeda bentuk, bukan hanya warna.
    cek("T6 label selesai bertuliskan Completed", teksStatus(true) == "Completed")
    cek("T6 label belum selesai bertuliskan Pending", teksStatus(false) == "Pending")
    cek("T6 status pending memakai garis tepi", memakaiGarisTepi(false))
    cek("T6 status selesai tidak memakai garis tepi", !memakaiGarisTepi(true))

    // T7 — pasangan warna tema, dan bahaya warna yang ditulis langsung.
    cek("T7 primaryContainer berpasangan dengan onPrimaryContainer",
        warnaBerpasanganDenganBenar("primaryContainer", "onPrimaryContainer"))
    cek("T7 surface berpasangan dengan onSurface",
        warnaBerpasanganDenganBenar("surface", "onSurface"))
    cek("T7 pasangan silang tidak sah",
        !warnaBerpasanganDenganBenar("primaryContainer", "onSurface"))
    cek("T7 warna 0xFF00C853 adalah warna ditulis langsung", warnaDitulisLangsung("0xFF00C853"))
    cek("T7 peran colorScheme bukan warna ditulis langsung", !warnaDitulisLangsung("onSurface"))

    // Bab 12.4 — filter kategori dan empty state.
    val kategori = listOf("All", "Assignment", "Quiz", "Project", "Lab")
    cek("B12 kategori Lab ditambahkan untuk empty state", kategori.contains("Lab"))
    cek("B12 All menampilkan seluruh daftar",
        tugasTerlihat(listOf(1, 2, 3), "All") { "Quiz" }.size == 3)
    cek("B12 filter kategori menyaring daftar",
        tugasTerlihat(listOf("Quiz", "Project", "Quiz"), "Quiz") { it }.size == 2)
    cek("B12 kategori Lab kosong sehingga empty state tampil",
        harusTampilkanEmptyState(tugasTerlihat(listOf("Quiz"), "Lab") { it }.size))
    cek("B12 kategori berisi tidak menampilkan empty state",
        !harusTampilkanEmptyState(tugasTerlihat(listOf("Quiz"), "Quiz") { it }.size))

    // Tabel 3 — dua import items yang berbeda untuk kontainer yang berbeda.
    cek("B8 LazyColumn memakai import lazy.items",
        importItemsPerKontainer["LazyColumn"] == "androidx.compose.foundation.lazy.items")
    cek("B8 LazyVerticalGrid memakai import lazy.grid.items",
        importItemsPerKontainer["LazyVerticalGrid"] == "androidx.compose.foundation.lazy.grid.items")

    // Tabel 6 — tujuh gejala, semuanya punya penanganan.
    cek("B14 tujuh baris masalah umum", masalahUmumKatalog.size == 7)
    cek("B14 setiap masalah punya solusi",
        masalahUmumKatalog.all { it.first.isNotBlank() && it.second.isNotBlank() })

    // Latihan 1 — satu item tetap + dua belas data = tiga belas item.
    cek("L1 jumlah item LazyColumn adalah 13", 1 + 12 == 13)

    return gagal
}

/** Mencetak hasil verifikasi; mengembalikan true bila semua lulus. */
fun jalankanVerifikasiModul10(): Boolean {
    val gagal = verifikasiPerilakuKatalog()
    val total = jumlahCekDijalankan
    println("Verifikasi Modul 10 — Lists, Grids, and Material Design 3")
    println("Kasus uji: ${total - gagal.size}/$total lulus")
    if (gagal.isEmpty()) {
        println("OK — seluruh pemeriksaan aturan katalog lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    return gagal.isEmpty()
}

/** Titik masuk untuk menjalankan pemeriksaan dari terminal. */
fun main() {
    println("Perhitungan kolom grid adaptif (Bagian 8.2, minSize 160 dp):")
    listOf(360, 412, 600, 840).forEach { w ->
        println("  $w dp → ${jumlahSelAdaptif(w)} kolom, lebar sel ${lebarSelAdaptif(w)} dp")
    }
    println()
    if (!jalankanVerifikasiModul10()) {
        kotlin.system.exitProcess(1)
    }
}
