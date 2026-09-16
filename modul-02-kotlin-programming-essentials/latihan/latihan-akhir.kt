/**
 * Latihan Akhir Modul 2 — lima soal yang menggabungkan seluruh konsep.
 */

/**
 * Soal 1: nama mata kuliah dengan val, jumlah pertemuan dengan var,
 * tambah satu pertemuan, tampilkan kalimat lengkap dengan String template.
 */
fun latihan1Pertemuan() {
    val namaMataKuliah = "Pengembangan Aplikasi Bergerak"
    var jumlahPertemuan = 6

    jumlahPertemuan += 1

    println("$namaMataKuliah sudah berlangsung $jumlahPertemuan pertemuan")
}

/** Inti hitungan soal 2: rata-rata sebagai Double, pembaginya bukan bilangan bulat. */
fun hitungRataRata(nilaiTugas: Int, nilaiUjian: Int): Double {
    return (nilaiTugas + nilaiUjian) / 2.0
}

/**
 * Soal 2: minta nilai tugas dan nilai ujian sebagai bilangan bulat,
 * hitung rata-ratanya sebagai Double, tampilkan dengan String template.
 */
fun latihan2RataRata() {
    print("Nilai tugas: ")
    val nilaiTugas = readln().toInt()

    print("Nilai ujian: ")
    val nilaiUjian = readln().toInt()

    val rataRata = hitungRataRata(nilaiTugas, nilaiUjian)
    println("Rata-rata: $rataRata")
}

/** Inti soal 3: keterangan kelulusan memakai if sebagai expression. */
fun keteranganKelulusan(rataRata: Double): String {
    return if (rataRata >= 60) "Lulus" else "Perlu perbaikan"
}

/** Soal 3: lanjutan soal 2 dengan keterangan kelulusan. */
fun latihan3Kelulusan() {
    val rataRata = hitungRataRata(80, 90)
    val keterangan = keteranganKelulusan(rataRata)

    println("Rata-rata $rataRata → $keterangan")
}

/** Soal 4: when mengubah angka bulan 1..12 menjadi nama bulan, else untuk luar jangkauan. */
fun namaBulan(bulan: Int): String {
    return when (bulan) {
        1 -> "Januari"
        2 -> "Februari"
        3 -> "Maret"
        4 -> "April"
        5 -> "Mei"
        6 -> "Juni"
        7 -> "Juli"
        8 -> "Agustus"
        9 -> "September"
        10 -> "Oktober"
        11 -> "November"
        12 -> "Desember"
        else -> "Bulan tidak valid"
    }
}

fun latihan4NamaBulan() {
    for (bulan in listOf(1, 8, 12, 13)) {
        println("$bulan → ${namaBulan(bulan)}")
    }
}

/** Soal 5: fungsi total SKS, dipanggil di dalam perulangan for. */
fun hitungTotalSks(jumlahMataKuliah: Int, sksPerMataKuliah: Int): Int {
    return jumlahMataKuliah * sksPerMataKuliah
}

fun latihan5TotalSks() {
    for (jumlah in 1..5) {
        val total = hitungTotalSks(jumlah, 3)
        println("$jumlah mata kuliah × 3 SKS = $total SKS")
    }
}
