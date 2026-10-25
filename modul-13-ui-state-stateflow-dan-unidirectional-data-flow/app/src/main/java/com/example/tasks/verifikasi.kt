package com.example.tasks

/**
 * Aturan, keputusan desain, dan tabel modul 13
 * (UI State, StateFlow, and Unidirectional Data Flow) — beserta pemeriksaannya.
 *
 * Modul ini menguji tiga hal yang tidak terlihat dari tampilan:
 *   1. apakah state layar dimodelkan sebagai satu snapshot yang tidak dapat melenceng;
 *   2. apakah setiap aksi punya jalur UDF yang jelas (layar → callback → ViewModel → state baru);
 *   3. apakah UI menerima aliran read-only, bukan penampung yang dapat ditulis.
 *
 * Berkas ini memakai Kotlin stdlib saja (tanpa Compose dan tanpa Android), sehingga dapat
 * dikompilasi dari terminal bersama berkas model:
 *
 *   kotlinc verifikasi.kt Task.kt TasksUiState.kt -include-runtime -d verifikasi.jar
 *
 * atau dari root repositori: `./jalankan.sh 13 perilaku`.
 */

// ---------------------------------------------------------------------------
// 0. Penghitung pemeriksaan (jangan menulis total manual)
// ---------------------------------------------------------------------------

var jumlahCekDijalankan: Int = 0
private var gagal: Int = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCekDijalankan++
    if (kondisi) {
        println("OK    $deskripsi")
    } else {
        gagal++
        println("GAGAL $deskripsi")
    }
}

// ---------------------------------------------------------------------------
// 1. Aturan murni yang dipakai aplikasi
// ---------------------------------------------------------------------------

/**
 * Validasi milik pemilik state: judul yang dapat disimpan adalah judul yang tidak kosong setelah
 * di-trim, ATAU `null` bila tidak ada yang bisa disimpan.
 */
fun judulTersimpan(judulMentah: String): String? = judulMentah.trim().ifEmpty { null }

/** Aksi "Add task" dengan judul kosong menghasilkan pesan, bukan tugas baru. */
fun perluPesanValidasi(draf: String): Boolean = judulTersimpan(draf) == null

/**
 * Transformasi state untuk "Add task" — fungsi murni supaya aturannya dapat diuji tanpa ViewModel.
 *
 * Perhatikan: ID diberikan sebagai PARAMETER (dibuat di luar `update`), dan judul kosong tidak
 * mengubah daftar tugas sama sekali.
 */
fun stateSetelahTambah(state: TasksUiState, idBaru: String): TasksUiState {
    val judul = judulTersimpan(state.draftTitle)
    return if (judul == null) {
        state.copy(inputError = PESAN_JUDUL_KOSONG)
    } else {
        state.copy(
            tasks = state.tasks + Task(idBaru, judul),
            draftTitle = "",
            inputError = null
        )
    }
}

/** Transformasi state untuk checkbox: mengganti tugas lewat `copy()`, identitas tetap. */
fun stateSetelahStatus(state: TasksUiState, id: String, selesai: Boolean): TasksUiState =
    state.copy(
        tasks = state.tasks.map { tugas ->
            if (tugas.id == id) tugas.copy(isCompleted = selesai) else tugas
        }
    )

/** Transformasi state untuk "Delete": `filterNot` berdasarkan ID, bukan posisi daftar terlihat. */
fun stateSetelahHapus(state: TasksUiState, id: String): TasksUiState =
    state.copy(tasks = state.tasks.filterNot { it.id == id })

/** Latihan tambahan "Clear completed": jalur UDF dan aturan filter yang sama, tanpa ID. */
fun stateSetelahHapusSelesai(state: TasksUiState): TasksUiState =
    state.copy(tasks = state.tasks.filterNot { it.isCompleted })

/** Jumlah tugas yang belum selesai — dihitung dari daftar lengkap, bukan dari daftar tersaring. */
fun sisaBelumSelesai(state: TasksUiState): Int = state.tasks.count { !it.isCompleted }

// ---------------------------------------------------------------------------
// 2. "State and Events Are Different" — klasifikasi tiap butir
// ---------------------------------------------------------------------------

/** Kategori sebuah butir: state adalah nilai sekarang, event adalah sesuatu yang terjadi. */
enum class Kategori { STATE, EVENT }

