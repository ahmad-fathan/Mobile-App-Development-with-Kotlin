package com.example.todoapp.api

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Bagian 6.3 — endpoint dideskripsikan sebagai interface, bukan ditulis manual.
 *
 * `@GET("todos")` = base URL + todos. `suspend` = dipanggil dari coroutine sehingga layar
 * tidak macet selama menunggu server. `List<Todo>` = tipe hasil yang dibuat Gson dari JSON.
 */
interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    /** Latihan 3 — endpoint satu data memakai path parameter. */
    @GET("todos/{id}")
    suspend fun getTodo(@Path("id") id: Int): Todo
}

/** Latihan 2 — fungsi interface untuk endpoint `/posts`. */
interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
