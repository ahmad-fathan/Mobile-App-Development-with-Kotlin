package com.example.campuscoursebrowser

import kotlin.math.pow

/**
 * Aturan yang dapat dihitung dari modul 11 (UI/UX and Adaptive Layout) — dan pemeriksaannya.
 *
 * Modul ini menilai layar dari KEPUTUSAN DESAIN, bukan dari tampilannya. Tabel 2, 5, 7, 10, dan
 * 12 memuat aturan yang bisa diperiksa tanpa emulator: kontras minimum, target sentuh, penanda
 * pilihan yang bukan hanya warna, breakpoint kelas lebar, kebijakan dua panel, dan matriks uji.
 * Semua aturan itu dimodelkan sebagai fungsi murni di berkas ini, lalu DIPAKAI oleh berkas bab
 * (bukan disalin), sehingga aturan dan tampilan tidak dapat berbeda diam-diam.
 *
 * Berkas ini hanya memakai Kotlin stdlib — tanpa Compose dan tanpa Android — sehingga dapat
 * dikompilasi sendiri dari terminal:
 *
 *   kotlinc app/src/main/java/com/example/campuscoursebrowser/verifikasi.kt \
 *       -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * atau dari root repositori: `./jalankan.sh 11 perilaku`.
 */

// ---------------------------------------------------------------------------
// 1. Aksesibilitas dasar (Bab 6 / Bagian 7)
// ---------------------------------------------------------------------------

/** Bagian 7.1: target sentuh minimal 48 dp pada kedua sisi. */
const val TARGET_SENTUH_MINIMAL_DP = 48

fun targetSentuhMemadai(lebarDp: Int, tinggiDp: Int): Boolean =
    lebarDp >= TARGET_SENTUH_MINIMAL_DP && tinggiDp >= TARGET_SENTUH_MINIMAL_DP

/** Bagian 7.1: ikon yang terlihat boleh lebih kecil daripada target sentuhnya. */
fun ikonBolehLebihKecil(ikonDp: Int, targetDp: Int): Boolean =
    ikonDp < targetDp && targetDp >= TARGET_SENTUH_MINIMAL_DP

/**
 * Bagian 7.1: kontras minimum 4.5:1 untuk teks normal, 3:1 untuk teks besar
 * (≥ 18 sp reguler atau ≥ 14 sp tebal).
 */
fun kontrasMinimal(teksBesar: Boolean): Double = if (teksBesar) 3.0 else 4.5

/** Luminans relatif satu kanal sRGB, mengikuti rumus WCAG. */
private fun kanal(nilai: Int): Double {
    val c = nilai.coerceIn(0, 255) / 255.0
    return if (c <= 0.03928) c / 12.92 else ((c + 0.055) / 1.055).pow(2.4)
}

/** Warna #RRGGBB dipecah menjadi tiga kanal; format lain ditolak, bukan ditebak. */
fun kanalDariHex(hex: String): Triple<Int, Int, Int> {
    val bersih = hex.removePrefix("#")
    require(bersih.length == 6) { "Warna harus berformat RRGGBB: $hex" }
    return Triple(
        bersih.substring(0, 2).toInt(16),
        bersih.substring(2, 4).toInt(16),
        bersih.substring(4, 6).toInt(16)
    )
}

/** Rasio kontras dua warna: 1.0 (sama) sampai 21.0 (hitam di atas putih). */
fun rasioKontras(hexLatar: String, hexTeks: String): Double {
    val (lr, lg, lb) = kanalDariHex(hexLatar)
    val (tr, tg, tb) = kanalDariHex(hexTeks)
    val a = 0.2126 * kanal(lr) + 0.7152 * kanal(lg) + 0.0722 * kanal(lb)
    val b = 0.2126 * kanal(tr) + 0.7152 * kanal(tg) + 0.0722 * kanal(tb)
    val terang = if (a > b) a else b
    val gelap = if (a > b) b else a
    return (terang + 0.05) / (gelap + 0.05)
}

/** Apakah pasangan warna memenuhi syarat untuk ukuran teks tersebut. */
fun kontrasMemenuhiSyarat(hexLatar: String, hexTeks: String, teksBesar: Boolean = false): Boolean =
    rasioKontras(hexLatar, hexTeks) >= kontrasMinimal(teksBesar)

