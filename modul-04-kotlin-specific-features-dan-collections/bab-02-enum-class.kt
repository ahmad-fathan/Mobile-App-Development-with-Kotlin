/**
 * Bab 2 — Enum Class
 *
 * enum class mendefinisikan sekumpulan pilihan yang tetap, sehingga salah ketik
 * seperti "AKTIP" tidak mungkin terjadi: nilai di luar daftar ditolak compiler.
 */
enum class StudentStatus {
    ACTIVE, INACTIVE, GRADUATED
}

data class StudentWithStatus(
    val name: String,
    val status: StudentStatus
)

/**
 * Karena semua kemungkinan nilai enum sudah diketahui, when dapat bersifat exhaustive,
 * artinya setiap status pasti ditangani — sehingga cabang else tidak diperlukan.
 */
fun describeStatus(status: StudentStatus): String = when (status) {
    StudentStatus.ACTIVE -> "Currently studying"
    StudentStatus.INACTIVE -> "Not currently studying"
    StudentStatus.GRADUATED -> "Completed studies"
}

fun contohEnum() {
    val student = StudentWithStatus("Ali", StudentStatus.ACTIVE)
    println(student.status)
}

fun contohEnumDenganWhen() {
    for (status in StudentStatus.entries) {
        println("$status → ${describeStatus(status)}")
    }
}

/** Latihan Bagian: when tanpa else untuk seluruh nilai enum. */
fun latihanEnum() {
    val students = listOf(
        StudentWithStatus("Ali", StudentStatus.ACTIVE),
        StudentWithStatus("Budi", StudentStatus.INACTIVE),
        StudentWithStatus("Citra", StudentStatus.GRADUATED)
    )

    for (student in students) {
        println("${student.name}: ${describeStatus(student.status)}")
    }
}
