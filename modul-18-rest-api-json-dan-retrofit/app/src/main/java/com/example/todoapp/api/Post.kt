package com.example.todoapp.api

import com.google.gson.Gson

/** Latihan 2 — pemetaan JSON dari `https://jsonplaceholder.typicode.com/posts/1`. */
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

/** Isi endpoint `/posts/1` (disingkat pada bagian body). */
const val JSON_SATU_POST = """
{
  "userId": 1,
  "id": 1,
  "title": "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
  "body": "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum"
}
"""

fun bacaSatuPost(json: String = JSON_SATU_POST): Post =
    Gson().fromJson(json, Post::class.java)
