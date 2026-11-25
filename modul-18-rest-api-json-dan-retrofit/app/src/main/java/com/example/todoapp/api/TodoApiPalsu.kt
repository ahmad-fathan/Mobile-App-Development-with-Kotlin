package com.example.todoapp.api

import com.example.todoapp.galatHttp
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Implementasi palsu `TodoApiService` untuk mencoba ketiga state UI **tanpa internet**.
 *
 * Berguna saat praktikum: dosen/mahasiswa dapat memperlihatkan Loading, Success, dan Error
 * meskipun jaringan laboratorium sedang mati. `TodoViewModel` menerima `api` sebagai parameter,
 * jadi tidak ada kode produksi yang perlu diubah.
 */
class TodoApiPalsu(
    private val hasil: Hasil = Hasil.Berhasil,
    private val jedaMs: Long = 900L
) : TodoApiService {

    enum class Hasil { Berhasil, TanpaKoneksi, EndpointSalah }

    private val contoh = listOf(
        Todo(1, 1, "delectus aut autem", false),
        Todo(1, 2, "quis ut nam facilis et officia qui", false),
        Todo(1, 3, "fugiat veniam minus", true)
    )

    override suspend fun getTodos(): List<Todo> {
        delay(jedaMs)
        return when (hasil) {
            Hasil.Berhasil -> contoh
            Hasil.TanpaKoneksi -> throw IOException("Tidak ada koneksi internet")
            Hasil.EndpointSalah -> throw galatHttp(404)
        }
    }

    override suspend fun getTodo(id: Int): Todo {
        delay(jedaMs)
        if (hasil == Hasil.EndpointSalah || id !in 1..200) throw galatHttp(404)
        return contoh.firstOrNull { it.id == id } ?: contoh.first()
    }
}
