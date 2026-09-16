# Mobile Application Development with Kotlin

Kumpulan source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak (Mobile Application Development)** di **Program Studi Sarjana Informatika, Universitas Islam Indonesia**.

Source code ini mendampingi modul kuliah: setiap bab pada modul memiliki berkas Kotlin tersendiri agar mahasiswa dapat membaca, menjalankan, dan memodifikasi contoh dengan mudah.

## Modul yang tersedia

- **Modul 2 — Kotlin Programming Essentials** (`src/main/kotlin/id/uii/mad/modul02/`)

## Struktur proyek

```
.
├── build.gradle.kts                         # konfigurasi Gradle (Kotlin/JVM)
├── settings.gradle.kts
├── gradlew / gradlew.bat                    # Gradle Wrapper
├── gradle/wrapper/                          # wrapper JAR & properti
└── src/main/kotlin/id/uii/mad/modul02/
    ├── Main.kt                              # titik masuk + menu contoh
    ├── Bab01StrukturProgram.kt              # val, var, const val, konvensi penamaan
    ├── Bab02TipeDataOperator.kt             # tipe data, konversi, operator, String template
    ├── Bab03InputOutput.kt                  # print(), println(), readln()
    ├── Bab04Percabangan.kt                  # if, else if, when
    ├── Bab05Perulangan.kt                   # range, for, while, do-while, break/continue
    ├── Bab06Fungsi.kt                       # definisi, parameter, nilai kembalian
    ├── latihan/LatihanAkhir.kt              # lima latihan akhir modul
    └── Verifikasi.kt                        # pemeriksaan otomatis hasil belajar
```

Satu berkas mewakili satu bagian modul. Nama berkas dibuat sejajar dengan judul bab supaya mudah dicari saat belajar.

## Prasyarat

- JDK 17 atau lebih baru (Android Studio sudah menyertakan JDK bawaan)
- Tidak perlu memasang Gradle — gunakan `./gradlew` yang sudah tersedia

## Cara menjalankan

Dari direktori proyek:

```bash
# macOS/Linux
./gradlew run

# Windows
gradlew.bat run
```

Perintah tersebut menampilkan menu interaktif. Pilih nomor untuk menjalankan contoh pada bab tertentu:

```
=== Modul 2: Kotlin Programming Essentials ===
 1. Struktur program dan variabel
 2. Tipe data dan operator
 3. Input dan output
 4. Percabangan if dan when
 5. Perulangan
 6. Fungsi
 7. Latihan akhir
 8. Verifikasi otomatis
 0. Keluar
```

Contoh pada pilihan **3** dan **7** menunggu masukan dari keyboard, jadi ketik nilainya lalu tekan Enter.

### Verifikasi otomatis

Seluruh perilaku bahasa yang dapat diperiksa tanpa masukan keyboard diuji oleh satu perintah:

```bash
./gradlew run --args=verifikasi
```

Keluaran akhirnya menyebutkan jumlah pemeriksaan dan kegagalannya, misalnya:

```
Total: 25 pemeriksaan, 0 gagal
```

Bila ada pemeriksaan yang gagal, program keluar dengan kode status bukan nol sehingga bisa dipakai pada alur otomatis.

## Catatan belajar

- **`val` dulu, `var` kemudian.** Pakai `val` selama nilainya tidak perlu berubah; alur program jadi lebih mudah dilacak ketika terjadi kesalahan.
- **Pembagian bilangan bulat membuang pecahan.** `7 / 2` menghasilkan `3`, sedangkan `7 / 2.0` menghasilkan `3.5`. Pastikan salah satu operand bertipe `Double` bila hasil pecahannya dibutuhkan.
- **`readln()` selalu mengembalikan `String`.** Konversi dengan `toInt()` atau `toDouble()` sebelum nilai itu dipakai berhitung.
- **Badan fungsi yang hanya satu perhitungan** lebih ringkas ditulis sebagai *expression body* (`fun f(a: Int) = a * 2`) daripada bentuk blok dengan `return`.

## Menghubungkan ke Android

Seluruh contoh ditulis sebagai program console supaya perhatian tertuju pada logika bahasa, bukan tata letak antarmuka. Keterampilan yang sama dipakai kembali di balik antarmuka Android: masukan tidak lagi datang dari keyboard console melainkan dari komponen antarmuka, tetapi kebutuhan mengonversi dan memeriksa masukan tetap sama.

## Lisensi dan penggunaan

Source code ini disediakan untuk keperluan pembelajaran mata kuliah Pengembangan Aplikasi Bergerak, Program Studi Sarjana Informatika, Universitas Islam Indonesia. Mahasiswa dipersilakan membaca, menjalankan, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah**
Program Studi Sarjana Informatika — Universitas Islam Indonesia
