package com.example.todoapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Bagian 6.4 — satu instance Retrofit untuk seluruh aplikasi.
 *
 * Perhatikan empat bagian: `object` (satu instance bersama), `baseUrl` (wajib diakhiri `/`),
 * `GsonConverterFactory` (pengubah JSON), dan `create` (membuat objek nyata dari interface).
 */
object RetrofitClient {
    const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    /** `by lazy` = baru dibuat saat pertama dipakai, lalu dipakai ulang. */
    val api: TodoApiService by lazy { buatApi() }
    val apiPost: PostApiService by lazy { buatApiPost() }

    fun buatApi(baseUrl: String = BASE_URL): TodoApiService =
        retrofit(baseUrl).create(TodoApiService::class.java)

    fun buatApiPost(baseUrl: String = BASE_URL): PostApiService =
        retrofit(baseUrl).create(PostApiService::class.java)

    private fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
