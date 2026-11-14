package com.example.tasknav.bab

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
import com.example.tasknav.perbandinganRoute
import com.example.tasknav.pop
import com.example.tasknav.push

/**
 * Bab 2 — Back Stack, Route Type-Safe, dan Menyiapkan Proyek
 *
 * Bagian modul: 4 (Back Stack), 5 (Route Berbasis String dan Route Type-Safe),
 * 6 (Menyiapkan Proyek, 6.1 Dependensi, 6.2 Struktur Berkas).
 *
 * Tabel back stack pada bab ini dihitung oleh fungsi murni di `verifikasi.kt`
 * ([backStackAwal], [push], [pop], [labelBackStack]), jadi angka yang tampil bukan hafalan.
 */

/** Tiga momen pada Gambar 3 modul, dihitung dari model back stack. */
private val daftar = Entri("TaskList")
private val setelahBuka2 = push(backStackAwal(daftar), Entri("TaskDetail", mapOf("taskId" to "2")))
private val setelahBack = pop(setelahBuka2)

@Composable
fun BackStackDanRouteScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Back stack, route type-safe, dan proyek",
            keterangan = "LIFO: entri terakhir masuk adalah yang pertama keluar"
        )

        Tabel(
            "Tabel — isi back stack (bawah → atas)",
            listOf(
                "Aplikasi dibuka" to labelBackStack(backStackAwal(daftar)),
                "Setelah membuka tugas 2" to labelBackStack(setelahBuka2),
                "Setelah Back ditekan" to labelBackStack(setelahBack),
                "Setelah membuka tugas 3" to
                    labelBackStack(push(setelahBack, Entri("TaskDetail", mapOf("taskId" to "3")))),
                "Back pada start destination" to "aplikasi ditutup (stack kosong)"
            )
        )
        Text(
            "Tiga aturan: entri paling bawah adalah start destination; navigate() melakukan push; " +
                "Back melakukan pop dan menutup aplikasi bila hanya tersisa start destination.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Route berbasis string (gaya lama)", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            composable(
                route = "detail/{taskId}",
                arguments = listOf(navArgument("taskId") { type = NavType.IntType })
            ) { entry ->
                val id = entry.arguments?.getInt("taskId") ?: 0
            }

            navController.navigate("detail/${'$'}id")   // string, tidak diperiksa compiler
            """
        )

        Text("Route type-safe (dipakai modul ini)", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            @Serializable
            data class TaskDetail(val taskId: Int)

            composable<TaskDetail> { entry ->
                val route: TaskDetail = entry.toRoute()
                val id = route.taskId
            }

            navController.navigate(TaskDetail(id))
            """
        )
        Tabel(
            "Tabel 2 — perbandingan",
            perbandinganRoute.map { it.aspek to "${it.stringRoute}  →  ${it.typeSafe}" }
        )

        Text("6.1 Dependensi", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            # gradle/libs.versions.toml
            [versions]
            navigationCompose = "2.10.2"

            [libraries.androidx-navigation-compose]
            module = "androidx.navigation:navigation-compose"
            version.ref = "navigationCompose"

            [plugins.kotlin-serialization]
            id = "org.jetbrains.kotlin.plugin.serialization"
            version.ref = "kotlin"
            """
        )
        Text(
            "Navigation 2.10 membutuhkan AGP baru. Bila build gagal karena Android Studio lebih lama, " +
                "turunkan navigation-compose ke 2.9.8. Versi plugin serialization selalu sama dengan versi Kotlin.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("6.2 Struktur berkas", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Tabel 3 — berkas aplikasi TaskNav",
            listOf(
                "data/TaskRepository.kt" to "model Task dan data contoh",
                "navigation/Routes.kt" to "tipe-tipe route",
                "ui/TaskListScreen.kt" to "layar daftar tugas",
                "ui/TaskDetailScreen.kt" to "layar detail satu tugas",
                "navigation/AppNavHost.kt" to "navigation graph",
                "MainActivity.kt" to "titik masuk aplikasi"
            )
        )
        Text(
            "Screen tidak mengetahui keberadaan navigasi; seluruh keputusan navigasi berkumpul di AppNavHost.kt.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackStackDanRoutePreview() {
    com.example.tasknav.ui.theme.TaskNavTheme { BackStackDanRouteScreen() }
}
