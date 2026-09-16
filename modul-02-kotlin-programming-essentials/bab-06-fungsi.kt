/**
 * Bab 6 — Fungsi
 */

/** Mendefinisikan fungsi: tidak mengembalikan nilai (Unit, penulisannya dihilangkan). */
fun tampilkanSambutan() {
    println("Selamat datang di Student Task Tracker!")
}

/** Parameter dan nilai kembalian. */
fun hitungSisaTugas(total: Int, selesai: Int): Int {
    return total - selesai
}

/** Expression body: ringkas untuk isi fungsi berupa satu perhitungan. */
fun hitungSisaTugasRingkas(total: Int, selesai: Int) = total - selesai

/** Parameter dengan nilai default. */
fun sambutMahasiswa(nama: String, kelas: String = "PAB"): String {
    return "Halo $nama dari kelas $kelas"
}

fun contohFungsi() {
    tampilkanSambutan()

    val sisa = hitungSisaTugas(5, 2)
    println("Sisa tugas: $sisa")
    println("Sisa tugas (ringkas): ${hitungSisaTugasRingkas(5, 2)}")
    println(sambutMahasiswa("Alya"))
}

/**
 * Latihan Bagian: Fungsi Waktu Belajar
 * hitungMenitBelajar(3, 25) = 75; hitungMenitBelajar(4, 30) = 120.
 */
fun hitungMenitBelajar(sesi: Int, menitPerSesi: Int): Int {
    return sesi * menitPerSesi
}

fun latihanWaktuBelajar() {
    println("${hitungMenitBelajar(3, 25)} menit")
    println("${hitungMenitBelajar(4, 30)} menit")
}