/**
 * Bagian 7.1: keadaan "selected" tidak boleh dibawa HANYA oleh warna. Pilihan baru dianggap
 * cukup bila teks tombolnya berubah atau ada semantics selected.
 */
fun penandaPilihanCukupDenganWarna(
    labelTombolTerpilih: String?,
    punyaSemanticsSelected: Boolean
): Boolean = labelTombolTerpilih.isNullOrBlank() && !punyaSemanticsSelected

/**
 * Bagian 7.2: ikon dekoratif yang sudah dijelaskan teks di sebelahnya memakai
 * contentDescription = null; ikon tunggal yang dapat ditekan memerlukan deskripsi aksi.
 */
fun contentDescriptionIkon(ikonDekoratif: Boolean, aksi: String): String? =
    if (ikonDekoratif) null else aksi

/** Bagian 7.3: tinggi tetap hanya aman untuk teks yang tidak penting bagi penyelesaian tugas. */
fun tinggiTetapAman(teksPenting: Boolean): Boolean = !teksPenting

/** Bagian 7.3: teks memakai satuan sp; dp tidak mengikuti ukuran font sistem. */
fun satuanTeksDapatDiskalakan(satuan: String): Boolean = satuan == "sp"

// ---------------------------------------------------------------------------
// 2. Hierarki dan konsistensi (Bab 5 / Bagian 6)
// ---------------------------------------------------------------------------

/** Bagian 6.3: aksi yang sama memakai label yang sama. */
fun labelAksiSeragam(semuaLabel: List<String>): Boolean =
    semuaLabel.isNotEmpty() && semuaLabel.toSet().size == 1

/** Bagian 6.4: jangan menampilkan pesan berhasil sebelum operasi benar-benar berhasil. */
fun bolehMenampilkanPesanBerhasil(operasiBerhasil: Boolean): Boolean = operasiBerhasil

// ---------------------------------------------------------------------------
// 3. Requirement yang dapat diamati (Bab 3 / Bagian 4, Tabel 2)
// ---------------------------------------------------------------------------

/** Satu baris Tabel 2: kebutuhan, keputusan UI, dan cara memeriksanya. */
data class RequirementUi(
    val kebutuhan: String,
    val keputusanUi: String,
    val caraMemeriksa: String
)

/** Tabel 2 — empat terjemahan kebutuhan menjadi keputusan UI yang dapat diperiksa. */
val requirementCourseBrowser: List<RequirementUi> = listOf(
    RequirementUi(
        kebutuhan = "Mengenali mata kuliah dengan cepat",
        keputusanUi = "Letakkan judul mata kuliah sebelum informasi sekunder",
        caraMemeriksa = "Minta teman menemukan mata kuliah yang disebut namanya"
    ),
    RequirementUi(
        kebutuhan = "Memahami apa yang terjadi setelah mengetuk",
        keputusanUi = "Gunakan aksi eksplisit \u201cView details\u201d",
        caraMemeriksa = "Tanyakan apa yang teman harapkan sebelum ia mengetuk"
    ),
    RequirementUi(
        kebutuhan = "Membaca dengan nyaman",
        keputusanUi = "Dukung pembesaran teks dan kontras yang jelas",
        caraMemeriksa = "Perbesar ukuran font sistem lalu periksa layar"
    ),
    RequirementUi(
        kebutuhan = "Tetap melanjutkan tugas saat ukuran jendela berubah",
        keputusanUi = "Simpan state mata kuliah terpilih di atas cabang layout",
        caraMemeriksa = "Pilih mata kuliah, ubah ukuran jendela, lalu periksa pilihannya"
    )
)

/** Requirement hanya berguna bila setiap baris menyebut cara memeriksanya. */
fun semuaRequirementDapatDiperiksa(daftar: List<RequirementUi>): Boolean =
    daftar.isNotEmpty() && daftar.all {
        it.kebutuhan.isNotBlank() && it.keputusanUi.isNotBlank() && it.caraMemeriksa.isNotBlank()
    }

/** Requirement yang menyangkut perubahan ukuran jendela (baris terakhir Tabel 2). */
fun requirementTentangUkuranJendela(daftar: List<RequirementUi>): List<RequirementUi> =
    daftar.filter { it.kebutuhan.contains("ukuran jendela", ignoreCase = true) }

