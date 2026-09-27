# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

Seluruh berkas di repositori ini adalah **Kotlin murni (`.kt`)** yang dapat dikompilasi dan dijalankan tanpa Gradle maupun Android Studio. Framework Android (Jetpack Compose) belum dipakai karena mahasiswa belum mempelajarinya.

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

Setiap folder modul dapat dijalankan sendiri, jadi nama berkas yang sama (`Main.kt`, `verifikasi.kt`, `latihan/latihan-akhir.kt`) boleh muncul di setiap modul.

```
.
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

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-04-.../`, tanpa mengubah apa pun yang lain. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

## Menjalankan

Butuh **JDK 17+** dan **Kotlin compiler (`kotlinc`)**. Install compiler:

```bash
# macOS
brew install kotlin

# Windows: unduh kotlin-compiler-*.zip dari https://github.com/JetBrains/kotlin/releases
# lalu tambahkan folder bin/ ke PATH
```

Kompilasi satu modul menjadi satu berkas `.jar`, lalu jalankan:

```bash
cd modul-03-oop-dan-null-safety
kotlinc . -include-runtime -d app.jar
java -jar app.jar                # menu contoh interaktif
java -jar app.jar verifikasi     # pemeriksaan otomatis
```

Ganti nama foldernya untuk modul lain, misalnya:

```bash
cd modul-02-kotlin-programming-essentials
kotlinc . -include-runtime -d app.jar
java -jar app.jar verifikasi
```

Opsi `-include-runtime` diperlukan agar berkas `.jar` dapat langsung dijalankan tanpa menyertakan library Kotlin secara terpisah. Berkas `app.jar` adalah hasil build dan tidak perlu ikut di-commit.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankannya, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
