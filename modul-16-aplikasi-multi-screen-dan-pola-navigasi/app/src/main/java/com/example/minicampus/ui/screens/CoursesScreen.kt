package com.example.minicampus.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.minicampus.model.Course

/**
 * Daftar mata kuliah (Bagian 9.4 modul).
 *
 * Layar ini menerima data dan satu callback, bukan NavController. Saat sebuah item ditekan,
 * layar hanya memanggil [onCourseClick]; keputusan berpindah layar dibuat di
 * [com.example.minicampus.navigation.CampusApp].
 */
@Composable
fun CoursesScreen(
    courses: List<Course>,
    onCourseClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Courses", style = MaterialTheme.typography.headlineMedium)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(courses, key = { it.id }) { course ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onCourseClick(course.id) }
                ) {
                    Text(
                        text = course.title,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
