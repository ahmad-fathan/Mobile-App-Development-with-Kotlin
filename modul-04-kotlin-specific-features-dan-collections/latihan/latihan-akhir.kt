/**
 * Latihan Akhir Modul 4 — lima soal yang menggabungkan seluruh konsep.
 */

// --- Soal 1 & 2 -----------------------------------------------------------

enum class CourseStatus { ACTIVE, COMPLETED }

data class Course(
    val code: String,
    val name: String,
    val status: CourseStatus
) {
    companion object {
        const val PREFIX = "PAB" // Soal 5
    }
}

/** Soal 1 langkah 5: teks seperti "PAB101 - Mobile App Development - ACTIVE". */
fun Course.displayInfo(): String = "$code - $name - $status"

/**
 * Soal 2: when tanpa cabang else.
 * Semua kemungkinan nilai enum sudah ditangani, sehingga when bersifat exhaustive.
 * Menambahkan konstanta DROPPED ke CourseStatus membuat compiler melaporkan bahwa
 * when ini belum lengkap — itulah gunanya enum dibandingkan String.
 */
fun describe(status: CourseStatus): String = when (status) {
    CourseStatus.ACTIVE -> "Sedang ditempuh"
    CourseStatus.COMPLETED -> "Sudah selesai"
}

fun latihan1ProgramMataKuliah(): MutableList<Course> {
    val courses = mutableListOf(
        Course("PAB101", "Mobile App Development", CourseStatus.ACTIVE),
        Course("PAB102", "Kotlin Fundamentals", CourseStatus.COMPLETED),
        Course("PAB103", "Data Structures", CourseStatus.ACTIVE)
    )

    courses.add(Course("PAB104", "Android UI", CourseStatus.ACTIVE))
    courses.removeAt(2)

    for (course in courses) {
        println(course.displayInfo())
    }

    val (code, name, status) = courses.first()
    println("destructuring: $code / $name / $status")

    return courses
}

fun latihan2DeskripsiStatus() {
    val courses = latihan1ProgramMataKuliah().toList()

    for (course in courses) {
        println("${course.code}: ${describe(course.status)}")
    }
}

// --- Soal 3 ---------------------------------------------------------------

/**
 * Soal 3: ukuran Set tidak bertambah saat "Kotlin" ditambahkan karena Set hanya
 * menyimpan setiap nilai satu kali — menambahkan elemen yang sudah ada diabaikan.
 */
fun latihan3TagKeahlian(): Int {
    val skills = mutableSetOf("Kotlin", "Java")

    skills.add("Python")
    skills.add("Kotlin")

    println("ukuran Set: ${skills.size}")
    println("Swift  in skills: ${"Swift" in skills}")
    println("Python in skills: ${"Python" in skills}")

    return skills.size
}

// --- Soal 4 ---------------------------------------------------------------

fun latihan4DaftarNilai(): MutableMap<Int, Int> {
    val scores = mutableMapOf<Int, Int>()

    scores[101] = 80
    scores[102] = 90
    scores[103] = 75

    scores[102] = 95 // perbarui nilai salah satu mahasiswa
    scores.remove(101) // hapus satu mahasiswa

    for ((nim, score) in scores) {
        println("$nim - $score")
    }
    println("NIM 999 (tidak ada di Map): ${scores[999]}")

    return scores
}

// --- Soal 5 ---------------------------------------------------------------

/**
 * Soal 5: mata kuliah hanya ditambahkan bila jumlahnya belum mencapai
 * AppConfig.MAX_COURSES dan kodenya diawali Course.PREFIX.
 */
fun MutableList<Course>.addCourse(course: Course): Boolean {
    if (size >= AppConfig.MAX_COURSES) return false
    if (!course.code.startsWith(Course.PREFIX)) return false

    add(course)
    return true
}

fun latihan5KonfigurasiAplikasi() {
    val courses = mutableListOf(
        Course("PAB101", "Mobile App Development", CourseStatus.ACTIVE),
        Course("PAB102", "Kotlin Fundamentals", CourseStatus.COMPLETED),
        Course("PAB103", "Data Structures", CourseStatus.ACTIVE),
        Course("PAB104", "Android UI", CourseStatus.ACTIVE),
        Course("PAB105", "Testing", CourseStatus.ACTIVE)
    )

    println("kode benar, tapi penuh     : ${courses.addCourse(Course("PAB106", "Room", CourseStatus.ACTIVE))}")
    println("kode salah, masih ada slot : ${courses.addCourse(Course("TIF101", "Basis Data", CourseStatus.ACTIVE))}")

    val kecil = mutableListOf<Course>()
    println("kode benar, masih ada slot : ${kecil.addCourse(Course("PAB101", "Mobile App Development", CourseStatus.ACTIVE))}")
    println("jumlah mata kuliah: ${kecil.size}")
}