// ---------------------------------------------------------------------------
// 4. Layout responsive dan adaptive (Bab 7 / Bagian 8, Tabel 5 dan 7)
// ---------------------------------------------------------------------------

/** Batas kelas lebar jendela (Tabel 5), dalam dp. */
const val LEBAR_COMPACT_BATAS_ATAS = 600
const val LEBAR_MEDIUM_BATAS_ATAS = 840
const val LEBAR_EXPANDED_BATAS_ATAS = 1200
const val LEBAR_LARGE_BATAS_ATAS = 1600

/** Kebijakan modul: dua panel hanya bila lebar ≥ 840 dp DAN tinggi ≥ 480 dp (Tabel 7). */
const val LEBAR_MINIMAL_DUA_PANEL = 840
const val TINGGI_MINIMAL_DUA_PANEL = 480

/** Nama kelas lebar jendela untuk sebuah lebar dalam dp. */
fun kelasLebarJendela(lebarDp: Int): String = when {
    lebarDp < LEBAR_COMPACT_BATAS_ATAS -> "Compact"
    lebarDp < LEBAR_MEDIUM_BATAS_ATAS -> "Medium"
    lebarDp < LEBAR_EXPANDED_BATAS_ATAS -> "Expanded"
    lebarDp < LEBAR_LARGE_BATAS_ATAS -> "Large"
    else -> "Extra-large"
}

/** Nama kelas tinggi jendela; pedoman memakai batas bawah 480 dp dan 900 dp. */
fun kelasTinggiJendela(tinggiDp: Int): String = when {
    tinggiDp < TINGGI_MINIMAL_DUA_PANEL -> "Compact"
    tinggiDp < 900 -> "Medium"
    else -> "Expanded"
}

/**
 * Kebijakan layout Campus Course Browser (Tabel 7) sebagai fungsi murni.
 * `true` berarti panel daftar dan panel detail tampil bersamaan.
 */
fun kebijakanDuaPanel(lebarDp: Int, tinggiDp: Int): Boolean =
    lebarDp >= LEBAR_MINIMAL_DUA_PANEL && tinggiDp >= TINGGI_MINIMAL_DUA_PANEL

/** Mengapa lebar medium tetap satu panel: dua panel berarti tiap panel < ±420 dp. */
fun alasanMediumSatuPanel(lebarDp: Int): String =
    "Pada $lebarDp dp, dua panel hanya mendapat ${lebarDp / 2} dp per panel, " +
        "sehingga kartu menjadi terlalu sempit."

// ---------------------------------------------------------------------------
// 5. Matriks uji (Bab 10 / Bagian 11, Tabel 10)
// ---------------------------------------------------------------------------

/** Satu baris Tabel 10. Ukuran adalah dp jendela aplikasi, bukan piksel tangkapan layar. */
data class KasusUjiDesain(
    val pengujian: String,
    val buktiDiharapkan: String,
    val lebarDp: Int? = null,
    val tinggiDp: Int? = null
)

/** Tabel 10 — matriks uji minimum Campus Course Browser. */
val matriksUjiCourseBrowser: List<KasusUjiDesain> = listOf(
    KasusUjiDesain(
        "360 x 800 dp",
        "Satu panel; detail terbuka dan Back kembali ke daftar",
        lebarDp = 360,
        tinggiDp = 800
    ),
    KasusUjiDesain(
        "700 x 900 dp",
        "Satu panel sesuai kebijakan modul",
        lebarDp = 700,
        tinggiDp = 900
    ),
    KasusUjiDesain(
        "1000 x 800 dp",
        "Daftar dan detail terlihat bersamaan",
        lebarDp = 1000,
        tinggiDp = 800
    ),
    KasusUjiDesain(
        "900 x 400 dp",
        "Satu panel sesuai kebijakan tinggi",
        lebarDp = 900,
        tinggiDp = 400
    ),
    KasusUjiDesain(
        "Ubah ukuran di sekitar 839/840 dp",
        "Struktur panel berubah; mata kuliah terpilih tetap"
    ),
    KasusUjiDesain(
        "Perbesar ukuran teks; gunakan judul panjang",
        "Teks tetap tersedia dan aksi tetap terjangkau"
    ),
    KasusUjiDesain("Aktifkan TalkBack", "Kontrol dan keadaan terpilih dapat dipahami"),
    KasusUjiDesain(
        "Gunakan Tab dan Enter pada keyboard",
        "Fokus terlihat dan tugas utama dapat dijalankan"
    )
)

