package com.example.campuscoursebrowser.latihan

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
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
import com.example.campuscoursebrowser.jalankanVerifikasiModul11
import com.example.campuscoursebrowser.kebijakanDuaPanel
import com.example.campuscoursebrowser.ui.theme.CampusCourseBrowserTheme

/**
 * Latihan Akhir Modul 11 — UI/UX and Adaptive Layout.
 *
 * Satu proyek kecil (sekitar 2 jam) di kelas: mengubah Campus Course Browser menjadi katalog
 * dengan tema lain, mengikuti Lima Langkah pada Tabel 13 modul. Tema yang dipilih di sini adalah
 * KATALOG LABORATORIUM (nama lab, lokasi, fasilitas).
 *
 *   1. Analisis singkat   → user story + dua requirement (15 menit)
 *   2. Sketsa             → tiga sketsa layar (15 menit)
 *   3. Data dan kartu     → aplikasi berjalan dengan tema baru (45 menit)
 *   4. Menguji            → tiga pengujian dan hasilnya (25 menit)
 *   5. Mengumpulkan       → kode dan lembar jawaban (20 menit)
 *
 * Yang TIDAK boleh diubah saat menyalin kode: bagian yang membaca ukuran jendela
 * (showTwoPanesUntuk di MainActivity.kt) dan bagian yang menyimpan item terpilih dengan
 * rememberSaveable di atas percabangan layout. Keduanya sudah menangani layout adaptif.
 *
 * Untuk mencoba hasil latihan di emulator, ganti CourseBrowserApp() pada MainActivity.kt dengan
 * KatalogAdaptifApp().
 */

// ---------------------------------------------------------------------------
// Langkah 1 — Analisis singkat
// ---------------------------------------------------------------------------

/**
 * User story tema katalog laboratorium, format "Sebagai …, saya ingin … agar …".
 * Skenario kelas ini masih HIPOTESIS: pada proyek nyata ia harus diperiksa dengan wawancara atau
 * observasi mahasiswa yang benar-benar mencari laboratorium.
 */
const val USER_STORY_LATIHAN =
    "Sebagai mahasiswa, saya ingin melihat lokasi dan fasilitas sebuah laboratorium agar saya " +
        "dapat memutuskan laboratorium mana yang sesuai untuk praktikum saya."

/** Satu requirement latihan: keputusan UI dan cara memeriksanya (format Tabel 2 modul). */
data class RequirementLatihan(val keputusanUi: String, val caraMemeriksa: String)

/**
 * Dua requirement, salah satunya wajib berkaitan dengan perubahan ukuran jendela.
 * Requirement pertama menguji hierarki kartu; requirement kedua menguji layout adaptif.
 */
val duaRequirementLatihan: List<RequirementLatihan> = listOf(
    RequirementLatihan(
        keputusanUi = "Letakkan nama laboratorium dengan titleLarge sebelum lokasi dan fasilitas " +
            "yang memakai bodyMedium",
        caraMemeriksa = "Minta teman menemukan laboratorium yang disebut namanya dalam lima detik"
    ),
    RequirementLatihan(
        keputusanUi = "Simpan id laboratorium terpilih di atas percabangan layout, dan tampilkan " +
            "panel daftar serta panel detail bersamaan pada lebar ≥ 840 dp dan tinggi ≥ 480 dp",
        caraMemeriksa = "Pilih satu laboratorium, putar jendela ke landscape dan kembali, " +
            "lalu periksa laboratorium yang sama tetap terpilih"
    )
)

/** True bila setidaknya satu requirement menyangkut perubahan ukuran jendela. */
fun adaRequirementTentangUkuranJendela(daftar: List<RequirementLatihan>): Boolean =
    daftar.any { it.keputusanUi.contains("lebar", ignoreCase = true) }

// ---------------------------------------------------------------------------
// Langkah 2 — Sketsa
// ---------------------------------------------------------------------------

