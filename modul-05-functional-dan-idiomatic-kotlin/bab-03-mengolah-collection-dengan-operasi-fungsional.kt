/**
 * Bab 3 — Mengolah Collection dengan Operasi Fungsional
 *
 * Sebagian besar operasi collection adalah higher-order function yang menerima lambda.
 * Sifat yang perlu dipegang: hasilnya dikembalikan sebagai list baru, bukan ditulis
 * ke list asal, sehingga hasil operasi harus disimpan atau langsung dipakai.
 *
 * data class Product dideklarasikan di sini karena dipakai oleh bab ini, bab 4 (pipeline),
 * dan bab 5 (scope function).
 */

data class Product(val name: String, val price: Int)

/** Membuktikan hasil operasi adalah list baru dan list asal tidak berubah. */
fun contohHasilTidakMengubahAsal() {
    val prices = listOf(10, 25, 80)
    val expensive = prices.filter { it >= 20 }

    println(expensive)
    println(prices)
}

/** filter menerima predicate: elemen yang membuatnya true disimpan. */
fun contohFilter() {
    val prices = listOf(10, 25, 80)
    val expensive = prices.filter { price -> price >= 20 }
    println(expensive)
}

/** map mengubah setiap elemen; jumlah elemen tetap, tipe elemen boleh berubah. */
fun contohMap() {
    val prices = listOf(10, 25, 80)
    val labels = prices.map { price -> "Rp$price" }
    println(labels)
}

/** find berhenti pada elemen pertama yang cocok; tipenya nullable. */
fun contohFind() {
    val prices = listOf(10, 25, 80)
    val firstExpensive: Int? = prices.find { it >= 20 }
    val missing: Int? = prices.find { it > 100 }

    println(firstExpensive)
    println(missing)
    println(if (missing == null) "tidak ada yang cocok" else "ada yang cocok")
}

/** forEach hanya melakukan aksi; hasilnya Unit, bukan list baru. */
fun contohForEach() {
    val names = listOf("Pen", "Book", "Bag")
    names.forEach { name -> println(name) }
}

/** sortedBy menghasilkan list baru yang terurut naik berdasarkan nilai dari lambda. */
fun contohSortedBy() {
    val products = listOf(
        Product("Pen", 10),
        Product("Bag", 80),
        Product("Book", 25)
    )
    val sorted = products.sortedBy { it.price }

    println(sorted.map { it.name })
    println(products.map { it.name })
}

/**
 * Latihan Bagian: menjawab pertanyaan dari Pendahuluan.
 * Dari daftar produk, ambil nama produk yang harganya paling sedikit 20.
 */
fun latihanPendahuluan(): List<String> {
    val products = listOf(
        Product("Pen", 10),
        Product("Book", 25),
        Product("Bag", 80)
    )

    val names = products.filter { it.price >= 20 }.map { it.name }
    println(names)

    return names
}