/** Harapan kebijakan untuk satu kasus uji; null bila kasusnya tidak menyebut ukuran. */
fun harapanDuaPanel(kasus: KasusUjiDesain): Boolean? {
    val lebar = kasus.lebarDp ?: return null
    val tinggi = kasus.tinggiDp ?: return null
    return kebijakanDuaPanel(lebar, tinggi)
}

// ---------------------------------------------------------------------------
// 6. Masalah umum (Bab 11 / Bagian 12, Tabel 12)
// ---------------------------------------------------------------------------

/** Satu baris Tabel 12: gejala, penyebab yang mungkin, dan solusinya. */
data class MasalahUmumAdaptif(
    val masalah: String,
    val penyebab: String,
    val solusi: String
)

val masalahUmumAdaptif: List<MasalahUmumAdaptif> = listOf(
    MasalahUmumAdaptif(
        "currentWindowAdaptiveInfoV2 tidak dikenali",
        "Dependensi Material 3 Adaptive belum ditambahkan atau versinya lebih lama dari 1.3.0",
        "Tambahkan androidx.compose.material3.adaptive:adaptive:1.3.0, lakukan Gradle sync, " +
            "periksa import"
    ),
    MasalahUmumAdaptif(
        "isWidthAtLeastBreakpoint tidak ditemukan",
        "Import WindowSizeClass berasal dari API lama material3.windowsizeclass",
        "Gunakan androidx.window.core.layout.WindowSizeClass"
    ),
    MasalahUmumAdaptif(
        "Mata kuliah terpilih hilang saat rotasi atau resize",
        "State disimpan dengan remember atau di dalam salah satu cabang layout",
        "Simpan ID dengan rememberSaveable di atas percabangan layout"
    ),
    MasalahUmumAdaptif(
        "Layout tidak pernah menjadi dua panel di emulator",
        "Tinggi jendela di bawah 480 dp atau lebar di bawah 840 dp",
        "Periksa ukuran jendela dalam dp; perbesar jendela atau putar perangkat"
    ),
    MasalahUmumAdaptif(
        "Judul kartu terpotong saat font diperbesar",
        "Kartu memakai tinggi tetap atau maxLines = 1",
        "Biarkan tinggi mengikuti isi dan izinkan teks membungkus"
    ),
    MasalahUmumAdaptif(
        "TalkBack membacakan label dua kali",
        "Content description ditambahkan pada elemen yang teksnya sudah dibaca",
        "Hapus deskripsi yang redundan dan uji ulang dengan TalkBack"
    ),
    MasalahUmumAdaptif(
        "weight tidak dapat dipakai",
        "Modifier dipanggil di luar scope Row atau Column",
        "Pindahkan pemanggilan ke dalam scope Row/Column"
    )
)

/** Setiap gejala harus punya penyebab dan solusi; baris kosong membuat tabel tidak berguna. */
fun semuaMasalahPunyaSolusi(daftar: List<MasalahUmumAdaptif> = masalahUmumAdaptif): Boolean =
    daftar.isNotEmpty() && daftar.all {
        it.masalah.isNotBlank() && it.penyebab.isNotBlank() && it.solusi.isNotBlank()
    }

// ---------------------------------------------------------------------------
// 7. Pemeriksaan
// ---------------------------------------------------------------------------

/** Jumlah pemeriksaan yang benar-benar dijalankan; diisi oleh verifikasiPerilakuCourseBrowser(). */
var jumlahCekDijalankan: Int = 0

/**
 * Menjalankan seluruh pemeriksaan yang dapat dihitung tanpa emulator.
 * Mengembalikan daftar kegagalan; daftar kosong berarti semuanya lulus.
 */
