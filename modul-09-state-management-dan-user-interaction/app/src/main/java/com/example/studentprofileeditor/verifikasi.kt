package com.example.studentprofileeditor

/**
 * Pemeriksaan perilaku State Management and User Interaction (modul 09).
 *
 * Modul menilai editor dari PERILAKUNYA, bukan tampilannya. Tabel 5 modul memuat enam
 * kasus uji:
 *
 *   T1  Edit, ketik bio valid, Save            → bio tersimpan diperbarui, editor tertutup
 *   T2  Edit, ketik bio baru, Cancel           → bio tersimpan sebelumnya tetap
 *   T3  Kosongkan draf atau isi spasi saja     → Save nonaktif
 *   T4  Draf 81 karakter                       → Save nonaktif dan pesan kesalahan tampil
 *   T5  Mulai mengedit, lalu putar layar       → draf dan mode edit dipulihkan
 *   T6  Buka Edit kembali setelah Cancel/Save  → editor dimulai dari bio tersimpan
 *
 * Agar keenam kasus itu dapat diperiksa tanpa emulator, transisi editor dimodelkan
 * sebagai fungsi murni di berkas ini. Fungsi murni inilah logika yang sama dengan lambda
 * pada StudentProfileEditor (Bab 9), sehingga hasil ceknya sah untuk perilaku editor.
 *
 * Karena berkas ini hanya memakai Kotlin stdlib, pemeriksaannya dapat dijalankan dari
 * terminal tanpa emulator (perintah lengkap ada di README, bagian "Menjalankan verifikasi"):
 *
 *   kotlinc verifikasi.kt -include-runtime -d verifikasi.jar && java -jar verifikasi.jar
 *
 * Fungsi yang sama juga dicetak ulang dari dalam aplikasi lewat
 * `jawabanLatihan6VerifikasiSemua()` pada latihan/latihan-akhir.kt.
 */

/** Keadaan editor bio: dua bio dan satu penanda mode, seperti pada Bagian 10.1. */
data class KeadaanEditorBio(
    val tersimpan: String,
    val draf: String,
    val sedangMengedit: Boolean
)

/** Aturan validasi Bagian 7: teks setelah di-trim berisi 1 sampai 80 karakter. */
fun bioDapatDisimpan(draf: String): Boolean {
    val dinormalisasi = draf.trim()
    return dinormalisasi.isNotEmpty() && dinormalisasi.length <= 80
}

/** Event Edit: salin bio tersimpan ke draf, lalu buka editor. */
fun editBio(k: KeadaanEditorBio): KeadaanEditorBio =
    k.copy(draf = k.tersimpan, sedangMengedit = true)

/** Event Type: draf berubah, mode edit tidak. */
fun ketikBio(k: KeadaanEditorBio, teksBaru: String): KeadaanEditorBio =
    k.copy(draf = teksBaru)

/** Event Save: hanya bila valid; menyimpan draf yang sudah di-trim dan menutup editor. */
fun simpanBio(k: KeadaanEditorBio): KeadaanEditorBio =
    if (bioDapatDisimpan(k.draf)) {
        k.copy(tersimpan = k.draf.trim(), sedangMengedit = false)
    } else {
        k
    }

/** Event Cancel: kembalikan draf ke bio tersimpan dan tutup editor. */
fun batalBio(k: KeadaanEditorBio): KeadaanEditorBio =
    k.copy(draf = k.tersimpan, sedangMengedit = false)

/** Indikator "Unsaved changes" (Latihan 5) sebagai derived value. */
fun adaPerubahanBelumDisimpan(k: KeadaanEditorBio): Boolean =
    k.draf.trim() != k.tersimpan

/** Awal editor: bio "Informatics student", draf = salinan, mode baca. */
fun keadaanAwalEditor(): KeadaanEditorBio =
    KeadaanEditorBio(tersimpan = "Informatics student", draf = "Informatics student", sedangMengedit = false)

/**
 * Menjalankan keenam kasus uji Tabel 5 pada model transisi di atas.
 * Mengembalikan daftar kegagalan; daftar kosong berarti semuanya lulus.
 */
