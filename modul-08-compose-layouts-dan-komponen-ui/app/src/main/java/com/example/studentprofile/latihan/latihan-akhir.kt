package com.example.studentprofile.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofile.ui.theme.StudentProfileTheme

/**
 * Latihan Akhir Modul 8 — enam latihan pada proyek Student Profile.
 *
 * Latihan 1 sampai 3 menguji pemahaman konsep, Latihan 4 sampai 6 meminta mengubah
 * layar akhir. Setiap fungsi di bawah sudah berisi jawaban yang dapat dijalankan;
 * ubah isi setContent di MainActivity untuk mencobanya.
 */

/**
 * Latihan 1 — Memprediksi posisi pada Row dengan Arrangement.SpaceBetween.
 *
 * (a) Nama berada di ujung kiri dan tombol di ujung kanan, karena SpaceBetween
 *     menaruh ruang sisa di antara kedua anak.
 * (b) Dengan Arrangement.End, kedua anak berpindah ke ujung kanan dan tetap
 *     bersebelahan — yang berubah hanya posisi kelompok anak di sepanjang sumbu
 *     utama, bukan jarak antar-anaknya.
 * (c) Tanpa fillMaxWidth(), Row hanya selebar isinya sehingga tidak ada ruang sisa
 *     untuk disebar; SpaceBetween tampak sama dengan Start.
 *
 * Ketiga prediksi itu diperiksa dengan tiga fungsi berikut.
 */
@Composable
fun Latihan1aSpaceBetween() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

@Composable
fun Latihan1bArrangementEnd() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

@Composable
fun Latihan1cTanpaFillMaxWidth() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Budi")
        Button(onClick = { }) { Text("Edit") }
    }
}

/**
 * Latihan 2 — Posisi di dalam Box.
 *
 * (a) Label "Online" pindah ke pojok kanan atas dengan align(Alignment.TopEnd).
 * (b) Dengan contentAlignment = Alignment.BottomStart, hanya "BS" yang berpindah
 *     (ke kiri bawah), karena "Online" sudah punya align sendiri.
 * (c) Text("New") dengan align(Alignment.TopEnd) ditulis SESUDAH "Online", sehingga
 *     digambar terakhir dan terlihat di atas keduanya.
 */