/** Sketsa 1: daftar pada jendela sempit. Nomor 1, 2, 3 = urutan perhatian pada kartu. */
val SKETSA_DAFTAR_SEMPIT: String = """
    ┌────────────────────────┐
    │ Campus Labs            │
    ├────────────────────────┤
    │ 1 Lab Jaringan         │
    │   2 Gedung F, Lt. 3    │
    │     3 View details →   │
    ├────────────────────────┤
    │ Lab Multimedia         │
    │   Gedung E, Lt. 1      │
    │     View details →     │
    └────────────────────────┘
""".trimIndent()

/** Sketsa 2: detail pada jendela sempit, dengan jalan kembali yang eksplisit. */
val SKETSA_DETAIL_SEMPIT: String = """
    ┌────────────────────────┐
    │ ← Back to labs         │
    │ Lab Jaringan           │
    │ Gedung F, Lt. 3        │
    │ 3 Fasilitas: 24 PC,    │
    │   switch, router       │
    └────────────────────────┘
""".trimIndent()

/** Sketsa 3: daftar dan detail pada jendela lebar. */
val SKETSA_LEBAR: String = """
    ┌──────────────────────────────────────────┐
    │ Campus Labs                              │
    ├───────────────────┬──────────────────────┤
    │ 1 Lab Jaringan    │ Lab details          │
    │   2 Gd. F, Lt. 3  │ Lab Jaringan         │
    ├───────────────────┤ Gedung F, Lt. 3      │
    │ Lab Multimedia    │ 24 PC, switch, router│
    └───────────────────┴──────────────────────┘
""".trimIndent()

/** Tiga sketsa yang harus dikumpulkan pada Langkah 2. */
fun semuaSketsaLengkap(): Boolean =
    listOf(SKETSA_DAFTAR_SEMPIT, SKETSA_DETAIL_SEMPIT, SKETSA_LEBAR).all { it.isNotBlank() }

// ---------------------------------------------------------------------------
// Langkah 3 — Mengubah data dan kartu
// ---------------------------------------------------------------------------

/**
 * Pengganti data class Course untuk tema laboratorium. Nama class sengaja berbeda dari Course
 * supaya kedua katalog dapat hidup di satu source set.
 */
data class LabItem(
    val id: String,
    val name: String,
    val location: String,
    val description: String
)

/**
 * Lima item (modul meminta minimal lima), satu di antaranya bernama panjang untuk menguji
 * pembungkusan teks saat ukuran font sistem diperbesar.
 */
val sampleLabItems: List<LabItem> = listOf(
    LabItem(
        id = "jaringan",
        name = "Lab Jaringan Komputer",
        location = "Gedung F, Lantai 3",
        description = "24 PC, switch manageable, dan router untuk praktikum jaringan. " +
            "Dipakai juga untuk kelas administrasi server."
    ),
    LabItem(
        id = "multimedia",
        name = "Lab Multimedia",
        location = "Gedung E, Lantai 1",
        description = "Workstation grafis, pen tablet, dan scanner untuk praktikum desain " +
            "antarmuka serta pengolahan citra."
    ),
    LabItem(
        id = "ai",
        name = "Laboratorium Kecerdasan Artifisial dan Pembelajaran Mesin Terapan",
        location = "Gedung F, Lantai 4",
        description = "GPU workstation untuk pelatihan model. Judul panjang ini sengaja dipakai " +
            "untuk memeriksa pembungkusan teks pada kartu."
    ),
    LabItem(
        id = "basisdata",
        name = "Lab Basis Data",
        location = "Gedung E, Lantai 2",
        description = "Server latihan, PC klien, dan proyektor untuk praktikum perancangan " +
            "basis data serta pemrograman SQL."
    ),
    LabItem(
        id = "mobile",
        name = "Lab Pengembangan Aplikasi Bergerak",
        location = "Gedung E, Lantai 3",
        description = "Emulator dan perangkat uji Android untuk praktikum Compose, pengujian " +
            "perilaku aplikasi, dan demo proyek akhir."
    )
)

