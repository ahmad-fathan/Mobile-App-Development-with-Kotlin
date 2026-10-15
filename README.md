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
| 08 | Compose Layouts and UI Components | Proyek Android Studio |
| 09 | State Management and User Interaction | Proyek Android Studio |
| 10 | Lists, Grids, and Material Design 3 | Proyek Android Studio |

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
├── modul-07-jetpack-compose-basics/             # proyek Android Studio
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
├── modul-08-compose-layouts-dan-komponen-ui/    # proyek Android Studio
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradle/libs.versions.toml                # versi plugin dan dependensi
│   ├── gradlew, gradlew.bat, gradle/wrapper/
│   └── app/
│       ├── build.gradle.kts                     # konfigurasi modul app
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/studentprofile/
│           │   ├── MainActivity.kt              # Student Profile Screen (Bagian 9)
│           │   ├── bab/bab-01-layar-sebagai-susunan-kontainer.kt
│           │   ├── bab/bab-02-column-dan-row-lebih-dalam.kt
│           │   ├── bab/bab-03-arrangement-dan-alignment.kt
│           │   ├── bab/bab-04-box-menumpuk-dan-menempatkan-elemen.kt
│           │   ├── bab/bab-05-modifier-ukuran-jarak-dan-tampilan.kt
│           │   ├── bab/bab-06-komponen-material-3-dan-tema.kt
│           │   ├── bab/bab-07-topappbar.kt
│           │   ├── bab/bab-08-scaffold-struktur-sebuah-layar.kt
│           │   ├── bab/bab-09-membangun-student-profile-screen.kt
│           │   ├── latihan/latihan-akhir.kt      # latihan akhir modul
│           │   └── ui/theme/                     # Color.kt, Theme.kt, Type.kt
│           └── res/                              # mipmap, values, xml
├── modul-09-state-management-dan-user-interaction/  # proyek Android Studio
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradle/libs.versions.toml                # versi plugin dan dependensi
│   ├── gradlew, gradlew.bat, gradle/wrapper/
│   └── app/
│       ├── build.gradle.kts                     # konfigurasi modul app
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/studentprofileeditor/
│           │   ├── MainActivity.kt              # Student Profile Editor (Bagian 10.6)
│           │   ├── verifikasi.kt                # pemeriksaan perilaku editor (T1–T6)
│           │   ├── bab/bab-01-dari-layar-profil-ke-editor-profil.kt
│           │   ├── bab/bab-02-state-dan-event.kt
│           │   ├── bab/bab-03-umur-state-dengan-remember.kt
│           │   ├── bab/bab-04-menyimpan-state-dengan-remembersaveable.kt
│           │   ├── bab/bab-05-recomposition-lebih-dalam.kt
│           │   ├── bab/bab-06-derived-state-dan-validasi.kt
│           │   ├── bab/bab-07-menampilkan-ui-berdasarkan-state.kt
│           │   ├── bab/bab-08-state-hoisting.kt
│           │   ├── bab/bab-09-membangun-student-profile-editor.kt
│           │   ├── bab/bab-10-mengatasi-masalah-umum.kt
│           ├── latihan/latihan-akhir.kt      # latihan akhir modul
│           └── ui/theme/                     # Color.kt, Theme.kt, Type.kt
│       └── res/                              # mipmap, values, xml
├── modul-10-lists-grids-dan-material-design-3/      # proyek Android Studio
│   ├── settings.gradle.kts
│   ├── build.gradle.kts
│   ├── gradle.properties
│   ├── gradle/libs.versions.toml                # versi plugin dan dependensi
│   ├── gradlew, gradlew.bat, gradle/wrapper/
│   ├── probe-modul10.py                         # pengujian Tabel 5 lewat adb/uiautomator
│   └── app/
│       ├── build.gradle.kts                     # konfigurasi modul app
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/example/studenttaskcatalog/
│           │   ├── MainActivity.kt              # Student Task Catalog (Bagian 12.2)
│           │   ├── StudentTask.kt               # data class + 12 data contoh + kategori
│           │   ├── verifikasi.kt                # pemeriksaan aturan katalog (T1–T7)
│           │   ├── bab/bab-01-dari-kumpulan-data-ke-item-tampilan.kt
│           │   ├── bab/bab-02-mengapa-lazy-layout.kt
│           │   ├── bab/bab-03-mengenal-lazycolumn.kt
│           │   ├── bab/bab-04-item-composable-yang-dapat-dipakai-ulang.kt
│           │   ├── bab/bab-05-key-yang-stabil.kt
│           │   ├── bab/bab-06-lazyrow-untuk-kumpulan-horizontal.kt
│           │   ├── bab/bab-07-lazyverticalgrid.kt
│           │   ├── bab/bab-08-merancang-kartu-untuk-list-dan-grid.kt
│           │   ├── bab/bab-09-menerapkan-peran-tema-material-3.kt
│           │   ├── bab/bab-10-beralih-antara-list-dan-grid.kt
│           │   ├── bab/bab-11-menyusun-layar-katalog-lengkap.kt
│           │   ├── bab/bab-12-pengayaan-filter-kategori-dan-empty-state.kt
│           │   ├── bab/bab-13-menguji-layar-dan-masalah-umum.kt
│           │   ├── latihan/latihan-akhir.kt      # latihan akhir modul
│           │   └── ui/theme/                     # Color.kt, Theme.kt, Type.kt
│           └── res/                              # mipmap, values, xml
```

Modul berikutnya cukup ditambah sebagai folder sejajar, misalnya `modul-11-.../`, tanpa mengubah apa pun yang lain. Nama bab dibuat sepanjang judulnya supaya isi berkas bisa ditebak dari nama berkas saja.

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

1. **File → Open**, pilih folder modul, misalnya `modul-08-compose-layouts-dan-komponen-ui`.
2. Tunggu **Gradle sync** selesai (unduhan pertama bisa beberapa menit).
3. Pilih perangkat pada device selector, lalu klik **Run** (segitiga hijau).

Dari terminal:

```bash
cd modul-08-compose-layouts-dan-komponen-ui
./gradlew assembleDebug          # hasil: app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat yang aktif
```

Dari root repositori, `./jalankan.sh 08` juga bekerja: modul yang punya `gradlew` otomatis dialihkan ke Gradle (`./jalankan.sh 08 verifikasi` menjalankan `assembleDebug`).

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

#### Tampilan modul 08 — Student Profile / Greeting Screen

`MainActivity` memasang `StudentProfileApp()` — satu `Scaffold` dengan `TopAppBar` bertuliskan "Student Profile", kartu profil berisi avatar inisial dan label status, kolom nama, dua tombol sama lebar, dan teks sapaan. Untuk mencoba contoh lain, ganti pemanggilan di dalam `setContent { }`:

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `StudentProfileApp()` | `MainActivity.kt` | 9.7–9.8 |
| `StudentProfileScreen()` | `MainActivity.kt` | 9.4–9.6 |
| `ProfileCard()` / `ProfileAvatar()` | `MainActivity.kt` | 9.2–9.3 |
| `ContohCardDenganRow()` | `bab/bab-01-layar-sebagai-susunan-kontainer.kt` | 2 |
| `HeaderBersarang()`, `DuaTombolDenganWeight()` | `bab/bab-02-column-dan-row-lebih-dalam.kt` | 3 |
| `ColumnArrangementCenter()`, `RowSpaceBetween()` | `bab/bab-03-arrangement-dan-alignment.kt` | 4 |
| `AvatarDenganLabelStatus()`, `BoxSembilanPosisi()` | `bab/bab-04-box-menumpuk-dan-menempatkan-elemen.kt` | 5 |
| `UrutanBackgroundLaluPadding()` / `UrutanPaddingLaluBackground()` | `bab/bab-05-modifier-ukuran-jarak-dan-tampilan.kt` | 6 |
| `TeksDenganPeranTema()`, `ContohCardTema()` | `bab/bab-06-komponen-material-3-dan-tema.kt` | 7 |
| `ProfileTopBar()` | `bab/bab-07-topappbar.kt` | 7.4 |
| `ContohScaffold()` | `bab/bab-08-scaffold-struktur-sebuah-layar.kt` | 8 |
| `Tahap1Header()` … `Tahap6CardBertema()` | `bab/bab-09-membangun-student-profile-screen.kt` | 9.1–9.6 |
| `Latihan1aSpaceBetween()` … `Latihan5ProfilDiTengah()` | `latihan/latihan-akhir.kt` | Latihan 1–5 |

Latihan 6 berupa daftar nilai warna/tipografi/jarak pada kode akhir; jawabannya ada di KDoc `latihan6RingkasanKonsistensi()`.

Perilaku yang dapat diuji di emulator: ketik nama lalu **Show Greeting** menghasilkan `Hello, <nama>! Welcome to class.`; kolom kosong atau hanya berisi spasi menghasilkan `Hello, guest! Welcome to class.`; **Clear** mengosongkan kolom sekaligus sapaan.

#### Tampilan modul 09 — Student Profile Editor

`MainActivity` memasang `StudentProfileEditorApp()` — `Scaffold` yang sama dengan modul sebelumnya (TopAppBar "Student Profile", kartu profil, avatar), tetapi kontennya adalah editor bio: mode baca dengan tombol **Edit bio**, dan mode edit berisi field bio, penghitung `n/80 characters`, pesan kesalahan, serta tombol **Save** dan **Cancel**. Untuk mencoba contoh lain, ganti pemanggilan di dalam `setContent { }`:

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `StudentProfileEditor()` | `bab/bab-09-membangun-student-profile-editor.kt` | 10.1–10.5 |
| `BioEditor()` / `BioReadOnly()` | `bab/bab-09-membangun-student-profile-editor.kt` | 10.3–10.5 |
| `ContohSatuNilaiSatuAksi()` | `bab/bab-01-dari-layar-profil-ke-editor-profil.kt` | 2 |
| `KetukanSaveAdalahEvent()` | `bab/bab-02-state-dan-event.kt` | 3 |
| `BioDraftTest()` | `bab/bab-03-umur-state-dengan-remember.kt` | 4.1 |
| `BioDraftSaveableTest()` | `bab/bab-04-menyimpan-state-dengan-remembersaveable.kt` | 5 |
| `PelacakPembacaanState()` | `bab/bab-05-recomposition-lebih-dalam.kt` | 6 |
| `PolaDerivedYangBenar()` / `PolaStateTerpisahYangSalah()` | `bab/bab-06-derived-state-dan-validasi.kt` | 7 |
| `PercabanganIfBiarkanIndukMemegangState()` | `bab/bab-07-menampilkan-ui-berdasarkan-state.kt` | 8 |
| `HoistEditorDemo()` / `HoistBioEditor()` | `bab/bab-08-state-hoisting.kt` | 9 |
| `Latihan1NoteEditor()` … `Latihan5IndikatorPerubahan()` | `latihan/latihan-akhir.kt` | Latihan 1–5 |

Perilaku yang dapat diuji di emulator: **Edit bio** membuka editor; **Save** hanya aktif bila teks setelah di-trim berisi 1–80 karakter (field berwarna merah dan pesannya muncul bila tidak valid); **Cancel** mengembalikan bio tersimpan; draf dan mode edit tetap utuh setelah rotasi; dan setelah Save, editor dibuka lagi dari bio tersimpan — bukan dari draf mentah (coba draf `"  Hi  "`).

Latihan 1–3 dan 6 menguji pemahaman konsep; jawabannya ada pada KDoc masing-masing fungsi. Latihan 4 (editor nama panggilan) dapat dicoba dengan memanggil `Latihan4EditorNamaPanggilan(modifier = Modifier.padding(innerPadding))`; Latihan 6 menjalankan seluruh pemeriksaan otomatis dari dalam aplikasi lewat `jawabanLatihan6VerifikasiSemua()`.

#### Menjalankan verifikasi perilaku modul 09 (tanpa emulator)

Modul 09 menilai editor dari perilakunya. Karena itu modul ini menyertakan `verifikasi.kt` — model transisi editor (Edit, Type, Save, Cancel) sebagai fungsi murni tanpa Compose — sehingga keenam kasus uji Tabel 5 dapat diperiksa hanya dengan Kotlin compiler:

```bash
# dari root repositori
./jalankan.sh 09 perilaku

