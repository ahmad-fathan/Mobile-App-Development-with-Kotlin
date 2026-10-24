package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.campuscoursebrowser.Course
import com.example.campuscoursebrowser.sampleCourses

/**
 * Bab 8 (Bagian 9) — Mewujudkan Keputusan Desain di Compose
 *
 * 9.1 Memetakan keputusan ke alat Compose (Tabel 8):
 *   hierarki informasi        → MaterialTheme.typography
 *   struktur item konsisten   → composable CourseCard yang dipakai ulang
 *   konten menyesuaikan ruang → fillMaxWidth, weight, widthIn
 *   konten panjang            → LazyColumn dan detail yang dapat di-scroll
 *   berpindah struktur        → komposisi bersyarat berdasarkan state
 *   mempertahankan pilihan    → state di-hoist + rememberSaveable
 *   makna aksesibel           → komponen Material + semantics
 *
 * 9.2 Informasi jendela dibaca SATU KALI di dekat akar aplikasi, lalu diubah menjadi satu
 *   keputusan Boolean yang diteruskan ke layar. Nilai Boolean itulah yang membuat Preview tidak
 *   bergantung pada jendela perangkat.
 *
 * 9.3 Pilihan disimpan DI ATAS percabangan layout, sehingga kedua cabang membaca state yang
 *   sama. Yang disimpan adalah id (String), bukan seluruh objek Course, agar cocok untuk
 *   rememberSaveable. State UI sementara dipisahkan dari data aplikasi yang persisten.
 *
 * 9.4 Composable daftar dan detail yang sama dipakai di semua cabang; yang berubah hanya susunan
 *   di sekelilingnya. Ingat: weight hanya bermakna di dalam scope Row atau Column.
 *
 * 9.5 Responsive di dalam panel: GridCells.Adaptive mengikuti lebar KONTAINER, bukan lebar
 *   jendela. Grid ini alternatif opsional dan tidak dipakai contoh list-detail.
 *
 * 9.6 Navigasi tingkat atas juga dapat beradaptasi (navigation bar vs navigation rail). Jangan
 *   menghafal aturan "medium selalu rail": NavigationSuiteScaffold memilih bentuk yang sesuai,
 *   dan pemilihan destinasi tetap memerlukan state aplikasi. Pengayaan, tidak dipakai di sini.
 *
 * 9.7 Contoh modul memakai if dan Row eksplisit agar mekanismenya terlihat. Aplikasi yang lebih
 *   besar sebaiknya memakai ListDetailPaneScaffold, yang juga memperhitungkan postur perangkat.
 *
 * 9.8 Foldable dan inset: jauhkan kontrol penting dari hinge, hormati system bar, cutout, dan
 *   keyboard, serta terapkan content padding dari Scaffold.
 */

/**
 * Tabel 8 — keputusan desain dan alat Compose yang mewujudkannya. Baris terakhir (makna
 * aksesibel) mewujud di Bab 9 melalui semantics pada judul dan tombol kartu.
 */
val keputusanDanAlatCompose: List<Pair<String, String>> = listOf(
    "Membuat tingkat informasi jelas" to "MaterialTheme.typography",
    "Memakai ulang struktur item yang konsisten" to "Composable CourseCard",
    "Membiarkan konten menyesuaikan ruang" to "fillMaxWidth, weight, widthIn",
    "Menjaga konten panjang tetap dapat dipakai" to "LazyColumn, konten detail yang di-scroll",
    "Berpindah antarstruktur layout" to "Komposisi bersyarat berdasarkan state",
    "Mempertahankan item terpilih" to "State yang di-hoist dan rememberSaveable",
    "Memberikan makna yang aksesibel" to "Komponen Material dan semantics"
)

/** Tabel 9 — dua tingkat implementasi layout adaptif. */
val perbandinganImplementasi: List<Pair<String, String>> = listOf(
    "if dan Row eksplisit" to
        "Memperlihatkan keputusan layout; parameter Boolean membuat preview sederhana",
    "ListDetailPaneScaffold" to
        "Mengoordinasikan panel list-detail dengan API navigasi panel yang lebih kaya",
    "Tidak ada penempatan yang memperhitungkan hinge" to
        "API adaptive memperhitungkan informasi jendela dan postur"
)

/**
 * Catatan: fungsi `showTwoPanesUntuk(sizeClass: WindowSizeClass)` yang menerjemahkan window size
 * class menjadi satu Boolean dideklarasikan di paket akar, berkas MainActivity.kt, karena ia
 * dipanggil tepat di dekat akar aplikasi (`CourseBrowserApp`). Bab ini hanya memakai hasilnya.
 * WindowSizeClass yang dipakai adalah androidx.window.core.layout.WindowSizeClass — bukan API
 * lama androidx.compose.material3.windowsizeclass. Keduanya jangan dicampur.
 */

/**
 * Satu state pilihan di atas cabang layout. `selectedId` adalah SATU-SATUNYA sumber pilihan;
 * mengganti layout tidak boleh memilih mata kuliah yang berbeda.
 */
@Composable
fun ContohStateDiAtasCabangLayout(duaPanel: Boolean = false) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val terpilih: Course? = sampleCourses.firstOrNull { it.id == selectedId }

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            if (duaPanel) "Dua panel" else "Satu panel",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            "selectedId = ${selectedId ?: "null"} — " +
                (terpilih?.title ?: "belum ada mata kuliah dipilih"),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            "Pilihan dibaca kedua cabang; memilih pada satu cabang memperbarui cabang lain.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/**
 * 9.5 — Grid adaptif di dalam panel. Jumlah kolom mengikuti lebar kontainer, bukan lebar jendela.
 * minSize 180 dp hanyalah titik awal: uji dengan teks yang diperbesar sebelum mempertahankannya.
 */
@Composable
fun GridCourseAdaptif(courses: List<Course> = sampleCourses) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 180.dp),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(courses, key = { it.id }) { course ->
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(course.title, style = MaterialTheme.typography.titleSmall)
                Text(course.summary, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 260)
@Composable
private fun StateDiAtasCabangPreview() {
    ContohStateDiAtasCabangLayout()
}
