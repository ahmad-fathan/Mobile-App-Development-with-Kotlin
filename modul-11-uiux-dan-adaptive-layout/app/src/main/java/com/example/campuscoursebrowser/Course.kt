package com.example.campuscoursebrowser

/**
 * Model data Campus Course Browser (Bagian 10 modul).
 *
 * Deklarasi Course, sampleCourses, dan teks antarmuka berada di berkas ini, pada paket akar
 * aplikasi, karena dipakai ulang oleh bab, latihan, dan pemeriksaan otomatis. Satu source set
 * Kotlin hanya boleh mendeklarasikan satu nama, jadi jangan menyalin deklarasi ini ke berkas bab.
 *
 * Satu Course = satu item di daftar dan satu isi panel detail. Data disimpan di memori dengan
 * listOf(...): modul ini sengaja tanpa jaringan, login, database, maupun pendaftaran mata kuliah,
 * karena fokusnya keputusan UI/UX dan layout adaptif, bukan cara memperoleh data.
 *
 * Tiga aturan data contoh:
 *   1. Minimal tiga mata kuliah, agar daftar benar-benar perlu digulir pada ponsel.
 *   2. Id unik dan pendek — id dipakai sebagai key daftar dan disimpan rememberSaveable.
 *   3. Satu judul sengaja panjang ("Data Analysis and Visualization for Decision Making")
 *      untuk menguji pembungkusan teks saat ukuran font sistem diperbesar (Bagian 7.3).
 */

/** Satu mata kuliah: empat informasi, menjadi satu item di layar. */
data class Course(
    val id: String,
    val title: String,
    val summary: String,
    val description: String
)

/** Data contoh di memori: tiga id unik, satu judul panjang. */
val sampleCourses = listOf(
    Course(
        id = "mobile",
        title = "Mobile App Development",
        summary = "Build user interfaces with Kotlin and Compose.",
        description = "Practice designing and building mobile interfaces. " +
            "Learn to connect user tasks to components, state, and layouts."
    ),
    Course(
        id = "text",
        title = "Text Analytics",
        summary = "Find useful patterns in text data.",
        description = "Study text preparation, representation, and analysis. " +
            "Apply these ideas to practical text classification tasks."
    ),
    Course(
        id = "data",
        title = "Data Analysis and Visualization for Decision Making",
        summary = "Prepare, inspect, and communicate data.",
        description = "Use descriptive statistics and visualizations to understand data. " +
            "Explain findings through clear evidence and appropriate charts."
    )
)

/**
 * Teks antarmuka modul. Contoh di modul menulis teks literal agar mudah dibaca; aplikasi yang
 * mendukung banyak bahasa sebaiknya memindahkannya ke string resource (Bagian 10.5).
 */
object TeksCourseBrowser {
    const val JUDUL_APLIKASI = "Campus Course Browser"
    const val DETAIL = "Course details"
    const val PILIH_DETAIL = "Select a course to read its details."
    const val KEMBALI = "Back to courses"
    const val LIHAT_DETAIL = "View details"
    const val TERPILIH = "Selected course"

    /** Label aksesibilitas tombol; status terpilih dibawa TEKS, bukan warna saja (Bagian 7.1). */
    fun labelTombol(judul: String, terpilih: Boolean): String =
        if (terpilih) "Selected course: $judul" else "View details for $judul"
}
