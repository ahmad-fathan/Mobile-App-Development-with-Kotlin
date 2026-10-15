package com.example.studenttaskcatalog.bab

/**
 * Bab 13 (Bagian 13–14) — Menguji Layar dan Mengatasi Masalah Umum
 *
 * Aplikasi yang tampak benar di Preview belum tentu benar di semua kondisi. Tabel 5 modul
 * berisi tujuh pengujian yang perlu dijalankan sebelum layar dianggap selesai:
 *
 *   ID  Tindakan                          Hasil yang diharapkan
 *   T1  Muat 12 data contoh               semua data dapat dicapai dengan menggulir
 *   T2  Gulir daftar vertikal             daftar bergulir tanpa kartu terpotong
 *   T3  Gulir strip kategori              strip bergulir secara horizontal
 *   T4  Beralih ke grid                   data yang sama tampil dalam grid
 *   T5  Perlebar layar (grid adaptif)     jumlah kolom mengikuti lebar yang tersedia
 *   T6  Periksa tugas selesai/belum       label status terbaca dan benar
 *   T7  Gunakan tema terang dan gelap     teks tetap terbaca (warna semantik)
 *
 * Petunjuk menjalankannya:
 *   - T2: gulir cepat sampai akhir dan kembali; kartu terakhir tidak boleh terpotong tepi layar.
 *   - T3: bila semua chip muat di layar, tambahkan kategori "Lab" atau nama yang lebih panjang.
 *   - T5: harapkan 2 kolom pada ponsel 412 dp dan 4 kolom pada jendela 840 dp dengan pengaturan
 *         Bagian 8.2 (padding 16 dp, jarak 12 dp, minSize 160 dp).
 *   - T7: ubah emulator ke tema gelap melalui Settings, Display. Warna yang ditulis langsung
 *         biasanya gagal di sini.
 *
 * Bagian 14 (Tabel 6) merangkum masalah yang paling sering muncul saat membangun list dan
 * grid, beserta penyebab dan perbaikannya. Perhitungan jumlah kolom pada pemeriksaan T5
 * dihitung oleh fungsi murni pada `verifikasi.kt`, sehingga hasilnya dapat diperiksa tanpa
 * emulator lewat `./jalankan.sh 10 perilaku`.
 */

/**
 * Tabel 6 Bagian 14 dideklarasikan pada `verifikasi.kt` (`masalahUmumKatalog`) karena dipakai
 * oleh pemeriksaan otomatis; berkas ini hanya menjelaskannya dalam komentar. Perhitungan
 * jawaban T5 (jumlah kolom) juga ada di verifikasi.kt (`jumlahSelAdaptif`, `lebarSelAdaptif`)
 * dan dicetak oleh `main()` berkas itu.
 */

