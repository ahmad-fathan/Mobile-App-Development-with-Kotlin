package com.example.studentgreeting.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

/**
 * Pembungkus tema proyek StudentGreeting.
 *
 * Nama fungsi ini dibuat otomatis dari nama proyek saat pembuatan proyek baru,
 * jadi pada proyek Anda namanya dapat berbeda — sesuaikan pemanggilannya di
 * MainActivity dan di setiap @Preview. Tema ini dipakai oleh Scaffold dan seluruh
 * komponen Material 3 di dalamnya.
 */
@Composable
fun StudentGreetingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Warna dinamis tersedia pada Android 12 (API 31) ke atas.
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
