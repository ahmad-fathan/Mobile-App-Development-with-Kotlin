package com.example.studentprofile.bab

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 7 (Bagian 7.4) — TopAppBar dan Aksesibilitas
 *
 * TopAppBar menampilkan judul layar di bagian atas. Isinya diberikan melalui SLOT,
 * yaitu parameter yang menerima composable: title (wajib), serta navigationIcon
 * dan actions yang opsional. Contoh ini hanya memakai title.
 *
 * Anotasi @OptIn(ExperimentalMaterial3Api::class) diperlukan karena versi Material 3
 * yang lebih lama menandai TopAppBar sebagai API eksperimental; tanpa anotasi itu
 * proyek gagal dikompilasi. Pada versi yang lebih baru anotasi ini tidak diperlukan
 * tetapi juga tidak menimbulkan masalah, sehingga aman dipertahankan.
 *
 * 7.5 — tiga kebiasaan aksesibilitas:
 *   - teks terbaca: tipografi tema memakai sp dan pasangan warna "on" menjaga kontras;
 *   - area sentuh cukup besar: tombol dan kolom input Material 3 menjaga minimum
 *     48 dp, jadi jangan diperkecil di bawah itu;
 *   - gambar bermakna diberi contentDescription (mis. "Profile picture of Budi
 *     Santoso"); nilai null hanya untuk gambar dekoratif.
 *
 * Avatar pada bab berikutnya berupa teks inisial, sehingga sudah dapat dibacakan
 * pembaca layar seperti TalkBack.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    TopAppBar(
        title = { Text("Student Profile") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileTopBarPreview() {
    StudentProfileTheme {
        ProfileTopBar()
    }
}
