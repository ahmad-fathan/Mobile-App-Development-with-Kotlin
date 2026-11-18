package com.example.minicampus

import com.example.minicampus.model.Course
import com.example.minicampus.navigation.CourseDetail
import com.example.minicampus.navigation.Courses
import com.example.minicampus.navigation.Home
import com.example.minicampus.navigation.Profile
import com.example.minicampus.viewmodel.CourseViewModel

/**
 * Verifikasi perilaku modul 16 (Aplikasi Multi-Screen dan Pola Navigasi) tanpa emulator.
 *
 * Berkas ini memakai Kotlin stdlib saja — tanpa Compose dan tanpa Android — tetapi tetap
 * menguji kode aplikasi yang sebenarnya: [CourseViewModel] dan keempat route di
 * `navigation/Destinations.kt`. Model back stack di bawah meniru perilaku
 * `navigate()` / `popBackStack()` / `popUpTo` / `launchSingleTop` Navigation Compose
 * sehingga Tabel 4 dan Tabel 5 modul dapat diperiksa.
 *
 *   kotlinc berkas... -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *   (dari root repositori: ./jalankan.sh 16 perilaku)
 */

// ---------------------------------------------------------------------------
// 0. Penghitung pemeriksaan
// ---------------------------------------------------------------------------

private var jumlahCek: Int = 0
private var gagal: Int = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCek++
    if (kondisi) println("OK    $deskripsi") else {
        gagal++
        println("GAGAL $deskripsi")
    }
}

// ---------------------------------------------------------------------------
// 1. Model back stack (meniru NavController)
// ---------------------------------------------------------------------------

/** Satu entri back stack: nama destination + argumennya (disimpan sebagai teks, seperti saved state). */
data class Entri(val route: String, val argumen: Map<String, String> = emptyMap()) {
    val label: String
        get() = if (argumen.isEmpty()) route
        else "$route(${argumen.entries.joinToString(", ") { "${it.key}=${it.value}" }})"
}

fun labelBackStack(stack: List<Entri>): String = stack.joinToString(" → ") { it.label }

/** `navigate()` = push. */
fun push(stack: List<Entri>, baru: Entri): List<Entri> = stack + baru

/** Tombol Back = pop. Stack kosong berarti aplikasi ditutup. */
fun pop(stack: List<Entri>): List<Entri> = stack.dropLast(1)

/**
 * `navigate(route) { popUpTo(Home) { saveState = true }; launchSingleTop = true; restoreState = true }`
 * seperti pada `navigateToTopLevel()` di CampusApp.kt.
 *
 * `popUpTo(Home)` tanpa `inclusive` mempertahankan Home; `launchSingleTop` tidak menambah
 * salinan bila destination tujuan sudah berada di puncak.
 */
fun navigasiTopLevel(stack: List<Entri>, tujuan: Entri): List<Entri> {
    val indeksHome = stack.indexOfLast { it.route == "Home" }
    val setelahPop = if (indeksHome < 0) stack else stack.take(indeksHome + 1)
    return if (setelahPop.lastOrNull()?.route == tujuan.route) setelahPop else setelahPop + tujuan
}

/** Menandai entri mana yang aktif pada Navigation Bar (hanya untuk top-level). */
fun menuAktif(stack: List<Entri>): String? = stack.lastOrNull()?.route?.takeIf { it != "CourseDetail" }

/** Navigation Bar disembunyikan saat destination aktif adalah Course Detail (showBottomBar). */
fun bottomBarTampil(stack: List<Entri>): Boolean = stack.lastOrNull()?.route != "CourseDetail"

// ---------------------------------------------------------------------------
// 2. Tabel 1 dan Tabel 3 — pola navigasi dan pembagian tanggung jawab MVVM
// ---------------------------------------------------------------------------

data class PolaNavigasi(val pola: String, val cocokUntuk: String, val jumlahItem: String)

val perbandinganPola: List<PolaNavigasi> = listOf(
    PolaNavigasi("Navigation Bar", "Bagian utama yang sering dibuka, terutama di ponsel", "3 sampai 5"),
    PolaNavigasi("Navigation Drawer", "Banyak bagian, atau bagian yang jarang dibuka", "5 atau lebih"),
    PolaNavigasi("Tabs", "Beberapa tampilan dari konten yang sama pada level yang sama", "2 sampai 5")
)

data class PeranMvvm(val bagian: String, val tanggungJawab: String, val contoh: String)

val pembagianMvvm: List<PeranMvvm> = listOf(
    PeranMvvm("Model", "Bentuk data", "Course(id, title, description)"),
    PeranMvvm("View", "Menampilkan UI dan menerima aksi pengguna", "CoursesScreen, CourseDetailScreen"),
    PeranMvvm("ViewModel", "Menyediakan state dan data", "CourseViewModel"),
    PeranMvvm("Navigation", "Mengatur destination dan transisi antarlayar", "CampusApp, Destinations")
)

