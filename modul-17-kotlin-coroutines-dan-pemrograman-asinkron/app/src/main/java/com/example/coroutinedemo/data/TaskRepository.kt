package com.example.coroutinedemo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Server tiruan (Subbagian 7.5).
 *
 * Kedua fungsi adalah suspend function yang memindahkan pekerjaannya sendiri ke [Dispatchers.IO],
 * sehingga keduanya main-safe: aman dipanggil dari main thread tanpa membuat layar macet.
 * Di aplikasi sungguhan, `delay()` diganti pemanggilan Retrofit atau Room.
 */
class TaskRepository {

    suspend fun getTasks(shouldFail: Boolean = false): List<Task> =
        withContext(Dispatchers.IO) {
            delay(2000L)
            if (shouldFail) {
                throw IOException("Server is not reachable")
            }
            listOf(
                Task(1, "Write lab report", "Mobile App Development"),
                Task(2, "Read chapter 5", "Data Science"),
                Task(3, "Prepare quiz", "Research Methodology")
            )
        }

    suspend fun getAnnouncement(): String =
        withContext(Dispatchers.IO) {
            delay(1000L)
            "Quiz 2 is next Monday"
        }
}
