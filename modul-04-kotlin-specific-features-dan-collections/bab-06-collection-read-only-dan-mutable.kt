/**
 * Bab 6 — Collection Read-only dan Mutable
 *
 * Collection adalah wadah untuk sekelompok nilai. Kotlin memiliki tiga jenis utama,
 * dan masing-masing hadir dalam dua versi: read-only dan mutable.
 *
 *   List : elemen berurutan dan boleh ada duplikat
 *   Set  : setiap elemen unik
 *   Map  : setiap key yang unik terhubung ke sebuah value
 *
 * Interface read-only   Interface mutable   Fungsi pembuat
 * List                  MutableList         listOf(), mutableListOf()
 * Set                   MutableSet          setOf(), mutableSetOf()
 * Map                   MutableMap          mapOf(), mutableMapOf()
 *
 * Referensi read-only tidak menyediakan operasi tambah, hapus, atau ubah. Namun
 * "read-only" tidak menjamin object di baliknya tidak pernah berubah melalui
 * referensi lain.
 */

fun contohTigaJenisCollection() {
    val courses = listOf("Kotlin", "AI", "Kotlin") // berurutan, duplikat boleh
    val skills = setOf("Kotlin", "Java", "Kotlin") // unik
    val students = mapOf(101 to "Ali", 102 to "Budi") // key → value

    println(courses)
    println(skills)
    println(students)
}

/** Referensi read-only tidak bisa diubah isinya, tetapi isinya tetap dapat dibaca. */
fun contohReadOnly() {
    val courses = listOf("Kotlin", "AI")

    println("jumlah: ${courses.size}")
    println("elemen pertama: ${courses.first()}")
    // courses.add("Data Science") // ditolak: listOf() tidak menyediakan add()
}

/**
 * Read-only tidak berarti object di baliknya tidak pernah berubah:
 * referensi mutable lain yang menunjuk object sama tetap dapat mengubah isinya.
 */
fun contohReadOnlyTidakMenjaminTidakBerubah() {
    val mutable = mutableListOf("Kotlin")
    val readOnly: List<String> = mutable

    println("sebelum: $readOnly")
    mutable.add("AI") // perubahan lewat referensi mutable
    println("sesudah: $readOnly")
}

/** Pilih versi mutable hanya ketika isi collection perlu berubah saat program berjalan. */
fun latihanReadOnlyDanMutable() {
    val readOnlyCourses: List<String> = listOf("Kotlin", "AI")
    val mutableCourses: MutableList<String> = mutableListOf("Kotlin", "AI")

    mutableCourses.add("Data Science")

    println("read-only: $readOnlyCourses (${readOnlyCourses.size} elemen)")
    println("mutable  : $mutableCourses (${mutableCourses.size} elemen)")
}