// ---------------------------------------------------------------------------
// 3. Tabel 4 dan Tabel 5 — skenario pengujian dan isi back stack
// ---------------------------------------------------------------------------

data class SkenarioUji(val nomor: Int, val aksi: String, val harapan: String)

val skenarioUji: List<SkenarioUji> = listOf(
    SkenarioUji(1, "Buka aplikasi", "Home tampil, item Home terpilih"),
    SkenarioUji(2, "Tekan Courses, Profile, lalu Home", "Layar berganti dan item terpilih ikut berubah"),
    SkenarioUji(3, "Tekan Mobile App Development", "Detail menampilkan judul dan deskripsinya"),
    SkenarioUji(4, "Perhatikan bagian bawah layar detail", "Navigation Bar tidak tampil"),
    SkenarioUji(5, "Tekan tombol Back pada layar detail", "Kembali ke Courses, Navigation Bar tampil"),
    SkenarioUji(6, "Tekan Data Science", "Detail menampilkan Data Science"),
    SkenarioUji(7, "Tekan Back sistem pada layar detail", "Kembali ke Courses"),
    SkenarioUji(8, "Tekan item Courses beberapa kali, lalu Back sistem", "Langsung kembali ke Home"),
    SkenarioUji(9, "Di Home, tekan Back sistem", "Aplikasi keluar")
)

private val HomeEntri = Entri("Home")
private val CoursesEntri = Entri("Courses")
private val ProfileEntri = Entri("Profile")
private fun detail(id: Int) = Entri("CourseDetail", mapOf("courseId" to id.toString()))

// ---------------------------------------------------------------------------
// 4. Pemeriksaan
// ---------------------------------------------------------------------------

