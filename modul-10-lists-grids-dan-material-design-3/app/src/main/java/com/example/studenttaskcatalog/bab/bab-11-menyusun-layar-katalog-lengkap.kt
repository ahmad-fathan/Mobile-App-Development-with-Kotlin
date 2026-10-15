package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.taskCategories
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 11 (Bagian 12) — Menyusun Layar Katalog Lengkap
 *
 * Semua potongan sudah tersedia. Bagian ini menyusunnya menjadi satu layar dan
 * menghubungkannya dengan Scaffold yang sudah ada (lihat MainActivity). Strukturnya dari
 * luar ke dalam:
 *
 *   Tema aplikasi dan SATU Scaffold dari pertemuan sebelumnya tetap seperti semula.
 *   innerPadding diberikan SATU KALI SAJA kepada StudentTaskCatalogScreen; jangan menambahkan
 *   Scaffold kedua di dalam layar.
 *   Layar berupa Column biasa dengan tiga anak: CategoryStrip, Row kontrol, dan satu lazy
 *   layout vertikal.
 *   Modifier.weight(1f) memberi lazy layout seluruh sisa tinggi layar.
 *
 * Susunan itu memastikan hanya ada SATU kontainer yang menggulir secara vertikal pada satu
 * waktu.
 *
 * 12.1 Kesalahan Umum: Gulir Vertikal Bersarang
 *
 * Kesalahan yang sering terjadi adalah menaruh LazyColumn di dalam Column yang diberi
 * verticalScroll:
 *
 *   HINDARI:
 *   Column(Modifier.verticalScroll(rememberScrollState())) {
 *       CategoryStrip(taskCategories)
 *       TaskList(sampleTasks)                 // crash
 *   }
 *
 * Induk yang dapat digulir mengukur anaknya dengan tinggi TAK TERBATAS. Lazy list tidak dapat
 * menentukan berapa item yang perlu di-compose bila tingginya tak terbatas, sehingga Compose
 * melempar error "Vertically scrollable component was measured with an infinity maximum
 * height constraints". Perbaikannya adalah memiliki TEPAT SATU kontainer gulir vertikal
 * dengan tinggi yang terbatas:
 *
 *   Lakukan:
 *   Column(Modifier.fillMaxSize()) {
 *       CategoryStrip(taskCategories)
 *       TaskList(sampleTasks, Modifier.weight(1f))
 *   }
 *
 * Bila judul atau strip kategori harus ikut tergulir bersama daftar, letakkan elemen tersebut
 * DI DALAM lazy list sebagai item { }. LazyRow di dalam Column tidak bermasalah, karena ia
 * menggulir secara HORIZONTAL dan tingginya ditentukan oleh chip di dalamnya.
 *
 * 12.3 Membangun Secara Bertahap
 *
 * Saat praktikum, bangun aplikasi dalam tujuh tahap. Setiap tahap menghasilkan sesuatu yang
 * dapat dilihat, sehingga kesalahan cepat ketahuan:
 *   1. Buat StudentTask dan 12 data contoh; proyek harus dapat dikompilasi.        (Bab 1)
 *   2. Buat TaskCard dengan peran tipografi dan StatusLabel; lihat satu kartu di Preview. (Bab 4, 9)
 *   3. Buat TaskList dengan LazyColumn dan item ber-key; daftar harus dapat digulir. (Bab 4, 5)
 *   4. Tambahkan CategoryStrip dengan LazyRow; chip harus dapat digulir ke samping. (Bab 6)
 *   5. Buat TaskGrid dengan Fixed(2), lalu ganti ke Adaptive(160.dp).              (Bab 7)
 *   6. Tambahkan tombol List/Grid dengan rememberSaveable; tombol harus menukar tampilan. (Bab 10)
 *   7. Jalankan pada layar sempit dan layar lebar; jumlah kolom grid harus berubah.
 *      Gunakan emulator Resizable, emulator tablet, atau Preview dengan widthDp = 600.
 *
 * Tidak ada dependensi Gradle baru yang dibutuhkan: proyek Compose Material 3 standar sudah
 * memuat library foundation (lazy layout) dan material3 (Card serta chip).
 */

/** Layar katalog: satu strip horizontal + kontrol + satu lazy layout vertikal. */
@Composable
fun StudentTaskCatalogScreen(modifier: Modifier = Modifier) {
    var showGrid by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        CategoryStrip(
            categories = taskCategories,
            modifier = Modifier.padding(top = 12.dp)
        )
        TaskViewToggle(showGrid = showGrid, onToggle = { showGrid = !showGrid })
        // weight(1f) memberi lazy layout sisa tinggi layar, sehingga tidak pernah diukur
        // dengan tinggi tak terbatas.
        if (showGrid) {
            TaskGrid(tasks = sampleTasks, modifier = Modifier.weight(1f))
        } else {
            TaskList(tasks = sampleTasks, modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun StudentTaskCatalogScreenPreview() {
    StudentTaskCatalogTheme { StudentTaskCatalogScreen() }
}
