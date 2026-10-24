package com.example.campuscoursebrowser.bab

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import com.example.campuscoursebrowser.Course
import com.example.campuscoursebrowser.TeksCourseBrowser
import com.example.campuscoursebrowser.sampleCourses
import com.example.campuscoursebrowser.ui.theme.CampusCourseBrowserTheme

/**
 * Bab 9 (Bagian 10) — Membangun Campus Course Browser
 *
 * Aplikasi utuh yang menghubungkan prinsip desain dengan Compose: data lokal, kartu yang mudah
 * dibaca dan dapat dipakai ulang, penanda pilihan, layout satu dan dua panel, keadaan detail
 * kosong, serta penanganan Back.
 *
 * 10.1 Persiapan proyek
 *   1. Proyek Empty Activity, Kotlin + Jetpack Compose, package dipertahankan.
 *   2. Compose BOM dan Activity Compose dari template; Material 3 dan Foundation tersedia.
 *   3. Tambahkan `androidx.compose.material3.adaptive:adaptive:1.3.0` (sudah ada di
 *      gradle/libs.versions.toml modul ini sebagai `libs.androidx.material3.adaptive`).
 *   4. Kode diletakkan di bawah baris `package` pada MainActivity.kt, atau di berkas Kotlin
 *      baru dalam package yang sama — di modul ini: berkas ini.
 *   5. Di dalam setContent, panggil CourseBrowserApp() di dalam pembungkus tema proyek.
 *
 * 10.2 Memanggil dari Activity — lihat MainActivity.kt. Jangan menumpuk tema proyek dengan
 *   MaterialTheme polos tanpa alasan.
 *
 * 10.4 Alur kode dan alasan desainnya (tujuh langkah)
 *   (1) CourseBrowserApp membaca informasi jendela; CourseBrowser menerima kebijakan Boolean,
 *       sehingga tiap alternatif layout mudah di-preview.
 *   (2) Id mata kuliah terpilih disimpan dengan rememberSaveable DI ATAS kedua cabang, sehingga
 *       mengubah ukuran jendela mengganti tampilan tanpa kehilangan pilihan.
 *   (3) Satu implementasi daftar dan satu implementasi detail melayani kedua layout, sehingga
 *       kata dan tipografi tetap konsisten.
 *   (4) Tinggi kartu mengikuti isinya; daftar dan detail masing-masing dapat di-scroll.
 *   (5) Kartu terpilih memakai teks yang terlihat ("Selected course") dan semantics selected,
 *       jadi pilihan tidak hanya ditandai warna. Setiap tombol punya label aksesibilitas.
 *   (6) Padding dari Scaffold melindungi konten dari inset yang dikelola Scaffold.
 *   (7) widthIn(max = 1200.dp) adalah batas keterbacaan contoh, bukan breakpoint Android.
 *
 * 10.5 Batasan: perilaku Back pada aplikasi produksi ditentukan model navigasinya sendiri;
 *   pemulihan posisi scroll daftar, penempatan fokus saat panel berubah, dan pemulihan scroll per
 *   mata kuliah belum lengkap; window size class tidak mendeteksi hinge sehingga aplikasi ini
 *   belum dapat disebut siap untuk foldable; teks literal dan data lokal dipakai agar contoh
 *   tetap kecil.
 */

/*
 * Catatan: akar aplikasi `CourseBrowserApp()` — yang membaca ukuran jendela lalu memanggil layar
 * ini dengan satu Boolean — ada di paket akar (MainActivity.kt), sesuai Bagian 9.2: informasi
 * jendela dibaca di batas aplikasi, bukan di dalam layar.
 */

/**
 * Layar Course Browser. Parameter Boolean membuat komponen ini dapat diuji dan di-preview tanpa
 * mengubah ukuran jendela.
 */
