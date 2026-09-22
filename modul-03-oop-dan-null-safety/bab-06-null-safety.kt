/**
 * Bab 6 — Null Safety
 *
 * Tipe tanpa tanda tanya bersifat non-nullable: null ditolak compiler.
 * Tipe dengan tanda tanya (String?) bersifat nullable: boleh berisi null.
 */

/** Elvis + safe call: gunakan nilai kiri bila ada, jika tidak nilai default. */
fun panjangNamaAman(nama: String?): Int {
    return nama?.length ?: 0
}

/** Jika null, kembalikan "Unknown" — tanpa memakai !!. */
fun namaTampilAman(nama: String?): String {
    return nama ?: "Unknown"
}

/** name non-nullable tidak dapat diisi null. */
fun contohTipeNonNullable() {
    var name: String = "Andi"
    println(name)

    // name = null // ditolak compiler
}

/** nickname nullable boleh diisi null. */
fun contohTipeNullable() {
    var nickname: String? = "Andi"
    println(nickname)

    nickname = null
    println(nickname)
}

/** Safe call (?.) mengembalikan null tanpa error bila nilainya null. */
fun contohSafeCall() {
    val name: String? = null
    println(name?.length)
}

/** Elvis operator (?:) menyediakan nilai default. */
fun contohElvis() {
    val name: String? = null
    val displayName = name ?: "Guest"
    println(displayName)

    val username: String? = null
    val length = username?.length ?: 0
    println(length)
}

/**
 * Not-null assertion (!!) mematikan perlindungan Null Safety.
 * Dipakai hanya ketika nilai benar-benar dijamin tidak null — jika ternyata null, aplikasi crash.
 */
fun contohNotNullAssertion() {
    val name: String? = "Andi"
    println(name!!.length)
}

/** Latihan Bagian: cetak panjang nama dan "Unknown" bila nilainya null, tanpa !!. */
fun latihanNullSafety() {
    var studentName: String? = null

    println("panjang : ${panjangNamaAman(studentName)}")
    println("nama    : ${namaTampilAman(studentName)}")

    studentName = "Alya"
    println("panjang : ${panjangNamaAman(studentName)}")
    println("nama    : ${namaTampilAman(studentName)}")
}
