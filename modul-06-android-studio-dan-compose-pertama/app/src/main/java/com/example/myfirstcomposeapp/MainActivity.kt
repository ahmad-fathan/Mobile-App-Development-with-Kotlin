package com.example.myfirstcomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.myfirstcomposeapp.bab.MyFirstScreen
import com.example.myfirstcomposeapp.ui.theme.MyFirstComposeAppTheme

/**
 * Bagian 7 — Titik Awal Aplikasi: MainActivity
 *
 * onCreate() adalah entry point aplikasi, perannya sama dengan fungsi main() pada
 * program Kotlin di konsol. setContent { } menetapkan tata letak layar melalui
 * fungsi composable, dan MyFirstComposeAppTheme { } adalah pembungkus tema proyek.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyFirstComposeAppTheme {
                MyFirstScreen()
            }
        }
    }
}