data class ButirStateEvent(val contoh: String, val kategori: Kategori, val alasan: String)

val butirStateEvent: List<ButirStateEvent> = listOf(
    ButirStateEvent("Input berisi \"Read chapter 2\"", Kategori.STATE, "Nilai sekarang dari draf"),
    ButirStateEvent("Pengguna mengubah input", Kategori.EVENT, "Sesuatu baru saja terjadi"),
    ButirStateEvent("Sebuah tugas ditandai selesai", Kategori.STATE, "Status tugas saat ini"),
    ButirStateEvent("Pengguna menekan checkbox", Kategori.EVENT, "Permintaan untuk mengubah status"),
    ButirStateEvent("Pesan validasi terlihat", Kategori.STATE, "Informasi UI saat ini"),
    ButirStateEvent("Pengguna menekan Add", Kategori.EVENT, "Permintaan untuk menambah tugas")
)

// ---------------------------------------------------------------------------
// 3. "Decide Who Owns Each Value"
// ---------------------------------------------------------------------------

enum class Pemilik { COMPOSABLE, VIEWMODEL, DATA_LAYER }

data class KeputusanPemilik(val nilai: String, val pemilik: Pemilik, val alasan: String)

val keputusanPemilik: List<KeputusanPemilik> = listOf(
    KeputusanPemilik(
        "Dropdown yang terbuka (detail UI kecil)",
        Pemilik.COMPOSABLE,
        "Hanya dipakai di satu tempat dan tidak dibutuhkan logika layar"
    ),
    KeputusanPemilik(
        "Daftar tugas, draf judul, filter aktif",
        Pemilik.VIEWMODEL,
        "Dipakai logika layar dan harus bertahan saat konfigurasi dibuat ulang"
    ),
    KeputusanPemilik(
        "Tugas yang tersimpan di database",
        Pemilik.DATA_LAYER,
        "Data aplikasi yang harus tetap ada setelah proses dimatikan"
    )
)

// ---------------------------------------------------------------------------
// 4. "Flow, StateFlow, and Compose State" — peran tiap API
// ---------------------------------------------------------------------------

data class PeranApi(val api: String, val peran: String)

val peranApiState: List<PeranApi> = listOf(
    PeranApi("Flow<T>", "Abstraksi aliran asinkron umum; dapat cold atau hot"),
    PeranApi("MutableStateFlow<T>", "State holder yang boleh diubah ViewModel"),
    PeranApi("StateFlow<T>", "Akses read-only ke nilai sekarang dan pembaruannya"),
    PeranApi("Compose State<T>", "Nilai teramati yang dibaca Compose untuk memperbarui UI"),
    PeranApi("collectAsStateWithLifecycle()", "Jembatan StateFlow ke Compose State, sadar lifecycle")
)

// ---------------------------------------------------------------------------
// 5. "UDF and MVVM Work Together" — pertanyaan yang dijawab masing-masing
// ---------------------------------------------------------------------------

data class KonsepPertanyaan(val konsep: String, val pertanyaan: String)

val konsepPertanyaan: List<KonsepPertanyaan> = listOf(
    KonsepPertanyaan("MVVM", "Komponen mana yang punya tanggung jawab apa?"),
    KonsepPertanyaan("UDF", "Ke arah mana state dan aksi bergerak?"),
    KonsepPertanyaan("StateFlow", "Bagaimana state holder menerbitkan state terkini dan perubahannya?")
)

// ---------------------------------------------------------------------------
// 6. "Trace One Complete Interaction" — enam langkah satu klik checkbox
// ---------------------------------------------------------------------------

data class LangkahTrace(val nomor: Int, val kejadian: String, val arah: String)

val langkahTrace: List<LangkahTrace> = listOf(
    LangkahTrace(1, "Pengguna mencentang checkbox tugas", "aksi naik"),
    LangkahTrace(2, "UI memanggil onTaskCompletionChanged(task.id, true)", "aksi naik"),
    LangkahTrace(3, "ViewModel membuat tugas dan daftar pengganti", "pemilik state"),
    LangkahTrace(4, "_uiState menerbitkan snapshot baru", "pemilik state"),
    LangkahTrace(5, "Koleksi sadar-lifecycle memperbarui Compose State", "state turun"),
    LangkahTrace(6, "UI menampilkan kotak tercentang dan ringkasan 1 selesai", "state turun")
)

