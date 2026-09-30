/**
 * Bab 6 — Generics Dasar
 *
 * List<T> adalah satu definisi untuk berbagai tipe. Huruf T adalah type parameter,
 * tempat tipe elemen ditentukan saat list dipakai; String pada List<String> disebut
 * type argument. Pemeriksaan tipe tetap berlaku: compiler menolak Int di List<String>.
 */

fun contohListGeneric() {
    val names: List<String> = listOf("Alya", "Budi")
    val scores: List<Int> = listOf(80, 90)

    println(names)
    println(scores)
}

/**
 * Fungsi generic: <T> mendeklarasikan type parameter, List<T> inputnya, T? hasilnya.
 * Dalam kode sebenarnya cukup memanggil firstOrNull() dari standard library.
 */
fun <T> firstOrNull(items: List<T>): T? {
    return items.firstOrNull()
}

fun contohFungsiGeneric() {
    val firstName: String? = firstOrNull(listOf("Alya", "Budi"))
    val firstScore: Int? = firstOrNull(listOf(80, 90))
    val nothing: Int? = firstOrNull(emptyList<Int>())

    println(firstName)
    println(firstScore)
    println(nothing)
}

/**
 * Generics dapat digabung dengan function type: T tipe elemen input, R tipe elemen hasil.
 * Di sini transformAll sekaligus memakai generics, (T) -> R, higher-order function, dan map.
 */
fun <T, R> transformAll(items: List<T>, change: (T) -> R): List<R> {
    return items.map(change)
}

fun contohFungsiGenericDenganLambda() {
    val lengths = transformAll(listOf("Pen", "Book")) { it.length }
    println(lengths)
}

/** Latihan Bagian: transformAll mengubah List<Int> menjadi List<String>. */
fun latihanGenerics(): List<String> {
    val labels = transformAll(listOf(10, 25, 80)) { "Rp$it" }
    println(labels)

    val nama: String? = firstOrNull(listOf("Kotlin", "AI"))
    println(nama)

    return labels
}
