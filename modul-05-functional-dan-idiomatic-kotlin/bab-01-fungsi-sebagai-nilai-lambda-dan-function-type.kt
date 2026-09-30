/**
 * Bab 1 — Fungsi sebagai Nilai: Lambda dan Function Type
 *
 * Lambda adalah fungsi yang ditulis langsung sebagai nilai, tanpa nama dan tanpa
 * kata kunci fun. Setiap nilai di Kotlin punya tipe, termasuk fungsi: tipenya
 * disebut function type dan ditulis (tipe input) -> tipe hasil.
 */

/** Lambda satu parameter. Kurung kurawal { } menandai awal dan akhir lambda. */
fun contohLambda() {
    val double = { number: Int -> number * 2 }
    println(double(4)) // 8 — baru berjalan saat dipanggil
}

/** Lambda dengan dua input ditulis dengan pola yang sama. */
fun contohLambdaDuaInput() {
    val multiply = { a: Int, b: Int -> a * b }
    println(multiply(3, 4)) // 12
}

/**
 * Function type menyatakan dengan jelas jenis fungsi yang disimpan sebuah variabel.
 * Pada lambda di bawah, tipe Int tidak lagi ditulis: compiler menyimpulkannya
 * dari function type yang sudah dideklarasikan.
 */
fun contohFunctionType() {
    val double: (Int) -> Int = { number -> number * 2 }
    val combine: (String, String) -> String = { a, b -> a + b }
    val notifyUser: () -> Unit = { println("Saved") }

    println(double(5))
    println(combine("Kot", "lin"))
    notifyUser()
}

/** Latihan Bagian: dua deklarasi bertipe, satu di antaranya () -> Unit. */
fun latihanFungsiSebagaiNilai(): Int {
    val toSquare: (Int) -> Int = { number -> number * number }
    val label: (String) -> String = { text -> "[$text]" }

    println(toSquare(6))
    println(label("Kotlin"))

    return toSquare(6)
}
