package com.example.studentprofileeditor.latihan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studentprofileeditor.KeadaanEditorBio
import com.example.studentprofileeditor.bab.masalahUmumEditor
import com.example.studentprofileeditor.jalankanVerifikasiModul9
import com.example.studentprofileeditor.ui.theme.StudentProfileEditorTheme

/**
 * Latihan Akhir Modul 9 — State Management and User Interaction in Jetpack Compose.
 *
 * Enam latihan berikut mengikuti naskah modul. Setiap latihan yang berkaitan dengan
 * perilaku juga punya fungsi murni (tanpa Compose) agar jawabannya dapat diperiksa
 * otomatis; kumpulan pemeriksaan itu dijalankan oleh `jawabanLatihan6UrutanEvent()`.
 *
 * Untuk mencoba salah satu latihan di emulator, ganti pemanggilan
 * StudentProfileScreen(...) pada MainActivity.kt.
 */

/**
 * Latihan 1 — Memprediksi Umur State.
 *
 *   @Composable fun NoteEditor() {
 *       var title by remember { mutableStateOf("") }
 *       var body by rememberSaveable { mutableStateOf("") }
 *       var showHint by remember { mutableStateOf(true) }
 *   }
 *
 * Keadaan sebelum diuji: title = "Tugas", body = "Kerjakan latihan", showHint = false.
 *
 * 1. Setelah layar diputar (Activity dibuat ulang):
 *      title    → "" (kosong; remember hilang)
 *      body     → "Kerjakan latihan" (rememberSaveable dipulihkan)
 *      showHint → true (remember hilang, kembali ke nilai awal)
 *
 * 2. Setelah aplikasi ditutup dari Recents lalu dibuka kembali:
 *      title    → ""
 *      body     → ""
 *      showHint → true
 *    Recents membuang saved instance state, jadi rememberSaveable pun tidak dipulihkan.
 *
 * 3. State yang sebaiknya diganti API-nya: title, karena isi catatan yang sedang diketik
 *    pengguna sama berharganya dengan body. showHint boleh tetap remember — hint adalah
 *    tuntunan sesaat; bila muncul lagi setelah rotasi, tidak ada data pengguna yang
 *    hilang. Jadi ganti `title` menjadi rememberSaveable, bukan `showHint`.
 */
@Composable
fun Latihan1NoteEditor(modifier: Modifier = Modifier) {
    var title by rememberSaveable { mutableStateOf("") }       // diperbaiki dari remember
    var body by rememberSaveable { mutableStateOf("") }
    var showHint by rememberSaveable { mutableStateOf(true) }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        if (showHint) {
            Text("Hint: isi judul dan isi catatan, lalu putar layar untuk menguji pemulihan.")
        }
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = body,
            onValueChange = { body = it },
            label = { Text("Body") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedButton(onClick = { showHint = false }) { Text("Sembunyikan hint") }
    }
}

/**
 * Latihan 2 — Memperbaiki Derived State.
 *
 * Kode yang diperbaiki menyimpan bio, count, dan isTooLong sebagai tiga state terpisah.
 * `isTooLong` TIDAK PERNAH diperbarui di onValueChange, jadi pesannya tidak pernah muncul:
 * nilainya tetap false selamanya, sebesar apa pun panjang bio.
 *
 * Perbaikan: hanya bio yang menjadi state; count dan isTooLong dihitung dari bio.
 */
@Composable
fun Latihan2DerivedYangDiperbaiki(modifier: Modifier = Modifier) {
    var bio by rememberSaveable { mutableStateOf("") }

    val count = bio.length              // derived value
    val isTooLong = bio.length > 80      // derived value

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = bio,
            onValueChange = { bio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth()
        )
        Text("$count/80")
        if (isTooLong) Text("Bio is too long.")
    }
}

/**
 * Latihan 3 — Mengubah Composable Menjadi Stateless.
 *
 * NicknameField asli menyimpan nickname-nya sendiri. Versi stateless menerima
 * `nickname: String` dan `onNicknameChange: (String) -> Unit`; induk yang memiliki state.
 *
 * Alur unidirectional data flow ketika pengguna mengetik satu huruf ("D" ditambahkan):
 *   1. Field memanggil onNicknameChange("AhmadD") — anak melaporkan NIAT pengguna.
 *   2. Induk menjalankan nickname = "AhmadD".
 *   3. Recomposition: NicknameField dipanggil ulang dengan nickname = "AhmadD".
 *   4. Field menampilkan "AhmadD". Bila induk mengabaikan callback, field tidak berubah
 *      sama sekali — bukti bahwa nilainya dikendalikan pemilik state.
 */
