package id.uii.mad.modul02

/**
 * Bab 4 — Percabangan dengan if dan when
 */

/** if/else paling dasar. */
fun contohIfElse() {
    val nilai = 75

    if (nilai >= 60) {
        println("Lulus")
    } else {
        println("Perlu perbaikan")
    }
}

/** if sebagai expression: hasilnya langsung disimpan ke variabel, else wajib ada. */
fun contohIfExpression() {
    val nilai = 75
    val hasil = if (nilai >= 60) "Lulus" else "Perlu perbaikan"
    println(hasil)
}

/** Lebih dari dua pilihan: else if, diperiksa berurutan dari atas ke bawah. */
fun contohElseIf() {
    val nilai = 85

    if (nilai >= 80) {
        println("Sangat baik")
    } else if (nilai >= 60) {
        println("Lulus")
    } else {
        println("Perlu perbaikan")
    }
}

/** when lebih ringkas daripada rangkaian else if. */
fun contohWhen() {
    val pilihanMenu = 2

    val pesan = when (pilihanMenu) {
        1 -> "Tambah tugas"
        2 -> "Lihat tugas"
        3 -> "Keluar"
        else -> "Pilihan tidak tersedia"
    }

    println(pesan)
}

/** when juga bisa menerima range sebagai cabang. */
fun contohWhenRange() {
    val nilai = 85

    val huruf = when (nilai) {
        in 80..100 -> "A"
        in 70..79 -> "B"
        in 60..69 -> "C"
        else -> "D"
    }

    println("Nilai huruf: $huruf")
}

/**
 * Latihan Bagian: Status Tenggat
 * sisaHari < 0 → "Terlambat", = 0 → "Jatuh tempo hari ini", > 0 → "Belum jatuh tempo".
 */
fun statusTenggat(sisaHari: Int): String {
    return if (sisaHari < 0) {
        "Terlambat"
    } else if (sisaHari == 0) {
        "Jatuh tempo hari ini"
    } else {
        "Belum jatuh tempo"
    }
}

fun latihanStatusTenggat() {
    for (sisaHari in listOf(-1, 0, 2)) {
        println("sisaHari=$sisaHari → ${statusTenggat(sisaHari)}")
    }
}
