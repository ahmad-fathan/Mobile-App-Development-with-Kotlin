/**
 * Bab 9 — Map dan MutableMap
 *
 * Map menghubungkan setiap key yang unik dengan sebuah value.
 * Pasangan key dan value ditulis dengan kata kunci to.
 */
fun contohMap() {
    val students = mapOf(
        101 to "Ali",
        102 to "Budi"
    )

    println(students[101])
    println(students[999]) // key tidak ada → null
}

/**
 * Pada MutableMap, penugasan dengan kurung siku punya dua arti:
 * menambah entri baru bila key belum ada, atau mengganti value bila key sudah ada.
 */
fun contohMutableMap() {
    val students = mutableMapOf(101 to "Ali", 102 to "Budi")

    students[103] = "Citra" // add
    students[102] = "Budi Santoso" // update
    students.remove(101) // remove

    println(students) // key selalu unik, jadi tidak mungkin ada entri kembar
}

/**
 * Latihan Bagian 4: Daftar Nilai
 * Map NIM ke nilai ujian: perbarui satu nilai, hapus satu entri, cetak tiap entri
 * dengan destructuring, lalu cetak nilai untuk NIM yang tidak ada di Map (null).
 */
fun latihanDaftarNilai(): MutableMap<Int, Int> {
    val scores = mutableMapOf(101 to 80, 102 to 90, 103 to 75)

    scores[102] = 95
    scores.remove(101)

    for ((nim, score) in scores) {
        println("$nim - $score")
    }
    println("NIM 999: ${scores[999]}") // null
    return scores
}
