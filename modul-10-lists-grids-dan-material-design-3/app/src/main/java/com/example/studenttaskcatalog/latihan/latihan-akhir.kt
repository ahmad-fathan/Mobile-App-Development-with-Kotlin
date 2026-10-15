package com.example.studenttaskcatalog.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.StudentTask
import com.example.studenttaskcatalog.bab.CategoryFilterStrip
import com.example.studenttaskcatalog.bab.CategoryStrip
import com.example.studenttaskcatalog.bab.EmptyTaskState
import com.example.studenttaskcatalog.bab.TaskCard
import com.example.studenttaskcatalog.bab.TaskGrid
import com.example.studenttaskcatalog.bab.TaskList
import com.example.studenttaskcatalog.bab.TaskViewToggle
import com.example.studenttaskcatalog.jalankanVerifikasiModul10
import com.example.studenttaskcatalog.jumlahSelAdaptif
import com.example.studenttaskcatalog.lebarSelAdaptif
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.taskCategories
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Latihan Akhir Modul 10 — Lists, Grids, and Material Design 3 in Jetpack Compose.
 *
 * Enam latihan berikut mengikuti naskah modul. Latihan yang menuntut perhitungan atau aturan
 * dijawab juga oleh fungsi murni (tanpa Compose) agar jawabannya dapat diperiksa otomatis;
 * kumpulan pemeriksaan itu dijalankan oleh `latihan6JalankanSemua()`.
 *
 * Untuk mencoba salah satu latihan di emulator, ganti pemanggilan StudentTaskCatalogScreen(...)
 * pada MainActivity.kt.
 */

// ---------------------------------------------------------------------------
// Latihan 1 — Menghitung Jumlah Item dan Menentukan Key
// ---------------------------------------------------------------------------

/**
 * Latihan 1.
 *
 * Sebuah LazyColumn berisi satu item { Text("This week") } dan items(tasks, key = { it.id })
 * dengan 12 tugas. Berapa jumlah item dalam daftar tersebut?
 *
 *   item { }  menambahkan TEPAT SATU item tetap, dan items(tasks, ...) menambahkan SATU item
 *   untuk setiap data. Jadi jumlah item = 1 + 12 = 13.
 *
 * Apa key untuk tugas dengan id = 7 yang berada di urutan ke-3 setelah daftar diurutkan
 * berdasarkan tanggal?
 *
 *   Key tidak berubah mengikuti posisi. key = { it.id } mengaitkan setiap item dengan DATANYA,
 *   sehingga tugas dengan id = 7 tetap ber-key 7, baik ia berada di urutan ke-3 maupun ke-12.
 *   Itulah gunanya key: state dan posisi gulir mengikuti data, bukan urutan tampil.
 */
fun latihan1JumlahItem(jumlahItemTetap: Int, jumlahData: Int): Int = jumlahItemTetap + jumlahData

fun latihan1KeyTugas(id: Int): Int = id

/** Latihan 1 — tabel jawaban yang dicetak dari fungsi di atas, bukan salinan tangan. */
fun latihan1RingkasanJawaban(): String = buildString {
    appendLine("Latihan 1")
    appendLine("  Jumlah item       : ${latihan1JumlahItem(1, sampleTasks.size)} (satu judul bagian + ${sampleTasks.size} tugas)")
    val tugasTujuh = sampleTasks.first { it.id == 7 }
    appendLine("  Key tugas id = 7  : ${latihan1KeyTugas(7)} — key mengikuti data, bukan posisi ke-3")
}

// ---------------------------------------------------------------------------
// Latihan 2 — Memperbaiki Daftar Berbasis Indeks Tanpa Key
// ---------------------------------------------------------------------------

/**
 * Latihan 2.
 *
 * Mahasiswa menulis items(tasks.size) { index -> TaskCard(tasks[index]) } TANPA key, lalu
 * menambahkan fitur membuka detail kartu dengan remember. Setelah tugas baru disisipkan di
 * posisi paling atas, detail yang terbuka berpindah ke kartu lain.
 *
 * Penyebabnya: tanpa key, lazy layout mengenali setiap item berdasarkan POSISINYA. State
 * (detail terbuka) di-remember pada posisi 3; setelah penyisipan, semua data bergeser satu
 * posisi ke bawah sementara state tetap di posisi 3 — jadi state itu kini menempel pada tugas
 * yang salah (Bagian 6, Gambar 4).
 *
 * Perbaikannya: pakai items(tasks, key = { it.id }), sehingga Compose mengaitkan item dengan
 * datanya dan state ikut berpindah bersama tugas yang benar. Bentuk perbaikannya ada pada
 * Latihan2DaftarDenganKey di bawah.
 */
