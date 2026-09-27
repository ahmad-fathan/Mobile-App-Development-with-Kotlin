/**
 * Bab 3 — Object dan Companion Object
 */

/**
 * object declaration: mendefinisikan class sekaligus membuat satu-satunya instance.
 * Tidak ada pemanggilan constructor — anggotanya diakses lewat nama object.
 */
object AppConfig {
    const val APP_NAME = "Student App"
    const val VERSION = "1.0"
    const val MAX_COURSES = 5
}

/**
 * companion object: anggota yang terkait dengan class-nya, bukan dengan instance-nya.
 * Nilainya sama untuk semua object, jadi diakses lewat nama class.
 */
class UniversityStudent(val name: String) {
    companion object {
        const val UNIVERSITY = "UII"
    }
}

/** Anggota object diakses langsung: AppConfig.APP_NAME. */
fun contohObjectDeclaration() {
    println(AppConfig.APP_NAME)
    println(AppConfig.VERSION)
}

/** UNIVERSITY diakses lewat class, name hanya lewat instance. */
fun contohCompanionObject() {
    println(UniversityStudent.UNIVERSITY)

    val student = UniversityStudent("Ali")
    println(student.name)
}

/** Latihan Bagian: nilai bersama dibaca tanpa membuat instance. */
fun latihanObjectDanCompanion() {
    println("${AppConfig.APP_NAME} v${AppConfig.VERSION}")
    println("${UniversityStudent.UNIVERSITY} — batas mata kuliah: ${AppConfig.MAX_COURSES}")
}