// ---------------------------------------------------------------------------
// 7. "Recomposition, Rotation, and Process Death" — Tabel perilaku
// ---------------------------------------------------------------------------

data class PerilakuKehidupan(val situasi: String, val perilaku: String)

val perilakuKehidupanState: List<PerilakuKehidupan> = listOf(
    PerilakuKehidupan("Recomposition", "ViewModel yang sama tetap ada; state tidak diinisialisasi ulang"),
    PerilakuKehidupan("Activity dibuat ulang karena rotasi", "ViewModel bertahan dalam scope owner-nya"),
    PerilakuKehidupan("Owner dihapus permanen", "ViewModel dibersihkan; state di memori hilang"),
    PerilakuKehidupan("Process death", "ViewModel dan StateFlow di memori hilang")
)

// ---------------------------------------------------------------------------
// 8. "StateFlow Is Not an Event Queue"
// ---------------------------------------------------------------------------

data class LangkahAntrean(val nilai: String, val diterimaCollectorLambat: Boolean)

/** A → B → C dengan collector lambat: B dilewati karena nilai terbaru menang. */
val langkahAntreanState: List<LangkahAntrean> = listOf(
    LangkahAntrean("A", true),
    LangkahAntrean("B", false),
    LangkahAntrean("C", true)
)

// ---------------------------------------------------------------------------
// 9. "Common Mistakes and Corrections"
// ---------------------------------------------------------------------------

data class KesalahanPerbaikan(val kesalahan: String, val perbaikan: String)

val kesalahanUmumState: List<KesalahanPerbaikan> = listOf(
    KesalahanPerbaikan("Menampilkan MutableStateFlow secara publik", "Jadikan private; terbitkan StateFlow"),
    KesalahanPerbaikan("Hanya membaca uiState.value di Composable", "Kumpulkan lewat collectAsStateWithLifecycle()"),
    KesalahanPerbaikan("Mengubah list yang sudah ada di dalam state", "Buat list baru dan snapshot state baru"),
    KesalahanPerbaikan("Memanggil onAddTask() langsung di badan Composable", "Panggil dari callback tombol"),
    KesalahanPerbaikan("Menyimpan draf kedua di dalam layar", "Gambar draf yang diberikan pemilik state"),
    KesalahanPerbaikan("Menghapus berdasarkan indeks daftar tersaring", "Identifikasi tugas lewat ID"),
    KesalahanPerbaikan("Menganggap StateFlow bertahan saat process death", "Tambahkan SavedStateHandle atau penyimpanan permanen")
)

// ---------------------------------------------------------------------------
// 10. "Set Up the Practice Project" — berkas dan dependensi
// ---------------------------------------------------------------------------

val berkasProyekLatihan: List<Pair<String, String>> = listOf(
    "Task.kt" to "model tugas",
    "TasksUiState.kt" to "state layar + nilai turunan",
    "TasksViewModel.kt" to "pemilik state dan aksi",
    "TasksScreen.kt" to "route, layar, preview",
    "MainActivity.kt" to "memanggil TasksRoute()"
)

val dependensiLatihan: List<String> = listOf(
    "androidx.lifecycle:lifecycle-viewmodel-compose",
    "androidx.lifecycle:lifecycle-runtime-compose",
    "org.jetbrains.kotlinx:kotlinx-coroutines-android",
    "Compose + Material 3 + activity-compose dari template"
)

// ---------------------------------------------------------------------------
// 11. "Guided Practice" dan "Independent Practice"
// ---------------------------------------------------------------------------

/** Tujuh langkah praktik terbimbing; tiap baris menanyakan event, method, field, dan hasil UI. */
val langkahPraktikTerbimbing: List<String> = listOf(
    "Jalankan layar awal dan periksa state default",
    "Ketik judul dan telusuri callback ke onDraftTitleChanged()",
    "Tambah dua tugas dan pastikan input dikosongkan",
    "Kirim input kosong dan periksa inputError",
    "Selesaikan satu tugas dan periksa ringkasan",
    "Aktifkan filter incomplete-only dan jelaskan mengapa tugas selesai menghilang",
    "Rotasi perangkat dan periksa state yang bertahan"
)

