/**
 * Bab 10 — Memilih Collection dan Collection Berisi Object
 *
 * Array : ukuran tetap       → nilai latihan yang jumlahnya tetap
 * List  : berurutan, duplikat → mata kuliah yang diambil
 * Set   : elemen unik         → keahlian atau tag
 * Map   : key unik → value    → NIM ke nama
 *
 * Pilih versi mutable bila isi collection perlu berubah selama program berjalan.
 */

data class StudentEntry(val id: Int, val name: String)

/**
 * Pola yang paling sering dipakai saat menampilkan daftar data di layar:
 * data class mendefinisikan bentuk data → object disimpan dalam List<Student>
 * → perulangan membaca property setiap object.
 */
fun contohCollectionBerisiObject() {
    val students = listOf(
        StudentEntry(101, "Ali"),
        StudentEntry(102, "Budi")
    )

    for (student in students) {
        println(student.name)
    }
}

/** Membandingkan sifat tiap wadah pada data yang sama. */
fun contohRangkumanCollection() {
    val courses = listOf("Kotlin", "AI", "Data Science", "Kotlin")
    val skills = setOf("Kotlin", "Java", "Kotlin")
    val students = mapOf(101 to "Ali", 102 to "Budi")

    println("courses : ${courses.size} elemen, duplikat boleh")
    println("skills  : ${skills.size} elemen unik")
    println("students: ${students.size} entri")
}

/** Latihan Bagian: pilih wadah sesuai sifat datanya. */
fun latihanMemilihCollection() {
    val scores = intArrayOf(80, 90, 75) // ukuran tetap
    val courses = mutableListOf("Kotlin", "AI") // berurutan, bisa berubah
    val tags = mutableSetOf("Android", "Kotlin") // unik
    val studentByNim = mapOf(101 to "Ali", 102 to "Budi") // key → value

    courses.add("Data Science")
    tags.add("Android")

    println("scores  : ${scores.joinToString()}")
    println("courses : $courses")
    println("tags    : $tags")
    println("NIM 102 : ${studentByNim[102]}")
}
