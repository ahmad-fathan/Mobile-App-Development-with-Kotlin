package com.example.todoapp.api

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Bagian 5.4 — satu task dari JSON menjadi data class.
 *
 * Aturan pemetaan: setiap key JSON menjadi satu properti dengan nama **sama persis**
 * (termasuk huruf besar-kecil), dan tipenya cocok dengan jenis value di JSON.
 */
data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

/** Satu object JSON, persis seperti yang dikirim endpoint `/todos/1` (Bagian 5.1). */
const val JSON_SATU_TODO = """
{
  "userId": 1,
  "id": 1,
  "title": "delectus aut autem",
  "completed": false
}
"""

/** Array JSON berisi dua task, seperti awal respons endpoint `/todos` (Bagian 5.2). */
const val JSON_DUA_TODO = """
[
  { "userId": 1, "id": 1, "title": "delectus aut autem", "completed": false },
  { "userId": 1, "id": 2, "title": "quis ut nam facilis", "completed": false }
]
"""

/** Mengubah teks JSON menjadi satu objek: inilah yang dikerjakan Gson di balik Retrofit. */
fun bacaSatuTodo(json: String = JSON_SATU_TODO): Todo =
    Gson().fromJson(json, Todo::class.java)

/** Mengubah teks JSON array menjadi daftar: `[ ]` berarti banyak task. */
fun bacaDaftarTodo(json: String = JSON_DUA_TODO): List<Todo> {
    val tipe = object : TypeToken<List<Todo>>() {}.type
    return Gson().fromJson(json, tipe)
}