/** Empat syarat Independent Practice "Delete a Task". */
val syaratHapusTugas: List<String> = listOf(
    "Layar mengirim ID lewat onDeleteTask: (String) -> Unit",
    "Route menghubungkan callback ke method ViewModel",
    "ViewModel membuat daftar baru dengan filterNot",
    "Daftar dan ringkasan completed/total ikut berubah konsisten",
    "Penghapusan bekerja saat filter incomplete-only aktif"
)

// ---------------------------------------------------------------------------
// 12. "Check Your Understanding" — tujuh pertanyaan dan jawabannya
// ---------------------------------------------------------------------------

data class TanyaJawab(val pertanyaan: String, val jawaban: String)

val tanyaJawabState: List<TanyaJawab> = listOf(
    TanyaJawab(
        "Mengapa UI menerima StateFlow, bukan MutableStateFlow?",
        "Hanya pemilik state yang boleh menulis; UI meminta perubahan lewat method."
    ),
    TanyaJawab(
        "Mengapa completedCount diturunkan dari daftar?",
        "Nilai turunan tidak dapat melenceng dari daftar sumbernya."
    ),
    TanyaJawab(
        "Bagaimana StateFlow menjadi teramati oleh Compose?",
        "Dikumpulkan menjadi Compose State oleh collectAsStateWithLifecycle()."
    ),
    TanyaJawab(
        "Mengapa tidak boleh mengubah property tugas di tempat?",
        "Mutasi di tempat merusak penalaran snapshot dan mungkin tidak menghasilkan nilai baru yang teramati."
    ),
    TanyaJawab(
        "Apa yang terjadi bila tugas selesai saat filter aktif?",
        "Tugas tetap tersimpan, hilang dari daftar tersaring, dan ringkasan tetap menghitung semua tugas."
    ),
    TanyaJawab(
        "Apakah UDF memerlukan satu sealed event class?",
        "Tidak. Method bernama dan callback sudah cukup."
    ),
    TanyaJawab(
        "State mana yang bertahan saat rotasi, mana yang bertahan saat process death?",
        "State ViewModel bertahan saat rotasi; tidak ada data tugas yang bertahan saat process death."
    )
)

// ---------------------------------------------------------------------------
// 13. Pemeriksaan
// ---------------------------------------------------------------------------

