package com.example.studytasks

/**
 * Potret (snapshot) seluruh nilai yang dibutuhkan untuk menampilkan layar Study Tasks pada satu
 * saat (Bagian 7.4).
 *
 * ViewModel menerbitkan SATU object [TasksUiState], sehingga draf, daftar tugas, dan pesan
 * kesalahan selalu berubah bersama dan tidak pernah tampil dalam kombinasi yang tidak konsisten.
 *
 * - [tasks] adalah potret untuk layar; pemilik data tugas yang sebenarnya tetap repository;
 * - [errorMessage] bersifat nullable: nilai `null` berarti tidak ada pesan yang perlu ditampilkan.
 */
data class TasksUiState(
    val draft: String = "",
    val tasks: List<StudyTask> = emptyList(),
    val errorMessage: String? = null
)
