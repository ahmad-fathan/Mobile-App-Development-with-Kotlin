package com.example.minicampus.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Identitas mahasiswa dummy (Bagian 9.4 modul). Tanpa form dan tanpa fitur edit.
 */
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Profile", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Name: Budi Santoso")
        Text(text = "Student ID: 12345678")
        Text(text = "Program: Informatics")
    }
}
