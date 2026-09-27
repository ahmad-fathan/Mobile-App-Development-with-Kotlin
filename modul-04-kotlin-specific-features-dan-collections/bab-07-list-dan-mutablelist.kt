/**
 * Bab 7 — List dan MutableList
 *
 * List menjaga urutan elemen, mendukung indeks, dan mengizinkan duplikat.
 */
fun contohList() {
    val courses = listOf("Kotlin", "AI", "Kotlin")

    println(courses[0])
    println(courses.size) // duplikat tetap dihitung: 3
    println(courses.indexOf("AI"))
}

/** for mengambil elemen satu per satu sesuai urutan di dalam list. */
fun contohIterasiList() {
    val courses = listOf("Kotlin", "AI", "Data Science")

    for (course in courses) {
        println(course)
    }
}

/**
 * listOf() menghasilkan list yang isinya tidak dapat diubah.
 * Jika perlu menambah, mengganti, atau menghapus, gunakan mutableListOf().
 * Perhatikan: val hanya mengunci referensinya, isi mutable collection tetap bisa berubah.
 */
fun contohMutableList() {
    val courses = mutableListOf("Kotlin", "AI")

    courses.add("Data Science") // tambah di akhir
    courses[1] = "Machine Learning" // ganti pada indeks tertentu
    courses.remove("Kotlin") // hapus berdasarkan nilainya

    println(courses)
}

/**
 * Latihan Bagian 2: Keranjang Belanja
 * Mulai dari mutableListOf("Laptop", "Mouse"), tambah dua item, hapus satu,
 * cetak setiap product, lalu cetak jumlahnya.
 */
fun latihanKeranjangBelanja() {
    val cart = mutableListOf("Laptop", "Mouse")

    cart.add("Keyboard")
    cart.add("Headset")
    cart.remove("Mouse")

    for (product in cart) {
        println(product)
    }
    println("jumlah product: ${cart.size}")
}
