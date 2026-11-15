package com.example.tasknav.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasknav.Entri
import com.example.tasknav.JudulBab
import com.example.tasknav.KodeBlok
import com.example.tasknav.Tabel
import com.example.tasknav.backStackAwal
import com.example.tasknav.labelBackStack
import com.example.tasknav.pop
import com.example.tasknav.push
import com.example.tasknav.ui.theme.TaskNavTheme
import kotlinx.serialization.Serializable

/**
 * Latihan akhir Modul 15 — Navigation with Jetpack Compose.
 *
 * Enam soal modul (Bagian "Latihan"). Soal 3, 4, dan 6 sudah diimplementasikan di aplikasi
 * (`ui/AboutScreen.kt`, `ui/CourseTasksScreen.kt`, `ui/WelcomeScreen.kt` + `navigation/AppNavHost.kt`);
 * soal 1, 2, dan 5 dijawab di berkas ini sebagai deklarasi dan perhitungan yang ikut dikompilasi,
 * bukan sebagai teks hafalan.
 */

// ---------------------------------------------------------------------------
// Latihan 1 — bentuk route setiap layar
// ---------------------------------------------------------------------------

/** (a) Halaman About: tidak butuh data → `object`. */
@Serializable
object AboutRoute

/** (b) Profil mahasiswa berdasarkan NIM: butuh satu nilai → `data class` dengan satu properti. */
@Serializable
data class StudentProfile(val nim: String)

/** (c) Hasil pencarian: kata kunci boleh kosong → properti bertipe nullable dengan nilai default. */
@Serializable
data class SearchResults(val query: String? = null)

// ---------------------------------------------------------------------------
// Latihan 2 — isi back stack setelah urutan aksi
// ---------------------------------------------------------------------------

/**
 * Aplikasi dibuka → tugas 1 dibuka → Back → tugas 2 dibuka → tugas 3 dibuka lewat tautan.
 *
 * Hasil: `TaskList → TaskDetail(2) → TaskDetail(3)`.
 * Back sekali: kembali ke Detail(2). Back kedua: kembali ke TaskList.
 * Detail(1) sudah dibuang saat Back pertama, jadi tidak pernah muncul lagi.
 */
fun latihan2BackStack(): List<Entri> {
    val daftar = backStackAwal(Entri("TaskList"))
    var stack = push(daftar, Entri("TaskDetail", mapOf("taskId" to "1")))
    stack = pop(stack)
    stack = push(stack, Entri("TaskDetail", mapOf("taskId" to "2")))
    stack = push(stack, Entri("TaskDetail", mapOf("taskId" to "3")))
    return stack
}

fun latihan2Jawaban(): Triple<String, String, String> {
    val stack = latihan2BackStack()
    return Triple(
        labelBackStack(stack),
        labelBackStack(stack.dropLast(1)),
        labelBackStack(stack.dropLast(2))
    )
}

// ---------------------------------------------------------------------------
// Latihan 5 — navigate() di badan composable
// ---------------------------------------------------------------------------

/**
 * Teman menulis `navController.navigate(TaskDetail(taskId = 1))` langsung di badan composable
 * `TaskListScreen`. Akibatnya, pemanggilan itu berjalan pada SETIAP composition dan recomposition,
 * sehingga Detail(1) bisa ditumpuk lebih dari sekali. Lebih parah lagi, setiap kali pengguna menekan
 * Back dan TaskList tampil kembali, badan composable dijalankan ulang dan Detail(1) langsung terbuka
 * lagi: pengguna terjebak dan tidak pernah bisa kembali ke daftar.
 *
 * Perbaikan: pindahkan pemanggilan ke dalam callback event (mis. tombol "Open first task").
 * `LaunchedEffect(Unit)` saja TIDAK cukup, karena destination TaskList keluar dari composition saat
 * Detail tampil, lalu efeknya berjalan lagi ketika pengguna kembali. Bila memang harus terbuka
 * otomatis sekali, simpan penanda dengan `rememberSaveable` dan periksa sebelum memanggil navigate().
 */
fun latihan5Penjelasan(): Pair<String, String> =
    "navigate() di badan composable berjalan setiap (re)composition → Detail(1) ditumpuk, " +
        "dan setiap kembali ke daftar Detail(1) terbuka lagi sehingga pengguna terjebak" to
        "Panggil navigate() hanya di lambda event; bila harus otomatis sekali, " +
        "pakai penanda rememberSaveable (LaunchedEffect(Unit) saja tetap terulang)"

// ---------------------------------------------------------------------------
// Layar ringkasan jawaban
// ---------------------------------------------------------------------------

@Composable
fun LatihanScreen(modifier: Modifier = Modifier) {
    val (isiStack, setelahSatuBack, setelahDuaBack) = latihan2Jawaban()
    val (masalah, perbaikan) = latihan5Penjelasan()

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Latihan akhir modul",
            keterangan = "Soal 3, 4, dan 6 sudah berupa layar di aplikasi"
        )

        Text("1. Bentuk route", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            @Serializable object AboutRoute                          // (a) tanpa data
            @Serializable data class StudentProfile(val nim: String)  // (b) butuh NIM
            @Serializable data class SearchResults(val query: String? = null)  // (c) opsional
            """
        )

        Text("2. Isi back stack", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Urutan: buka tugas 1 → Back → buka 2 → buka 3",
            listOf(
                "Setelah urutan aksi" to isiStack,
                "Back sekali" to setelahSatuBack,
                "Back kedua" to setelahDuaBack
            )
        )

        Text("3, 4, 6. Layar yang ditambahkan", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Berkas dan route",
            listOf(
                "ui/AboutScreen.kt" to "@Serializable object About — dibuka dari tombol About di top app bar TaskList",
                "ui/CourseTasksScreen.kt" to "@Serializable data class CourseTasks(val course: String) — dari tombol di layar detail; judul contoh \"Databases (1 task)\"",
                "ui/WelcomeScreen.kt" to "@Serializable object Welcome — start destination; Get started memakai popUpTo<Welcome> inclusive"
            )
        )

        Text("5. navigate() di badan composable", style = MaterialTheme.typography.titleSmall)
        Tabel("Gejala dan perbaikan", listOf("Gejala" to masalah, "Perbaikan" to perbaikan))
    }
}

@Preview(showBackground = true)
@Composable
fun LatihanPreview() {
    TaskNavTheme { LatihanScreen() }
}
