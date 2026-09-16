package id.uii.mad.modul02

/**
 * Bab 5 — Perulangan
 */

/** Bentuk range dan progression. */
fun contohRange() {
    println((1..5).toList())          // [1, 2, 3, 4, 5]
    println((1 until 5).toList())     // [1, 2, 3, 4]
    println((5 downTo 1).toList())    // [5, 4, 3, 2, 1]
    println((1..5 step 2).toList())   // [1, 3, 5]

    val nilai = 85
    println(nilai in 0..100)          // true
}

/** for menelusuri setiap nilai dalam range. */
fun contohFor() {
    for (hari in 1..3) {
        println("Hari $hari: belajar Kotlin")
    }
}

/** while: yang menentukan berhenti adalah kondisi, bukan jumlah iterasi. */
fun contohWhile() {
    var sisaTugas = 3

    while (sisaTugas > 0) {
        println("Sisa tugas: $sisaTugas")
        sisaTugas--            // tanpa baris ini perulangan tidak pernah berhenti
    }
}

/** do-while memeriksa kondisi setelah blok dijalankan, jadi minimal sekali jalan. */
fun contohDoWhile() {
    var masukan = "salah"

    do {
        println("Memeriksa masukan: $masukan")
        masukan = "benar"
    } while (masukan != "benar")

    println("Selesai")
}

/** break menghentikan perulangan, continue melewati satu iterasi. */
fun contohBreakContinue() {
    for (angka in 1..5) {
        if (angka == 3) break
        println(angka)            // mencetak 1, 2
    }

    for (angka in 1..5) {
        if (angka == 3) continue
        println(angka)            // mencetak 1, 2, 4, 5
    }
}

/**
 * Latihan Bagian: Jadwal Belajar
 * Lima baris jadwal "Hari n: belajar 20 menit", lalu versi step untuk hari 1, 3, 5.
 */
fun latihanJadwalBelajar() {
    for (hari in 1..5) {
        println("Hari $hari: belajar 20 menit")
    }

    println("--- hanya hari ganjil ---")
    for (hari in 1..5 step 2) {
        println("Hari $hari: belajar 20 menit")
    }
}
