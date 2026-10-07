# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

Modul 02–05 berisi **Kotlin murni (`.kt`)** yang dapat dikompilasi dan dijalankan tanpa Gradle maupun Android Studio. Modul 06 ke atas berisi **proyek Android Studio** (Jetpack Compose) dengan Gradle.

## Daftar modul

| Modul | Judul | Bentuk |
| --- | --- | --- |
| 02 | Kotlin Programming Essentials | Kotlin konsol |
| 03 | OOP in Kotlin & Null Safety | Kotlin konsol |
| 04 | Kotlin-Specific Features & Collections | Kotlin konsol |
| 05 | Functional & Idiomatic Kotlin | Kotlin konsol |
| 06 | Android Studio and Your First Jetpack Compose App | Proyek Android Studio |
| 07 | Jetpack Compose Basics | Proyek Android Studio |

## Struktur folder

Modul **konsol** menempati satu folder di root proyek, dengan pola nama:

```
modul-<nomor>-<judul-modul-dalam-huruf-kecil>
```

Di dalamnya, setiap bab modul menjadi satu berkas dengan pola:

```
bab-<nomor>-<judul-bab-dalam-huruf-kecil>.kt
```

Modul **Android** juga menempati satu folder dengan pola nama yang sama, tetapi isinya adalah proyek Android Studio lengkap (Gradle wrapper, `app/`, `res/`, `AndroidManifest.xml`). Struktur di dalamnya mengikuti tata letak proyek template **Empty Activity**. Agar isi setiap berkas tetap bisa ditebak, kode sumber aplikasi dipecah mengikuti nomor bagian modul:

```
app/src/main/java/<paket>/
├── MainActivity.kt                  # titik masuk + setContent
├── bab/
│   ├── bab-01-<judul-bab>.kt        # satu bagian modul = satu berkas
│   └── ...
└── latihan/
    └── latihan-akhir.kt             # latihan akhir modul
```