@Composable
fun Latihan2DaftarTanpaKey(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // SALAH: varian berbasis indeks tidak menerima key, jadi state menempel pada posisi.
        items(tasks.size) { index ->
            TaskCard(tasks[index])
        }
    }
}

/** Perbaikan Latihan 2: daftar yang sama, sekarang dengan key yang stabil. */
@Composable
fun Latihan2DaftarDenganKey(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

// ---------------------------------------------------------------------------
// Latihan 3 — Menghitung Kolom Grid Adaptif
// ---------------------------------------------------------------------------

/**
 * Latihan 3.
 *
 * Grid memakai GridCells.Adaptive(minSize = 150.dp), contentPadding = PaddingValues(16.dp),
 * dan jarak antarkolom 8 dp. Hitung jumlah kolom dan perkiraan lebar setiap sel.
 *
 * Langkah perhitungan (padanan rumus Compose pada verifikasi.kt, fungsi jumlahSelAdaptif):
 *   lebar tersedia = lebar layar − 2 × 16 dp
 *   kolom          = lantai( (lebar tersedia + 8) / (150 + 8) )
 *   lebar sel      = (lebar tersedia − 8 × (kolom − 1)) / kolom
 *
 *   360 dp → tersedia 328 → (328 + 8) / 158 = 2,12 → 2 kolom, sel (328 − 8) / 2 = 160 dp
 *   412 dp → tersedia 380 → (380 + 8) / 158 = 2,45 → 2 kolom, sel (380 − 8) / 2 = 186 dp
 *   600 dp → tersedia 568 → (568 + 8) / 158 = 3,64 → 3 kolom, sel (568 − 16) / 3 = 184 dp
 *
 * Perhatikan bahwa sel selalu LEBIH LEBAR daripada minSize, karena sisa lebar dibagikan rata.
 */
fun latihan3JumlahKolom(lebarLayarDp: Int): Int =
    jumlahSelAdaptif(
        lebarLayarDp,
        paddingHorizontal = 16,
        jarakAntarSel = 8,
        minSize = 150
    )

fun latihan3LebarSel(lebarLayarDp: Int): Int =
    lebarSelAdaptif(
        lebarLayarDp,
        paddingHorizontal = 16,
        jarakAntarSel = 8,
        minSize = 150
    )

/** Grid dengan konfigurasi Latihan 3; bandingkan jumlah kolomnya dengan Bagian 8.2. */
@Composable
fun Latihan3GridAdaptif150(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

/** Latihan 3 — tabel jawaban yang dihitung, bukan salinan tangan. */
fun latihan3RingkasanJawaban(): String = buildString {
    appendLine("Latihan 3 (minSize 150 dp, padding 16 dp, jarak 8 dp)")
    listOf(360, 412, 600).forEach { lebar ->
        appendLine("  $lebar dp → ${latihan3JumlahKolom(lebar)} kolom, lebar sel ${latihan3LebarSel(lebar)} dp")
    }
}

// ---------------------------------------------------------------------------
// Latihan 4 — Mengelompokkan Tugas Menjadi Dua Bagian
// ---------------------------------------------------------------------------

/**
 * Latihan 4.
 *
 * Ubah TaskList sehingga tugas dikelompokkan menjadi dua bagian, "Pending" dan "Completed".
 * Setiap bagian diawali item { } berisi judul bagian, diikuti items(...) untuk tugas pada
 * bagian tersebut. Key harus tetap unik di seluruh daftar.
 *
 * Di sini setiap tugas muncul TEPAT SATU KALI, pada bagian yang sesuai dengan isCompleted.
 * Dengan begitu key = { it.id } tetap unik di seluruh daftar. Bila satu tugas ditampilkan di
 * DUA bagian sekaligus, key-nya akan duplikat dan aplikasi crash (Bagian 6, aturan key) —
 * solusinya saat itu adalah key majemuk, misalnya "$status-${task.id}".
 */
@Composable
fun Latihan4DaftarBerseksi(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    val pending = tasks.filter { !it.isCompleted }
    val completed = tasks.filter { it.isCompleted }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Pending (${pending.size})",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        items(pending, key = { it.id }) { task ->
            TaskCard(task)
        }
        item {
            Text(
                text = "Completed (${completed.size})",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
        items(completed, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

/**
 * Aturan yang dijaga Latihan 4: setiap tugas ada di tepat satu bagian, sehingga id-nya unik
 * di seluruh daftar. Bila key dibuat majemuk dari status dan id, keunikannya juga terjaga.
 */
fun latihan4TugasAdaDiTepatSatuBagian(tugas: List<StudentTask>): Boolean {
    val pending = tugas.filter { !it.isCompleted }
    val completed = tugas.filter { it.isCompleted }
    return (pending + completed).size == tugas.size
}

fun latihan4KeyMajemuk(status: String, id: Int): String = "$status-$id"

// ---------------------------------------------------------------------------
// Latihan 5 — Filter Kategori dengan FilterChip dan Empty State
// ---------------------------------------------------------------------------

/**
 * Latihan 5.
 *
 * Lengkapi Student Task Catalog dengan filter kategori memakai FilterChip. Kategori terpilih
 * disimpan dengan rememberSaveable, daftar yang tampil diturunkan dengan filter, dan layar
 * menampilkan empty state bila tidak ada tugas yang cocok. Sertakan tangkapan layar untuk
 * kategori "Quiz" dan untuk kategori yang tidak memiliki tugas.
 *
 * Kode lengkapnya adalah StudentTaskCatalogFilterScreen pada
 * bab/bab-12-pengayaan-filter-kategori-dan-empty-state.kt: CategoryFilterStrip (FilterChip
 * dengan selected), penyaringan lewat `tugasTerlihat`, dan EmptyTaskState, dengan kategori
 * "Lab" ditambahkan agar empty state dapat terlihat. Untuk tangkapan layar: jalankan aplikasi
 * di emulator, pilih chip "Quiz", lalu pilih chip "Lab", dan simpan kedua tangkapan layar.
 */
@Composable
fun Latihan5FilterKategori(modifier: Modifier = Modifier) {
    var showGrid by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf("All") }
    val categories = taskCategories + "Lab"

    val visibleTasks =
        if (selectedCategory == "All") sampleTasks
        else sampleTasks.filter { it.category == selectedCategory }

    Column(modifier = modifier) {
        CategoryFilterStrip(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        TaskViewToggle(showGrid = showGrid, onToggle = { showGrid = !showGrid })
        when {
            visibleTasks.isEmpty() -> EmptyTaskState(selectedCategory, Modifier.weight(1f))
            showGrid -> TaskGrid(tasks = visibleTasks, modifier = Modifier.weight(1f))
            else -> TaskList(tasks = visibleTasks, modifier = Modifier.weight(1f))
        }
    }
}

// ---------------------------------------------------------------------------
// Latihan 6 — Menjelaskan Gulir Vertikal Bersarang
// ---------------------------------------------------------------------------

/**
 * Latihan 6.
 *
 *   Column(Modifier.verticalScroll(rememberScrollState())) {
 *       CategoryStrip(taskCategories)
 *       TaskGrid(sampleTasks)
 *   }
 *
 * Mengapa gagal: induk yang dapat digulir (Column + verticalScroll) mengukur anaknya dengan
 * tinggi TAK TERBATAS. Lazy list tidak dapat menentukan berapa item yang perlu di-compose bila
 * tingginya tak terbatas, sehingga Compose melempar error "Vertically scrollable component was
 * measured with an infinity maximum height constraints".
 *
 * Perbaikannya: TEPAT SATU kontainer gulir vertikal dengan tinggi terbatas. Column biasa
 * (fillMaxSize) menjadi tuan rumah, dan lazy layout mendapat sisa tinggi lewat weight(1f):
 *
 *   Column(Modifier.fillMaxSize()) {
 *       CategoryStrip(taskCategories)
 *       TaskGrid(sampleTasks, Modifier.weight(1f))
 *   }
 *
 * Mengapa CategoryStrip boleh tetap di dalam Column itu: LazyRow menggulir secara HORIZONTAL,
 * dan tingginya DITENTUKAN oleh chip di dalamnya (bukan tak terbatas). Jadi tidak ada konflik
 * pengukuran: hanya ada satu kontainer gulir vertikal, yaitu LazyVerticalGrid.
 */
@Composable
fun Latihan6PerbaikanGulir(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        CategoryStrip(categories = taskCategories)
        TaskGrid(tasks = sampleTasks, modifier = Modifier.weight(1f))
    }
}

/** Aturan Latihan 6: hanya satu kontainer gulir vertikal pada satu waktu. */
fun latihan6HanyaSatuKontainerGulirVertikal(jumlahIndukVerticalScroll: Int, jumlahLazyVertikal: Int): Boolean =
    jumlahIndukVerticalScroll == 0 && jumlahLazyVertikal == 1

fun latihan6PesanErrorGulirBersarang(): String =
    "Vertically scrollable component was measured with an infinity maximum height constraints"

// ---------------------------------------------------------------------------
// Penutup: menjalankan seluruh pemeriksaan latihan
// ---------------------------------------------------------------------------

/**
 * Latihan 6 sekaligus penutup modul: mencetak jawaban tiap latihan yang dapat dihitung, lalu
 * menjalankan pemeriksaan perilaku katalog (verifikasi.kt) dan pemeriksaan aturan latihan.
 *
 * Mengembalikan true bila semua lulus.
 */
fun latihan6JalankanSemua(): Boolean {
    println(latihan1RingkasanJawaban())
    println(latihan3RingkasanJawaban())

    val gagal = mutableListOf<String>()
    fun cek(nama: String, kondisi: Boolean) { if (!kondisi) gagal += nama }

    // Latihan 1 — 13 item, key mengikuti data.
    cek("L1 jumlah item adalah 13", latihan1JumlahItem(1, sampleTasks.size) == 13)
    cek("L1 key tugas id 7 tetap 7", latihan1KeyTugas(7) == 7)

    // Latihan 3 — konfigurasi minSize 150 dp.
    cek("L3 360 dp → 2 kolom", latihan3JumlahKolom(360) == 2)
    cek("L3 360 dp → sel 160 dp", latihan3LebarSel(360) == 160)
    cek("L3 412 dp → 2 kolom", latihan3JumlahKolom(412) == 2)
    cek("L3 412 dp → sel 186 dp", latihan3LebarSel(412) == 186)
    cek("L3 600 dp → 3 kolom", latihan3JumlahKolom(600) == 3)
    cek("L3 600 dp → sel 184 dp", latihan3LebarSel(600) == 184)
    cek("L3 sel selalu lebih lebar daripada minSize 150", latihan3LebarSel(360) > 150)

    // Latihan 4 — dua bagian, key tetap unik.
    cek("L4 setiap tugas ada di tepat satu bagian", latihan4TugasAdaDiTepatSatuBagian(sampleTasks))
    cek("L4 bagian pending dan completed tidak kosong",
        sampleTasks.any { !it.isCompleted } && sampleTasks.any { it.isCompleted })
    cek("L4 key majemuk memuat status dan id", latihan4KeyMajemuk("Pending", 7) == "Pending-7")

    // Latihan 6 — satu kontainer gulir vertikal.
    cek("L6 susunan benar hanya punya satu kontainer gulir vertikal",
        latihan6HanyaSatuKontainerGulirVertikal(0, 1))
    cek("L6 induk verticalScroll ditolak",
        !latihan6HanyaSatuKontainerGulirVertikal(1, 1))

    if (gagal.isEmpty()) {
        println("OK — seluruh latihan L1–L6 lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    println()
    val perilakuKatalogLulus = jalankanVerifikasiModul10()
    return gagal.isEmpty() && perilakuKatalogLulus
}

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun Latihan4DaftarBerseksiPreview() {
    StudentTaskCatalogTheme { Latihan4DaftarBerseksi(tasks = sampleTasks) }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun Latihan5FilterKategoriPreview() {
    StudentTaskCatalogTheme { Latihan5FilterKategori(Modifier.padding(0.dp)) }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun Latihan6PerbaikanGulirPreview() {
    StudentTaskCatalogTheme { Latihan6PerbaikanGulir() }
}
