package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Bab 4 (Bagian 5) — Membuat Sketsa Alur Tugas
 *
 * Wireframe low-fidelity adalah sketsa kotak dan label tanpa warna atau jenis huruf. Tujuannya
 * memastikan alur tugas benar sebelum memilih komponen. Tiga prinsip yang dipegang:
 *
 *   1. mulai dari informasi dan aksi, baru pilih komponen;
 *   2. buat wireframe low-fidelity lebih dulu;
 *   3. pertahankan tugas pengguna walaupun tampilannya berubah.
 *
 * Pada jendela sempit alurnya berurutan (daftar, detail, Back, daftar). Pada jendela lebar
 * daftar dan detail tampil bersamaan; sebelum ada pilihan, panel detail menampilkan
 * "Select a course".
 */

/** Alur tugas pada jendela sempit: satu panel bergantian. */
fun langkahAlurSempit(): List<String> = listOf(
    "Daftar mata kuliah",
    "Pilih mata kuliah",
    "Detail mata kuliah",
    "Tekan Back",
    "Kembali ke daftar"
)

/** Alur tugas pada jendela lebar: dua panel bersamaan. */
fun langkahAlurLebar(): List<String> = listOf(
    "Panel daftar dan panel detail tampil bersamaan",
    "Panel detail kosong menampilkan \u201cSelect a course\u201d",
    "Pilih mata kuliah: panel detail diperbarui",
    "Pilih mata kuliah lain: panel detail langsung berganti"
)

/** Jumlah layar yang harus dilewati untuk kembali ke daftar (0 = daftar tidak pernah hilang). */
fun jumlahLangkahKembali(duaPanel: Boolean): Int = if (duaPanel) 0 else 1

@Composable
fun SketsaWireframeSempit() {
    Text(
        text = """
            ┌──────────────────────┐
            │ Campus Courses       │
            ├──────────────────────┤
            │ 1 Text Analytics     │
            │   2 View details →   │
            ├──────────────────────┤
            │ Mobile App Dev       │
            │   View details →     │
            └──────────────────────┘
               ↓ pilih nomor 1
            ┌──────────────────────┐
            │ ← Back to courses    │
            │ Text Analytics       │
            │ 3 Deskripsi kuliah   │
            └──────────────────────┘
        """.trimIndent(),
        style = MaterialTheme.typography.bodySmall,
        fontFamily = FontFamily.Monospace
    )
}

@Composable
fun SketsaWireframeLebar() {
    Text(
        text = """
            ┌───────────────────────────────────────────┐
            │ Campus Course Browser                     │
            ├────────────────────┬──────────────────────┤
            │ 1 Text Analytics   │ Course details       │
            │   2 View details →  │ 3 Text Analytics     │
            ├────────────────────┤   Deskripsi kuliah   │
            │ Mobile App Dev     │                      │
            │   View details →   │                      │
            └────────────────────┴──────────────────────┘
        """.trimIndent(),
        style = MaterialTheme.typography.bodySmall,
        fontFamily = FontFamily.Monospace
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 420)
@Composable
private fun SketsaWireframeSempitPreview() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) { SketsaWireframeSempit() }
}

@Preview(showBackground = true, widthDp = 480, heightDp = 240)
@Composable
private fun SketsaWireframeLebarPreview() {
    SketsaWireframeLebar()
}