# atau langsung
cd modul-09-state-management-dan-user-interaction
kotlinc app/src/main/java/com/example/studentprofileeditor/verifikasi.kt -include-runtime -d verifikasi.jar
java -jar verifikasi.jar
```

Keluaran yang diharapkan: `Kasus uji: 17/17 lulus`. Pemeriksaan yang sama juga dibungkus oleh `jawabanLatihan6VerifikasiSemua()`, yang mencetak tabel urutan event Latihan 6 dan menjalankan keenam kasus uji nama panggilan Latihan 4.

Untuk menguji keenam kasus Tabel 5 **pada perangkat sungguhan**, modul ini menyertakan skrip ADB yang menggerakkan emulator lewat `uiautomator` dan mencetak teks yang benar-benar terlihat:

```bash
export PATH="$HOME/Library/Android/sdk/platform-tools:$PATH"
cd modul-09-state-management-dan-user-interaction
python3 probe-modul09.py        # 20/20 lulus pada Medium_Phone_API_36.0
```

Skrip itu menulis tangkapan layar tiap langkah ke `/tmp/m09-*.png` (mode baca, draf kosong, draf 81 karakter, setelah rotasi, dan seterusnya) sehingga hasil pengujian dapat dipakai sebagai bukti pada lembar kerja Latihan 6.

#### Tampilan modul 10 — Student Task Catalog

`MainActivity` memasang `StudentTaskCatalogApp()` — `Scaffold` dengan TopAppBar "Student Tasks" berisi layar katalog: strip kategori (`LazyRow` berisi `SuggestionChip`), baris `12 tasks` + tombol **Show grid**, lalu satu lazy layout vertikal yang berganti antara `TaskList` (`LazyColumn`) dan `TaskGrid` (`LazyVerticalGrid` adaptif). Untuk mencoba contoh lain, ganti pemanggilan di dalam `setContent { }`:

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `StudentTaskCatalogScreen()` | `bab/bab-11-menyusun-layar-katalog-lengkap.kt` | 12.2 |
| `TaskCard()` / `StatusLabel()` | `bab/bab-04-...`, `bab/bab-09-...` | 5, 10.1 |
| `TaskList()` | `bab/bab-04-item-composable-yang-dapat-dipakai-ulang.kt` | 5.1 |
| `CategoryStrip()` | `bab/bab-06-lazyrow-untuk-kumpulan-horizontal.kt` | 7 |
| `TaskGrid()` / `ContohGridDuaKolom()` | `bab/bab-07-lazyverticalgrid.kt` | 8 |
| `TaskViewToggle()` / `ContohBeralihListDanGrid()` | `bab/bab-10-beralih-antara-list-dan-grid.kt` | 11 |
| `StudentTaskCatalogFilterScreen()` / `EmptyTaskState()` | `bab/bab-12-pengayaan-filter-kategori-dan-empty-state.kt` | 12.4–12.5 |
| `ContohKumpulanDataKeItem()` | `bab/bab-01-dari-kumpulan-data-ke-item-tampilan.kt` | 2 |
| `ContohColumnSeluruhDaftar()` / `ContohLazyColumn()` | `bab/bab-02-mengapa-lazy-layout.kt` | 3 |
| `TaskTitleList()` / `ContohLazyColumnJudulBagian()` | `bab/bab-03-mengenal-lazycolumn.kt` | 4 |
| `DaftarDenganKey()` / `DaftarTanpaKey()` | `bab/bab-05-key-yang-stabil.kt` | 6 |
| `ContohKartuMetadataMendatar()` / `ContohKartuMetadataVertikal()` | `bab/bab-08-merancang-kartu-untuk-list-dan-grid.kt` | 9 |
| `Latihan1RingkasanJawaban()` … `latihan6JalankanSemua()` | `latihan/latihan-akhir.kt` | Latihan 1–6 |

Perilaku yang dapat diuji di emulator (Tabel 5 modul): 12 tugas semuanya tercapai dengan menggulir; kartu terakhir tidak terpotong tepi layar; keempat chip kategori tampil; tombol **Show grid** menukar daftar dan grid dari data yang sama; grid tampil dua kolom pada layar 1080 px; label `Completed`/`Pending` terbaca; dan daftar tetap tampil pada tema gelap.

#### Menjalankan verifikasi aturan modul 10 (tanpa emulator)

Modul 10 menilai layar dari aturan yang melandasinya. `verifikasi.kt` memuat aturan itu sebagai fungsi murni tanpa Compose — perhitungan kolom Adaptive, keunikan key, pasangan warna tema, dan penyaringan kategori:

```bash
# dari root repositori
./jalankan.sh 10 perilaku

# atau langsung
cd modul-10-lists-grids-dan-material-design-3
kotlinc app/src/main/java/com/example/studenttaskcatalog/verifikasi.kt -include-runtime -d verifikasi.jar
java -jar verifikasi.jar
```

Keluaran yang diharapkan: `Kasus uji: 36/36 lulus`, didahului perhitungan kolom grid (360 dp → 1 kolom, 412 dp → 2 kolom, 840 dp → 4 kolom pada minSize 160 dp). Pemeriksaan yang sama juga dibungkus oleh `latihan6JalankanSemua()`, yang mencetak jawaban Latihan 1 dan Latihan 3.

Untuk menguji ketujuh kasus Tabel 5 **pada perangkat sungguhan**, modul ini menyertakan skrip ADB:

```bash
export PATH="$HOME/Library/Android/sdk/platform-tools:$PATH"
cd modul-10-lists-grids-dan-material-design-3
python3 probe-modul10.py        # T1–T7 lulus pada Medium_Phone_API_36.0
```

Skrip itu menulis tangkapan layar tiap langkah ke `/tmp/m10-*.png` (daftar, kartu terakhir, mode grid, label status, dan tema gelap).


## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankannya, dan mengubahnya untuk keperluan belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
