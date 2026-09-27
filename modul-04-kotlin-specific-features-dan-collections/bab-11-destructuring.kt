/**
 * Bab 11 — Destructuring
 *
 * Destructuring memberikan property dari primary constructor ke beberapa variabel
 * sekaligus, sesuai urutannya. Data class Student dan enum StudentStatus sudah
 * didefinisikan pada bab sebelumnya.
 */

fun contohDestructuring() {
    val student = Student(101, "Ali")
    val (id, name) = student

    println("$id - $name")
}

/** Urutan variabel mengikuti urutan property, bukan nama variabelnya. */
fun contohUrutanDestructuring() {
    val student = Student(101, "Ali")
    val (a, b) = student

    println("component1 → a = $a")
    println("component2 → b = $b")
}

/** Destructuring pada entri Map memecah langsung menjadi key dan value. */
fun contohDestructuringMap() {
    val students = mapOf(101 to "Ali", 102 to "Budi")

    for ((id, name) in students) {
        println("$id - $name")
    }
}

/**
 * Latihan Bagian 5: Contoh Gabungan
 * enum StudentStatus (Bab 2) + data class + extension function + MutableList dipakai bersama.
 */
data class StudentRecord(val id: Int, val name: String, val status: StudentStatus)

fun StudentRecord.displayInfo(): String = "$id - $name - $status"

fun latihanContohGabungan() {
    val students = mutableListOf(
        StudentRecord(101, "Ali", StudentStatus.ACTIVE),
        StudentRecord(102, "Budi", StudentStatus.INACTIVE)
    )

    students.add(StudentRecord(103, "Citra", StudentStatus.ACTIVE))

    for (student in students) {
        println(student.displayInfo())
    }

    val (id, name, status) = students.first()
    println("destructuring: $id / $name / $status")
}
