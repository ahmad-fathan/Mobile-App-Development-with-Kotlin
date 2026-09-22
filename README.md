# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

## Daftar modul

| Modul | Judul |
| --- | --- |
| 02 | Kotlin Programming Essentials |
| 03 | OOP in Kotlin & Null Safety |

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
├── modul-02-kotlin-programming-essentials/
│   ├── Main.kt                                  # titik masuk + menu contoh
│   ├── bab-01-struktur-program-dan-variabel.kt
│   ├── bab-02-tipe-data-dan-operator.kt
│   ├── bab-03-input-dan-output.kt
│   ├── bab-04-percabangan-if-dan-when.kt
│   ├── bab-05-perulangan.kt
│   ├── bab-06-fungsi.kt
│   ├── verifikasi.kt                            # pemeriksaan otomatis
│   └── latihan/
│       └── latihan-akhir.kt                     # latihan akhir modul
└── modul-03-oop-dan-null-safety/
    ├── Main.kt                                  # titik masuk + menu contoh
    ├── bab-01-class-dan-object.kt
    ├── bab-02-constructor-getter-dan-setter.kt
    ├── bab-03-inheritance.kt
    ├── bab-04-abstract-class-dan-interface.kt
    ├── bab-05-visibility-modifier.kt
    ├── bab-06-null-safety.kt
    ├── bab-07-smart-cast.kt
    ├── verifikasi.kt                            # pemeriksaan otomatis
    └── latihan/
        └── latihan-akhir.kt                     # latihan akhir modul
```

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-04-jetpack-compose-dasar/`, tanpa mengubah konfigurasi Gradle. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

## Menjalankan

Karena setiap modul memakai nama berkas yang sama (`Main.kt`, `verifikasi.kt`, `latihan/latihan-akhir.kt`), satu waktu hanya mengompilasi satu modul. Pilih modul dengan `-Pmodul=<nomor>`; tanpa opsi itu modul 02 yang dipakai.

```bash
./gradlew run                             # menu contoh interaktif modul 02
./gradlew run --args=verifikasi           # pemeriksaan otomatis modul 02
./gradlew run -Pmodul=03                  # menu contoh interaktif modul 03
./gradlew run -Pmodul=03 --args=verifikasi # pemeriksaan otomatis modul 03
```

Butuh JDK 17+ (Android Studio sudah menyertakan JDK bawaan). Gradle tidak perlu dipasang.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankannya, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