@Composable
fun CourseBrowser(showTwoPanes: Boolean) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val selectedCourse = sampleCourses.firstOrNull { it.id == selectedId }

    // Dalam model pengajaran ini, Back menghapus pilihan lebih dulu. Pada jendela lebar, daftar
    // dan panel detail kosong tetap terlihat.
    BackHandler(enabled = selectedId != null) { selectedId = null }

    Scaffold { innerPadding ->
        Box(
            Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            // Batasi panjang baris bacaan pada jendela yang sangat lebar.
            Column(
                Modifier
                    .widthIn(max = 1200.dp)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    TeksCourseBrowser.JUDUL_APLIKASI,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                if (showTwoPanes) {
                    Row(
                        Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        CourseList(
                            selectedId = selectedId,
                            onSelect = { selectedId = it },
                            modifier = Modifier.weight(1f).fillMaxSize()
                        )
                        CourseDetails(
                            course = selectedCourse,
                            showBack = false,
                            onBack = { selectedId = null },
                            modifier = Modifier.weight(1.4f).fillMaxSize()
                        )
                    }
                } else if (selectedCourse != null) {
                    CourseDetails(
                        course = selectedCourse,
                        showBack = true,
                        onBack = { selectedId = null },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                } else {
                    CourseList(
                        selectedId = selectedId,
                        onSelect = { selectedId = it },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }
        }
    }
}

/** Daftar mata kuliah; satu-satunya kontainer yang menggulir di panel ini. */
@Composable
private fun CourseList(
    selectedId: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleCourses, key = { it.id }) { course ->
            CourseCard(
                course = course,
                isSelected = course.id == selectedId,
                onOpen = { onSelect(course.id) }
            )
        }
    }
}

/**
 * Kartu yang dapat dipakai ulang di kedua layout. Tingginya mengikuti isi: tidak ada tinggi
 * tetap dan tidak ada maxLines, sehingga judul panjang tetap terbaca saat font diperbesar.
 */
@Composable
private fun CourseCard(
    course: Course,
    isSelected: Boolean,
    onOpen: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(course.title, style = MaterialTheme.typography.titleLarge)
            Text(course.summary, style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = onOpen,
                modifier = Modifier.semantics {
                    selected = isSelected
                    contentDescription = TeksCourseBrowser.labelTombol(course.title, isSelected)
                }
            ) {
                Text(if (isSelected) TeksCourseBrowser.TERPILIH else TeksCourseBrowser.LIHAT_DETAIL)
            }
        }
    }
}

/**
 * Panel detail. `course == null` adalah keadaan detail kosong pada layout dua panel.
 * `key(course?.id)` mengatur ulang scroll saat mata kuliah lain dipilih; menyimpan scroll per
 * mata kuliah adalah latihan pengembangan terpisah.
 */
@Composable
private fun CourseDetails(
    course: Course?,
    showBack: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    key(course?.id) {
        Column(
            modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (showBack) {
                TextButton(onClick = onBack) { Text(TeksCourseBrowser.KEMBALI) }
            }
            Text(
                TeksCourseBrowser.DETAIL,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics { heading() }
            )
            if (course == null) {
                Text(TeksCourseBrowser.PILIH_DETAIL)
            } else {
                Text(course.title, style = MaterialTheme.typography.headlineSmall)
                Text(course.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

/** Preview jendela sempit, 360 x 800 dp. */
@Preview(widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun CompactPreview() {
    CampusCourseBrowserTheme { CourseBrowser(showTwoPanes = false) }
}

/** Preview jendela lebar, 1000 x 800 dp: daftar dan panel detail kosong terlihat bersamaan. */
@Preview(widthDp = 1000, heightDp = 800, showBackground = true)
@Composable
private fun WidePreview() {
    CampusCourseBrowserTheme { CourseBrowser(showTwoPanes = true) }
}

/** Preview teks diperbesar: judul panjang tetap terbaca utuh dan tombol tetap terlihat. */
@Preview(widthDp = 360, heightDp = 800, fontScale = 1.5f, showBackground = true)
@Composable
private fun LargeTextPreview() {
    CampusCourseBrowserTheme { CourseBrowser(showTwoPanes = false) }
}
