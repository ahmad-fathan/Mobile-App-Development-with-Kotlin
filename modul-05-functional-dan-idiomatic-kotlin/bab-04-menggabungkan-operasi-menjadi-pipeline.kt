/**
 * Bab 4 — Menggabungkan Operasi menjadi Pipeline
 *
 * Karena setiap operasi menghasilkan list baru, hasil satu operasi dapat langsung
 * menjadi input operasi berikutnya. Pipeline dibaca dari atas ke bawah, satu baris
 * satu tujuan. Product dideklarasikan di bab 3.
 */

fun contohPipeline() {
    val products = listOf(
        Product("Pen", 10),
        Product("Book", 25),
        Product("Bag", 80)
    )
    val names = products
        .filter { it.price >= 20 }
        .sortedBy { it.price }
        .map { it.name }

    println(names)
    println(products.map { it.name }) // list asal tidak berubah
}

/**
 * Latihan Bagian 2
 * Setelah filter: [8, 5] — 2 dan 1 dibuang karena tidak lebih dari 2.
 * Setelah sortedBy: [5, 8] — urutan berubah karena diurutkan menaik (filter
 * mempertahankan urutan asal, sortedBy tidak).
 * Setelah map: [50, 80] — setiap elemen dikalikan 10.
 */
fun latihanBagian2(): Triple<List<Int>, List<Int>, List<Int>> {
    val numbers = listOf(8, 2, 5, 1)

    val setelahFilter = numbers.filter { it > 2 }
    val setelahSorted = setelahFilter.sortedBy { it }
    val setelahMap = setelahSorted.map { it * 10 }

    println("setelah filter   : $setelahFilter")
    println("setelah sortedBy : $setelahSorted")
    println("setelah map      : $setelahMap")

    return Triple(setelahFilter, setelahSorted, setelahMap)
}
