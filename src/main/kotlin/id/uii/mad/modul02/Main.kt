package id.uii.mad.modul02

import id.uii.mad.modul02.latihan.latihan1Pertemuan
import id.uii.mad.modul02.latihan.latihan2RataRata
import id.uii.mad.modul02.latihan.latihan3Kelulusan
import id.uii.mad.modul02.latihan.latihan4NamaBulan
import id.uii.mad.modul02.latihan.latihan5TotalSks

/**
 * Titik masuk seluruh contoh Modul 2.
 *
 * Jalankan tanpa argumen untuk memilih contoh dari menu,
 * atau dengan ./gradlew run --args=verifikasi untuk menjalankan verifikasi otomatis.
 */
fun main(args: Array<String>) {
    if (args.firstOrNull() == "verifikasi") {
        val lulus = jalankanVerifikasi()
        if (!lulus) kotlin.system.exitProcess(1)
        return
    }

    while (true) {
        println()
        println("=== Modul 2: Kotlin Programming Essentials ===")
        println(" 1. Struktur program dan variabel")
        println(" 2. Tipe data dan operator")
        println(" 3. Input dan output")
        println(" 4. Percabangan if dan when")
        println(" 5. Perulangan")
        println(" 6. Fungsi")
        println(" 7. Latihan akhir")
        println(" 8. Verifikasi otomatis")
        println(" 0. Keluar")
        print("Pilih menu: ")

        val pilihan = readlnOrNull()?.trim() ?: break

        when (pilihan) {
            "1" -> menuStrukturProgram()
            "2" -> menuTipeDataOperator()
            "3" -> menuInputOutput()
            "4" -> menuPercabangan()
            "5" -> menuPerulangan()
            "6" -> menuFungsi()
            "7" -> menuLatihanAkhir()
            "8" -> jalankanVerifikasi()
            "0" -> return
            else -> println("Pilihan tidak tersedia")
        }
    }
}

private fun menuStrukturProgram() {
    contohSambutan()
    contohValVar()
    contohValTidakBisaDiubah()
    contohKonstanta()
    latihanProfilMahasiswa()
}

private fun menuTipeDataOperator() {
    contohTipeDasar()
    contohTypeInference()
    contohKonversi()
    contohAritmetika()
    contohPenugasan()
    contohPerbandinganLogika()
    contohStringTemplate()
    latihanRataRataNilai()
}

private fun menuInputOutput() {
    contohBacaNama()
    contohTotalWaktuBelajar()
}

private fun menuPercabangan() {
    contohIfElse()
    contohIfExpression()
    contohElseIf()
    contohWhen()
    contohWhenRange()
    latihanStatusTenggat()
}

private fun menuPerulangan() {
    contohRange()
    contohFor()
    contohWhile()
    contohDoWhile()
    contohBreakContinue()
    latihanJadwalBelajar()
}

private fun menuFungsi() {
    contohFungsi()
    latihanWaktuBelajar()
}

private fun menuLatihanAkhir() {
    latihan1Pertemuan()
    latihan3Kelulusan()
    latihan4NamaBulan()
    latihan5TotalSks()
    // latihan2RataRata() menunggu masukan keyboard — dijalankan terakhir.
    latihan2RataRata()
}
