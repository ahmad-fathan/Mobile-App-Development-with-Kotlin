# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

Seluruh berkas di repositori ini adalah **Kotlin murni (`.kt`)** yang dapat dikompilasi dan dijalankan tanpa Gradle maupun Android Studio. Framework Android (Jetpack Compose) belum dipakai karena mahasiswa belum mempelajarinya.

## Daftar modul

| Modul | Judul |
| --- | --- |
| 02 | Kotlin Programming Essentials |
| 03 | OOP in Kotlin & Null Safety |
| 04 | Kotlin-Specific Features & Collections |
| 05 | Functional & Idiomatic Kotlin |

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
├── jalankan.sh                                  # kompilasi + jalankan satu modul
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
├── modul-03-oop-dan-null-safety/
│   ├── Main.kt                                  # titik masuk + menu contoh
│   ├── bab-01-class-dan-object.kt
│   ├── bab-02-constructor-getter-dan-setter.kt
│   ├── bab-03-inheritance.kt
│   ├── bab-04-abstract-class-dan-interface.kt
│   ├── bab-05-visibility-modifier.kt
│   ├── bab-06-null-safety.kt
│   ├── bab-07-smart-cast.kt
│   ├── verifikasi.kt                            # pemeriksaan otomatis
│   └── latihan/
│       └── latihan-akhir.kt                     # latihan akhir modul
├── modul-04-kotlin-specific-features-dan-collections/
│   ├── Main.kt
│   ├── bab-01-data-class.kt
│   ├── bab-02-enum-class.kt
│   ├── bab-03-object-dan-companion-object.kt
│   ├── bab-04-extension-function.kt
│   ├── bab-05-array.kt
│   ├── bab-06-collection-read-only-dan-mutable.kt
│   ├── bab-07-list-dan-mutablelist.kt
│   ├── bab-08-set-dan-mutableset.kt
│   ├── bab-09-map-dan-mutablemap.kt
│   ├── bab-10-memilih-dan-menggabungkan-collection.kt
│   ├── bab-11-destructuring.kt
│   ├── verifikasi.kt
│   └── latihan/
│       └── latihan-akhir.kt
└── modul-05-functional-dan-idiomatic-kotlin/
    ├── Main.kt                                  # titik masuk + menu contoh
    ├── bab-01-fungsi-sebagai-nilai-lambda-dan-function-type.kt
    ├── bab-02-higher-order-function.kt
    ├── bab-03-mengolah-collection-dengan-operasi-fungsional.kt
    ├── bab-04-menggabungkan-operasi-menjadi-pipeline.kt
    ├── bab-05-scope-function.kt
    ├── bab-06-generics-dasar.kt
    ├── verifikasi.kt                            # pemeriksaan otomatis
    └── latihan/
        └── latihan-akhir.kt                     # latihan akhir modul
```

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-06-.../`, tanpa mengubah apa pun yang lain. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

## Menjalankan

Tidak perlu Gradle, tidak perlu emulator, dan tidak perlu membuat proyek Android. Ada tiga cara, pilih yang paling nyaman.

### Cara 1 — Skrip `jalankan.sh` (paling singkat)

Dari root repositori:

```bash
./jalankan.sh                 # menu contoh modul 02 (interaktif)
./jalankan.sh 03              # menu contoh modul 03
./jalankan.sh 03 verifikasi   # pemeriksaan otomatis modul 03
```

Skrip ini mencari sendiri compiler Kotlin: memakai `kotlinc` bawaan Android Studio bila ada, jika tidak memakai `kotlinc` dari `PATH`. Jadi bila Android Studio sudah terpasang, tidak ada yang perlu di-install lagi.

### Cara 2 — Terminal di dalam Android Studio

1. **File → Open**, pilih **folder modul**, misalnya `modul-04-kotlin-specific-features-dan-collections`. Jangan pilih root repositori, dan jangan memakai **New Project** — tidak diperlukan `build.gradle` sama sekali.
2. Buka Terminal di Android Studio (`⌥F12` di macOS, `Alt+F12` di Windows/Linux).
3. Jalankan:

```bash
K="/Applications/Android Studio.app/Contents/plugins/Kotlin/kotlinc/bin/kotlinc"   # macOS
"$K" . -include-runtime -d app.jar
java -jar app.jar                # menu contoh interaktif
java -jar app.jar verifikasi     # pemeriksaan otomatis
```

Di Windows, compiler bawaan Android Studio ada di `C:\Program Files\Android\Android Studio\plugins\Kotlin\kotlinc\bin\kotlinc.bat`.

### Cara 3 — Tombol Run hijau

Setelah folder modul dibuka, buka `Main.kt` lalu klik ikon **Run** (segitiga hijau) di gutter pada baris `fun main`, dan pilih `Run 'MainKt'`.

Hanya `Main.kt` yang punya `fun main`, karena itu hanya berkas itu yang menampilkan tombol Run. Berkas `bab-*.kt` sengaja hanya berisi contoh dan fungsi, jadi tidak dapat dijalankan sendiri — itu memang rancangannya, bukan kesalahan.

### Tanpa Android Studio

Butuh **JDK 17+** dan **Kotlin compiler (`kotlinc`)**:

```bash
# macOS
brew install kotlin

# Windows: unduh kotlin-compiler-*.zip dari https://github.com/JetBrains/kotlin/releases
# lalu tambahkan folder bin/ ke PATH
```

```bash
cd modul-03-oop-dan-null-safety
kotlinc . -include-runtime -d app.jar
java -jar app.jar verifikasi
```

Opsi `-include-runtime` diperlukan agar berkas `.jar` dapat langsung dijalankan tanpa menyertakan library Kotlin secara terpisah. Berkas `app.jar` adalah hasil build dan tidak perlu ikut di-commit.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankannya, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
