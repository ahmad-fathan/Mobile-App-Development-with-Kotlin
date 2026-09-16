/**
 * Bab 3 — Input dan Output di Console
 *
 * print()   : menampilkan teks tanpa berpindah baris
 * println() : menampilkan teks lalu berpindah baris
 * readln()  : membaca satu baris masukan keyboard sebagai String
 *
 * Catatan: seluruh fungsi pada bab ini menunggu masukan dari keyboard, sehingga
 * dijalankan satu per satu dari menu, bukan lewat verifikasi otomatis.
 */

/** print() + readln() berdampingan: kursor tetap di sebelah pertanyaan. */
fun contohBacaNama() {
    print("Masukkan nama Anda: ")
    val nama = readln()

    println("Halo, $nama!")
}

/** readln() selalu mengembalikan String, jadi angka harus dikonversi lebih dulu. */
fun contohTotalWaktuBelajar() {
    print("Jumlah sesi: ")
    val sesi = readln().toInt()

    print("Menit per sesi: ")
    val menit = readln().toInt()

    val total = sesi * menit
    println("Total waktu belajar: $total menit")
}