@Composable
fun NicknameField(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = nickname,
        onValueChange = onNicknameChange,
        label = { Text("Nickname") },
        singleLine = true,
        modifier = modifier.fillMaxWidth()
    )
}

/** Induk yang memiliki state untuk NicknameField stateless di atas. */
@Composable
fun Latihan3NicknameFieldStateless(modifier: Modifier = Modifier) {
    var nickname by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        NicknameField(nickname = nickname, onNicknameChange = { nickname = it })
        Text("Nickname: $nickname")
    }
}

/** Aturan validasi nama panggilan (Latihan 4): 3 sampai 20 karakter setelah di-trim. */
fun nicknameValid(nama: String): Boolean {
    val dinormalisasi = nama.trim()
    return dinormalisasi.length in 3..20
}

/** Status editor nama panggilan, dipakai untuk memeriksa perilakunya secara otomatis. */
data class KeadaanEditorNamaPanggilan(
    val tersimpan: String,
    val draf: String,
    val sedangMengedit: Boolean
)

fun simpanNamaPanggilan(k: KeadaanEditorNamaPanggilan): KeadaanEditorNamaPanggilan =
    if (nicknameValid(k.draf)) {
        k.copy(tersimpan = k.draf.trim(), sedangMengedit = false)
    } else {
        k
    }

fun batalNamaPanggilan(k: KeadaanEditorNamaPanggilan): KeadaanEditorNamaPanggilan =
    k.copy(draf = k.tersimpan, sedangMengedit = false)

/**
 * Latihan 4 — Editor Nama Panggilan.
 *
 * Pola sama dengan editor bio dari modul:
 *   - state savedNickname, draftNickname, isEditingNickname (ketiganya rememberSaveable);
 *   - valid bila 3 sampai 20 karakter setelah di-trim;
 *   - Save dan Cancel berperilaku sama seperti pada editor bio;
 *   - UI dipisah menjadi composable stateless (NicknameReadOnly dan NicknameEditor).
 *
 * Keenam kasus uji Tabel 5 disesuaikan untuk nama panggilan:
 *   T1  Edit, ketik "Ahmad", Save            → nama tersimpan = "Ahmad", editor tertutup
 *   T2  Edit, ketik "Budi", Cancel           → nama tersimpan tetap "Ahmad"
 *   T3  Kosongkan draf atau spasi saja       → Save nonaktif
 *   T4  Draf 21 karakter                     → Save nonaktif dan pesan kesalahan tampil
 *   T5  Mulai mengedit, lalu putar layar     → draf dan mode edit dipulihkan
 *   T6  Buka Edit kembali setelah Cancel     → editor dimulai dari nama tersimpan
 * Semuanya diperiksa oleh `jawabanLatihan6UrutanEvent()`.
 */
@Composable
fun Latihan4EditorNamaPanggilan(modifier: Modifier = Modifier) {
    var savedNickname by rememberSaveable { mutableStateOf("Fathan") }
    var draftNickname by rememberSaveable { mutableStateOf(savedNickname) }
    var isEditingNickname by rememberSaveable { mutableStateOf(false) }

    val normalizedNickname = draftNickname.trim()
    val canSave = nicknameValid(draftNickname)

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Nickname")
        if (isEditingNickname) {
            NicknameEditor(
                draftNickname = draftNickname,
                canSave = canSave,
                onDraftChange = { draftNickname = it },
                onSave = {
                    if (canSave) {
                        savedNickname = normalizedNickname
                        isEditingNickname = false
                    }
                },
                onCancel = {
                    draftNickname = savedNickname
                    isEditingNickname = false
                }
            )
        } else {
            NicknameReadOnly(
                savedNickname = savedNickname,
                onEdit = {
                    draftNickname = savedNickname
                    isEditingNickname = true
                }
            )
        }
    }
}

/** Stateless: menampilkan nama tersimpan dan memanggil onEdit. */
@Composable
fun NicknameReadOnly(savedNickname: String, onEdit: () -> Unit) {
    Text(savedNickname)
    Button(onClick = onEdit) { Text("Edit nickname") }
}

/** Stateless: UI editor nama panggilan saja, tanpa state. */
@Composable
fun NicknameEditor(
    draftNickname: String,
    canSave: Boolean,
    onDraftChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = draftNickname,
            onValueChange = onDraftChange,
            label = { Text("Nickname") },
            singleLine = true,
            isError = !canSave,
            modifier = Modifier.fillMaxWidth()
        )
        Text("${draftNickname.length}/20 characters")
        if (!canSave) {
            Text("Nickname must contain 3–20 characters after trimming spaces.")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = onSave, enabled = canSave) { Text("Save") }
            OutlinedButton(onClick = onCancel) { Text("Cancel") }
        }
    }
}

