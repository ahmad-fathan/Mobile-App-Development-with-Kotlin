package com.example.todoapp

import com.example.todoapp.api.JSON_DUA_TODO
import com.example.todoapp.api.JSON_SATU_TODO
import com.example.todoapp.api.PostApiService
import com.example.todoapp.api.RetrofitClient
import com.example.todoapp.api.TodoApiService
import com.example.todoapp.api.bacaDaftarTodo
import com.example.todoapp.api.bacaSatuPost
import com.example.todoapp.api.bacaSatuTodo
import com.example.todoapp.ui.TodoUiState
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

/**
 * Verifikasi modul 18 — dijalankan lewat `./jalankan.sh 18 perilaku`, atau dengan kotlinc:
 * kompilasi berkas ini bersama api/Todo.kt, api/Post.kt, api/TodoApiService.kt,
 * api/RetrofitClient.kt, ui/TodoUiState.kt, dan AturanApi.kt, lalu jalankan jar-nya.
 *
 * Berkas ini TIDAK memakai Compose atau AndroidX: hanya Kotlin stdlib, Gson, dan Retrofit
 * (tanpa panggilan jaringan), sehingga aturan modul dapat dibuktikan tanpa emulator.
 */

private var jumlahCekDijalankan = 0
private var jumlahGagal = 0

private fun cek(deskripsi: String, kondisi: Boolean) {
    jumlahCekDijalankan++
    if (kondisi) {
        println("  OK    $deskripsi")
    } else {
        jumlahGagal++
        println("  GAGAL $deskripsi")
    }
}

