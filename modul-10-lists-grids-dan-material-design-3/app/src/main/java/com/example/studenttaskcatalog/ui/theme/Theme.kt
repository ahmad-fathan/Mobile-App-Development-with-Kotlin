package com.example.studenttaskcatalog.ui.theme

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
 * Pembungkus tema proyek StudentTaskCatalog.
 *
 * Tema ini sama dengan modul Compose Layouts and UI Components; yang berubah hanya
 * namanya, karena proyek modul 09 disalin menjadi proyek mandiri dengan paket baru
 * (`com.example.studenttaskcatalog`). Modul 09 sendiri tidak mengubah tema.
 */
@Composable
fun StudentTaskCatalogTheme(
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
