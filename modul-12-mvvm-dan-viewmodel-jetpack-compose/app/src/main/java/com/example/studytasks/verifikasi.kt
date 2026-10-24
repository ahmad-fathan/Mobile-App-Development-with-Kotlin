package com.example.studytasks

/**
 * Aturan dan tabel modul 12 (MVVM and ViewModel in Jetpack Compose) — beserta pemeriksaannya.
 *
 * Modul ini menilai sebuah layar dari PEMILIK setiap tanggung jawab dan dari UMUR setiap nilai
 * state, bukan dari tampilannya. Sebagian besar isi modul adalah tabel: Tabel 2 (urusan dan
 * komponen), 3 (tiga bagian MVVM), 4 (tiga lifecycle), 5 (dampak peristiwa), 6 (pemilik state),
 * 7 (ketahanan nilai), 8 (pemeriksaan aplikasi), dan 9 (kesalahan umum). Semua tabel itu
 * dimodelkan sebagai data di berkas ini, lalu DIPAKAI oleh berkas bab untuk digambar — bukan
 * disalin — sehingga tabel di layar dan tabel yang diperiksa tidak dapat berbeda diam-diam.
 *
 * Selain tabel, berkas ini memuat dua aturan murni yang benar-benar dipakai aplikasi:
 * [judulTersimpan] (dipakai repository) dan [perluPesanValidasi] (dipakai ViewModel).
 *
 * Berkas ini hanya memakai Kotlin stdlib — tanpa Compose dan tanpa Android — sehingga dapat
 * dikompilasi dari terminal together dengan berkas data layer:
 *
 *   kotlinc app/src/main/java/com/example/studytasks/verifikasi.kt \
 *       app/src/main/java/com/example/studytasks/StudyTask.kt \
 *       app/src/main/java/com/example/studytasks/TaskRepository.kt \
 *       app/src/main/java/com/example/studytasks/InMemoryTaskRepository.kt \
 *       app/src/main/java/com/example/studytasks/TasksUiState.kt \
 *       -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * atau dari root repositori: `./jalankan.sh 12 perilaku`.
 */

// ---------------------------------------------------------------------------
// 0. Aturan murni yang dipakai aplikasi
// ---------------------------------------------------------------------------

/** Bagian 7.7 — pesan yang dilihat pengguna saat judul kosong. Satu sumber untuk UI dan uji. */
const val PESAN_JUDUL_KOSONG = "Enter a task title."

/**
 * Bagian 7.3 — aturan data layer: judul yang tersimpan tidak boleh kosong.
 * Mengembalikan judul yang sudah di-trim, atau `null` bila tidak ada yang bisa disimpan.
 */
fun judulTersimpan(judulMentah: String): String? = judulMentah.trim().ifEmpty { null }

/**
 * Bagian 7.7 — kapan ViewModel harus menampilkan pesan validasi.
 *
 * Pemeriksaan ini terjadi SEBELUM `require` di repository, sehingga pengguna melihat pesan,
 * bukan aplikasi yang crash.
 */
fun perluPesanValidasi(draf: String): Boolean = judulTersimpan(draf) == null

// ---------------------------------------------------------------------------
// 1. Tabel 2 — urusan dan komponen yang bertanggung jawab (Bagian 3.1)
// ---------------------------------------------------------------------------

data class UrusanLayer(val urusan: String, val komponen: String)

val urusanLayerTasks: List<UrusanLayer> = listOf(
    UrusanLayer("Menampilkan kontrol dan menyusun konten", "Composable screen"),
    UrusanLayer("Menghasilkan state layar dan menangani aksi pada layar", "ViewModel"),
    UrusanLayer("Memiliki data tugas dan operasi terhadapnya", "Repository"),
    UrusanLayer("Berkomunikasi dengan database atau layanan tertentu", "Data source, bila diperlukan")
)

// ---------------------------------------------------------------------------
// 2. Tabel 3 — tiga bagian MVVM (Bagian 4.1)
// ---------------------------------------------------------------------------

data class PeranMvvm(val bagian: String, val makna: String)

val peranMvvmTasks: List<PeranMvvm> = listOf(
    PeranMvvm("Model", "Data tugas dan operasi yang disediakan oleh repository"),
    PeranMvvm("View", "Fungsi Composable yang menampilkan layar dan melaporkan aksi"),
    PeranMvvm("ViewModel", "State holder layar yang mengoordinasikan aksi dan akses data")
)

