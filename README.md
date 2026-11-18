# Mobile Application Development with Kotlin

Source code contoh dan latihan untuk mata kuliah **Pengembangan Aplikasi Bergerak**, Program Studi Sarjana Informatika, Universitas Islam Indonesia.

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
| 11 | UI/UX and Adaptive Layout | Proyek Android Studio |
| 12 | MVVM and ViewModel in Jetpack Compose | Proyek Android Studio |
| 13 | UI State, StateFlow, and Unidirectional Data Flow | Proyek Android Studio |
| 15 | Navigation with Jetpack Compose | Proyek Android Studio |
| 15s | Navigation with Jetpack Compose — versi sederhana | Proyek Android Studio |
| 16 | Aplikasi Multi-Screen dan Pola Navigasi | Proyek Android Studio |

Setiap modul berada di folder `modul-<nomor>-<judul>/` dan berdiri sendiri. Akhiran `s` menandai versi ringkas dari modul bernomor sama.

## Menjalankan

**Modul konsol (02–05)** — dari root repositori:

```bash
./jalankan.sh 03              # jalankan contoh
./jalankan.sh 03 verifikasi   # pemeriksaan otomatis
```

**Modul Android (06 ke atas)** — buka **folder modul** (bukan root) di Android Studio, tunggu Gradle sync, lalu Run. Dari terminal:

```bash
cd modul-16-aplikasi-multi-screen-dan-pola-navigasi
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug
```

Modul 09 ke atas menyertakan `verifikasi.kt` yang bisa dijalankan tanpa emulator:

```bash
./jalankan.sh 12 perilaku
./jalankan.sh 16 perilaku
```

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankan, dan mengubah kode ini untuk belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