/** Teks antarmuka tema laboratorium. */
object TeksKatalogLab {
    const val JUDUL = "Campus Labs"
    const val DETAIL = "Lab details"
    const val PILIH = "Select a lab to read its location and facilities."
    const val KEMBALI = "Back to labs"
    const val LIHAT = "View details"
    const val TERPILIH = "Selected lab"

    fun labelTombol(nama: String, terpilih: Boolean): String =
        if (terpilih) "Selected lab: $nama" else "View details for $nama"
}

/**
 * Akar layar latihan. Ukuran jendela diserahkan sebagai Boolean, sama seperti CourseBrowser,
 * sehingga tiga pengujian Langkah 4 dapat dilakukan lewat preview.
 */
@Composable
fun KatalogAdaptifApp(twoPanes: Boolean = false) {
    KatalogAdaptifScreen(showTwoPanes = twoPanes)
}

/**
 * Layar katalog adaptif. Perhatikan bagian yang TIDAK boleh diubah: `rememberSaveable` berada di
 * ATAS percabangan layout, dan daftar/detail yang sama dipakai kedua cabang.
 */
@Composable
fun KatalogAdaptifScreen(showTwoPanes: Boolean) {
    var selectedId by rememberSaveable { mutableStateOf<String?>(null) }
    val selected = sampleLabItems.firstOrNull { it.id == selectedId }

    BackHandler(enabled = selectedId != null) { selectedId = null }

    Scaffold { innerPadding ->
        Row(
            Modifier.fillMaxSize().padding(innerPadding),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                Modifier
                    .widthIn(max = 1200.dp)
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    TeksKatalogLab.JUDUL,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.semantics { heading() }
                )
                if (showTwoPanes) {
                    Row(
                        Modifier.fillMaxWidth().weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        LabList(
                            selectedId = selectedId,
                            onSelect = { selectedId = it },
                            modifier = Modifier.weight(1f).fillMaxSize()
                        )
                        LabDetails(
                            lab = selected,
                            showBack = false,
                            onBack = { selectedId = null },
                            modifier = Modifier.weight(1.4f).fillMaxSize()
                        )
                    }
                } else if (selected != null) {
                    LabDetails(
                        lab = selected,
                        showBack = true,
                        onBack = { selectedId = null },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                } else {
                    LabList(
                        selectedId = selectedId,
                        onSelect = { selectedId = it },
                        modifier = Modifier.fillMaxWidth().weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun LabList(
    selectedId: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(sampleLabItems, key = { it.id }) { lab ->
            LabCard(
                lab = lab,
                isSelected = lab.id == selectedId,
                onOpen = { onSelect(lab.id) }
            )
        }
    }
}

/**
 * Kartu tema laboratorium: urutan 1 nama (titleLarge), 2 lokasi (bodyMedium), 3 aksi.
 * Tinggi mengikuti isi — tanpa tinggi tetap dan tanpa maxLines — supaya nama panjang tetap
 * terbaca setelah ukuran font diperbesar.
 */
@Composable
private fun LabCard(lab: LabItem, isSelected: Boolean, onOpen: () -> Unit) {
    Card(Modifier.fillMaxWidth()) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(lab.name, style = MaterialTheme.typography.titleLarge)
            Text(lab.location, style = MaterialTheme.typography.bodyMedium)
            Button(
                onClick = onOpen,
                modifier = Modifier.semantics {
                    selected = isSelected
                    contentDescription = TeksKatalogLab.labelTombol(lab.name, isSelected)
                }
            ) {
                Text(if (isSelected) TeksKatalogLab.TERPILIH else TeksKatalogLab.LIHAT)
            }
        }
    }
}

@Composable
private fun LabDetails(
    lab: LabItem?,
    showBack: Boolean,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    key(lab?.id) {
        Column(
            modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (showBack) {
                TextButton(onClick = onBack) { Text(TeksKatalogLab.KEMBALI) }
            }
            Text(
                TeksKatalogLab.DETAIL,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.semantics { heading() }
            )
            if (lab == null) {
                Text(TeksKatalogLab.PILIH)
            } else {
                Text(lab.name, style = MaterialTheme.typography.headlineSmall)
                Text(lab.location, style = MaterialTheme.typography.bodyLarge)
                Text(lab.description, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 800, showBackground = true)
@Composable
private fun KatalogCompactPreview() {
    CampusCourseBrowserTheme { KatalogAdaptifScreen(showTwoPanes = false) }
}

@Preview(widthDp = 1000, heightDp = 800, showBackground = true)
@Composable
private fun KatalogWidePreview() {
    CampusCourseBrowserTheme { KatalogAdaptifScreen(showTwoPanes = true) }
}

@Preview(widthDp = 360, heightDp = 800, fontScale = 1.5f, showBackground = true)
@Composable
private fun KatalogFontBesarPreview() {
    CampusCourseBrowserTheme { KatalogAdaptifScreen(showTwoPanes = false) }
}

// ---------------------------------------------------------------------------
// Langkah 4 — Menguji
// ---------------------------------------------------------------------------

/** Satu pengujian Langkah 4 beserta bukti yang harus dicatat. */
data class PengujianLatihan(val pengujian: String, val buktiDicari: String)

val tigaPengujianLatihan: List<PengujianLatihan> = listOf(
    PengujianLatihan(
        "Preview compact dan preview lebar",
        "Urutan informasi pada kartu sesuai sketsa: nama, lokasi, aksi"
    ),
    PengujianLatihan(
        "Preview dengan fontScale = 1.5f",
        "Nama lab yang panjang tetap terbaca utuh dan tombol tetap terlihat"
    ),
    PengujianLatihan(
        "Jalankan di emulator, pilih satu item, lalu putar ke landscape",
        "Item yang sama tetap terpilih; tampilan tetap satu panel karena tinggi " +
            "landscape di bawah 480 dp"
    )
)

/**
 * Harapan layout untuk ukuran jendela tertentu. Pada ponsel landscape (mis. 800 x 360 dp) lebar
 * cukup tetapi tinggi di bawah 480 dp, sehingga kebijakan modul tetap memakai satu panel — yang
 * diuji pada Langkah 4 adalah PILIHAN yang bertahan, bukan jumlah panel.
 */
fun harapanPanelLatihan(lebarDp: Int, tinggiDp: Int): String =
    if (kebijakanDuaPanel(lebarDp, tinggiDp)) "dua panel" else "satu panel"

/** Id item yang harus tetap terpilih setelah jendela berubah ukuran (state di atas cabang). */
fun itemTetapTerpilihSetelahResize(idSebelum: String?, idSesudah: String?): Boolean =
    idSebelum == idSesudah

// ---------------------------------------------------------------------------
// Langkah 5 — Lembar jawaban
// ---------------------------------------------------------------------------

/**
 * Ringkasan lembar jawaban: user story, dua requirement, dan hasil tiga pengujian. Sketsa dan
 * tangkapan layar dilampirkan terpisah; yang dicetak di sini hanya teks yang dapat diperiksa.
 */
fun ringkasanLembarJawaban(): String = buildString {
    appendLine("User story:")
    appendLine("  $USER_STORY_LATIHAN")
    appendLine("Dua requirement:")
    duaRequirementLatihan.forEachIndexed { i, r ->
        appendLine("  ${i + 1}. ${r.keputusanUi}")
        appendLine("     Cara memeriksa: ${r.caraMemeriksa}")
    }
    appendLine("Tiga pengujian:")
    tigaPengujianLatihan.forEachIndexed { i, p ->
        appendLine("  ${i + 1}. ${p.pengujian} → ${p.buktiDicari}")
    }
    appendLine("Kalimat penutup:")
    appendLine(
        "  Bagian desain yang paling membantu adalah penanda pilihan yang tetap bertahan saat " +
            "jendela berubah ukuran; buktinya, setelah rotasi ke landscape item yang sama tetap " +
            "terpilih dan tugas \"buka detail lalu kembali\" selesai tanpa bantuan."
    )
}

/** Latihan 6 modul 10 memakai pola serupa: satu tombol menjalankan seluruh pemeriksaan. */
fun latihanJalankanVerifikasi(): Boolean = jalankanVerifikasiModul11()
