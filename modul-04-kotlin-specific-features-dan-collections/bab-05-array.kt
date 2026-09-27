/**
 * Bab 5 — Array
 *
 * Array menyimpan beberapa nilai dalam satu variabel dan setiap nilai diakses
 * melalui indeks. Tiga sifatnya: indeks mulai dari 0, elemen dapat diganti,
 * dan ukurannya tetap setelah dibuat.
 */

fun contohArray() {
    val courses = arrayOf("Kotlin", "AI", "Data Science")

    println(courses[0]) // indeks dimulai dari 0
    courses[1] = "Machine Learning" // elemen dapat diganti
    println(courses.joinToString())
    println("jumlah elemen: ${courses.size}") // ukuran tetap
}

/** Array khusus untuk tipe dasar yang sering dipakai. */
fun contohArrayTipeDasar() {
    val scores = intArrayOf(80, 90, 75)
    val prices = doubleArrayOf(2.5, 3.7)
    val grades = charArrayOf('A', 'B')
    val answers = booleanArrayOf(true, false)

    println(scores.joinToString())
    println(prices.joinToString())
    println(grades.joinToString())
    println(answers.joinToString())
}

/** Latihan Bagian: ganti satu elemen lalu cetak seluruh isi array. */
fun latihanArray(): IntArray {
    val scores = intArrayOf(80, 90, 75)
    scores[2] = 95
    return scores
}