// ---------------------------------------------------------------------------
// 3. Bagian 4.2 — lima langkah satu aksi Add task
// ---------------------------------------------------------------------------

data class LangkahAksi(val urutan: Int, val pelaku: String, val kegiatan: String)

val langkahAksiAddTask: List<LangkahAksi> = listOf(
    LangkahAksi(1, "View", "Melaporkan klik tombol kepada ViewModel"),
    LangkahAksi(2, "ViewModel", "Memeriksa draf saat ini"),
    LangkahAksi(3, "Repository", "Menambahkan tugas"),
    LangkahAksi(4, "ViewModel", "Menerbitkan state layar yang baru"),
    LangkahAksi(5, "View", "Menampilkan daftar yang baru")
)

/** Bagian 4.3 — siapa yang memiliki apa. Dua pemilik, dua urusan yang berbeda. */
val kepemilikanStateTasks: List<Pair<String, String>> = listOf(
    "Repository" to "memiliki data tugas",
    "ViewModel" to "memiliki draf, pesan validasi, dan representasi data tugas untuk layar"
)

// ---------------------------------------------------------------------------
// 4. Tabel 4 — tiga lifecycle (Bagian 5.2)
// ---------------------------------------------------------------------------

data class LifecycleTiga(val nama: String, val penjelasan: String)

val tigaLifecycleTasks: List<LifecycleTiga> = listOf(
    LifecycleTiga(
        "Composition",
        "Sebuah Composable masuk ke Composition, dapat mengalami recomposition berkali-kali, " +
            "lalu akhirnya keluar"
    ),
    LifecycleTiga(
        "Activity",
        "Activity induk dibuat, dimulai, dilanjutkan, dihentikan, dan akhirnya dihancurkan"
    ),
    LifecycleTiga(
        "ViewModel",
        "Sebuah instance dipertahankan oleh ViewModelStoreOwner sampai scope pemilik tersebut berakhir"
    )
)

// ---------------------------------------------------------------------------
// 5. Tabel 5 — dampak peristiwa terhadap ViewModel (Bagian 5.3)
// ---------------------------------------------------------------------------

/** Nasib instance ViewModel setelah sebuah peristiwa. */
enum class NasibViewModel { TETAP, HILANG, DIBERSIHKAN }

data class PeristiwaViewModel(
    val peristiwa: String,
    val dampak: String,
    val nasib: NasibViewModel
)

val peristiwaViewModelTasks: List<PeristiwaViewModel> = listOf(
    PeristiwaViewModel(
        "Nilai yang diamati berubah",
        "ViewModel yang sama menerbitkan state baru",
        NasibViewModel.TETAP
    ),
    PeristiwaViewModel(
        "Activity dibuat ulang karena perubahan konfigurasi",
        "ViewModel dalam scope-nya tetap dipertahankan",
        NasibViewModel.TETAP
    ),
    PeristiwaViewModel(
        "Sistem mematikan proses aplikasi",
        "Instance ViewModel hilang",
        NasibViewModel.HILANG
    ),
    PeristiwaViewModel(
        "Owner-nya dihapus secara permanen (misalnya Activity selesai)",
        "ViewModel dibersihkan (cleared)",
        NasibViewModel.DIBERSIHKAN
    )
)

/** Bagian 5.3 — hanya perubahan konfigurasi yang MEMBUAT ULANG Activity tetapi TIDAK membuang ViewModel. */
fun viewModelBertahanSaat(peristiwa: String): Boolean =
    peristiwaViewModelTasks
        .firstOrNull { it.peristiwa == peristiwa }
        ?.nasib == NasibViewModel.TETAP

// ---------------------------------------------------------------------------
// 6. Tabel 6 — pemilik yang tepat untuk setiap state (Bagian 6.1)
// ---------------------------------------------------------------------------

/** Lapisan tempat sebuah nilai state disimpan. */
enum class PemilikState { UI_LOKAL, STATE_HOLDER_UI, VIEWMODEL, REPOSITORY, PENYIMPANAN_PERMANEN }

data class ContohState(
    val contoh: String,
    val pemilik: PemilikState,
    val keterangan: String
)