private fun verifikasiPerilakuModul13(): Boolean {
    jumlahCekDijalankan = 0
    gagal = 0

    println("Verifikasi Modul 13 — UI State, StateFlow, and Unidirectional Data Flow")
    println()

    // --- Aturan murni -------------------------------------------------------
    cek("judul kosong ditolak", judulTersimpan("   ") == null)
    cek("judul di-trim sebelum disimpan", judulTersimpan("  Read chapter 2 ") == "Read chapter 2")
    cek("pesan validasi muncul untuk draf kosong", perluPesanValidasi(""))
    cek("tidak ada pesan validasi untuk draf berisi", !perluPesanValidasi("Read"))

    // --- Derived state ------------------------------------------------------
    val tigaTugas = TasksUiState(
        tasks = listOf(
            Task("1", "Review StateFlow", isCompleted = true),
            Task("2", "Implement UDF"),
            Task("3", "Skim the slides")
        )
    )
    cek("completedCount dihitung dari daftar", tigaTugas.completedCount == 1)
    cek("summaryText memakai jumlah total", tigaTugas.summaryText == "1 of 3 tasks completed")
    cek("visibleTasks = semua tugas saat filter mati", tigaTugas.visibleTasks.size == 3)

    val tersaring = tigaTugas.copy(showIncompleteOnly = true)
    cek("visibleTasks menyaring yang selesai", tersaring.visibleTasks.map { it.id } == listOf("2", "3"))
    cek("ringkasan tetap menghitung daftar lengkap", tersaring.summaryText == "1 of 3 tasks completed")
    cek("layar kosong saat semua tugas selesai dan filter aktif",
        tersaring.copy(tasks = listOf(Task("1", "a", isCompleted = true))).showsEmptyMessage)
    cek("layar tidak kosong saat filter mati walau semua selesai",
        !tersaring.copy(showIncompleteOnly = false, tasks = listOf(Task("1", "a", isCompleted = true))).showsEmptyMessage)

    // --- Immutable update dengan copy() -------------------------------------
    val sebelum = TasksUiState(draftTitle = "Read")
    val sesudah = sebelum.copy(draftTitle = "Read chapter 2")
    cek("copy() tidak mengubah snapshot lama", sebelum.draftTitle == "Read")
    cek("copy() menghasilkan snapshot baru", sesudah.draftTitle == "Read chapter 2")
    cek("property yang tidak disebut copy() tetap", sesudah.showIncompleteOnly == sebelum.showIncompleteOnly)

    // --- Add task -----------------------------------------------------------
    val setelahTambah = stateSetelahTambah(TasksUiState(draftTitle = "  Read chapter 2  "), "id-1")
    cek("tambah tugas menambahkan satu baris", setelahTambah.tasks.size == 1)
    cek("judul tersimpan sudah di-trim", setelahTambah.tasks.single().title == "Read chapter 2")
    cek("tambah tugas mengosongkan draf", setelahTambah.draftTitle == "")
    cek("tambah tugas membersihkan pesan", setelahTambah.inputError == null)

    val setelahKosong = stateSetelahTambah(TasksUiState(draftTitle = "   "), "id-2")
    cek("tambah judul kosong tidak membuat tugas", setelahKosong.tasks.isEmpty())
    cek("tambah judul kosong menampilkan pesan", setelahKosong.inputError == PESAN_JUDUL_KOSONG)
    cek("draf kosong yang gagal tetap seperti semula", setelahKosong.draftTitle == "   ")

    // ID dibuat sekali di luar update: dua panggilan dengan ID berbeda = dua tugas berbeda
    val duaTugas = stateSetelahTambah(stateSetelahTambah(TasksUiState(draftTitle = "Same"), "id-a").copy(draftTitle = "Same"), "id-b")
    cek("judul sama tetap mendapat dua ID berbeda", duaTugas.tasks.map { it.id }.toSet() == setOf("id-a", "id-b"))

    // --- Toggle -------------------------------------------------------------
    val setelahCentang = stateSetelahStatus(tigaTugas, "2", true)
    cek("checkbox mengubah status tugas yang dituju", setelahCentang.tasks.first { it.id == "2" }.isCompleted)
    cek("checkbox tidak mengubah ID tugas", setelahCentang.tasks.map { it.id } == listOf("1", "2", "3"))
    cek("checkbox tidak mengubah tugas lain", setelahCentang.tasks.first { it.id == "3" } == tigaTugas.tasks.first { it.id == "3" })
    cek("aksi identik berulang menghasilkan nilai yang sama",
        stateSetelahStatus(setelahCentang, "2", true) == setelahCentang)
    cek("tugas selesai hilang dari daftar tersaring",
        stateSetelahStatus(tersaring, "3", true).visibleTasks.none { it.id == "3" })
    cek("tugas selesai tetap ada di daftar lengkap",
        stateSetelahStatus(tersaring, "3", true).tasks.any { it.id == "3" })

    // --- Delete (Independent Practice) --------------------------------------
    val setelahHapus = stateSetelahHapus(tigaTugas, "2")
    cek("hapus menyisakan dua tugas", setelahHapus.tasks.size == 2)
    cek("hapus tidak mengubah ID tugas lain", setelahHapus.tasks.map { it.id } == listOf("1", "3"))
    cek("hapus mempertahankan urutan yang tersisa", setelahHapus.tasks.map { it.title } == listOf("Review StateFlow", "Skim the slides"))
    cek("hapus memperbarui ringkasan", setelahHapus.summaryText == "1 of 2 tasks completed")

    val tersaringLaluHapus = stateSetelahHapus(tersaring, "2")
    cek("hapus bekerja saat filter aktif (ID, bukan indeks)", tersaringLaluHapus.tasks.none { it.id == "2" })
    cek("hapus saat filter aktif tidak menghapus tugas tersembunyi",
        tersaringLaluHapus.tasks.any { it.id == "1" } && tersaringLaluHapus.tasks.size == 2)
    cek("hapus ID yang tidak ada tidak mengubah apa pun", stateSetelahHapus(tigaTugas, "tidak-ada") == tigaTugas)

    // --- Clear completed (latihan tambahan) --------------------------------
    val setelahBersih = stateSetelahHapusSelesai(tigaTugas)
    cek("clear completed menyisakan yang belum selesai", setelahBersih.tasks.map { it.id } == listOf("2", "3"))
    cek("clear completed memperbarui ringkasan", setelahBersih.summaryText == "0 of 2 tasks completed")
    cek("clear completed saat semua selesai menghasilkan daftar kosong",
        stateSetelahHapusSelesai(tigaTugas.copy(tasks = listOf(Task("1", "a", isCompleted = true)))).tasks.isEmpty())
    cek("clear completed pada daftar kosong tetap kosong", stateSetelahHapusSelesai(TasksUiState()).tasks.isEmpty())
    cek("sisa yang belum selesai dihitung dari daftar lengkap", sisaBelumSelesai(tigaTugas) == 2)
    cek("sisa tidak terpengaruh filter", sisaBelumSelesai(tersaring) == sisaBelumSelesai(tigaTugas))

    // --- Tabel dan keputusan desain ----------------------------------------
    cek("Tabel state/event memuat 6 butir", butirStateEvent.size == 6)
    cek("state dan event sama banyak", butirStateEvent.count { it.kategori == Kategori.STATE } == 3 &&
        butirStateEvent.count { it.kategori == Kategori.EVENT } == 3)
    cek("setiap butir punya alasan", butirStateEvent.all { it.alasan.isNotBlank() })
    cek("tiga pemilik nilai disebutkan", keputusanPemilik.map { it.pemilik }.toSet().size == 3)
    cek("draf judul dimiliki ViewModel",
        keputusanPemilik.any { it.nilai.contains("draf") && it.pemilik == Pemilik.VIEWMODEL })
    cek("data permanen dimiliki data layer",
        keputusanPemilik.any { it.nilai.contains("database") && it.pemilik == Pemilik.DATA_LAYER })
    cek("Compose State dijelaskan di tabel API", peranApiState.any { it.api == "Compose State<T>" })
    cek("tiga konsep MVVM/UDF/StateFlow dijelaskan", konsepPertanyaan.size == 3)
    cek("trace interaksi punya 6 langkah", langkahTrace.size == 6)
    cek("trace diawali aksi naik dan diakhiri state turun",
        langkahTrace.first().arah == "aksi naik" && langkahTrace.last().arah == "state turun")
    cek("empat situasi lifecycle dijelaskan", perilakuKehidupanState.size == 4)
    cek("setiap situasi punya perilaku", perilakuKehidupanState.all { it.perilaku.isNotBlank() })
    cek("rotation mempertahankan ViewModel",
        perilakuKehidupanState.first { it.situasi.lowercase().contains("rotasi") }.perilaku.contains("bertahan"))
    cek("process death menghilangkan state di memori",
        perilakuKehidupanState.first { it.situasi.lowercase().contains("process death") }.perilaku.contains("hilang"))
    cek("collector lambat melewatkan nilai tengah",
        langkahAntreanState.filter { it.diterimaCollectorLambat }.map { it.nilai } == listOf("A", "C"))
    cek("tujuh kesalahan umum punya perbaikan", kesalahanUmumState.size == 7)
    cek("larangan menulis MutableStateFlow publik tercatat",
        kesalahanUmumState.any { it.perbaikan.contains("StateFlow") })
    cek("lima berkas proyek latihan disebutkan", berkasProyekLatihan.size == 5)
    cek("ketiga dependensi wajib disebutkan",
        dependensiLatihan.contains("androidx.lifecycle:lifecycle-viewmodel-compose") &&
            dependensiLatihan.contains("androidx.lifecycle:lifecycle-runtime-compose") &&
            dependensiLatihan.contains("org.jetbrains.kotlinx:kotlinx-coroutines-android"))
    cek("praktik terbimbing punya 7 langkah", langkahPraktikTerbimbing.size == 7)
    cek("syarat hapus tugas lengkap (5 butir)", syaratHapusTugas.size == 5)
    cek("tujuh pertanyaan pemahaman terjawab", tanyaJawabState.size == 7)
    cek("semua jawaban pemahaman terisi", tanyaJawabState.all { it.jawaban.isNotBlank() })

    println()
    println("Kasus uji: ${jumlahCekDijalankan - gagal}/$jumlahCekDijalankan lulus")
    return gagal == 0
}

fun main() {
    val lulus = verifikasiPerilakuModul13()
    if (lulus) {
        println("OK — aturan UI state, StateFlow, dan UDF lulus")
    } else {
        println("GAGAL — ada pemeriksaan yang tidak lulus")
        kotlin.system.exitProcess(1)
    }
}
