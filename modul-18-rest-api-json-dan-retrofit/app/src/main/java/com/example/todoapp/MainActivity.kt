package com.example.todoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapp.bab.ClientServerHttpScreen
import com.example.todoapp.bab.JsonScreen
import com.example.todoapp.bab.KenapaDataServerScreen
import com.example.todoapp.bab.RestApiScreen
import com.example.todoapp.bab.RetrofitScreen
import com.example.todoapp.bab.TigaStateScreen
import com.example.todoapp.latihan.LatihanScreen
import com.example.todoapp.ui.TodoScreen
import com.example.todoapp.ui.theme.TodoAppTheme

/**
 * Titik masuk aplikasi TodoApp (modul 18, Bagian 7.4).
 *
 * Menu memilih satu layar bab supaya seluruh bagian modul dapat dicoba dari satu APK.
 * Pilihan **7** adalah aplikasi yang sesungguhnya: `TodoScreen()` dengan Retrofit dan jaringan.
 */
private val daftarMenu = listOf(
    "1" to "Bab 1 — Kenapa data dari server? (Bagian 2)",
    "2" to "Bab 2 — Client, server, HTTP (Bagian 3)",
    "3" to "Bab 3 — REST API (Bagian 4)",
    "4" to "Bab 4 — JSON dan data class (Bagian 5)",
    "5" to "Bab 5 — Retrofit (Bagian 6)",
    "6" to "Bab 6 — Loading, success, error (Bagian 7)",
    "7" to "Aplikasi TodoApp — data dari internet sungguhan",
    "8" to "Latihan akhir (Bagian 9)"
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MenuModul(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun MenuModul(modifier: Modifier = Modifier) {
    var pilihan by remember { mutableStateOf<String?>(null) }

    if (pilihan == null) {
        Column(
            modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)
        ) {
            Text("Modul 18 — REST API, JSON, Retrofit", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "Pilih layar yang ingin dicoba.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            daftarMenu.forEach { (nomor, judul) ->
                OutlinedButton(
                    onClick = { pilihan = nomor },
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text("$nomor. $judul")
                }
            }
        }
        return
    }

    Column(modifier = modifier.fillMaxSize()) {
        OutlinedButton(
            onClick = { pilihan = null },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("← Kembali ke menu")
        }
        Box(modifier = Modifier.fillMaxSize()) {
            when (pilihan) {
                "1" -> KenapaDataServerScreen()
                "2" -> ClientServerHttpScreen()
                "3" -> RestApiScreen()
                "4" -> JsonScreen()
                "5" -> RetrofitScreen()
                "6" -> TigaStateScreen()
                "7" -> TodoScreen()
                else -> LatihanScreen()
            }
        }
    }
}