val contohStateTasks: List<ContohState> = listOf(
    ContohState(
        "Status terbuka-tutup sementara pada sebuah card",
        PemilikState.UI_LOKAL,
        "State UI lokal, sering kali rememberSaveable"
    ),
    ContohState(
        "Posisi scroll sebuah daftar",
        PemilikState.STATE_HOLDER_UI,
        "State holder UI seperti rememberLazyListState"
    ),
    ContohState(
        "Draf yang dipakai untuk validasi layar",
        PemilikState.VIEWMODEL,
        "ViewModel, dalam contoh ini"
    ),
    ContohState(
        "Data tugas yang dipakai di beberapa layar",
        PemilikState.REPOSITORY,
        "Repository"
    ),
    ContohState(
        "Tugas selesai yang tetap dibutuhkan setelah aplikasi dibuka ulang",
        PemilikState.PENYIMPANAN_PERMANEN,
        "Penyimpanan permanen yang diakses melalui repository"
    )
)

/** Pedoman state hoisting tetap berlaku: pemilik terendah yang perlu membaca atau mengubahnya. */
fun pemilikUntuk(contoh: String): PemilikState? =
    contohStateTasks.firstOrNull { it.contoh == contoh }?.pemilik

// ---------------------------------------------------------------------------
// 7. Tabel 7 — ketahanan nilai pada setiap mekanisme (Bagian 6.2)
// ---------------------------------------------------------------------------

/** Tiga peristiwa yang harus dibedakan; mencampuradukkannya adalah sumber kebingungan utama. */
enum class PeristiwaNilai(val label: String) {
    RECOMPOSITION("Recomposition"),
    PEMBUATAN_ULANG_KONFIGURASI("Pembuatan ulang karena konfigurasi"),
    PEMBUATAN_ULANG_PROSES("Pembuatan ulang proses oleh sistem")
}

enum class MekanismeSimpan(val label: String) {
    REMEMBER("remember"),
    REMEMBER_SAVEABLE("rememberSaveable"),
    VIEWMODEL("ViewModel saja"),
    VIEWMODEL_SAVEDSTATEHANDLE("ViewModel dengan SavedStateHandle")
}

data class BarisKetahanan(
    val mekanisme: MekanismeSimpan,
    val recomposition: Boolean,
    val konfigurasi: Boolean,
    val proses: Boolean
) {
    fun bertahanPada(peristiwa: PeristiwaNilai): Boolean = when (peristiwa) {
        PeristiwaNilai.RECOMPOSITION -> recomposition
        PeristiwaNilai.PEMBUATAN_ULANG_KONFIGURASI -> konfigurasi
        PeristiwaNilai.PEMBUATAN_ULANG_PROSES -> proses
    }
}

val ketahananNilaiTasks: List<BarisKetahanan> = listOf(
    BarisKetahanan(MekanismeSimpan.REMEMBER, recomposition = true, konfigurasi = false, proses = false),
    BarisKetahanan(
        MekanismeSimpan.REMEMBER_SAVEABLE,
        recomposition = true,
        konfigurasi = true,
        proses = true
    ),
    BarisKetahanan(MekanismeSimpan.VIEWMODEL, recomposition = true, konfigurasi = true, proses = false),
    BarisKetahanan(
        MekanismeSimpan.VIEWMODEL_SAVEDSTATEHANDLE,
        recomposition = true,
        konfigurasi = true,
        proses = true
    )
)

/** Ketahanan satu mekanisme pada satu peristiwa (Tabel 7). */
fun nilaiBertahan(mekanisme: MekanismeSimpan, peristiwa: PeristiwaNilai): Boolean =
    ketahananNilaiTasks.first { it.mekanisme == mekanisme }.bertahanPada(peristiwa)

// ---------------------------------------------------------------------------
// 8. Tabel 8 — pemeriksaan aplikasi Study Tasks (Bagian 12)
// ---------------------------------------------------------------------------

data class PemeriksaanAplikasi(
    val aksi: String,
    val harapan: String,
    val pemilikJawaban: String
)

