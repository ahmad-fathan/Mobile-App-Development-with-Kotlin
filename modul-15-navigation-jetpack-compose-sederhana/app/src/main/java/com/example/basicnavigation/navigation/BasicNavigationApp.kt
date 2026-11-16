package com.example.basicnavigation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.basicnavigation.ui.DetailScreen
import com.example.basicnavigation.ui.HomeScreen
import com.example.basicnavigation.ui.ProfileScreen

/**
 * Bagian 4.5 — NavHost Praktik 1.
 *
 * [rememberNavController] membuat dan mengingat NavController. NavHost mendaftarkan ketiga
 * destination dengan route string dari [Routes], dan [Routes.Home] menjadi start destination.
 */
@Composable
fun BasicNavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route
    ) {
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
        composable(Routes.Profile.route) {
            ProfileScreen(navController)
        }
        composable(Routes.Detail.route) {
            DetailScreen(navController)
        }
    }
}