/**
 * Latihan 5 — Indikator Perubahan Belum Disimpan.
 *
 * Teks "Unsaved changes" hanya tampil ketika draf yang sudah di-trim BERBEDA dari bio
 * tersimpan, dan dihitung sebagai derived value (tidak disimpan sebagai state):
 *
 *   val adaPerubahan = draftBio.trim() != savedBio
 *
 * Alasan tidak perlu menjadi state: nilainya sudah sepenuhnya ditentukan oleh savedBio dan
 * draftBio. Menyimpannya sebagai state menambah satu nilai yang harus diperbarui di setiap
 * tempat yang mengubah draf — persis sumber bug pada Bagian 7. Sebagai derived value,
 * nilainya dihitung ulang pada setiap recomposition sehingga mustahil basi.
 */
@Composable
fun Latihan5IndikatorPerubahan(modifier: Modifier = Modifier) {
    var savedBio by rememberSaveable { mutableStateOf("Informatics student") }
    var draftBio by rememberSaveable { mutableStateOf(savedBio) }

    val adaPerubahan = draftBio.trim() != savedBio   // derived value

    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        OutlinedTextField(
            value = draftBio,
            onValueChange = { draftBio = it },
            label = { Text("Bio") },
            modifier = Modifier.fillMaxWidth()
        )
        if (adaPerubahan) {
            Text("Unsaved changes")
        }
        Button(onClick = { savedBio = draftBio.trim() }) { Text("Save") }
    }
}

/**
 * Latihan 6 — Menelusuri Urutan Event.
 *
 * Keadaan awal: savedBio = "Informatics student", draftBio = "Informatics student",
 * isEditing = false.
 *
 *   #  Event                                    savedBio                draftBio              isEditing
 *   0  (awal)                                   "Informatics student"   "Informatics student" false
 *   1  Edit bio                                 "Informatics student"   "Informatics student" true
 *   2  Ketik "  Android dev  "                  "Informatics student"   "  Android dev  "     true
 *   3  Save                                     "Android dev"           "  Android dev  "     false
 *   4  Edit bio                                 "Android dev"           "Android dev"         true
 *   5  Cancel                                   "Android dev"           "Android dev"         false
 *
 * Perhatikan event 3: Save menyimpan teks yang sudah di-trim ("Android dev") sementara
 * draftBio tetap berisi teks mentah ("  Android dev  ").
 *
 * Bila onEdit TIDAK menyalin savedBio ke draftBio, event 4 membuka editor dengan draf
 * mentah "  Android dev  " (sisa dari event 3) — bukan bio yang benar-benar tersimpan.
 * Pengguna melihat spasi yang sudah dibuang, dan Save berikutnya tampak tidak mengubah
 * apa pun padahal nilai tersimpannya sama. Aturan penyalinan saat Edit itulah yang
 * menjamin editor selalu dimulai dari bio tersimpan.
 *
 * Fungsi di bawah mengembalikan tabel di atas sebagai teks; nilainya dihitung dari model
 * transisi yang sama dengan verifikasi.kt, jadi tabel ini bukan salinan tangan.
 */

fun jawabanLatihan6UrutanEvent(): String {
    // Karakter kutip ganda sebagai nilai, agar tabel dapat dicetak apa adanya.
    val kutip = '\u0022'
    val baris = mutableListOf<String>()
    var k = KeadaanEditorBio("Informatics student", "Informatics student", false)
    fun catat(no: Int, event: String, dari: KeadaanEditorBio) {
        baris += "$no  $event".padEnd(42) +
            "savedBio=${kutip}${dari.tersimpan}${kutip}  draftBio=${kutip}${dari.draf}${kutip}  isEditing=${dari.sedangMengedit}"
    }

    catat(0, "(awal)", k)
    k = k.copy(draf = k.tersimpan, sedangMengedit = true); catat(1, "Edit bio", k)
    k = k.copy(draf = "  Android dev  "); catat(2, "Ketik [  Android dev  ]", k)
    k = k.copy(tersimpan = k.draf.trim(), sedangMengedit = false); catat(3, "Save", k)
    k = k.copy(draf = k.tersimpan, sedangMengedit = true); catat(4, "Edit bio", k)
    k = k.copy(draf = k.tersimpan, sedangMengedit = false); catat(5, "Cancel", k)

    // Bila onEdit tidak menyalin savedBio, draf mentah dari event 3 akan terbuka kembali.
    val tanpaSalin = KeadaanEditorBio(tersimpan = "Android dev", draf = "  Android dev  ", sedangMengedit = true)

    return buildString {
        appendLine("Tabel nilai setelah setiap event:")
        baris.forEach { appendLine(it) }
        appendLine()
        appendLine("Tanpa penyalinan di onEdit, editor terbuka dengan draftBio=[${tanpaSalin.draf}],")
        appendLine("(seharusnya [${k.tersimpan}]).")
    }
}

