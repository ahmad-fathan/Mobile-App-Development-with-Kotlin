package com.example.basicnavigation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.basicnavigation.bab.Bab1KonsepNavigasi
import com.example.basicnavigation.bab.Bab2KomponenNavigasi
import com.example.basicnavigation.bab.Bab3Praktik1
import com.example.basicnavigation.bab.Bab4ArgumenNavigasi
import com.example.basicnavigation.bab.Bab5Praktik2

/**
 * Ringkasan lima bab modul dalam satu layar bergulir.
 *
 * Tiap bab tetap berupa composable mandiri di paket `bab` (punya @Preview sendiri); layar ini hanya
 * menumpuknya supaya materi bisa dibaca langsung dari aplikasi.
 */
@Composable
fun MateriScreen() {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Bab1KonsepNavigasi()
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
        Bab2KomponenNavigasi()
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
        Bab3Praktik1()
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
        Bab4ArgumenNavigasi()
        HorizontalDivider(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp))
        Bab5Praktik2()
        Text("", modifier = Modifier.padding(bottom = 24.dp))
    }
}
