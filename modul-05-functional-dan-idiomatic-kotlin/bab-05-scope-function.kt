/**
 * Bab 5 — Scope Function
 *
 * Scope function menjalankan satu blok kode dengan satu object sebagai konteksnya.
 * Untuk memilih, jawab dua pertanyaan: object dirujuk dengan it atau this, dan
 * fungsi mengembalikan object itu sendiri atau hasil lambda.
 *
 * Product dideklarasikan di bab 3.
 */

class Profile {
    var name: String = ""
    var city: String = ""
}

/** let: object dirujuk dengan it, mengembalikan hasil lambda; sering dipasangkan ?. */
fun contohLet() {
    val name: String? = "Alya"
    val message: String? = name?.let { "Hello, $it" }
    println(message)

    val emptyName: String? = null
    val emptyMessage: String? = emptyName?.let { "Hello, $it" }
    println(emptyMessage) // safe call ?. yang melewati let, bukan let-nya
}

/** apply: object dirujuk dengan this, mengembalikan object itu sendiri. */
fun contohApply() {
    val profile = Profile().apply {
        name = "Alya"
        city = "Yogyakarta"
    }

    println(profile.name)
    println(profile.city)
}

/** also: object dirujuk dengan it, mengembalikan object yang sama (aksi sampingan). */
fun contohAlso() {
    val names = listOf("Alya", "Budi")
        .also { println("Loaded ${it.size} names") }

    println(names)
}

/** run dan with: keduanya merujuk this dan mengembalikan hasil lambda. */
fun contohRunDanWith() {
    val product = Product("Book", 25)
    val label = product.run { "$name costs $price" }
    val description = with(product) { "$name: $price" }

    println(label)
    println(description)
}

/** Latihan Bagian: susun Profil dengan apply, catat dengan also, hitung dengan run. */
fun latihanScopeFunction(): Profile {
    val profile = Profile().apply {
        name = "Budi"
        city = "Bandung"
    }.also { println("Profil dibuat untuk ${it.name}") }

    val ringkasan = profile.run { "$name tinggal di $city" }
    println(ringkasan)

    return profile
}
