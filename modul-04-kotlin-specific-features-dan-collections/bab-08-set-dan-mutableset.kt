/**
 * Bab 8 — Set dan MutableSet
 *
 * Set hanya menyimpan elemen unik: nilai yang sama dimasukkan lebih dari sekali
 * tetap tersimpan satu kali. Gunakan Set ketika keanggotaan dan keunikan data
 * lebih penting daripada urutan.
 */
fun contohSet() {
    val skills = setOf("Kotlin", "Java", "Kotlin")

    println(skills.size) // 2, bukan 3
    println("Kotlin" in skills) // operator in memeriksa keanggotaan
    println("Swift" in skills)
}

fun contohMutableSet() {
    val skills = mutableSetOf("Kotlin", "Java")

    skills.add("Python")
    skills.add("Kotlin") // tidak menambah elemen baru, ukuran tidak berubah
    skills.remove("Java")

    println(skills)
}

/**
 * Latihan Bagian 3: Tag Keahlian Unik
 * Ukuran Set tidak bertambah saat "Kotlin" ditambahkan untuk kedua kali karena
 * Set menyimpan setiap nilai unik satu kali saja.
 */
fun latihanTagKeahlian(): Int {
    val skills = mutableSetOf("Kotlin", "Java")

    skills.add("Python")
    skills.add("Kotlin")

    println(skills.size)
    println("Swift in skills : ${"Swift" in skills}")
    println("Python in skills: ${"Python" in skills}")
    return skills.size
}
