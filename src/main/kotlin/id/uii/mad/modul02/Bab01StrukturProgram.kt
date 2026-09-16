package id.uii.mad.modul02

/**
 * Bab 1 — Struktur Program dan Variabel
 *
 * Setiap program console Kotlin dimulai dari fungsi main().
 */

/** Konstanta: nilainya harus sudah pasti saat kode dikompilasi. */
const val MAX_TUGAS = 10

/** Contoh paling dasar: dua baris keluaran. */
fun contohSambutan() {
    println("Hello, Kotlin!")
    println("Selamat datang di kelas.")
}

/** val tidak dapat ditugaskan ulang, var dapat diubah. */
fun contohValVar() {
    val namaMahasiswa = "Alya"

    var tugasSelesai = 2
    tugasSelesai = 3

    println("$namaMahasiswa menyelesaikan $tugasSelesai tugas")
}

/** val yang ditugaskan ulang ditolak compiler — baris di bawah sengaja dikomentari. */
fun contohValTidakBisaDiubah() {
    val namaMataKuliah = "Pengembangan Aplikasi Bergerak"
    // namaMataKuliah = "Sains Data"   // galat kompilasi: val cannot be reassigned

    println(namaMataKuliah)
}

/** Konstanta ditulis di luar main() dan biasanya memakai HURUF_KAPITAL. */
fun contohKonstanta() {
    println("Maksimum tugas per mata kuliah: $MAX_TUGAS")
}

/**
 * Latihan Bagian: Profil Mahasiswa
 * Simpan nama dengan val, jumlah SKS dengan var, tambah 3 SKS, tampilkan dengan String template.
 */
fun latihanProfilMahasiswa() {
    val nama = "Alya"
    var jumlahSks = 20

    jumlahSks += 3

    println("$nama telah menempuh $jumlahSks SKS")
}
