package com.example.todoapp

import com.example.todoapp.api.Todo
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import com.example.todoapp.ui.TodoUiState
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response

/**
 * Aturan murni modul 18 yang dipakai bersama oleh `bab/`, `ui/`, dan `verifikasi.kt`.
 *
 * Dideklarasikan SEKALI di paket akar: menyalinnya ke setiap bab memicu
 * `Conflicting declarations` saat folder dikompilasi sekaligus.
 */

/** Bagian 3.1 — base URL + endpoint menjadi satu alamat lengkap. */
fun gabungAlamat(baseUrl: String, endpoint: String): String =
    baseUrl.trimEnd('/') + "/" + endpoint.trimStart('/')

/** Bagian 3.2 — apakah base URL sah menurut Retrofit (wajib diakhiri garis miring). */
fun baseUrlSah(baseUrl: String): Boolean = baseUrl.endsWith("/")

/** Bagian 3.3 — kelompok status code dari digit pertamanya. */
fun kelompokStatus(kode: Int): String = when (kode / 100) {
    2 -> "berhasil"
    4 -> "kesalahan pada request dari client"
    5 -> "server sedang bermasalah"
    else -> "di luar tiga kelompok yang dibahas modul"
}

/** Bagian 5.4 — array JSON berisi task ditulis sebagai List<Todo>. */
fun jumlahSelesai(todos: List<Todo>): Int = todos.count { it.completed }

/** Latihan 4 — teks ringkasan di atas daftar: "N of 200 tasks completed". */
fun ringkasanSelesai(todos: List<Todo>): String =
    "${jumlahSelesai(todos)} of ${todos.size} tasks completed"

/**
 * Bagian 7.2 — pesan yang ditampilkan untuk setiap jenis kegagalan.
 *
 * IOException = request tidak sampai ke server. HttpException = server menjawab, tetapi
 * dengan status code gagal.
 */
fun pesanKegagalan(galat: Throwable): String = when (galat) {
    is IOException -> "No internet connection"
    is HttpException -> "Server error: ${galat.code()}"
    else -> "Unknown error: ${galat.message}"
}

/** Membuat HttpException dengan status code tertentu, untuk menguji cabang error. */
fun galatHttp(kode: Int): HttpException =
    HttpException(Response.error<Any>(kode, "".toResponseBody(null)))

/** Bagian 7.1 — nama state untuk ditampilkan di layar contoh. */
fun namaState(state: TodoUiState): String = when (state) {
    TodoUiState.Loading -> "Loading"
    is TodoUiState.Success -> "Success (${state.todos.size} task)"
    is TodoUiState.Error -> "Error (${state.message})"
}

/** Bagian 5.3 — alamat yang dipakai saat mengamati respons di browser. */
const val ALAMAT_TODOS = "https://jsonplaceholder.typicode.com/todos"
const val ALAMAT_TODO_1 = "https://jsonplaceholder.typicode.com/todos/1"
const val ALAMAT_TODO_999 = "https://jsonplaceholder.typicode.com/todos/999"

/** Status code yang diharapkan untuk ketiga alamat di atas (Bagian 5.3 dan Latihan 1). */
fun statusDiharapkan(alamat: String): Int = when {
    alamat.endsWith("/todos/999") -> 404
    alamat.startsWith(ALAMAT_TODOS) -> HttpURLConnection.HTTP_OK
    else -> HttpURLConnection.HTTP_NOT_FOUND
}

/** Nama host dari sebuah URL, untuk menampilkan asal data di layar. */
fun hostDari(url: String): String = runCatching { URI(url).host ?: "-" }.getOrDefault("-")