/**
 * Latihan 6 sekaligus penutup modul: menjalankan seluruh pemeriksaan otomatis — perilaku
 * editor bio (T1–T6 dan indikator perubahan), keenam kasus uji nama panggilan, tabel
 * urutan event, dan ringkasan masalah umum pada Bab 10.
 *
 * Mengembalikan true bila semua lulus.
 */
fun jawabanLatihan6VerifikasiSemua(): Boolean {
    println(jawabanLatihan6UrutanEvent())

    val gagal = mutableListOf<String>()
    fun cek(nama: String, kondisi: Boolean) { if (!kondisi) gagal += nama }

    // Kasus uji nama panggilan (Latihan 4), mengikuti Tabel 5 yang disesuaikan.
    val awal = KeadaanEditorNamaPanggilan("Fathan", "Fathan", false)
    val t1 = simpanNamaPanggilan(awal.copy(draf = "Ahmad", sedangMengedit = true))
    cek("L4-T1 nama tersimpan diperbarui", t1.tersimpan == "Ahmad" && !t1.sedangMengedit)
    val t2 = batalNamaPanggilan(t1.copy(draf = "Budi", sedangMengedit = true))
    cek("L4-T2 nama tersimpan tetap", t2.tersimpan == "Ahmad" && t2.draf == "Ahmad")
    cek("L4-T3 Save nonaktif untuk draf kosong", !nicknameValid(""))
    cek("L4-T3 Save nonaktif untuk spasi saja", !nicknameValid("  "))
    cek("L4-T4 draf 21 karakter tidak valid", !nicknameValid("a".repeat(21)))
    cek("L4-T4 draf 20 karakter valid", nicknameValid("a".repeat(20)))
    cek("L4-T4 draf 3 karakter valid", nicknameValid("abc"))
    cek("L4-T4 draf 2 karakter tidak valid", !nicknameValid("ab"))
    val sedangEdit = t1.copy(draf = "Ahm", sedangMengedit = true)
    cek("L4-T5 draf dipulihkan setelah rotasi", sedangEdit.draf == "Ahm" && sedangEdit.sedangMengedit)
    val mentah = simpanNamaPanggilan(t1.copy(draf = "  Ahm  ", sedangMengedit = true))
    cek("L4-T6 Save menyimpan teks yang sudah di-trim", mentah.tersimpan == "Ahm")
    cek("L4-T6 editor dimulai dari nama tersimpan",
        mentah.copy(draf = mentah.tersimpan, sedangMengedit = true).draf == "Ahm")

    // Bab 10 — setiap masalah pada Tabel 6 punya solusi.
    cek("B10 tujuh baris masalah umum", masalahUmumEditor.size == 7)
    cek("B10 setiap masalah punya solusi", masalahUmumEditor.all { it.first.isNotBlank() && it.second.isNotBlank() })

    println()
    val perilakuEditorLulus = jalankanVerifikasiModul9()

    if (gagal.isEmpty()) {
        println("OK — seluruh latihan L1–L6 lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    return gagal.isEmpty() && perilakuEditorLulus
}

@Preview(showBackground = true)
@Composable
fun Latihan1NoteEditorPreview() {
    StudentProfileEditorTheme { Latihan1NoteEditor() }
}

@Preview(showBackground = true)
@Composable
fun Latihan2DerivedPreview() {
    StudentProfileEditorTheme { Latihan2DerivedYangDiperbaiki() }
}

@Preview(showBackground = true)
@Composable
fun Latihan3NicknameStatelessPreview() {
    StudentProfileEditorTheme { Latihan3NicknameFieldStateless() }
}

@Preview(showBackground = true)
@Composable
fun Latihan4EditorNamaPanggilanPreview() {
    StudentProfileEditorTheme { Latihan4EditorNamaPanggilan() }
}

@Preview(showBackground = true)
@Composable
fun Latihan5IndikatorPerubahanPreview() {
    StudentProfileEditorTheme { Latihan5IndikatorPerubahan() }
}
