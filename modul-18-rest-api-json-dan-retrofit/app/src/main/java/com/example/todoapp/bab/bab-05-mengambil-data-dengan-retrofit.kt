package com.example.todoapp.bab

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
import androidx.compose.ui.unit.dp
import com.example.todoapp.JudulBab
import com.example.todoapp.KodeBlok
import com.example.todoapp.Tabel
import com.example.todoapp.api.RetrofitClient
import com.example.todoapp.baseUrlSah

/**
 * Bab 5 — Mengambil Data dengan Retrofit (berkas 05 = Bagian 6)
 *
 * Bagian modul: "Cara Kerja Retrofit", "Langkah 1: Dependency dan Izin Internet",
 * "Langkah 2: Interface API", "Langkah 3: Instance Retrofit".
 */

private val alurRetrofit = listOf(
    "1" to "Interface TodoApiService menyebutkan endpoint dan method.",
    "2" to "Retrofit mengirim request ke server sesuai isi interface.",
    "3" to "Server membalas dengan teks JSON.",
    "4" to "Gson converter mengubah teks JSON menjadi List<Todo>."
)

private val tabelBerkas = listOf(
    "Todo.kt" to "Data class untuk satu task",
    "TodoApiService.kt" to "Deskripsi endpoint dengan @GET",
    "RetrofitClient.kt" to "Base URL dan Gson converter",
    "TodoUiState.kt" to "Tiga state: Loading, Success, Error",
    "TodoViewModel.kt" to "Memanggil API dan memperbarui state",
    "TodoScreen.kt" to "Menggambar tampilan untuk setiap state",
    "MainActivity.kt" to "Menampilkan TodoScreen di dalam Scaffold"
)

@Composable
fun RetrofitScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        JudulBab(
            teks = "Bab 5 — Mengambil data dengan Retrofit",
            keterangan = "Kita cukup mendeskripsikan API sebagai interface; Retrofit membuat kode jaringannya."
        )

        Tabel("Bagian 6.1 — Empat langkah perjalanan data", alurRetrofit)

        Tabel(
            "Bagian 6.2 — Dependency dan izin",
            listOf(
                "retrofit" to "com.squareup.retrofit2:retrofit:3.0.0",
                "retrofit-gson" to "com.squareup.retrofit2:converter-gson:3.0.0",
                "Izin internet" to "<uses-permission android:name=\"android.permission.INTERNET\" />"
            )
        )
        Text(
            text = "Tanpa izin INTERNET, setiap request gagal meskipun kode lainnya benar. " +
                "Base URL aplikasi ini diakhiri garis miring: ${baseUrlSah(RetrofitClient.BASE_URL)}.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        KodeBlok(
            kode = """
            interface TodoApiService {
                @GET("todos")
                suspend fun getTodos(): List<Todo>
            }
            """
        )

        KodeBlok(
            kode = """
            object RetrofitClient {
                private const val BASE_URL = "${RetrofitClient.BASE_URL}"

                val api: TodoApiService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(TodoApiService::class.java)
            }
            """
        )

        Tabel("Tabel 6 — Berkas pada aplikasi TodoApp", tabelBerkas)
    }
}
