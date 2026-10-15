package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.StudentTask
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 7 (Bagian 8) — LazyVerticalGrid
 *
 * LazyVerticalGrid menyusun item dalam beberapa kolom dan menggulir secara vertikal. DSL-nya
 * kembali sama, dan TaskCard dapat dipakai TANPA perubahan apa pun. Satu-satunya parameter
 * baru adalah columns, yang menentukan cara lebar layar dibagi menjadi sel.
 *
 * KESALAHAN IMPORT PALING SERING SAAT PRAKTIKUM — grid memiliki items sendiri yang berasal
 * dari paket berbeda:
 *
 *   Kontainer                    Import untuk items
 *   LazyColumn, LazyRow          androidx.compose.foundation.lazy.items
 *   LazyVerticalGrid             androidx.compose.foundation.lazy.grid.items
 *
 * Kedua import boleh berada di satu berkas yang sama: keduanya extension function untuk
 * receiver yang berbeda (LazyListScope dan LazyGridScope), sehingga Kotlin memilih fungsi
 * yang tepat sesuai kontainer tempat items dipanggil. LazyVerticalGrid dan GridCells sendiri
 * diimpor dari androidx.compose.foundation.lazy.grid.
 *
 * 8.1 GridCells.Fixed dan GridCells.Adaptive
 *
 *   - GridCells.Fixed(2) selalu menghasilkan tepat dua kolom. Pada layar lebar, sel hanya
 *     bertambah lebar.
 *   - GridCells.Adaptive(minSize = 160.dp) menghitung berapa sel dengan lebar minimal 160 dp
 *     yang muat, lalu membagi sisa lebar secara merata ke semua sel.
 *
 * Karena sisa lebar dibagikan ke semua sel, sel Adaptive biasanya lebih lebar daripada
 * minSize. Contoh: dengan contentPadding 16 dp dan jarak 12 dp (Bagian 8.2), ponsel selebar
 * 412 dp menyisakan 380 dp untuk grid. (380 + 12) dibagi (160 + 12) = sekitar 2,27, sehingga
 * grid memiliki 2 kolom dengan lebar masing-masing 184 dp. Jendela 840 dp menghasilkan
 * 4 kolom, sedangkan ponsel kecil 360 dp hanya mendapat 1 kolom. JUMLAH KOLOM DITENTUKAN OLEH
 * LEBAR YANG DITERIMA GRID, bukan oleh orientasi: mode split screen atau tablet dapat
 * mengubah lebar tanpa rotasi.
 *
 * 8.2 Jarak dan contentPadding pada Grid
 *
 * Grid memiliki dua jenis jarak: antarkolom (horizontalArrangement) dan antarbaris
 * (verticalArrangement). Gunakan nilai yang sama dengan daftar vertikal agar peralihan list
 * dan grid terasa konsisten. Ingat pula bahwa padding dan jarak ini ikut masuk ke perhitungan
 * kolom Adaptive, sehingga jarak yang lebih besar dapat mengurangi satu kolom pada layar
 * sempit.
 */

/** Versi paling sederhana: tepat dua kolom, kartu yang sama. */
@Composable
fun ContohGridDuaKolom(modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Perhatikan import: items di sini berasal dari androidx.compose.foundation.lazy.grid.items.
        items(sampleTasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

/** Versi adaptif: jumlah kolom mengikuti lebar yang tersedia. */
@Composable
fun TaskGrid(tasks: List<StudentTask>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tasks, key = { it.id }) { task ->
            TaskCard(task)
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun ContohGridDuaKolomPreview() {
    StudentTaskCatalogTheme { ContohGridDuaKolom() }
}

// Tahap 7 Bagian 12.3: periksa jumlah kolom pada layar sempit dan lebar.
@Preview(showBackground = true, widthDp = 600, heightDp = 640)
@Composable
fun TaskGridWidePreview() {
    StudentTaskCatalogTheme { TaskGrid(tasks = sampleTasks) }
}
