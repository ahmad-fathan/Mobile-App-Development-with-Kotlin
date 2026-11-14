package com.example.tasknav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasknav.ui.theme.TaskNavTheme

/**
 * Layar pembuka (Latihan 6 modul).
 *
 * Tombol "Get started" hanya mengirim event ke atas. Pemegang NavController yang memutuskan
 * bahwa perpindahan ke TaskList harus membuang Welcome dari back stack (`popUpTo<Welcome>
 * { inclusive = true }`), sehingga Back di layar daftar menutup aplikasi.
 */
@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TaskNav", style = MaterialTheme.typography.headlineMedium)
        Text(
            "Navigation with Jetpack Compose",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Button(onClick = onGetStarted) { Text("Get started") }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    TaskNavTheme { WelcomeScreen(onGetStarted = {}) }
}
