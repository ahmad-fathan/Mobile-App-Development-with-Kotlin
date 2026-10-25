package com.example.tasks.bab

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
import com.example.tasks.JudulBab
import com.example.tasks.KodeBlok
import com.example.tasks.Tabel
import com.example.tasks.berkasProyekLatihan
import com.example.tasks.dependensiLatihan
import com.example.tasks.kesalahanUmumState
import com.example.tasks.langkahPraktikTerbimbing
import com.example.tasks.tanyaJawabState

/**
 * Bab 5 — Kesalahan Umum, Setelan Proyek, Praktik, dan Pemahaman
 *
 * Bagian modul: "Common Mistakes and Corrections", "Set Up the Practice Project",
 * "Guided Practice", "Independent Practice", "Check Your Understanding".
 *
 * Tabel-tabel di berkas ini dipakai ulang dari verifikasi.kt, sehingga tabel yang tampil di layar
 * dan tabel yang diperiksa tidak dapat berbeda diam-diam.
 */

@Composable
fun KesalahanUmumScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Kesalahan umum dan langkah praktik",
            keterangan = "Badan Composable dapat berjalan berulang: menggambar tidak boleh mengubah data."
        )

        Tabel(
            "Kesalahan umum dan perbaikannya",
            kesalahanUmumState.map { it.kesalahan to it.perbaikan }
        )

        Text("Setelan proyek latihan", style = MaterialTheme.typography.titleSmall)
        Tabel("Berkas di satu paket", berkasProyekLatihan.map { it.first to it.second })
        Tabel("Dependensi yang dibutuhkan", dependensiLatihan.map { it to "" })
        KodeBlok(
            """
            // gradle/libs.versions.toml
            androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle",
                name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
            androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle",
                name = "lifecycle-runtime-compose", version.ref = "lifecycleRuntimeKtx" }
            kotlinx-coroutines-android = { group = "org.jetbrains.kotlinx",
                name = "kotlinx-coroutines-android", version.ref = "coroutines" }

            // app/build.gradle.kts
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.coroutines.android)
            // Compose BOM tidak memilih versi Lifecycle maupun coroutines.
            """
        )

        Tabel(
            "Guided practice — tujuh langkah",
            langkahPraktikTerbimbing.mapIndexed { i, langkah -> "Langkah ${i + 1}" to langkah }
        )
        Text(
            "Untuk setiap aksi, identifikasi: event-nya, method ViewModel-nya, field state yang " +
                "berubah, dan UI yang dihasilkan.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text("Independent practice — Delete a Task", style = MaterialTheme.typography.titleSmall)
        Text(
            "Jalurnya sama dengan aksi lain; yang membedakan hanya operasi daftarnya (filterNot). " +
                "Jelaskan jalur UDF penghapusan SEBELUM menulis kode: layar mengirim ID → " +
                "onDeleteTask(id) → ViewModel membuat daftar baru → snapshot baru → layar menggambar ulang.",
            style = MaterialTheme.typography.bodyMedium
        )

        Tabel(
            "Check your understanding",
            tanyaJawabState.map { it.pertanyaan to it.jawaban }
        )

        Text("Rujukan", style = MaterialTheme.typography.titleSmall)
        Tabel(
            "Sumber resmi",
            listOf(
                "UI layer" to "developer.android.com/topic/architecture/ui-layer",
                "UI state production" to "developer.android.com/topic/architecture/ui-layer/state-production",
                "State holders and UI state" to "developer.android.com/topic/architecture/ui-layer/stateholders",
                "Architecture recommendations" to "developer.android.com/topic/architecture/recommendations",
                "State and Jetpack Compose" to "developer.android.com/develop/ui/compose/state",
                "Kotlin StateFlow" to "kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/",
                "MutableStateFlow.update" to "kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/update.html"
            )
        )

        Text(
            "Verifikasi modul ini: ./jalankan.sh 13 perilaku — 58 pemeriksaan aturan state, " +
                "StateFlow, dan UDF, dijalankan dari terminal.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KesalahanUmumPreview() {
    KesalahanUmumScreen()
}
