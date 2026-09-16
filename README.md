# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

## Daftar modul

| Modul | Judul |
| --- | --- |
| 02 | Kotlin Programming Essentials |

## Struktur folder

Setiap modul menempati satu folder di root proyek, dengan pola nama:

```
modul-<nomor>-<judul-modul-dalam-huruf-kecil>
```

Di dalamnya, setiap bab modul menjadi satu berkas dengan pola:

```
bab-<nomor>-<judul-bab-dalam-huruf-kecil>.kt
```

Contoh nyata:

```
.
├── build.gradle.kts
├── settings.gradle.kts
├── gradlew, gradlew.bat, gradle/wrapper/        # Gradle Wrapper
├── README.md
└── modul-02-kotlin-programming-essentials/
    ├── Main.kt                                  # titik masuk + menu contoh
    ├── bab-01-struktur-program-dan-variabel.kt
    ├── bab-02-tipe-data-dan-operator.kt
    ├── bab-03-input-dan-output.kt
    ├── bab-04-percabangan-if-dan-when.kt
    ├── bab-05-perulangan.kt
    ├── bab-06-fungsi.kt
    ├── verifikasi.kt                            # pemeriksaan otomatis
    └── latihan/
        └── latihan-akhir.kt                     # latihan akhir modul
```

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-03-jetpack-compose-dasar/`, tanpa mengubah konfigurasi Gradle. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

## Menjalankan

```bash
./gradlew run                             # menu contoh interaktif
./gradlew run --args=verifikasi           # pemeriksaan otomatis
```

Butuh JDK 17+ (Android Studio sudah menyertakan JDK bawaan). Gradle tidak perlu dipasang.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankan, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
