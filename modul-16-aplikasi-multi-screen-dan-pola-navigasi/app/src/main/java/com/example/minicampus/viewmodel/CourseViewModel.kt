package com.example.minicampus.viewmodel

import androidx.lifecycle.ViewModel
import com.example.minicampus.model.Course
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Satu-satunya sumber data untuk layar Courses dan Course Detail (Bagian 9.2 modul).
 *
 * Daftar disimpan dalam [MutableStateFlow] privat; bagian luar hanya menerima versi baca melalui
 * [asStateFlow], sehingga state tidak dapat diubah dari View. Data dummy ini tidak berubah selama
 * aplikasi berjalan — fokus modul adalah membaca state dan mengirim ID.
 */
class CourseViewModel : ViewModel() {
    private val _courses = MutableStateFlow(
        listOf(
            Course(1, "Mobile App Development", "Learning Android with Jetpack Compose."),
            Course(2, "Data Science", "Introduction to data analysis.")
        )
    )
    val courses: StateFlow<List<Course>> = _courses.asStateFlow()

    /** Dipakai layar detail; `null` berarti ID tidak dikenal sehingga layar menampilkan pesan. */
    fun getCourseById(id: Int): Course? =
        courses.value.find { it.id == id }
}
