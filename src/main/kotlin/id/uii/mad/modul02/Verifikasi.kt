package id.uii.mad.modul02

import id.uii.mad.modul02.latihan.hitungRataRata
import id.uii.mad.modul02.latihan.hitungTotalSks
import id.uii.mad.modul02.latihan.keteranganKelulusan
import id.uii.mad.modul02.latihan.namaBulan

/**
 * Verifikasi otomatis untuk seluruh fungsi yang bisa diuji tanpa masukan keyboard.
 * Jalankan dengan: ./gradlew run --args=verifikasi
 */
fun jalankanVerifikasi(): Boolean {
    val hasil = mutableListOf<Pair<String, Boolean>>()

    fun cek(nama: String, kondisi: Boolean) {
        hasil += nama to kondisi
        println(if (kondisi) "  OK   $nama" else "  GAGAL $nama")
    }

    println("Verifikasi Modul 2 — Kotlin Programming Essentials")

    cek("7 / 2 tetap bilangan bulat = 3", 7 / 2 == 3)
    cek("7 / 2.0 menjadi 3.5", 7 / 2.0 == 3.5)
    cek("3.9.toInt() membuang pecahan = 3", 3.9.toInt() == 3)
    cek("90 menit = 1.5 jam (Double)", 90.toDouble() / 60 == 1.5)
    cek("75 >= 60 && 85 >= 75", 75 >= 60 && 85 >= 75)
    cek("85 in 0..100", 85 in 0..100)
    cek("nilai 85 berada di antara 80 dan 100", 85 in 0..100 && 85 >= 80)
    cek("latihan sisa tugas 5 - 2 = 3", hitungSisaTugas(5, 2) == 3)
    cek("latihan waktu belajar 3 × 25 = 75", hitungMenitBelajar(3, 25) == 75)
    cek("latihan waktu belajar 4 × 30 = 120", hitungMenitBelajar(4, 30) == 120)
    cek("status tenggat -1 = Terlambat", statusTenggat(-1) == "Terlambat")
    cek("status tenggat 0 = Jatuh tempo hari ini", statusTenggat(0) == "Jatuh tempo hari ini")
    cek("status tenggat 2 = Belum jatuh tempo", statusTenggat(2) == "Belum jatuh tempo")
    cek("rata-rata (80 + 90) / 2.0 = 85.0", hitungRataRata(80, 90) == 85.0)
    cek("rata-rata tidak terpotong: (81 + 90) / 2.0 = 85.5", hitungRataRata(81, 90) == 85.5)
    cek("rata-rata 85.0 → Lulus", keteranganKelulusan(85.0) == "Lulus")
    cek("rata-rata 59.9 → Perlu perbaikan", keteranganKelulusan(59.9) == "Perlu perbaikan")
    cek("bulan 1 = Januari", namaBulan(1) == "Januari")
    cek("bulan 12 = Desember", namaBulan(12) == "Desember")
    cek("bulan 13 = Bulan tidak valid", namaBulan(13) == "Bulan tidak valid")
    cek("total SKS 1 × 3 = 3", hitungTotalSks(1, 3) == 3)
    cek("total SKS 5 × 3 = 15", hitungTotalSks(5, 3) == 15)
    cek("range 1..5 step 2 sama dengan [1, 3, 5]", (1..5 step 2).toList() == listOf(1, 3, 5))
    cek("progression 5 downTo 1 sama dengan [5, 4, 3, 2, 1]", (5 downTo 1).toList() == listOf(5, 4, 3, 2, 1))

    val gagal = hasil.count { !it.second }
    println("Total: ${hasil.size} pemeriksaan, $gagal gagal")
    return gagal == 0
}