@Composable
fun Latihan2aLabelKananAtas() {
    Box(modifier = Modifier.size(72.dp), contentAlignment = Alignment.Center) {
        Text("BS")
        Text(
            text = "Online",
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

@Composable
fun Latihan2bContentAlignmentBottomStart() {
    Box(modifier = Modifier.size(72.dp), contentAlignment = Alignment.BottomStart) {
        Text("BS")
        Text(
            text = "Online",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}

@Composable
fun Latihan2cTigaTeksBertumpuk() {
    Box(modifier = Modifier.size(72.dp), contentAlignment = Alignment.Center) {
        Text("BS")
        Text(
            text = "Online",
            modifier = Modifier.align(Alignment.BottomEnd)
        )
        // Ditulis terakhir → digambar paling atas.
        Text(
            text = "New",
            modifier = Modifier.align(Alignment.TopEnd)
        )
    }
}

/**
 * Latihan 3 — Urutan modifier.
 *
 * Rantai A (background lalu padding): background mewarnai seluruh area elemen,
 * termasuk area yang nanti dipakai padding, sehingga kuning mengisi area padding.
 *
 * Rantai B (padding lalu background): padding lebih dulu menambah ruang kosong
 * 16 dp, lalu background hanya mewarnai area di dalamnya; area padding tetap
 * transparan. Rantai inilah yang cocok untuk label status pada avatar, karena
 * tujuannya memberi ruang di sekeliling teks sekaligus mewarnai kotak labelnya —
 * sama seperti pemakaian .clip(...).background(...).padding(...) pada ProfileAvatar.
 */
@Composable
fun Latihan3RantaiA() {
    Text(
        text = "Online",
        modifier = Modifier
            .background(Color.Yellow)
            .padding(16.dp)
    )
}

@Composable
fun Latihan3RantaiB() {
    Text(
        text = "Online",
        modifier = Modifier
            .padding(16.dp)
            .background(Color.Yellow)
    )
}

/**
 * Latihan 4 — Menambahkan program studi.
 *
 * Tambahan: state program (String), OutlinedTextField kedua berlabel
 * "Study program", dan sapaan yang menyertakan program studi di dalam tanda kurung
 * bila kolomnya diisi. Tombol Clear mengosongkan kolom ini juga, dan sapaan tamu
 * tetap muncul bila nama kosong.
 */
@Composable
fun Latihan4DenganProgramStudi(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var program by remember { mutableStateOf("") }
    var greeting by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Your name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = program,
            onValueChange = { program = it },
            label = { Text("Study program") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    val nama = name.trim()
                    val prodi = program.trim()
                    greeting = when {
                        nama.isBlank() -> "Hello, guest! Welcome to class."
                        prodi.isBlank() -> "Hello, $nama! Welcome to class."
                        else -> "Hello, $nama! ($prodi)"
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Show Greeting")
            }
            OutlinedButton(
                onClick = {
                    name = ""
                    program = ""
                    greeting = ""
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }

        Text(
            text = greeting,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * Latihan 5 — Profil di tengah.
 *
 * Column dengan horizontalAlignment = Alignment.CenterHorizontally menggantikan Row,
 * sehingga avatar berada di atas nama dan semua isi kartu terpusat. Avatar diperbesar
 * menjadi 96 dp lewat parameter modifier.
 *
 * Column di dalam kartu memerlukan fillMaxWidth() agar isinya benar-benar berada di
 * tengah: tanpa itu Column hanya selebar anak terlebarnya, sehingga tidak ada ruang
 * sisa untuk dibagi dan CenterHorizontally tidak berpengaruh.
 */
@Composable
fun Latihan5ProfilDiTengah(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Latihan5AvatarTerpusat(initials = "BS", modifier = Modifier.size(96.dp))
            Text(
                text = "Budi Santoso",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Informatics student",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** Avatar yang meneruskan modifier ke layout terluar, jadi ukurannya dapat diubah pemanggil. */
@Composable
fun Latihan5AvatarTerpusat(initials: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Text(
            text = "Online",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.tertiary)
                .padding(horizontal = 4.dp, vertical = 2.dp)
        )
    }
}

/**
 * Latihan 6 — Memeriksa konsistensi.
 *
 * Daftar nilai pada kode akhir MainActivity.kt:
 *
 *   Warna       : semuanya peran tema — primary, onPrimary, primaryContainer,
 *                 onPrimaryContainer, surfaceVariant, onSurfaceVariant, tertiary,
 *                 onTertiary. Tidak ada lagi Color.LightGray / Color.Yellow.
 *   Ukuran teks : semuanya dari MaterialTheme.typography — titleLarge, titleMedium,
 *                 bodyMedium, labelSmall. Tidak ada sp tetap.
 *   Nilai jarak : 16 dp (padding tepi layar & dalam kartu, jarak antar-blok),
 *                 12 dp (jarak antar-tombol), 4 dp (nama ↔ deskripsi),
 *                 6 dp & 2 dp (padding label status), 72 dp & 96 dp (ukuran avatar).
 *
 * Nilai jarak tata letak sudah berasal dari skala 4/8/12/16 dp. Pengecualian yang
 * disengaja: 6 dp dan 2 dp hanya pada label status, serta 72/96 dp untuk ukuran
 * avatar — keduanya ukuran elemen, bukan jarak antar-blok. Konten tidak tertutup app
 * bar karena innerPadding diterapkan sekali di StudentProfileApp, dan tidak ada
 * elemen terpotong karena seluruh jarak memakai padding, bukan offset negatif.
 *
 * Ringkasan ini disediakan sebagai teks biasa (bukan composable) supaya dapat
 * dipanggil dari mana saja, termasuk dari modul berikutnya.
 */
fun latihan6RingkasanKonsistensi(): String = listOf(
    "warna: primary, onPrimary, primaryContainer, onPrimaryContainer,",
    "       surfaceVariant, onSurfaceVariant, tertiary, onTertiary",
    "teks : titleLarge, titleMedium, bodyMedium, labelSmall",
    "jarak: 16 dp (blok utama), 12 dp (antar-tombol), 4 dp (nama-deskripsi),",
    "       6 dp / 2 dp (label status), 72 dp / 96 dp (avatar)"
).joinToString("\n")

@Preview(showBackground = true)
@Composable
fun Latihan1aPreview() {
    StudentProfileTheme {
        Latihan1aSpaceBetween()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan1bPreview() {
    StudentProfileTheme {
        Latihan1bArrangementEnd()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan2aPreview() {
    StudentProfileTheme {
        Latihan2aLabelKananAtas()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan2cPreview() {
    StudentProfileTheme {
        Latihan2cTigaTeksBertumpuk()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan3RantaiAPreview() {
    StudentProfileTheme {
        Latihan3RantaiA()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan3RantaiBPreview() {
    StudentProfileTheme {
        Latihan3RantaiB()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan4Preview() {
    StudentProfileTheme {
        Latihan4DenganProgramStudi()
    }
}

@Preview(showBackground = true)
@Composable
fun Latihan5Preview() {
    StudentProfileTheme {
        Latihan5ProfilDiTengah()
    }
}
