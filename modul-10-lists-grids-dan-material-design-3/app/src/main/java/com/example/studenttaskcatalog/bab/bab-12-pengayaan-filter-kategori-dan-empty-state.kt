package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studenttaskcatalog.StudentTask
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.taskCategories
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 12 (Bagian 12.4–12.5) — Pengayaan: Filter Kategori dan Empty State
 *
 * Bagian ini OPSIONAL. Strip kategori dapat dibuat interaktif dengan menyimpan kategori
 * terpilih sebagai state, lalu menurunkan daftar yang tampil darinya. Chip yang dipakai
 * adalah FilterChip, karena ia memiliki tampilan terpilih bawaan.
 *
 *   var selectedCategory by rememberSaveable { mutableStateOf("All") }
 *   val visibleTasks =
 *       if (selectedCategory == "All") sampleTasks
 *       else sampleTasks.filter { it.category == selectedCategory }
 *
 * Bila tidak ada tugas yang cocok dengan kategori terpilih, layar sebaiknya menampilkan EMPTY
 * STATE berupa pesan singkat, bukan area kosong yang membingungkan. Kategori "Lab" ditambahkan
 * di sini agar empty state dapat terlihat, karena tidak ada data contoh yang memakainya.
 *
 * Catatan: versi Material 3 lama menandai FilterChip sebagai eksperimental; pada versi terbaru
 * anotasi opt-in berikut tidak berpengaruh.
 */

/** Strip kategori yang dapat dipilih; kategori terpilih ditandai FilterChip(selected = true). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryFilterStrip(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories, key = { it }) { category ->
            FilterChip(
                selected = category == selectedCategory,
                onClick = { onCategorySelected(category) },
                label = { Text(category) }
            )
        }
    }
}

/** Empty state: pesan singkat di tengah area yang tersisa. */
@Composable
fun EmptyTaskState(category: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "No $category tasks",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Choose another category or select All.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Layar versi pengayaan: filter kategori + empty state + tombol list/grid. */
@Composable
fun StudentTaskCatalogFilterScreen(modifier: Modifier = Modifier) {
    var showGrid by rememberSaveable { mutableStateOf(false) }
    var selectedCategory by rememberSaveable { mutableStateOf("All") }
    val categories = taskCategories + "Lab"

    val visibleTasks =
        if (selectedCategory == "All") sampleTasks
        else sampleTasks.filter { it.category == selectedCategory }

    Column(modifier = modifier.padding(top = 12.dp)) {
        CategoryFilterStrip(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        TaskViewToggleRow(
            jumlah = visibleTasks.size,
            showGrid = showGrid,
            onToggle = { showGrid = !showGrid }
        )
        when {
            visibleTasks.isEmpty() ->
                EmptyTaskState(selectedCategory, Modifier.weight(1f))
            showGrid ->
                TaskGrid(tasks = visibleTasks, modifier = Modifier.weight(1f))
            else ->
                TaskList(tasks = visibleTasks, modifier = Modifier.weight(1f))
        }
    }
}

/** Baris kontrol versi pengayaan: jumlah tugas yang TAMPIL, bukan jumlah seluruh data. */
@Composable
fun TaskViewToggleRow(
    jumlah: Int,
    showGrid: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$jumlah tasks", style = MaterialTheme.typography.titleSmall)
        androidx.compose.material3.OutlinedButton(onClick = onToggle) {
            Text(if (showGrid) "Show list" else "Show grid")
        }
    }
}

/** Menurunkan daftar yang tampil dari kategori terpilih (dipakai juga oleh verifikasi). */
fun tugasTerlihat(semua: List<StudentTask>, kategoriTerpilih: String): List<StudentTask> =
    if (kategoriTerpilih == "All") semua
    else semua.filter { it.category == kategoriTerpilih }

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun StudentTaskCatalogFilterScreenPreview() {
    StudentTaskCatalogTheme { StudentTaskCatalogFilterScreen() }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 320)
@Composable
fun EmptyTaskStatePreview() {
    StudentTaskCatalogTheme { EmptyTaskState(category = "Lab") }
}