fun verifikasiPerilakuCourseBrowser(): List<String> {
    val gagal = mutableListOf<String>()
    jumlahCekDijalankan = 0

    fun cek(nama: String, kondisi: Boolean) {
        jumlahCekDijalankan++
        if (!kondisi) gagal += nama
    }

    fun dekat(nilai: Double, harapan: Double, toleransi: Double = 0.01): Boolean =
        nilai >= harapan - toleransi && nilai <= harapan + toleransi

    // Bagian 7.1 — kontras (dua contoh pada Gambar 4 modul).
    val kontrasGelap = rasioKontras("FFFFFF", "1F2430")
    val kontrasAbu = rasioKontras("FFFFFF", "A0A6AE")
    cek("B7 #1F2430 pada putih ≈ 15,5:1", dekat(kontrasGelap, 15.5, 0.1))
    cek("B7 #A0A6AE pada putih ≈ 2,5:1", dekat(kontrasAbu, 2.45, 0.1))
    cek("B7 #1F2430 lolos untuk teks normal", kontrasMemenuhiSyarat("FFFFFF", "1F2430"))
    cek("B7 #A0A6AE gagal untuk teks normal", !kontrasMemenuhiSyarat("FFFFFF", "A0A6AE"))
    cek("B7 #A0A6AE tetap gagal untuk teks besar", !kontrasMemenuhiSyarat("FFFFFF", "A0A6AE", true))
    cek("B7 ambang teks normal 4.5:1", kontrasMinimal(false) == 4.5)
    cek("B7 ambang teks besar 3:1", kontrasMinimal(true) == 3.0)
    cek("B7 warna sama menghasilkan kontras 1:1", dekat(rasioKontras("1F2430", "1F2430"), 1.0))
    cek("B7 kontras simetris", dekat(kontrasGelap, rasioKontras("1F2430", "FFFFFF"), 0.001))

    // Bagian 7.1 — target sentuh.
    cek("B7 target 48 x 48 memadai", targetSentuhMemadai(48, 48))
    cek("B7 target 47 dp terlalu kecil", !targetSentuhMemadai(47, 48))
    cek("B7 target 48 x 47 terlalu pendek", !targetSentuhMemadai(48, 47))
    cek("B7 ikon 24 dp di target 48 dp sah", ikonBolehLebihKecil(24, 48))
    cek("B7 target 40 dp tidak sah walau ikon 24 dp", !ikonBolehLebihKecil(24, 40))

    // Bagian 7.1 dan 7.2 — pilihan dan teknologi asistif.
    cek("B7 pilihan dengan teks dan semantics sah", !penandaPilihanCukupDenganWarna("Selected course", true))
    cek("B7 pilihan dengan teks saja sah", !penandaPilihanCukupDenganWarna("Selected course", false))
    cek("B7 pilihan hanya warna tidak sah", penandaPilihanCukupDenganWarna(null, false))
    cek("B7 ikon dekoratif tanpa deskripsi", contentDescriptionIkon(true, "Search courses") == null)
    cek("B7 ikon tunggal berdeskripsi",
        contentDescriptionIkon(false, "Search courses") == "Search courses")

    // Bagian 7.3 — teks membesar.
    cek("B7 tinggi tetap tidak aman untuk teks penting", !tinggiTetapAman(true))
    cek("B7 tinggi tetap aman untuk label sekunder", tinggiTetapAman(false))
    cek("B7 sp dapat diskalakan", satuanTeksDapatDiskalakan("sp"))
    cek("B7 dp tidak mengikuti ukuran font", !satuanTeksDapatDiskalakan("dp"))

    // Bagian 6.3 dan 6.4 — konsistensi dan umpan balik.
    cek("B6 satu label konsisten", labelAksiSeragam(listOf("View details", "View details")))
    cek("B6 label berbeda tidak konsisten", !labelAksiSeragam(listOf("Open", "Details")))
    cek("B6 daftar label kosong tidak dianggap konsisten", !labelAksiSeragam(emptyList()))
    cek("B6 pesan berhasil ditahan bila gagal", !bolehMenampilkanPesanBerhasil(false))
    cek("B6 pesan berhasil boleh bila berhasil", bolehMenampilkanPesanBerhasil(true))

    // Bagian 4 — requirement.
    cek("B4 empat requirement pada Tabel 2", requirementCourseBrowser.size == 4)
    cek("B4 setiap requirement punya cara memeriksa",
        semuaRequirementDapatDiperiksa(requirementCourseBrowser))
    cek("B4 requirement tanpa cara memeriksa ditolak",
        !semuaRequirementDapatDiperiksa(
            listOf(RequirementUi("Kebutuhan", "Keputusan UI", " "))
        ))
    cek("B4 daftar kosong tidak dianggap lengkap", !semuaRequirementDapatDiperiksa(emptyList()))
    cek("B4 ada tepat satu requirement tentang ukuran jendela",
        requirementTentangUkuranJendela(requirementCourseBrowser).size == 1)

    // Bagian 8.3 — kelas lebar (Tabel 5).
    cek("B8 599 dp compact", kelasLebarJendela(599) == "Compact")
    cek("B8 600 dp medium", kelasLebarJendela(600) == "Medium")
    cek("B8 839 dp medium", kelasLebarJendela(839) == "Medium")
    cek("B8 840 dp expanded", kelasLebarJendela(840) == "Expanded")
    cek("B8 1199 dp expanded", kelasLebarJendela(1199) == "Expanded")
    cek("B8 1200 dp large", kelasLebarJendela(1200) == "Large")
    cek("B8 1600 dp extra-large", kelasLebarJendela(1600) == "Extra-large")
    cek("B8 479 dp tinggi compact", kelasTinggiJendela(479) == "Compact")
    cek("B8 480 dp tinggi medium", kelasTinggiJendela(480) == "Medium")

    // Bagian 8.5 — kebijakan dua panel (Tabel 7).
    cek("B8 840 x 480 dp dua panel", kebijakanDuaPanel(840, 480))
    cek("B8 839 x 800 dp tetap satu panel", !kebijakanDuaPanel(839, 800))
    cek("B8 1000 x 479 dp tetap satu panel", !kebijakanDuaPanel(1000, 479))
    cek("B8 700 x 900 dp (medium) satu panel", !kebijakanDuaPanel(700, 900))
    cek("B8 1000 x 800 dp dua panel", kebijakanDuaPanel(1000, 800))
    cek("B8 alasan medium menyebut separuh lebar",
        alasanMediumSatuPanel(700).contains("350"))

    // Bagian 11.2 — matriks uji (Tabel 10).
    cek("B11 delapan baris matriks uji", matriksUjiCourseBrowser.size == 8)
    cek("B11 360 x 800 dp diharapkan satu panel",
        harapanDuaPanel(matriksUjiCourseBrowser[0]) == false)
    cek("B11 700 x 900 dp diharapkan satu panel",
        harapanDuaPanel(matriksUjiCourseBrowser[1]) == false)
    cek("B11 1000 x 800 dp diharapkan dua panel",
        harapanDuaPanel(matriksUjiCourseBrowser[2]) == true)
    cek("B11 900 x 400 dp diharapkan satu panel",
        harapanDuaPanel(matriksUjiCourseBrowser[3]) == false)
    cek("B11 kasus tanpa ukuran tidak punya harapan panel",
        harapanDuaPanel(matriksUjiCourseBrowser[4]) == null)

    // Bagian 12 — masalah umum (Tabel 12).
    cek("B12 tujuh baris masalah umum", masalahUmumAdaptif.size == 7)
    cek("B12 setiap masalah punya penyebab dan solusi", semuaMasalahPunyaSolusi())
    cek("B12 masalah tanpa solusi ditolak",
        !semuaMasalahPunyaSolusi(listOf(MasalahUmumAdaptif("Gejala", "Penyebab", ""))))

    return gagal
}

/** Mencetak hasil verifikasi; mengembalikan true bila semua lulus. */
fun jalankanVerifikasiModul11(): Boolean {
    val gagal = verifikasiPerilakuCourseBrowser()
    val total = jumlahCekDijalankan
    println("Verifikasi Modul 11 — UI/UX and Adaptive Layout")
    println("Kasus uji: ${total - gagal.size}/$total lulus")
    if (gagal.isEmpty()) {
        println("OK — seluruh pemeriksaan aturan desain dan layout adaptif lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    return gagal.isEmpty()
}

/** Titik masuk untuk menjalankan pemeriksaan dari terminal. */
fun main() {
    println("Kebijakan layout Campus Course Browser (Tabel 7):")
    listOf(360 to 800, 700 to 900, 840 to 800, 1000 to 800, 900 to 400).forEach { (lebar, tinggi) ->
        val panel = if (kebijakanDuaPanel(lebar, tinggi)) "dua panel" else "satu panel"
        println(
            "  $lebar x $tinggi dp → ${kelasLebarJendela(lebar)}/" +
                "${kelasTinggiJendela(tinggi)} → $panel"
        )
    }
    println()
    if (!jalankanVerifikasiModul11()) {
        kotlin.system.exitProcess(1)
    }
}
