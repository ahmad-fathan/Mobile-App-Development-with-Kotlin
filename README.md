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

## Struktur

Setiap modul berada di folder `modul-<nomor>-<judul>/`. Di dalamnya, satu bagian modul menjadi satu berkas `bab-<nomor>-<judul>.kt`, dan latihan akhir ada di `latihan/latihan-akhir.kt`.

- **Modul 02–05**: berkas Kotlin biasa dengan `Main.kt` sebagai titik masuk.
- **Modul 06 ke atas**: proyek Android Studio (Jetpack Compose). Kode bab ada di `app/src/main/java/<paket>/bab/`.

## Menjalankan

### Modul konsol (02–05)

Dari root repositori:

```bash
./jalankan.sh 03              # menu contoh modul 03
./jalankan.sh 03 verifikasi   # pemeriksaan otomatis
```

Skrip ini memakai `kotlinc` bawaan Android Studio bila ada, atau `kotlinc` dari `PATH`. Cara lain: buka folder modul di Android Studio, buka `Main.kt`, lalu klik **Run** pada `fun main`.

### Modul Android (06 ke atas)

1. Di Android Studio, pilih **File → Open** dan buka **folder modul** (bukan root repositori).
2. Tunggu Gradle sync selesai.
3. Pilih emulator atau perangkat, lalu klik **Run**.

Untuk mencoba contoh dari bab tertentu, ganti composable yang dipanggil di dalam `setContent { }` pada `MainActivity.kt`.

Modul 09 ke atas juga menyertakan `verifikasi.kt` yang bisa dijalankan tanpa emulator:

```bash
./jalankan.sh 12 perilaku
```

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankan, dan mengubah kode ini untuk belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
