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
import com.example.tasknav.kesalahanUmumNavigasi
import com.example.tasknav.labelBackStack
import com.example.tasknav.opsiBackStack
import com.example.tasknav.perbedaanNavigation3
import com.example.tasknav.push
import com.example.tasknav.pushDenganPopUpTo
import com.example.tasknav.pushSingleTop
import com.example.tasknav.skenarioUji

/**
 * Bab 5 — Aplikasi Lengkap, Back Stack, Pengujian, Kesalahan Umum, dan Navigation 3
 *
 * Bagian modul: 9 (Aplikasi Latihan: TaskNav), 10 (Mengendalikan Back Stack, termasuk
 * Gambar 5), 11 (Menguji Aplikasi), 12 (Kesalahan Umum), 13 (Navigation 3).
 *
 * Isi back stack di bawah dihitung dari fungsi murni di `verifikasi.kt`, bukan ditulis manual.
 */

private val welcome = backStackAwal(Entri("Welcome"))
private val daftarTugas = Entri("TaskList")

@Composable
fun AplikasiDanKesalahanScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Aplikasi lengkap, back stack, pengujian, dan kesalahan umum",
            keterangan = "Bagian 9–13 modul"
        )

        Text("9 Aplikasi latihan TaskNav", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            setContent {
                TaskNavTheme {
                    AppNavHost()      // NavHost memegang NavController
                }
            }

            // data/TaskRepository.kt — tiga tugas di memori
            Task(1, "Read Navigation docs", "Mobile App Dev", "16 Nov")
            Task(2, "Submit lab report",   "Computer Networks", "18 Nov")
            Task(3, "Quiz: SQL joins",     "Databases", "20 Nov", isDone = true)
            """
        )
        Text(
            "Aplikasi di repo ini juga menyertakan layar Welcome (Latihan 6), About (Latihan 3), " +
                "dan CourseTasks (Latihan 4) supaya keenam latihan modul dapat dicoba di emulator.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("10 Mengendalikan back stack", style = MaterialTheme.typography.titleSmall)
        KodeBlok(
            """
            navController.navigate(TaskList) {
                popUpTo<Welcome> { inclusive = true }     // buang Welcome dari back stack
                launchSingleTop = true                      // cegah salinan ganda di atas
            }
            """
        )
        Tabel("Tabel 6 — opsi pengendali back stack", opsiBackStack.map { it.opsi to it.efek })
        Tabel(
            "Gambar 5 — isi back stack setelah \"Get started\" (bawah → atas)",
            listOf(
                "Tanpa popUpTo" to labelBackStack(push(welcome, daftarTugas)),
                "popUpTo tanpa inclusive" to
                    labelBackStack(pushDenganPopUpTo(welcome, daftarTugas, "Welcome", inclusive = false)),
                "popUpTo + inclusive = true" to
                    labelBackStack(pushDenganPopUpTo(welcome, daftarTugas, "Welcome", inclusive = true)),
                "Ketukan ganda dengan launchSingleTop" to
                    labelBackStack(
                        pushSingleTop(
                            pushDenganPopUpTo(welcome, daftarTugas, "Welcome", inclusive = true),
                            daftarTugas
                        )
                    )
            )
        )

        Text("11 Menguji aplikasi", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Tabel 7 — skenario pengujian",
            skenarioUji.map { "${it.nomor}. ${it.langkah}" to it.harapan }
        )

        Text("12 Kesalahan umum dan perbaikannya", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Tabel 8 — lima kesalahan",
            kesalahanUmumNavigasi.map { it.kasus to "${it.gejala} → ${it.perbaikan}" }
        )

        Text("13 Langkah selanjutnya: Navigation 3", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Perbedaan Nav2 (Navigation Compose) dan Navigation 3",
            perbedaanNavigation3.map { it.aspek to "${it.navCompose}  →  ${it.nav3}" }
        )
        Text(
            "Konsep modul ini tetap berlaku di Navigation 3: route sebagai tipe, argumen berupa ID, " +
                "dan cara berpikir dengan back stack. Navigation Compose masih didukung penuh.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AplikasiDanKesalahanPreview() {
    com.example.tasknav.ui.theme.TaskNavTheme { AplikasiDanKesalahanScreen() }
}
