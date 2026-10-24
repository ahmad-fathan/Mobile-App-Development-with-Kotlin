package com.example.campuscoursebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.window.core.layout.WindowSizeClass
import com.example.campuscoursebrowser.bab.CourseBrowser
import com.example.campuscoursebrowser.ui.theme.CampusCourseBrowserTheme

/**
 * Bagian 9.2 dan 10.2 — Membaca informasi jendela di batas aplikasi
 *
 * Ukuran jendela dibaca SATU KALI di dekat akar aplikasi, lalu diubah menjadi satu keputusan
 * layout (Boolean) yang diteruskan ke layar. Layar tidak pernah membaca ukuran jendela sendiri,
 * sehingga ia dapat di-preview dan diuji tanpa mengubah ukuran jendela perangkat.
 *
 * Karena `CourseBrowser` hanya menerima Boolean, preview dapat menyuntikkan kebijakan layout:
 * lihat CompactPreview, WidePreview, dan LargeTextPreview di bab-09.
 *
 * Untuk mencoba contoh bab lain tanpa emulator, ganti pemanggilan `CourseBrowserApp()` dengan
 * composable yang bersangkutan (semuanya punya @Preview):
 *
 *   KartuUserStory()                 bab-02  (Bagian 3)
 *   TabelRequirement()               bab-03  (Bagian 4)
 *   SketsaWireframeSempit()          bab-04  (Bagian 5)
 *   PerbandinganHierarki(...)        bab-05  (Bagian 6)
 *   RingkasanAksesibilitas()         bab-06  (Bagian 7)
 *   TabelKebijakanLayout()           bab-07  (Bagian 8)
 *   GridCourseAdaptif()              bab-08  (Bagian 9.5, pengayaan)
 *   TabelMatriksUji()                bab-10  (Bagian 11)
 *   TabelMasalahUmum()               bab-11  (Bagian 12)
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Pembungkus tema yang dihasilkan proyek, bukan MaterialTheme polos: warna dan
            // tipografi proyek tetap dipakai, dan tema tidak ditumpuk dua kali.
            CampusCourseBrowserTheme {
                CourseBrowserApp()
            }
        }
    }
}

/**
 * Menerjemahkan window size class menjadi satu keputusan layout (Tabel 7 baris ketiga).
 *
 * `true` hanya bila lebar jendela minimal 840 dp DAN tingginya minimal 480 dp. Batasnya diambil
 * dari konstanta library, bukan angka yang ditulis ulang, supaya ikut berubah bila pedoman
 * Android diperbarui.
 *
 * `currentWindowAdaptiveInfoV2` berasal dari paket androidx.compose.material3.adaptive dan
 * nilainya ikut berubah ketika jendela aplikasi berubah. Fungsi V2 mendukung kelas large dan
 * extra-large, serta menggantikan currentWindowAdaptiveInfo() yang deprecated sejak Adaptive
 * 1.3.0. WindowSizeClass di sini adalah androidx.window.core.layout.WindowSizeClass — JANGAN
 * dicampur dengan API lama androidx.compose.material3.windowsizeclass.
 */
fun showTwoPanesUntuk(sizeClass: WindowSizeClass): Boolean =
    sizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND) &&
        sizeClass.isHeightAtLeastBreakpoint(WindowSizeClass.HEIGHT_DP_MEDIUM_LOWER_BOUND)

/**
 * Akar aplikasi: membaca ukuran jendela SATU KALI, mengubahnya menjadi satu keputusan Boolean,
 * lalu menyerahkan keputusan itu ke layar. Dengan begitu layar tidak bergantung pada jendela
 * perangkat dan setiap alternatif layout dapat di-preview.
 */
@Composable
fun CourseBrowserApp() {
    val sizeClass = currentWindowAdaptiveInfoV2().windowSizeClass
    CourseBrowser(showTwoPanes = showTwoPanesUntuk(sizeClass))
}