private fun verifikasiPerilakuModul16(): Boolean {
    jumlahCek = 0
    gagal = 0

    println("Verifikasi Modul 16 — Aplikasi Multi-Screen dan Pola Navigasi")
    println()

    // --- ViewModel: satu sumber data ---------------------------------------
    val vm = CourseViewModel()
    cek("ViewModel menyediakan dua mata kuliah dummy", vm.courses.value.size == 2)
    cek("ID bersifat unik", vm.courses.value.map { it.id }.toSet().size == vm.courses.value.size)
    cek("getCourseById(1) mengembalikan Mobile App Development",
        vm.getCourseById(1)?.title == "Mobile App Development")
    cek("getCourseById(2) mengembalikan Data Science",
        vm.getCourseById(2)?.title == "Data Science")
    cek("getCourseById(99) mengembalikan null sehingga layar menampilkan Course not found",
        vm.getCourseById(99) == null)
    cek("deskripsi ikut tersedia di mana pun data dibaca",
        vm.getCourseById(2)?.description == "Introduction to data analysis.")

    // --- Type-safe route: hanya ID yang dikirim ---------------------------
    val rute = CourseDetail(1)
    cek("CourseDetail membawa satu argumen courseId", rute.courseId == 1)

    // --- Skenario 1: aplikasi dibuka --------------------------------------
    var stack = listOf(HomeEntri)
    cek("Skenario 1: back stack = Home", labelBackStack(stack) == "Home")
    cek("Skenario 1: item Home terpilih", menuAktif(stack) == "Home")
    cek("Skenario 1: Navigation Bar tampil", bottomBarTampil(stack))

    // --- Skenario 2: berpindah menu utama ---------------------------------
    stack = navigasiTopLevel(stack, CoursesEntri)
    cek("Skenario 2: Home → Courses, item Courses terpilih",
        labelBackStack(stack) == "Home → Courses" && menuAktif(stack) == "Courses")
    stack = navigasiTopLevel(stack, ProfileEntri)
    cek("Skenario 2: Profile menggantikan Courses tanpa menumpuk",
        labelBackStack(stack) == "Home → Profile" && menuAktif(stack) == "Profile")
    stack = navigasiTopLevel(stack, HomeEntri)
    cek("Skenario 2: kembali ke Home, item Home terpilih",
        labelBackStack(stack) == "Home" && menuAktif(stack) == "Home")
    cek("Skenario 2: menekan menu aktif tidak menambah salinan",
        labelBackStack(navigasiTopLevel(stack, HomeEntri)) == "Home")

    // --- Skenario 3–5: membuka detail dan kembali -------------------------
    stack = navigasiTopLevel(stack, CoursesEntri)
    stack = push(stack, detail(1))
    cek("Skenario 3: stack = Home, Courses, CourseDetail(1)",
        labelBackStack(stack) == "Home → Courses → CourseDetail(courseId=1)")
    cek("Skenario 3: ID dari route dipakai mencari data di ViewModel",
        vm.getCourseById(1)?.title == "Mobile App Development")
    cek("Skenario 4: Navigation Bar disembunyikan di layar detail", !bottomBarTampil(stack))
    cek("Skenario 4: tidak ada item menu yang aktif di layar detail", menuAktif(stack) == null)
    stack = pop(stack)
    cek("Skenario 5: Back kembali ke Courses", labelBackStack(stack) == "Home → Courses")
    cek("Skenario 5: Navigation Bar tampil lagi", bottomBarTampil(stack))

    // --- Skenario 6–7: detail kedua, ID berbeda ---------------------------
    stack = push(stack, detail(2))
    cek("Skenario 6: stack = Home, Courses, CourseDetail(2)",
        labelBackStack(stack) == "Home → Courses → CourseDetail(courseId=2)")
    cek("Skenario 6: detail menampilkan Data Science, bukan mata kuliah sebelumnya",
        vm.getCourseById(2)?.title == "Data Science")
    stack = pop(stack)
    cek("Skenario 7: Back sistem kembali ke Courses", labelBackStack(stack) == "Home → Courses")

    // --- Skenario 8–9: popUpTo(Home) dan keluar ---------------------------
    stack = navigasiTopLevel(stack, CoursesEntri)
    stack = navigasiTopLevel(stack, CoursesEntri)
    stack = navigasiTopLevel(stack, CoursesEntri)
    cek("Skenario 8: menekan Courses berulang kali tidak menumpuk",
        labelBackStack(stack) == "Home → Courses")
    cek("Skenario 8: Back sistem langsung ke Home", labelBackStack(pop(stack)) == "Home")
    cek("Skenario 9: Back sistem di Home menutup aplikasi", pop(listOf(HomeEntri)).isEmpty())

    // --- Latihan 1: mata kuliah ketiga ------------------------------------
    // Salinan daftar dengan tambahan Latihan 1; urutan mengikuti daftar di ViewModel.
    val katalog: List<Course> = vm.courses.value +
        Course(3, "Software Engineering", "Designing and building reliable software.")
    cek("Latihan 1: Software Engineering menjadi item ketiga",
        katalog.getOrNull(2)?.title == "Software Engineering")
    cek("Latihan 1: daftar asli di ViewModel tetap dua mata kuliah", vm.courses.value.size == 2)
    stack = push(push(listOf(HomeEntri, CoursesEntri), detail(1)), detail(3))
    cek("Latihan 1: detail mata kuliah ketiga dapat dibuka",
        labelBackStack(stack).endsWith("CourseDetail(courseId=3)"))
    cek("Latihan 1: Back dari detail ketiga kembali ke Courses",
        labelBackStack(pop(stack)) == "Home → Courses → CourseDetail(courseId=1)")

    // --- Latihan 2: ID tidak dikenal --------------------------------------
    val tidakDikenal = CourseDetail(99)
    cek("Latihan 2: ID 99 tidak ditemukan sehingga tampil Course not found",
        vm.getCourseById(tidakDikenal.courseId) == null)

    // --- Latihan 3: tanpa popUpTo, back stack menumpuk --------------------
    var tanpaPopUpTo = listOf(HomeEntri)
    tanpaPopUpTo = tanpaPopUpTo + CoursesEntri
    tanpaPopUpTo = tanpaPopUpTo + ProfileEntri
    tanpaPopUpTo = tanpaPopUpTo + CoursesEntri
    tanpaPopUpTo = tanpaPopUpTo + ProfileEntri
    cek("Latihan 3: tanpa popUpTo, stack menumpuk empat entri di atas Home",
        tanpaPopUpTo.size == 5)
    cek("Latihan 3: Back pertama tetap di Profile, bukan Home",
        pop(tanpaPopUpTo).last().route == "Courses")

    // --- Tabel modul -------------------------------------------------------
    cek("tiga pola navigasi utama dibandingkan", perbandinganPola.size == 3)
    cek("Navigation Bar cocok untuk 3 sampai 5 item",
        perbandinganPola.first { it.pola == "Navigation Bar" }.jumlahItem == "3 sampai 5")
    cek("empat baris pembagian tanggung jawab MVVM", pembagianMvvm.size == 4)
    cek("Navigation Bar adalah pilihan untuk tiga menu utama",
        perbandinganPola.first { it.pola == "Navigation Bar" }.cocokUntuk.contains("sering dibuka"))
    cek("sembilan skenario pengujian", skenarioUji.size == 9)
    cek("skenario 4 menguji Navigation Bar tersembunyi",
        skenarioUji.first { it.nomor == 4 }.harapan.contains("tidak tampil"))
    cek("skenario 9 menguji aplikasi keluar",
        skenarioUji.first { it.nomor == 9 }.harapan.contains("keluar"))

    println()
    println("Kasus uji: ${jumlahCek - gagal}/$jumlahCek lulus")
    return gagal == 0
}

fun main() {
    val lulus = verifikasiPerilakuModul16()
    if (lulus) {
        println("OK — aturan navigasi, back stack, MVVM, dan aliran data lulus")
    } else {
        println("GAGAL — ada pemeriksaan yang tidak lulus")
        kotlin.system.exitProcess(1)
    }
}
