package com.example.studytasks

/**
 * Kontrak data layer (Bagian 7.2).
 *
 * ViewModel bergantung pada interface ini, bukan pada class konkret. Dengan begitu implementasi
 * lain — misalnya yang memakai database — dapat diberikan kelak tanpa mengubah ViewModel.
 *
 * Fungsi di sini sinkron hanya karena pekerjaannya kecil dan berada di memori. Operasi
 * penyimpanan atau jaringan yang sesungguhnya memerlukan API asinkron yang aman dipanggil dari
 * main thread, seperti yang diperkenalkan pada Bagian 11.1.
 */
interface TaskRepository {
    fun getTasks(): List<StudyTask>
    fun addTask(title: String)
    fun toggleTask(id: Long)
}
