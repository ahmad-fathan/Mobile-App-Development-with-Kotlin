package com.example.coroutinedemo.data

/**
 * Model satu tugas. Kelas Kotlin biasa, belum ada coroutine sama sekali (Subbagian 7.4).
 */
data class Task(
    val id: Int,
    val title: String,
    val course: String
)
