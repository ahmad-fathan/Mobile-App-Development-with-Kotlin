package com.example.studentprofileeditor.bab

/**
 * Bab 10 (Bagian 12) — Mengatasi Masalah Umum
 *
 *   Gejala                                  Penyebab                        Solusi
 *   Draf hilang setelah rotasi              draftBio/isEditing masih        Ganti dengan
 *                                           memakai remember                rememberSaveable
 *   rememberSaveable unresolved reference   Import belum ditambahkan        import dari paket
 *                                                                           androidx.compose.
 *                                                                           runtime.saveable
 *   Field tidak berubah saat mengetik       onDraftChange tidak             onDraftChange =
 *                                           memperbarui draftBio            { draftBio = it }
 *   Save tetap aktif untuk bio kosong       canSave disimpan sebagai        Hitung canSave dari
 *                                           state terpisah                  draftBio dengan val
 *   Editor terbuka dengan teks lama setelah onEdit tidak menyalin           draftBio = savedBio
 *   Save                                    savedBio                        di onEdit
 *   Konten tertutup TopAppBar / ganda       innerPadding tidak diterapkan   Terapkan
 *                                           atau dua kali                   Modifier.padding(
 *                                                                           innerPadding)
 *                                                                           tepat satu kali
 *   Bio tersimpan hilang setelah aplikasi   Perilaku normal:                Penyimpanan
 *   ditutup dari Recents                    rememberSaveable bukan          permanen dibahas
 *                                           penyimpanan permanen            pada materi
 *                                                                           selanjutnya
 *
 * Sebagian besar masalah berasal dari dua sumber: state yang memakai API kurang tepat,
 * atau state yang disimpan di lebih dari satu tempat. Kembali ke prinsip single source of
 * truth biasanya sudah cukup.
 *
 * Fungsi di bawah mengodekan baris tabel yang dapat diperiksa secara mekanis, dan dipakai
 * oleh cek perilaku editor pada modul ini.
 */

/**
 * Nomor baris Tabel 6 sesuai urutan pada modul (1..7), dan daftar solusinya. Dipakai untuk
 * memastikan tiap masalah punya penanganan.
 */
val masalahUmumEditor: List<Pair<String, String>> = listOf(
    "Draf hilang setelah rotasi" to "Ganti remember dengan rememberSaveable",
    "rememberSaveable ditandai merah" to "Tambahkan import androidx.compose.runtime.saveable",
    "Field tidak berubah saat mengetik" to "onDraftChange = { draftBio = it }",
    "Save tetap aktif untuk bio kosong" to "Hitung canSave dari draftBio dengan val",
    "Editor terbuka dengan teks lama setelah Save" to "Tambahkan draftBio = savedBio di onEdit",
    "Konten tertutup TopAppBar atau jaraknya ganda" to
        "Terapkan Modifier.padding(innerPadding) tepat satu kali",
    "Bio tersimpan hilang setelah ditutup dari Recents" to
        "Perilaku normal; penyimpanan permanen dibahas pada materi selanjutnya"
)