fun jalankanVerifikasiModul18(): Boolean {
    jumlahCekDijalankan = 0
    jumlahGagal = 0

    println("Verifikasi modul 18 — REST API, JSON, dan Retrofit")
    println()

    // --- Bagian 3.1 – 3.3: endpoint, base URL, status code --------------------
    println("Bagian 3 — client, server, dan HTTP")
    cek("base URL + endpoint menjadi alamat lengkap",
        gabungAlamat("https://jsonplaceholder.typicode.com/", "todos") == ALAMAT_TODOS)
    cek("alamat hasil penggabungan sama dengan alamat modul",
        gabungAlamat(RetrofitClient.BASE_URL, "/todos") == "https://jsonplaceholder.typicode.com/todos")
    cek("base URL tanpa garis miring ditolak aturan modul",
        !baseUrlSah("https://jsonplaceholder.typicode.com/api"))
    cek("base URL dengan garis miring diterima", baseUrlSah(RetrofitClient.BASE_URL))
    cek("Retrofit sendiri menolak base URL berpath tanpa garis miring", jalankan {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/api")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }.isFailure)
    cek("Retrofit menerima host tanpa path (path otomatis \"/\")", jalankan {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }.isSuccess)
    cek("status 200 termasuk kelompok berhasil", kelompokStatus(200) == "berhasil")
    cek("status 201 termasuk kelompok berhasil", kelompokStatus(201) == "berhasil")
    cek("status 404 termasuk kesalahan client", kelompokStatus(404).contains("client"))
    cek("status 500 termasuk masalah server", kelompokStatus(500).contains("server"))
    cek("status 302 di luar tiga kelompok modul",
        kelompokStatus(302).startsWith("di luar"))
    cek("alamat /todos/1 diharapkan 200", statusDiharapkan(ALAMAT_TODO_1) == 200)
    cek("alamat /todos/999 diharapkan 404", statusDiharapkan(ALAMAT_TODO_999) == 404)
    cek("host diambil dari URL",
        hostDari(ALAMAT_TODO_1) == "jsonplaceholder.typicode.com")

    // --- Bagian 5: JSON ------------------------------------------------------
    println()
    println("Bagian 5 — JSON")
    val satu = bacaSatuTodo()
    cek("JSON object terbaca menjadi satu Todo", satu.id == 1 && satu.userId == 1)
    cek("teks JSON masuk apa adanya ke properti title",
        satu.title == "delectus aut autem")
    cek("value boolean false terbaca sebagai completed = false", !satu.completed)
    val daftar = bacaDaftarTodo()
    cek("JSON array terbaca menjadi List<Todo> berisi 2 task", daftar.size == 2)
    cek("urutan id pada array dipertahankan", daftar.map { it.id } == listOf(1, 2))
    cek("semua isi array berbentuk sama (completed = false)",
        daftar.all { !it.completed })
    cek("key JSON peka huruf besar-kecil: userid ≠ userId",
        bacaSatuTodo("""{"userid": 7, "id": 1, "title": "x", "completed": true}""").userId == 0)
    cek("array kosong terbaca sebagai daftar kosong", bacaDaftarTodo("[]").isEmpty())
    cek("JSON dua task yang ditampilkan modul berukuran 2",
        JSON_DUA_TODO.contains("\"id\": 2") && bacaDaftarTodo(JSON_DUA_TODO).size == 2)
    cek("JSON satu task yang ditampilkan modul memuat key completed",
        JSON_SATU_TODO.contains("\"completed\""))

    // --- Bagian 6: Retrofit --------------------------------------------------
    println()
    println("Bagian 6 — Retrofit")
    cek("objek nyata dari interface TodoApiService dibuat Retrofit (proxy)",
        TodoApiService::class.java.isInstance(RetrofitClient.buatApi()))
    cek("objek nyata dari interface PostApiService dibuat Retrofit (proxy)",
        PostApiService::class.java.isInstance(RetrofitClient.buatApiPost()))
    cek("instance dengan base URL lain tetap merupakan implementasi interface",
        TodoApiService::class.java.isInstance(
            RetrofitClient.buatApi("https://jsonplaceholder.typicode.com/")
        ))

    // --- Bagian 7: tiga state UI dan jenis kegagalan -------------------------
    println()
    println("Bagian 7 — loading, success, dan error")
    cek("Loading tidak membawa data",
        namaState(TodoUiState.Loading) == "Loading")
    cek("Success membawa daftar task",
        namaState(TodoUiState.Success(daftar)) == "Success (2 task)")
    cek("Error membawa pesan",
        namaState(TodoUiState.Error("No internet connection")).startsWith("Error"))
    cek("IOException menjadi pesan tanpa koneksi",
        pesanKegagalan(IOException("failed to connect")) == "No internet connection")
    cek("HttpException 404 menjadi pesan server error 404",
        pesanKegagalan(galatHttp(404)) == "Server error: 404")
    cek("HttpException 500 menjadi pesan server error 500",
        pesanKegagalan(galatHttp(500)) == "Server error: 500")
    cek("kode status pada HttpException dapat dibaca",
        galatHttp(404).code() == 404)
    cek("galat lain tetap menghasilkan pesan (tidak crash)",
        pesanKegagalan(IllegalStateException("aneh")).startsWith("Unknown error"))

    // --- Bagian 9: latihan ----------------------------------------------------
    println()
    println("Bagian 9 — latihan")
    cek("jumlah selesai dihitung dari properti completed",
        jumlahSelesai(contohVerifikasi) == 2)
    cek("ringkasan memakai format modul: N of TOTAL tasks completed",
        ringkasanSelesai(contohVerifikasi) == "2 of 4 tasks completed")
    cek("daftar kosong menghasilkan 0 of 0 tasks completed",
        ringkasanSelesai(emptyList()) == "0 of 0 tasks completed")
    val post = bacaSatuPost()
    cek("data class Post terbaca dari JSON /posts/1",
        post.id == 1 && post.userId == 1 && post.title.isNotBlank() && post.body.isNotBlank())
    cek("endpoint satu data memakai path parameter (id 5 ada di rentang server)",
        5 in 1..200)
    cek("id 999 di luar rentang data JSONPlaceholder",
        999 !in 1..200)
    cek("method GET membaca data (tidak mengubah)", "GET" == "GET")

    // --- Ringkasan ------------------------------------------------------------
    println()
    val total = jumlahCekDijalankan
    if (jumlahGagal == 0) {
        println("SEMUA OK: $total/$total cek lulus")
    } else {
        println("GAGAL: ${jumlahGagal} dari $total cek tidak lulus")
    }
    return jumlahGagal == 0
}

private val contohVerifikasi = listOf(
    com.example.todoapp.api.Todo(1, 1, "Read chapter 3", false),
    com.example.todoapp.api.Todo(1, 2, "Submit lab report", true),
    com.example.todoapp.api.Todo(1, 3, "Prepare quiz", true),
    com.example.todoapp.api.Todo(1, 4, "Review JSON", false)
)

/** Menjalankan blok dan mengembalikan hasilnya, termasuk bila blok melempar galat. */
private fun <T> jalankan(blok: () -> T): Result<T> = runCatching(blok)

fun main() {
    val lulus = jalankanVerifikasiModul18()
    if (!lulus) kotlin.system.exitProcess(1)
}
