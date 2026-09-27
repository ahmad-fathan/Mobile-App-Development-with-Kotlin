/**
 * Bab 1 — Data Class
 *
 * data class adalah class yang tugas utamanya menyimpan data.
 * Compiler membuat sendiri semua fungsi berikut berdasarkan property di primary constructor:
 * toString(), equals(), hashCode(), copy(), dan componentN().
 */
data class Student(
    val id: Int,
    val name: String
)

/** toString() otomatis: object langsung tercetak dalam bentuk yang mudah dibaca. */
fun contohToString() {
    val student = Student(101, "Ali")
    println(student)
}

/** equals() membuat operator == membandingkan isi object, bukan lokasi object di memori. */
fun contohEquals() {
    val first = Student(101, "Ali")
    val second = Student(101, "Ali")
    val third = Student(102, "Budi")

    println(first == second) // true — isinya sama
    println(first == third) // false — isinya berbeda
    println(first === second) // false — dua object yang terpisah
}

/** copy() membuat salinan dengan sebagian nilai diubah; object asli tidak ikut berubah. */
fun contohCopy() {
    val original = Student(101, "Ali")
    val updated = original.copy(name = "Ali Ahmad")

    println(original)
    println(updated)
}

/** Latihan Bagian: dua object, satu salinan dengan nama berbeda. */
fun latihanDataClass() {
    val a = Student(101, "Ali")
    val b = Student(102, "Budi")

    println(a)
    println(b)
    println(a.copy(name = "Ali Ahmad"))
    println("isi a tetap: $a")
}
