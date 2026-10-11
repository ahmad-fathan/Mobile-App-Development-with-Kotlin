package com.example.studentprofile.bab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Bab 4 (Bagian 5) — Box: Menumpuk dan Menempatkan Elemen
 *
 * Jika Row menempatkan anak bersebelahan dan Column bertingkat, Box menempatkan
 * anak-anaknya saling BERTUMPUK — anak-anak Box boleh saling menimpa. Urutan
 * penulisan menentukan urutan penggambaran: anak pertama digambar paling bawah,
 * anak berikutnya digambar di atasnya.
 *
 * Dua cara mengatur posisi anak di dalam Box:
 *   - contentAlignment pada Box: posisi bawaan untuk SEMUA anak;
 *   - Modifier.align(...) pada satu anak: mengganti posisi anak itu saja
 *     (hanya tersedia di dalam BoxScope).
 */

/** Dasar penumpukan: teks kedua digambar di atas teks pertama. */
@Composable
fun BoxDasar() {
    Box {
        Text("BS")       // digambar pertama
        Text("Online")   // digambar terakhir, di atas
    }
}

/** contentAlignment menentukan posisi bawaan semua anak. */
@Composable
fun BoxDenganContentAlignment() {
    Box(
        modifier = Modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("BS")
    }
}

/**
 * Avatar sederhana: inisial "BS" di tengah (mengikuti contentAlignment) dan label
 * "Online" di pojok kanan bawah (mengganti posisinya sendiri lewat align).
 *
 * Jika contentAlignment diubah menjadi BottomStart, hanya "BS" yang berpindah,
 * karena "Online" sudah punya align sendiri.
 */
@Composable
fun AvatarDenganLabelStatus() {
    Box(
        modifier = Modifier.size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        Text("BS")
        Text(
            text = "Online",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

/** Sembilan posisi Alignment pada Box ditunjukkan satu per satu di sini. */
@Composable
fun BoxSembilanPosisi() {
    val posisi = listOf(
        Alignment.TopStart, Alignment.TopCenter, Alignment.TopEnd,
        Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd,
        Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd
    )
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (baris in posisi.chunked(3)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (pos in baris) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(Color(0xFFEADDFF)),
                        contentAlignment = pos
                    ) {
                        Text("x")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarDenganLabelStatusPreview() {
    StudentProfileTheme {
        AvatarDenganLabelStatus()
    }
}

@Preview(showBackground = true)
@Composable
fun BoxDasarPreview() {
    StudentProfileTheme {
        Box(Modifier.size(200.dp).background(Color(0xFFEADDFF))) { BoxDasar() }
    }
}
