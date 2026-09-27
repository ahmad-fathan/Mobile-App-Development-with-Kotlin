/**
 * Verifikasi otomatis untuk seluruh fungsi yang bisa diuji tanpa masukan keyboard.
 * Jalankan dengan: java -jar app.jar verifikasi
 */
fun jalankanVerifikasiModul4(): Boolean {
    val hasil = mutableListOf<Pair<String, Boolean>>()

    fun cek(nama: String, kondisi: Boolean) {
        hasil += nama to kondisi
        println(if (kondisi) "  OK   $nama" else "  GAGAL $nama")
    }

    println("Verifikasi Modul 4 — Kotlin-Specific Features & Collections")

    // Bab 1 — data class.
    cek("toString() otomatis", Student(101, "Ali").toString() == "Student(id=101, name=Ali)")
    cek("== membandingkan isi object", Student(101, "Ali") == Student(101, "Ali"))
    cek("== menolak isi yang berbeda", Student(101, "Ali") != Student(102, "Budi"))
    cek("hashCode() stabil untuk isi yang sama", Student(101, "Ali").hashCode() == Student(101, "Ali").hashCode())
    cek("copy() menghasilkan object baru", Student(101, "Ali").copy(name = "Ali Ahmad") != Student(101, "Ali"))
    cek("copy() hanya mengubah property yang disebut", Student(101, "Ali").copy(name = "Ali Ahmad").id == 101)
    cek("copy() tidak mengubah object asli", Student(101, "Ali").let { it.copy(name = "Ali Ahmad"); it.name == "Ali" })

    // Bab 2 — enum class.
    cek("enum membatasi nilai status", StudentWithStatus("Ali", StudentStatus.ACTIVE).status == StudentStatus.ACTIVE)
    cek("describe(ACTIVE) = Currently studying", describeStatus(StudentStatus.ACTIVE) == "Currently studying")
    cek("describe(INACTIVE) = Not currently studying", describeStatus(StudentStatus.INACTIVE) == "Not currently studying")
    cek("describe(GRADUATED) = Completed studies", describeStatus(StudentStatus.GRADUATED) == "Completed studies")
    cek("enum punya 3 konstanta", StudentStatus.entries.size == 3)

    // Bab 3 — object dan companion object.
    cek("object AppConfig diakses tanpa instance", AppConfig.APP_NAME == "Student App")
    cek("konstanta VERSION", AppConfig.VERSION == "1.0")
    cek("companion object diakses lewat nama class", UniversityStudent.UNIVERSITY == "UII")
    cek("instance tetap punya property sendiri", UniversityStudent("Ali").name == "Ali")

    // Bab 4 — extension function.
    cek("extension pada String memakai this", "Ali".greeting() == "Hello, Ali!")
    cek("extension pada data class", StudentGrade("Ali", 85).isPassed())
    cek("isPassed() batas 60: 60 lulus", StudentGrade("Ali", 60).isPassed())
    cek("isPassed() batas 60: 59 tidak lulus", !StudentGrade("Ali", 59).isPassed())
    cek("isExpensive() menerima > 500000", Product("Laptop", 12_000_000).isExpensive())
    cek("isExpensive() menolak 500000", !Product("Mouse", 500_000).isExpensive())

    // Bab 5 — array.
    cek("array diakses lewat indeks mulai 0", arrayOf("Kotlin", "AI")[0] == "Kotlin")
    cek("elemen array dapat diganti", intArrayOf(80, 90, 75).let { it[2] = 95; it[2] == 95 })
    cek("ukuran array tetap setelah dibuat", intArrayOf(80, 90, 75).size == 3)
    cek("latihan array: elemen terakhir 95", latihanArray()[2] == 95)
    cek("intArrayOf menyimpan Int", intArrayOf(1, 2).sum() == 3)
    cek("charArrayOf menyimpan Char", charArrayOf('A', 'B').size == 2)

    // Bab 6 — collection read-only dan mutable.
    cek("listOf() read-only tapi bisa dibaca", listOf("Kotlin", "AI").size == 2)
    cek("referensi read-only melihat perubahan referensi mutable", mutableListOf("Kotlin").let { it.add("AI"); it.size == 2 })
    cek("tiga jenis collection hidup berdampingan", listOf(1).size == 1 && setOf(1, 1).size == 1 && mapOf(1 to "a").size == 1)

    // Bab 7 — List dan MutableList.
    cek("List menjaga urutan dan indeks", listOf("Kotlin", "AI", "Kotlin")[1] == "AI")
    cek("List mengizinkan duplikat", listOf("Kotlin", "AI", "Kotlin").size == 3)
    cek("indexOf() memberi posisi pertama", listOf("Kotlin", "AI", "Kotlin").indexOf("Kotlin") == 0)
    cek("MutableList add()", mutableListOf("Kotlin", "AI").also { it.add("Data Science") }.size == 3)
    cek("MutableList ganti elemen pada indeks", mutableListOf("Kotlin", "AI").let { it[1] = "ML"; it[1] == "ML" })
    cek("MutableList remove() berdasarkan nilai", mutableListOf("Kotlin", "AI").also { it.remove("Kotlin") } == listOf("AI"))
    cek("val tidak mengunci isi mutable list", mutableListOf("Kotlin").also { it.add("AI") }.size == 2)
    cek("latihan keranjang: Keyboard & Headset ada, Mouse hilang", mutableListOf("Laptop", "Mouse").let { cart ->
        cart.add("Keyboard")
        cart.add("Headset")
        cart.remove("Mouse")
        "Keyboard" in cart && "Headset" in cart && "Mouse" !in cart && cart.size == 3
    })

    // Bab 8 — Set dan MutableSet.
    cek("Set membuang duplikat", setOf("Kotlin", "Java", "Kotlin").size == 2)
    cek("operator in memeriksa keanggotaan", "Kotlin" in setOf("Kotlin", "Java"))
    cek("operator in menolak nilai yang tidak ada", "Swift" !in setOf("Kotlin", "Java"))
    cek("MutableSet add() elemen baru", mutableSetOf("Kotlin", "Java").also { it.add("Python") }.size == 3)
    cek("MutableSet mengabaikan elemen kembar", mutableSetOf("Kotlin", "Java").also { it.add("Kotlin") }.size == 2)
    cek("MutableSet remove()", "Java" !in mutableSetOf("Kotlin", "Java").also { it.remove("Java") })
    cek("latihan tag: ukuran tetap 3 walau Kotlin dimasukkan dua kali", latihanTagKeahlian() == 3)

    // Bab 9 — Map dan MutableMap.
    cek("Map membaca value lewat key", mapOf(101 to "Ali")[101] == "Ali")
    cek("key yang tidak ada menghasilkan null", mapOf(101 to "Ali")[999] == null)
    cek("MutableMap menambah entri baru", mutableMapOf(101 to "Ali").also { it[102] = "Budi" }.size == 2)
    cek("MutableMap mengganti value pada key yang sama", mutableMapOf(102 to "Budi").also { it[102] = "Budi Santoso" }[102] == "Budi Santoso")
    cek("MutableMap remove()", 101 !in mutableMapOf(101 to "Ali").also { it.remove(101) })
    cek("Map tidak menyimpan key kembar", mutableMapOf(101 to "Ali").also { it[101] = "Ali Ahmad" }.size == 1)
    cek("latihan daftar nilai: 101 terhapus", 101 !in latihanDaftarNilai())
    cek("latihan daftar nilai: 102 diperbarui", latihanDaftarNilai()[102] == 95)

    // Bab 10 — memilih collection dan collection berisi object.
    cek("List<Student> memakai property object", listOf(StudentEntry(101, "Ali")).first().name == "Ali")
    cek("List berisi object dapat diiterasi", listOf(StudentEntry(101, "Ali"), StudentEntry(102, "Budi")).map { it.id } == listOf(101, 102))
    cek("Array untuk jumlah tetap", intArrayOf(80, 90, 75).size == 3)

    // Bab 11 — destructuring.
    cek("destructuring data class mengikuti urutan property", Student(101, "Ali").let { val (id, name) = it; id == 101 && name == "Ali" })
    cek("destructuring entri Map", mapOf(101 to "Ali").entries.first().let { val (id, name) = it; id == 101 && name == "Ali" })
    cek("component1/component2 tersedia otomatis", Student(101, "Ali").component1() == 101 && Student(101, "Ali").component2() == "Ali")
    cek("destructuring contoh gabungan", StudentRecord(101, "Ali", StudentStatus.ACTIVE).let { val (id, name, status) = it; id == 101 && name == "Ali" && status == StudentStatus.ACTIVE })

    // Latihan akhir.
    cek("latihan 1: list akhir berisi 3 mata kuliah", latihan1ProgramMataKuliah().size == 3)
    cek("latihan 1: displayInfo() memakai format modul", Course("PAB101", "Mobile App Development", CourseStatus.ACTIVE).displayInfo() == "PAB101 - Mobile App Development - ACTIVE")
    cek("latihan 2: describe() tanpa else", describe(CourseStatus.COMPLETED) == "Sudah selesai")
    cek("latihan 3: ukuran Set 3", latihan3TagKeahlian() == 3)
    cek("latihan 4: hasil Map setelah update & remove", latihan4DaftarNilai() == mutableMapOf(102 to 95, 103 to 75))
    cek("latihan 5: konstanta PREFIX dan MAX_COURSES", Course.PREFIX == "PAB" && AppConfig.MAX_COURSES == 5)
    cek("latihan 5: tolak saat penuh", mutableListOf<Course>().let {
        repeat(AppConfig.MAX_COURSES) { i -> it.add(Course("PAB10$i", "MK $i", CourseStatus.ACTIVE)) }
        !it.addCourse(Course("PAB199", "Kelebihan", CourseStatus.ACTIVE)) && it.size == AppConfig.MAX_COURSES
    })
    cek("latihan 5: tolak kode di luar prefix", !mutableListOf<Course>().addCourse(Course("TIF101", "Basis Data", CourseStatus.ACTIVE)))
    cek("latihan 5: terima kode benar saat masih ada slot", mutableListOf<Course>().addCourse(Course("PAB101", "Mobile App", CourseStatus.ACTIVE)))

    val gagal = hasil.count { !it.second }
    println("Total: ${hasil.size} pemeriksaan, $gagal gagal")
    return gagal == 0
}
