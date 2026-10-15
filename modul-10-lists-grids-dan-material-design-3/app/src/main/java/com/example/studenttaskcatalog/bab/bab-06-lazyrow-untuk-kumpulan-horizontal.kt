package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.taskCategories
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 6 (Bagian 7) — LazyRow untuk Kumpulan Horizontal
 *
 * LazyRow adalah pasangan horizontal dari LazyColumn. DSL-nya sama, items dan key juga sama,
 * tetapi ia menggulir ke samping dan memakai horizontalArrangement untuk jarak antaritem.
 * Kita memakainya untuk strip kategori di bagian atas layar; key di sini adalah NAMA
 * kategori itu sendiri, karena setiap nama kategori unik.
 *
 * SuggestionChip adalah chip Material 3 untuk menampilkan pilihan, dan onClick-nya sengaja
 * dikosongkan. Bila nanti kita ingin menandai kategori yang sedang dipilih, chip yang tepat
 * adalah FilterChip dengan parameter selected, karena tampilan terpilihnya sudah disediakan
 * (versi itu ada pada Bab 12).
 *
 * Pada ponsel nyata, empat chip pendek mungkin muat seluruhnya sehingga strip belum perlu
 * digulir. Strip baru menggulir bila isinya lebih lebar daripada layar.
 *
 *   Aspek              LazyColumn            LazyRow
 *   Arah gulir         vertikal              horizontal
 *   Jarak antaritem    verticalArrangement   horizontalArrangement
 *   Isi yang umum      feed, tugas, pesan    kategori, rekomendasi, item terbaru
 *   Dalam aplikasi ini daftar tugas          strip kategori
 */

/** Strip kategori: satu SuggestionChip per kategori, digulir ke samping. */
@Composable
fun CategoryStrip(categories: List<String>, modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories, key = { it }) { category ->
            SuggestionChip(onClick = {}, label = { Text(category) })
        }
    }
}

/** LazyRow berisi teks: bentuk paling sederhana dari daftar horizontal. */
@Composable
fun ContohLazyRowTeks(modifier: Modifier = Modifier) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(taskCategories, key = { it }) { kategori ->
            Text(
                text = kategori,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryStripPreview() {
    StudentTaskCatalogTheme { CategoryStrip(categories = taskCategories) }
}

@Preview(showBackground = true)
@Composable
fun ContohLazyRowTeksPreview() {
    StudentTaskCatalogTheme { ContohLazyRowTeks() }
}