val pemeriksaanAplikasiTasks: List<PemeriksaanAplikasi> = listOf(
    PemeriksaanAplikasi(
        "Mengirim judul yang hanya berisi spasi",
        "Pesan validasi muncul; tidak ada tugas yang dibuat",
        "ViewModel menolak, repository tidak tersentuh"
    ),
    PemeriksaanAplikasi(
        "Menambahkan \"Read Compose documentation\"",
        "Satu tugas muncul dan input dikosongkan",
        "Repository membuat tugas; ViewModel mengosongkan draf"
    ),
    PemeriksaanAplikasi(
        "Menekan checkbox tugas tersebut",
        "Status selesai berubah; ID tugas tidak berubah",
        "Repository memakai copy(); ID tetap"
    ),
    PemeriksaanAplikasi(
        "Menambahkan judul yang sama dua kali",
        "Dua tugas muncul dengan ID yang berbeda",
        "Repository memberi nextId++"
    ),
    PemeriksaanAplikasi(
        "Membuat ulang Activity melalui perubahan konfigurasi",
        "Draf dan daftar tugas tetap ada",
        "ViewModel dipertahankan oleh ViewModelStoreOwner"
    ),
    PemeriksaanAplikasi(
        "Menutup Activity dengan tombol Back, lalu membuka aplikasi lagi",
        "Daftar dimulai dalam keadaan kosong",
        "Data hanya ada di memori"
    ),
    PemeriksaanAplikasi(
        "Membuka preview",
        "Layar menampilkan state contoh tanpa ViewModel",
        "TasksScreen menerima nilai biasa"
    )
)

// ---------------------------------------------------------------------------
// 9. Tabel 9 — kesalahan umum dan perbaikannya (Bagian 13)
// ---------------------------------------------------------------------------

data class KesalahanMvvm(val kesalahan: String, val perbaikan: String, val bagian: String)

val kesalahanUmumMvvm: List<KesalahanMvvm> = listOf(
    KesalahanMvvm(
        "Membuat ViewModel dengan constructor-nya di dalam Composable",
        "Gunakan provider viewModel() yang lifecycle-aware beserta factory",
        "Bagian 10"
    ),
    KesalahanMvvm(
        "Menyediakan MutableStateFlow secara publik",
        "Jadikan private dan sediakan StateFlow yang read-only",
        "Bagian 7.5"
    ),
    KesalahanMvvm(
        "Memanggil addTask() langsung di badan Composable",
        "Panggil hanya dari callback aksi pengguna, seperti onClick",
        "Bagian 7.11"
    ),
    KesalahanMvvm(
        "Mengubah isi mutable list secara langsung",
        "Terbitkan nilai state baru dengan collection pengganti",
        "Bagian 7.1"
    ),
    KesalahanMvvm(
        "Memanggil repository di dalam lambda update",
        "Panggil repository terlebih dahulu, lalu perbarui state",
        "Bagian 7.7"
    ),
    KesalahanMvvm(
        "Meneruskan ViewModel ke setiap baris daftar",
        "Teruskan data baris dan callback event",
        "Bagian 7.12"
    ),
    KesalahanMvvm(
        "Memindahkan object animasi dan layout ke ViewModel",
        "Biarkan perilaku khusus UI berada pada pemilik UI-nya",
        "Bagian 6.1"
    ),
    KesalahanMvvm(
        "Memperlakukan ViewModel sebagai database",
        "Simpan data yang harus awet melalui data layer",
        "Bagian 6.2"
    )
)

/** Bagian 13 — setiap baris harus punya perbaikan yang menyebut bagian modulnya. */
fun semuaKesalahanPunyaPerbaikan(daftar: List<KesalahanMvvm> = kesalahanUmumMvvm): Boolean =
    daftar.isNotEmpty() && daftar.all { it.perbaikan.isNotBlank() && it.bagian.startsWith("Bagian ") }

// ---------------------------------------------------------------------------
// 10. Pemeriksaan
// ---------------------------------------------------------------------------

var jumlahCekDijalankan: Int = 0

/**
 * Menjalankan seluruh pemeriksaan yang dapat dihitung tanpa emulator.
 * Mengembalikan daftar kegagalan; daftar kosong berarti semuanya lulus.
 */
