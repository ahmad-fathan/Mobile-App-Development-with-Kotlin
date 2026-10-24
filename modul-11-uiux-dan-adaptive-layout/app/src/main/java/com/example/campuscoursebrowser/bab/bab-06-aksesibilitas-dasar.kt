package com.example.campuscoursebrowser.bab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.campuscoursebrowser.TARGET_SENTUH_MINIMAL_DP
import com.example.campuscoursebrowser.contentDescriptionIkon
import com.example.campuscoursebrowser.penandaPilihanCukupDenganWarna
import com.example.campuscoursebrowser.tinggiTetapAman
import com.example.campuscoursebrowser.ikonBolehLebihKecil
import com.example.campuscoursebrowser.kontrasMinimal
import com.example.campuscoursebrowser.rasioKontras
import com.example.campuscoursebrowser.satuanTeksDapatDiskalakan
import com.example.campuscoursebrowser.targetSentuhMemadai

/**
 * Bab 6 (Bagian 7) — Aksesibilitas Dasar
 *
 * Aksesibilitas berarti orang dengan kemampuan berbeda dapat memahami dan mengoperasikan
 * antarmuka. Ia memengaruhi wireframe pertama, pemilihan komponen, dan evaluasi — bukan
 * tambahan visual di akhir.
 *
 * Empat pemeriksaan: target sentuh dan kontras (7.1), makna untuk teknologi asistif (7.2),
 * perancangan untuk teks yang membesar (7.3), dan kritik layar sebelum menulis kode (7.4).
 *
 * Angka acuan Android: target sentuh minimal 48 dp x 48 dp; ikon yang terlihat boleh lebih
 * kecil; kontras minimal 4.5:1 untuk teks normal dan 3:1 untuk teks besar.
 *
 * Aturannya sendiri (rasioKontras, kontrasMinimal, targetSentuhMemadai, ikonBolehLebihKecil,
 * satuanTeksDapatDiskalakan) dideklarasikan di paket akar, berkas verifikasi.kt, karena dipakai
 * juga oleh pemeriksaan otomatis.
 */

/**
 * 7.1 Jangan menyampaikan keadaan "selected" atau "error" HANYA dengan warna. Pada Course
 * Browser, tombol kartu terpilih tetap bertuliskan "Selected course" dan membawa semantics
 * selected, sehingga perbedaan tetap terbaca tanpa warna (penandaPilihanCukupDenganWarna).
 *
 * 7.2 Ikon dekoratif yang sudah dijelaskan teks di sebelahnya memakai contentDescription = null;
 * ikon tunggal yang dapat ditekan memerlukan deskripsi aksi (contentDescriptionIkon).
 *
 * 7.3 Tinggi tetap di sekitar teks yang panjangnya berubah-ubah memotong informasi
 * (tinggiTetapAman).
 */

/** Satu baris kritik desain (7.4): siapa terdampak, tugas apa yang sulit, usulan perbaikan. */
data class KritikDesain(
    val masalah: String,
    val terdampak: String,
    val tugasTerganggu: String,
    val perbaikan: String
)

/** Lima ciri layar yang sengaja dibuat bermasalah pada kegiatan kelas (7.4). */
val kriteriaLayarBermasalah: List<String> = listOf(
    "Hierarki judul yang lemah",
    "Label aksi yang tidak konsisten",
    "Kontrol yang kecil dan berdesakan",
    "Teks sekunder dengan kontras rendah",
    "Kartu bertinggi tetap yang memotong judul panjang"
)

/** Kritik hanya berguna bila menyebut siapa, tugas apa, dan perbaikannya. */
fun kritikLengkap(k: KritikDesain): Boolean =
    listOf(k.masalah, k.terdampak, k.tugasTerganggu, k.perbaikan).all { it.isNotBlank() }

/** Contoh heading yang ditandai melalui semantics; screen reader dapat melompat ke sini. */
@Composable
fun JudulDetailCourse() {
    Text(
        text = "Course details",
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.semantics { heading() }
    )
}

/**
 * Contoh target sentuh 48 dp: tanda 24 dp di tengah area sentuh 48 dp. Seluruh area itulah yang
 * disentuh pengguna, jadi jangan mengecilkannya menjadi seukuran gambarnya.
 *
 * Isi tombol ini hanya bentuk tanpa teks, sehingga contentDescription wajib ada; tanpa itu
 * TalkBack hanya membacakan "tombol".
 */
@Composable
fun TombolFavoritDenganTarget48() {
    Box(
        modifier = Modifier
            .size(TARGET_SENTUH_MINIMAL_DP.dp)
            .clickable(role = Role.Button) {}
            .semantics { contentDescription = "Save course" },
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )
    }
}

/**
 * Angka aksesibilitas dihitung dari aturan yang sama dengan verifikasi.kt, sehingga tabel di
 * layar tidak dapat berbeda dari hasil pemeriksaan otomatis.
 */
@Composable
fun RingkasanAksesibilitas() {
    val kontrasBaik = rasioKontras("FFFFFF", "1F2430")
    val kontrasBuruk = rasioKontras("FFFFFF", "A0A6AE")
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Target sentuh", style = MaterialTheme.typography.titleSmall)
        Text(
            "48 x 48 dp memadai: ${targetSentuhMemadai(48, 48)}; " +
                "ikon 24 dp di target 48 dp: ${ikonBolehLebihKecil(24, 48)}",
            style = MaterialTheme.typography.bodySmall
        )
        Text("Kontras teks", style = MaterialTheme.typography.titleSmall)
        Text(
            "#1F2430 pada putih = ${bulatkanSatuDesimal(kontrasBaik)}:1 " +
                "(ambang ${kontrasMinimal(false)}:1)",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            "#A0A6AE pada putih = ${bulatkanSatuDesimal(kontrasBuruk)}:1 " +
                "(tidak memenuhi syarat)",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFFA0A6AE)
        )
        Text(
            "Teks wajib memakai sp: ${satuanTeksDapatDiskalakan("sp")}; " +
                "tinggi tetap aman untuk teks penting: ${tinggiTetapAman(true)}",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

/** Pembulatan satu desimal tanpa memformat locale (String.format bisa bergantung locale). */
fun bulatkanSatuDesimal(nilai: Double): String {
    val dikali = (nilai * 10).toInt()
    return "${dikali / 10},${dikali % 10}"
}

@Preview(showBackground = true, widthDp = 360, heightDp = 360)
@Composable
private fun AksesibilitasPreview() {
    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        JudulDetailCourse()
        TombolFavoritDenganTarget48()
        RingkasanAksesibilitas()
    }
}
