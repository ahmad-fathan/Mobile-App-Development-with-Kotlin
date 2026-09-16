package id.uii.mad.modul02

/**
 * Bab 2 — Tipe Data dan Operator
 */

/** Tipe data dasar: Int, Long, Double, Float, Boolean, Char, String. */
fun contohTipeDasar() {
    val jumlahTugas: Int = 20
    val jarakLangkah: Long = 3_000_000_000L
    val ipk: Double = 3.75
    val suhu: Float = 36.5f
    val sudahLulus: Boolean = true
    val inisial: Char = 'A'
    val mataKuliah: String = "Kotlin"

    println("$jumlahTugas $jarakLangkah $ipk $suhu $sudahLulus $inisial $mataKuliah")
}

/** Type inference: tipe disimpulkan dari nilai awal, tetapi tidak boleh berganti. */
fun contohTypeInference() {
    val umur: Int = 20        // tipe ditulis eksplisit
    val umurLain = 20         // tipe disimpulkan sebagai Int

    println("$umur $umurLain")
}

/** Konversi tipe memakai toInt(), toLong(), toDouble(), toString(). */
fun contohKonversi() {
    val totalMenit = 90
    val jam = totalMenit.toDouble() / 60

    println(jam)              // 1.5
    println(3.9.toInt())      // 3 — bagian pecahan dibuang, bukan dibulatkan
}

/** Operator aritmetika, termasuk jebakan pembagian bilangan bulat. */
fun contohAritmetika() {
    println(7 + 2)            // 9
    println(7 - 2)            // 5
    println(7 * 2)            // 14
    println(7 / 2)            // 3
    println(7 % 2)            // 1
    println(7 / 2.0)          // 3.5

    val rataRata = (80 + 90) / 2.0
    println(rataRata)
}

/** Operator penugasan gabungan dan ++ / --. */
fun contohPenugasan() {
    var poin = 10
    poin += 5                 // 15
    poin -= 2                 // 13
    println(poin)

    var jumlahTugas = 2
    jumlahTugas++             // 3
    println(jumlahTugas)
}

/** Operator perbandingan dan logika. */
fun contohPerbandinganLogika() {
    val nilai = 75
    val kehadiran = 85

    println(nilai >= 60 && kehadiran >= 75)   // true
    println(nilai >= 80 || kehadiran >= 90)   // false
    println(!(nilai >= 60))                   // false
}

/** String template: $variabel untuk satu nilai, ${...} untuk perhitungan. */
fun contohStringTemplate() {
    val namaMahasiswa = "Alya"
    val totalTugas = 5
    val tugasSelesai = 2

    println("Halo, $namaMahasiswa!")
    println("Sisa tugas: ${totalTugas - tugasSelesai}")
}

/** Latihan Bagian bab ini dikerjakan bersama Bab 1: lihat latihanProfilMahasiswa(). */
fun latihanRataRataNilai() {
    val nilaiTugas = 80
    val nilaiUjian = 90

    val rataRata = (nilaiTugas + nilaiUjian) / 2.0
    println("Rata-rata nilai: $rataRata")
}