```
.
├── README.md
├── jalankan.sh                                  # kompilasi + jalankan modul konsol
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
├── modul-05-functional-dan-idiomatic-kotlin/
│   ├── Main.kt                                  # titik masuk + menu contoh
│   ├── bab-01-fungsi-sebagai-nilai-lambda-dan-function-type.kt
│   ├── bab-02-higher-order-function.kt
│   ├── bab-03-mengolah-collection-dengan-operasi-fungsional.kt
│   ├── bab-04-menggabungkan-operasi-menjadi-pipeline.kt
│   ├── bab-05-scope-function.kt
│   ├── bab-06-generics-dasar.kt
│   ├── verifikasi.kt                            # pemeriksaan otomatis
│   └── latihan/
│       └── latihan-akhir.kt                     # latihan akhir modul
├── modul-06-android-studio-dan-compose-pertama/ # proyek Android Studio
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradle/libs.versions.toml                # versi plugin dan dependensi
│   ├── gradlew, gradlew.bat, gradle/wrapper/
│   └── app/
│       ├── build.gradle.kts                     # konfigurasi modul app
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/myfirstcomposeapp/
│           │   ├── MainActivity.kt              # titik masuk + setContent
│           │   ├── bab/bab-01-composable-pertama.kt
│           │   ├── bab/bab-02-column-dan-preview.kt
│           │   ├── bab/bab-03-menampilkan-gambar.kt
│           │   ├── bab/bab-04-warna-latar-dan-padding.kt
│           │   ├── latihan/latihan-akhir.kt      # latihan akhir modul
│           │   └── ui/theme/                     # Color.kt, Theme.kt, Type.kt
│           └── res/                              # drawable, mipmap, values, xml
└── modul-07-jetpack-compose-basics/             # proyek Android Studio
    ├── settings.gradle.kts
    ├── build.gradle.kts
    ├── gradle.properties
    ├── gradle/libs.versions.toml                # versi plugin dan dependensi
    ├── gradlew, gradlew.bat, gradle/wrapper/
    └── app/
        ├── build.gradle.kts                     # konfigurasi modul app
        └── src/main/
            ├── AndroidManifest.xml
            ├── java/com/example/studentgreeting/
            │   ├── MainActivity.kt              # Student Greeting App (Bagian 11)
            │   ├── bab/bab-01-composable-dan-preview.kt
            │   ├── bab/bab-02-mengatur-tampilan-teks.kt
            │   ├── bab/bab-03-merangkai-modifier.kt
            │   ├── bab/bab-04-column-dan-row.kt
            │   ├── bab/bab-05-button-dan-event-handler.kt
            │   ├── bab/bab-06-state-remember-dan-mutablestateof.kt
            │   ├── bab/bab-07-menerima-input-dengan-textfield.kt
            │   ├── bab/bab-08-menampilkan-gambar.kt
            │   ├── latihan/latihan-akhir.kt      # latihan akhir modul
            │   └── ui/theme/                     # Color.kt, Theme.kt, Type.kt
            └── res/
                ├── drawable/                     # ikon template (vector)
                └── drawable-nodpi/student.png    # gambar contoh 512×512
```

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-07-.../`, tanpa mengubah apa pun yang lain. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

## Menjalankan

### Modul konsol (02–05)

Tidak perlu Gradle, tidak perlu emulator, dan tidak perlu membuat proyek Android. Ada tiga cara, pilih yang paling nyaman.

#### Cara 1 — Skrip `jalankan.sh` (paling singkat)

Dari root repositori:

```bash
./jalankan.sh                 # menu contoh modul 02 (interaktif)
./jalankan.sh 03              # menu contoh modul 03
./jalankan.sh 03 verifikasi   # pemeriksaan otomatis modul 03
```

Skrip ini mencari sendiri compiler Kotlin: memakai `kotlinc` bawaan Android Studio bila ada, jika tidak memakai `kotlinc` dari `PATH`. Jadi bila Android Studio sudah terpasang, tidak ada yang perlu di-install lagi.

#### Cara 2 — Terminal di dalam Android Studio

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

#### Cara 3 — Tombol Run hijau

Setelah folder modul dibuka, buka `Main.kt` lalu klik ikon **Run** (segitiga hijau) di gutter pada baris `fun main`, dan pilih `Run 'MainKt'`.

Hanya `Main.kt` yang punya `fun main`, karena itu hanya berkas itu yang menampilkan tombol Run. Berkas `bab-*.kt` sengaja hanya berisi contoh dan fungsi, jadi tidak dapat dijalankan sendiri — itu memang rancangannya, bukan kesalahan.

#### Tanpa Android Studio

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

### Modul Android (06 ke atas)

Bukalah **folder modul**, bukan root repositori:

1. **File → Open**, pilih folder modul, misalnya `modul-07-jetpack-compose-basics`.
2. Tunggu **Gradle sync** selesai (unduhan pertama bisa beberapa menit).
3. Pilih perangkat pada device selector, lalu klik **Run** (segitiga hijau).

Dari terminal:

```bash
cd modul-07-jetpack-compose-basics
./gradlew assembleDebug          # hasil: app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat yang aktif
```

Dari root repositori, `./jalankan.sh 07` juga bekerja: modul yang punya `gradlew` otomatis dialihkan ke Gradle (`./jalankan.sh 07 verifikasi` menjalankan `assembleDebug`).

Bila `local.properties` belum ada dan perintah Gradle dijalankan dari terminal (bukan dari Android Studio), tambahkan lokasi Android SDK:

```properties
sdk.dir=/Users/<nama-anda>/Library/Android/sdk   # macOS
```

Pada Mac, `JAVA_HOME` dapat diarahkan ke JBR bawaan Android Studio bila JDK belum terpasang:

```bash
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
```

#### Tampilan modul 06

`MainActivity` memasang `MyFirstScreen()` — layar dua baris teks dan ikon launcher di atas latar cyan. Untuk mencoba contoh lain, ganti pemanggilan di dalam `setContent { }`:

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `MyFirstScreen()` | `bab/bab-04-warna-latar-dan-padding.kt` | 12–13 |
| `LayarDuaBaris()` | `bab/bab-02-column-dan-preview.kt` | 10 |
| `LayarDenganGambar()` | `bab/bab-03-menampilkan-gambar.kt` | 11 |
| `GreetingBerwarnaLatar("Nama")` | `bab/bab-04-warna-latar-dan-padding.kt` | 13.2 |
| `Latihan1PersonalisasiLayar()` | `latihan/latihan-akhir.kt` | Latihan 1 |
| `Latihan2GambarSendiri()` | `latihan/latihan-akhir.kt` | Latihan 2 |

Setiap composable juga punya fungsi `@Preview` dengan nama serupa, sehingga tampilannya dapat diperiksa di panel Design tanpa menjalankan aplikasi.

#### Tampilan modul 07 — Student Greeting App

`MainActivity` memasang `StudentGreetingScreen()` — kolom isian nama, tombol **Say Hello**, teks sapaan, dan gambar. Setelah aplikasi berjalan, ketik sebuah nama lalu tekan tombolnya; sapaan muncul di bawah tombol. Untuk mencoba contoh lain, ganti pemanggilan di dalam `setContent { }`:

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `StudentGreetingScreen()` | `MainActivity.kt` | 11 |
| `WelcomeScreen()` | `bab/bab-01-composable-dan-preview.kt` | 3 |
| `TeksBesarTebal()` | `bab/bab-02-mengatur-tampilan-teks.kt` | 4 |
| `TeksSelebarLayar()` | `bab/bab-03-merangkai-modifier.kt` | 5 |
| `ColumnDenganJarak()` | `bab/bab-04-column-dan-row.kt` | 6 |
| `TombolSederhana()` | `bab/bab-05-button-dan-event-handler.kt` | 7 |
| `CounterExample()` | `bab/bab-06-state-remember-dan-mutablestateof.kt` | 8 |
| `NameInputDenganSapaan()` | `bab/bab-07-menerima-input-dengan-textfield.kt` | 9 |
| `GambarBerkuranTetap()` | `bab/bab-08-menampilkan-gambar.kt` | 10 |
| `Latihan1UbahTeks()` … `Latihan5KartuProfilMahasiswa()` | `latihan/latihan-akhir.kt` | Latihan 1–5 |

Interaksi (klik dan ketikan) tidak terlihat di Preview biasa. Jalankan aplikasi di emulator, atau aktifkan **mode interaktif** pada panel Preview — letak tombolnya dapat berbeda antarversi Android Studio.

Gambar contoh `res/drawable-nodpi/student.png` disertakan agar `R.drawable.student` langsung dikenali. Bila ingin memakai gambar sendiri, ganti berkas itu dengan nama yang hanya berisi huruf kecil, angka, dan garis bawah.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankannya, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