fun verifikasiPerilakuStudyTasks(): List<String> {
    val gagal = mutableListOf<String>()
    jumlahCekDijalankan = 0

    fun cek(nama: String, kondisi: Boolean) {
        jumlahCekDijalankan++
        if (!kondisi) gagal += nama
    }

    // Aturan murni yang dipakai aplikasi.
    cek("judul dengan spasi saja ditolak", judulTersimpan("   ") == null)
    cek("judul kosong ditolak", judulTersimpan("") == null)
    cek("judul di-trim sebelum disimpan", judulTersimpan("  Review Kotlin  ") == "Review Kotlin")
    cek("judul biasa lolos", judulTersimpan("Read Compose documentation") != null)
    cek("draf kosong butuh pesan", perluPesanValidasi(""))
    cek("draf spasi butuh pesan", perluPesanValidasi("   "))
    cek("draf berisi tidak butuh pesan", !perluPesanValidasi("Review Kotlin"))

    // Repository: tambah tugas.
    val repo = InMemoryTaskRepository() as TaskRepository
    cek("repository baru kosong", repo.getTasks().isEmpty())
    repo.addTask("  Review Kotlin  ")
    cek("satu tugas setelah addTask", repo.getTasks().size == 1)
    cek("tugas pertama mendapat ID 1", repo.getTasks().first().id == 1L)
    cek("judul disimpan tanpa spasi tepi", repo.getTasks().first().title == "Review Kotlin")
    cek("tugas baru belum selesai", !repo.getTasks().first().isDone)

    repo.addTask("Review Kotlin")
    cek("judul sama dua kali menjadi dua tugas", repo.getTasks().size == 2)
    cek("dua judul sama mendapat ID berbeda", repo.getTasks().map { it.id }.toSet().size == 2)
    cek("tugas kedua mendapat ID 2", repo.getTasks()[1].id == 2L)

    var pesanGagal: String? = null
    try {
        repo.addTask("   ")
    } catch (e: IllegalArgumentException) {
        pesanGagal = e.message
    }
    cek("judul kosong ditolak repository", pesanGagal != null)
    cek("penolakan tidak menambah tugas", repo.getTasks().size == 2)

    // Repository: salinan, bukan list miliknya.
    val salinan = repo.getTasks()
    cek("getTasks mengembalikan object list yang berbeda", salinan !== repo.getTasks())
    cek("daftar yang dikembalikan tidak dapat mengubah repository", repo.getTasks().size == 2)

    // Repository: toggle.
    repo.toggleTask(1L)
    cek("toggle menandai tugas selesai", repo.getTasks().first { it.id == 1L }.isDone)
    cek("toggle tidak mengubah tugas lain", !repo.getTasks().first { it.id == 2L }.isDone)
    cek("toggle tidak mengubah ID", repo.getTasks().first { it.id == 1L }.id == 1L)
    cek("toggle tidak mengubah judul", repo.getTasks().first { it.id == 1L }.title == "Review Kotlin")
    repo.toggleTask(1L)
    cek("toggle kedua mengembalikan status", !repo.getTasks().first { it.id == 1L }.isDone)
    repo.toggleTask(99L)
    cek("toggle ID yang tidak ada tidak mengubah apa pun", repo.getTasks().size == 2)

    // StudyTask tidak dapat diubah di tempat.
    val asli = StudyTask(1L, "Review Kotlin")
    val selesai = asli.copy(isDone = true)
    cek("copy() tidak mengubah object asli", !asli.isDone)
    cek("copy() menghasilkan nilai baru", selesai.isDone && selesai.id == asli.id)

    // TasksUiState: tidak pernah tampil dalam kombinasi yang tidak konsisten.
    val stateAwal = TasksUiState()
    cek("state awal punya draf kosong", stateAwal.draft.isEmpty())
    cek("state awal tidak punya tugas", stateAwal.tasks.isEmpty())
    cek("state awal tidak punya pesan", stateAwal.errorMessage == null)
    val stateError = stateAwal.copy(errorMessage = PESAN_JUDUL_KOSONG)
    cek("copy() mempertahankan draf", stateError.draft == stateAwal.draft)

    // Tabel 2 — empat urusan, satu tanggung jawab masing-masing.
    cek("T2 empat urusan", urusanLayerTasks.size == 4)
    cek("T2 setiap urusan punya komponen", urusanLayerTasks.all { it.komponen.isNotBlank() })
    cek("T2 Composable screen tidak memiliki data tugas",
        urusanLayerTasks.none { it.komponen == "Composable screen" && it.urusan.contains("Memiliki data") })
    cek("T2 repository memiliki data tugas",
        urusanLayerTasks.any { it.komponen == "Repository" && it.urusan.contains("Memiliki data") })

    // Tabel 3 — tiga bagian MVVM.
    cek("T3 tiga peran", peranMvvmTasks.size == 3)
    cek("T3 View adalah Composable", peranMvvmTasks[1].bagian == "View")
    cek("T3 ViewModel adalah state holder layar",
        peranMvvmTasks.first { it.bagian == "ViewModel" }.makna.lowercase().contains("state holder"))

    // Bagian 4.2 — lima langkah, berawal dan berakhir di View.
    cek("B4.2 lima langkah", langkahAksiAddTask.size == 5)
    cek("B4.2 langkah pertama dari View", langkahAksiAddTask.first().pelaku == "View")
    cek("B4.2 langkah terakhir di View", langkahAksiAddTask.last().pelaku == "View")
    cek("B4.2 repository hanya muncul sekali",
        langkahAksiAddTask.count { it.pelaku == "Repository" } == 1)
    cek("B4.2 urutan bernomor 1..5", langkahAksiAddTask.map { it.urutan } == listOf(1, 2, 3, 4, 5))

    // Tabel 4 — tiga lifecycle yang berbeda.
    cek("T4 tiga lifecycle", tigaLifecycleTasks.size == 3)
    cek("T4 ViewModel bertahan sampai scope owner berakhir",
        tigaLifecycleTasks.first { it.nama == "ViewModel" }.penjelasan.contains("ViewModelStoreOwner"))
    cek("T4 Composition menyebut recomposition",
        tigaLifecycleTasks.first { it.nama == "Composition" }.penjelasan.contains("recomposition"))

    // Tabel 5 — dampak peristiwa.
    cek("T5 empat peristiwa", peristiwaViewModelTasks.size == 4)
    cek("T5 nilai berubah: ViewModel tetap",
        viewModelBertahanSaat("Nilai yang diamati berubah"))
    cek("T5 perubahan konfigurasi: ViewModel tetap",
        viewModelBertahanSaat("Activity dibuat ulang karena perubahan konfigurasi"))
    cek("T5 process death: ViewModel hilang",
        !viewModelBertahanSaat("Sistem mematikan proses aplikasi"))
    cek("T5 owner dihapus: ViewModel dibersihkan",
        peristiwaViewModelTasks.last().nasib == NasibViewModel.DIBERSIHKAN)
    cek("T5 peristiwa tak dikenal tidak dianggap bertahan", !viewModelBertahanSaat("Peristiwa lain"))

    // Tabel 6 — pemilik state.
    cek("T6 lima contoh", contohStateTasks.size == 5)
    cek("T6 draf validasi milik ViewModel",
        pemilikUntuk("Draf yang dipakai untuk validasi layar") == PemilikState.VIEWMODEL)
    cek("T6 posisi scroll milik state holder UI",
        pemilikUntuk("Posisi scroll sebuah daftar") == PemilikState.STATE_HOLDER_UI)
    cek("T6 data lintas layar milik repository",
        pemilikUntuk("Data tugas yang dipakai di beberapa layar") == PemilikState.REPOSITORY)
    cek("T6 data awet milik penyimpanan permanen",
        pemilikUntuk("Tugas selesai yang tetap dibutuhkan setelah aplikasi dibuka ulang") ==
            PemilikState.PENYIMPANAN_PERMANEN)
    cek("T6 card terbuka-tutup milik state UI lokal",
        pemilikUntuk("Status terbuka-tutup sementara pada sebuah card") == PemilikState.UI_LOKAL)

    // Tabel 7 — matriks ketahanan, 4 mekanisme x 3 peristiwa.
    cek("T7 empat mekanisme", ketahananNilaiTasks.size == 4)
    cek("T7 remember bertahan saat recomposition",
        nilaiBertahan(MekanismeSimpan.REMEMBER, PeristiwaNilai.RECOMPOSITION))
    cek("T7 remember hilang saat Activity dibuat ulang",
        !nilaiBertahan(MekanismeSimpan.REMEMBER, PeristiwaNilai.PEMBUATAN_ULANG_KONFIGURASI))
    cek("T7 rememberSaveable bertahan saat rotasi",
        nilaiBertahan(MekanismeSimpan.REMEMBER_SAVEABLE, PeristiwaNilai.PEMBUATAN_ULANG_KONFIGURASI))
    cek("T7 rememberSaveable dipulihkan setelah process death",
        nilaiBertahan(MekanismeSimpan.REMEMBER_SAVEABLE, PeristiwaNilai.PEMBUATAN_ULANG_PROSES))
    cek("T7 ViewModel bertahan saat rotasi",
        nilaiBertahan(MekanismeSimpan.VIEWMODEL, PeristiwaNilai.PEMBUATAN_ULANG_KONFIGURASI))
    cek("T7 ViewModel saja hilang saat process death",
        !nilaiBertahan(MekanismeSimpan.VIEWMODEL, PeristiwaNilai.PEMBUATAN_ULANG_PROSES))
    cek("T7 ViewModel + SavedStateHandle bertahan saat process death",
        nilaiBertahan(MekanismeSimpan.VIEWMODEL_SAVEDSTATEHANDLE, PeristiwaNilai.PEMBUATAN_ULANG_PROSES))
    cek("T7 semua mekanisme bertahan saat recomposition",
        MekanismeSimpan.entries.all { nilaiBertahan(it, PeristiwaNilai.RECOMPOSITION) })
    cek("T7 hanya mekanisme berbasis saved state yang bertahan pada ketiga peristiwa",
        MekanismeSimpan.entries.filter { mekanisme ->
            PeristiwaNilai.entries.all { nilaiBertahan(mekanisme, it) }
        } == listOf(MekanismeSimpan.REMEMBER_SAVEABLE, MekanismeSimpan.VIEWMODEL_SAVEDSTATEHANDLE))
    cek("T7 ViewModel saja tidak bertahan pada ketiga peristiwa",
        MekanismeSimpan.VIEWMODEL !in MekanismeSimpan.entries.filter { mekanisme ->
            PeristiwaNilai.entries.all { nilaiBertahan(mekanisme, it) }
        })

    // Tabel 8 — tujuh pemeriksaan aplikasi.
    cek("T8 tujuh pemeriksaan", pemeriksaanAplikasiTasks.size == 7)
    cek("T8 setiap pemeriksaan punya harapan", pemeriksaanAplikasiTasks.all { it.harapan.isNotBlank() })
    cek("T8 setiap pemeriksaan menunjuk pemilik jawaban",
        pemeriksaanAplikasiTasks.all { it.pemilikJawaban.isNotBlank() })

    // Tabel 9 — delapan kesalahan umum.
    cek("T9 delapan kesalahan", kesalahanUmumMvvm.size == 8)
    cek("T9 setiap kesalahan punya perbaikan dan bagian", semuaKesalahanPunyaPerbaikan())
    cek("T9 kesalahan tanpa perbaikan ditolak",
        !semuaKesalahanPunyaPerbaikan(listOf(KesalahanMvvm("Salah", " ", "Bagian 1"))))
    cek("T9 daftar kosong tidak dianggap lengkap", !semuaKesalahanPunyaPerbaikan(emptyList()))

    return gagal
}

/** Mencetak hasil verifikasi; mengembalikan true bila semua lulus. */
fun jalankanVerifikasiModul12(): Boolean {
    val gagal = verifikasiPerilakuStudyTasks()
    val total = jumlahCekDijalankan
    println("Verifikasi Modul 12 — MVVM and ViewModel in Jetpack Compose")
    println("Kasus uji: ${total - gagal.size}/$total lulus")
    if (gagal.isEmpty()) {
        println("OK — aturan data layer, MVVM, dan ketahanan state lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    return gagal.isEmpty()
}

/** Titik masuk untuk menjalankan pemeriksaan dari terminal. */
fun main() {
    println("Ketahanan nilai state (Tabel 7):")
    PeristiwaNilai.entries.forEach { peristiwa ->
        print("  ${peristiwa.label}: ")
        println(
            ketahananNilaiTasks.joinToString { baris ->
                "${baris.mekanisme.label}=${if (baris.bertahanPada(peristiwa)) "ya" else "tidak"}"
            }
        )
    }
    println()
    if (!jalankanVerifikasiModul12()) {
        kotlin.system.exitProcess(1)
    }
}
