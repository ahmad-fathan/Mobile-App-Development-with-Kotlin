package com.example.studenttaskcatalog.bab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.studenttaskcatalog.sampleTasks
import com.example.studenttaskcatalog.ui.theme.StudentTaskCatalogTheme

/**
 * Bab 10 (Bagian 11) — Beralih antara List dan Grid
 *
 * Kita sudah memiliki TaskList (Bab 4) dan TaskGrid (Bab 7). Untuk beralih di antara
 * keduanya, kita memakai pola state dan event yang sudah dikenal: satu nilai Boolean dan satu
 * tombol.
 *
 *   var showGrid by rememberSaveable { mutableStateOf(false) }
 *   OutlinedButton(onClick = { showGrid = !showGrid }) {
 *       Text(if (showGrid) "Show list" else "Show grid")
 *   }
 *
 * rememberSaveable menjaga pilihan tampilan ketika layar diputar. Ia diimpor dari
 * androidx.compose.runtime.saveable.rememberSaveable, sedangkan mutableStateOf, getValue, dan
 * setValue dari androidx.compose.runtime. Delegasi by membutuhkan getValue dan setValue.
 *
 * Hal baru yang perlu diperhatikan adalah hasilnya: if hanya memilih KONTAINER mana yang
 * ditampilkan, tetapi keduanya menerima sampleTasks yang sama dan keduanya menggambar
 * TaskCard yang sama. DATA dan DESAIN ITEM dipakai bersama; yang berbeda hanyalah susunannya.
 * Modifier.weight(1f) muncul karena kode ini berada di dalam Column milik layar (Bab 11).
 */

/** Baris kontrol: jumlah tugas + tombol pengalih bentuk tampilan. */
@Composable
fun TaskViewToggle(showGrid: Boolean, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${sampleTasks.size} tasks",
            style = MaterialTheme.typography.titleSmall
        )
        OutlinedButton(onClick = onToggle) {
            Text(if (showGrid) "Show list" else "Show grid")
        }
    }
}

/** Contoh mandiri: tombol menukar antara daftar dan grid dari data yang sama. */
@Composable
fun ContohBeralihListDanGrid(modifier: Modifier = Modifier) {
    var showGrid by rememberSaveable { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize()) {
        TaskViewToggle(showGrid = showGrid, onToggle = { showGrid = !showGrid })
        // weight(1f) memberi lazy layout sisa tinggi layar, sehingga tidak pernah diukur
        // dengan tinggi tak terbatas (Bab 12.1).
        if (showGrid) {
            TaskGrid(tasks = sampleTasks, modifier = Modifier.weight(1f))
        } else {
            TaskList(tasks = sampleTasks, modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 640)
@Composable
fun ContohBeralihListDanGridPreview() {
    StudentTaskCatalogTheme { ContohBeralihListDanGrid() }
}
