package com.example.basicnavigation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.basicnavigation.navigation.Routes
import com.example.basicnavigation.ui.theme.BasicNavigationTheme

/** Bagian 4.4 — layar pertama Praktik 1: satu tombol menuju Profile. */
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Home Screen")
        Button(
            onClick = { navController.navigate(Routes.Profile.route) },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Go to Profile")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PratinjauHomeScreen() {
    BasicNavigationTheme {
        HomeScreen(navController = rememberNavController())
    }
}
