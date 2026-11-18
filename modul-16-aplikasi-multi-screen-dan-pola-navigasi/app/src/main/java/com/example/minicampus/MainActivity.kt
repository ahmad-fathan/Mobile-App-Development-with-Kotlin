package com.example.minicampus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.minicampus.navigation.CampusApp
import com.example.minicampus.ui.theme.MiniCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniCampusTheme {
                CampusApp()
            }
        }
    }
}