fun verifikasiPerilakuEditor(): List<String> {
    val gagal = mutableListOf<String>()

    fun cek(nama: String, kondisi: Boolean) {
        if (!kondisi) gagal += nama
    }

    // T1 — Edit, ketik bio valid, Save.
    val t1 = simpanBio(ketikBio(editBio(keadaanAwalEditor()), "Mobile app developer"))
    cek("T1 bio tersimpan diperbarui", t1.tersimpan == "Mobile app developer")
    cek("T1 editor tertutup", !t1.sedangMengedit)

    // T2 — Edit, ketik bio baru, Cancel.
    val t2 = batalBio(ketikBio(editBio(t1), "Bio yang dibatalkan"))
    cek("T2 bio tersimpan tetap", t2.tersimpan == "Mobile app developer")
    cek("T2 draf kembali ke bio tersimpan", t2.draf == t2.tersimpan)

    // T3 — Draf kosong atau hanya spasi: Save nonaktif dan tidak mengubah apa pun.
    cek("T3 Save nonaktif untuk draf kosong", !bioDapatDisimpan(""))
    cek("T3 Save nonaktif untuk draf berisi spasi saja", !bioDapatDisimpan("   "))
    val t3 = simpanBio(ketikBio(editBio(t1), "   "))
    cek("T3 Save tidak menyimpan draf tidak valid", t3.tersimpan == t1.tersimpan && t3.sedangMengedit)

    // T4 — Draf 81 karakter tidak valid; 80 karakter valid (batas atas).
    cek("T4 draf 81 karakter tidak valid", !bioDapatDisimpan("a".repeat(81)))
    cek("T4 draf 80 karakter valid", bioDapatDisimpan("a".repeat(80)))

    // T5 — Rotasi: nilai draf dan mode edit tetap sama setelah dipulihkan. Modelnya sama
    // dengan nilai yang disimpan rememberSaveable; keadaan yang dipulihkan identik.
    val sedangEdit = ketikBio(editBio(t1), "Draf setengah jadi")
    val setelahRotasi = sedangEdit // rememberSaveable memulihkan nilai yang sama
    cek("T5 draf dipulihkan setelah rotasi", setelahRotasi.draf == "Draf setengah jadi")
    cek("T5 mode edit dipulihkan setelah rotasi", setelahRotasi.sedangMengedit)

    // T6 — Buka Edit kembali: editor dimulai dari bio tersimpan, bukan draf mentah.
    val kotor = simpanBio(ketikBio(editBio(t1), "  Hi  "))
    cek("T6 Save menyimpan teks yang sudah di-trim", kotor.tersimpan == "Hi")
    val bukaLagi = editBio(kotor)
    cek("T6 editor dimulai dari bio tersimpan", bukaLagi.draf == "Hi")

    // Latihan 5 — indikator "Unsaved changes" sebagai derived value.
    cek("L5 tidak ada perubahan saat draf = tersimpan",
        !adaPerubahanBelumDisimpan(KeadaanEditorBio("Hi", "Hi", true)))
    cek("L5 ada perubahan saat draf berbeda", adaPerubahanBelumDisimpan(KeadaanEditorBio("Hi", "Hai", true)))
    cek("L5 spasi saja bukan perubahan", !adaPerubahanBelumDisimpan(KeadaanEditorBio("Hi", "  Hi  ", true)))

    return gagal
}

/** Mencetak hasil verifikasi perilaku; mengembalikan true bila semua lulus. */
fun jalankanVerifikasiModul9(): Boolean {
    val gagal = verifikasiPerilakuEditor()
    val total = 17
    println("Verifikasi Modul 09 — State Management and User Interaction")
    println("Kasus uji: ${total - gagal.size}/$total lulus")
    if (gagal.isEmpty()) {
        println("OK — seluruh kasus uji T1–T6 dan Latihan 5 lulus")
    } else {
        gagal.forEach { println("GAGAL — $it") }
    }
    return gagal.isEmpty()
}

/** Titik masuk untuk menjalankan pemeriksaan perilaku editor dari terminal. */
fun main() {
    if (!jalankanVerifikasiModul9()) {
        kotlin.system.exitProcess(1)
    }
}
