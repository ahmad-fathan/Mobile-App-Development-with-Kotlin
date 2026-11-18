package com.example.minicampus.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.minicampus.ui.screens.CourseDetailScreen
import com.example.minicampus.ui.screens.CoursesScreen
import com.example.minicampus.ui.screens.HomeScreen
import com.example.minicampus.ui.screens.ProfileScreen
import com.example.minicampus.viewmodel.CourseViewModel

/**
 * Titik temu seluruh aplikasi (Bagian 9.5 modul).
 *
 * Urutan logika: buat NavController dan satu [CourseViewModel] di tingkat aplikasi, kumpulkan
 * `courses` dari StateFlow, baca destination aktif, sembunyikan Navigation Bar saat Course Detail
 * terbuka, lalu daftarkan empat destination di dalam NavHost.
 *
 * ViewModel dibuat sekali di sini agar instance yang sama dipakai layar Courses dan Course Detail.
 * Layar tidak menerima NavController — hanya data dan callback.
 */
@Composable
fun CampusApp() {
    val navController = rememberNavController()
    val courseViewModel: CourseViewModel = viewModel()
    val courses by courseViewModel.courses.collectAsStateWithLifecycle()

    val entry by navController.currentBackStackEntryAsState()
    val destination = entry?.destination
    val showBottomBar = destination?.hasRoute<CourseDetail>() != true

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    NavigationBarItem(
                        selected = destination?.hasRoute<Home>() == true,
                        onClick = { navController.navigateToTopLevel(Home) },
                        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                        label = { Text("Home") }
                    )
                    NavigationBarItem(
                        selected = destination?.hasRoute<Courses>() == true,
                        onClick = { navController.navigateToTopLevel(Courses) },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Courses") }
                    )
                    NavigationBarItem(
                        selected = destination?.hasRoute<Profile>() == true,
                        onClick = { navController.navigateToTopLevel(Profile) },
                        icon = { Icon(Icons.Filled.Person, contentDescription = null) },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Home> {
                HomeScreen()
            }
            composable<Courses> {
                CoursesScreen(
                    courses = courses,
                    onCourseClick = { id -> navController.navigate(CourseDetail(id)) }
                )
            }
            composable<Profile> {
                ProfileScreen()
            }
            composable<CourseDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<CourseDetail>()
                CourseDetailScreen(
                    course = courseViewModel.getCourseById(route.courseId),
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}

/**
 * Navigasi ke top-level destination dari Navigation Bar.
 *
 * - `popUpTo(Home)`: membersihkan back stack sampai Home agar destination tidak menumpuk.
 * - `launchSingleTop`: mencegah destination yang sama dibuat dua kali saat menu aktif ditekan lagi.
 * - `saveState` / `restoreState`: menyimpan lalu memulihkan state setiap menu.
 */
private fun NavController.navigateToTopLevel(route: Any) {
    navigate(route) {
        popUpTo(Home) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
