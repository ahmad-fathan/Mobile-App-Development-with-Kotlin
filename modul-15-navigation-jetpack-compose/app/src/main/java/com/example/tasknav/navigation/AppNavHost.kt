package com.example.tasknav.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.tasknav.data.TaskRepository
import com.example.tasknav.ui.AboutScreen
import com.example.tasknav.ui.CourseTasksScreen
import com.example.tasknav.ui.TaskDetailScreen
import com.example.tasknav.ui.TaskListScreen
import com.example.tasknav.ui.WelcomeScreen

/**
 * Navigation graph aplikasi TaskNav (Bagian 9.4 modul).
 *
 * Berkas inilah tempat semua bagian bertemu: NavHost memegang controller, memberi data dan
 * handler klik ke layar daftar, lalu memberi ID hasil `toRoute()` dan handler Back ke layar detail.
 *
 * Layar tidak menerima NavController — hanya lambda event — sehingga setiap layar masih dapat
 * di-preview dan diuji sendirian.
 *
 * `startDestination` default [TaskList] mengikuti Bagian 9 modul. Latihan 6 dapat dicoba tanpa
 * mengubah kode lain: `AppNavHost(startDestination = Welcome)`. Tombol "Get started" memakai
 * `popUpTo<Welcome> { inclusive = true }`, sehingga setelah masuk ke TaskList tombol Back menutup
 * aplikasi, bukan kembali ke layar pembuka.
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: Any = TaskList
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable<Welcome> {
            WelcomeScreen(
                onGetStarted = {
                    navController.navigate(TaskList) {
                        popUpTo<Welcome> { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<TaskList> {
            TaskListScreen(
                tasks = TaskRepository.getTasks(),
                onTaskClick = { id ->
                    navController.navigate(TaskDetail(taskId = id))
                },
                onAboutClick = { navController.navigate(About) }
            )
        }

        composable<TaskDetail> { backStackEntry ->
            val route: TaskDetail = backStackEntry.toRoute()
            TaskDetailScreen(
                taskId = route.taskId,
                onBack = { navController.navigateUp() },
                // Latihan 4: dari detail ke daftar semua tugas mata kuliah yang sama.
                onOpenCourse = { course -> navController.navigate(CourseTasks(course)) }
            )
        }

        composable<About> {
            AboutScreen(onBack = { navController.navigateUp() })
        }

        composable<CourseTasks> { backStackEntry ->
            val route: CourseTasks = backStackEntry.toRoute()
            CourseTasksScreen(
                course = route.course,
                onTaskClick = { id -> navController.navigate(TaskDetail(taskId = id)) },
                onBack = { navController.navigateUp() }
            )
        }
    }
}
