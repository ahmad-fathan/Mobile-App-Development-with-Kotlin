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

## Struktur

Setiap modul berada di folder `modul-<nomor>-<judul>/`. Di dalamnya, satu bagian modul menjadi satu berkas `bab-<nomor>-<judul>.kt`, dan latihan akhir ada di `latihan/latihan-akhir.kt`.

- **Modul 02–05**: berkas Kotlin biasa dengan `Main.kt` sebagai titik masuk.
- **Modul 06 ke atas**: proyek Android Studio (Jetpack Compose). Kode bab ada di `app/src/main/java/<paket>/bab/`.
- **Akhiran `s`** pada nomor modul (mis. `15s`) menandai versi ringkas dari modul yang sama, mis.
  `modul-15-navigation-jetpack-compose-sederhana/`.

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
./jalankan.sh 15s perilaku     # modul 15 versi sederhana
```

### Modul 15 versi sederhana (`15s`)

`modul-15-navigation-jetpack-compose-sederhana/` mengikuti naskah modul 15 apa adanya: tiga komponen
navigasi (NavController, NavHost, NavGraph), back stack, praktik tiga layar, lalu mengirim argumen
antarlayar — tanpa katalog tugas, data layer, atau 39 pemeriksaan milik
`modul-15-navigation-jetpack-compose/`. Proyeknya **mandiri**: `BasicNavigation`, paket
`com.example.basicnavigation`, tema `BasicNavigationTheme`.

Aplikasinya dibuka lewat menu (`MenuPraktik()`): Praktik 1 (navigasi dasar), Praktik 2 (kirim argumen),
Materi bab 1–5, dan Latihan Akhir. Tombol Back di dalam praktik kembali ke menu.

| Composable | Berkas | Bagian modul |
| --- | --- | --- |
| `BasicNavigationApp()` | `navigation/BasicNavigationApp.kt` | 4.5 |
| `Praktik2App()` | `navigation/Praktik2App.kt` | 6.5 (`MainScreen()` di naskah) |
| `HomeScreen()`, `ProfileScreen()`, `DetailScreen()` | `ui/HomeScreen.kt`, `ui/ProfileScreen.kt`, `ui/DetailScreen.kt` | 4.4 |
| `OrderFormScreen(onNavigateToDetails)` | `ui/OrderFormScreen.kt` | 6.4 / Latihan 1 |
| `OrderDetailsScreen(navController, productName, quantity)` | `ui/OrderDetailsScreen.kt` | 6.4 / Latihan 2 |
| `Bab1KonsepNavigasi()` … `Bab5Praktik2()` | `bab/bab-01 … bab-05` | 2–6 |
| `LatihanScreen()` | `ui/LatihanScreen.kt` | Latihan 1–5 + pengayaan |

Penyimpangan yang disengaja agar cukup satu proyek: dua praktik digabung (naskah meminta dua proyek),
layar formulir bernama `OrderFormScreen` (naskah menamainya `HomeScreen`, sudah dipakai praktik 1),
`MainScreen()` menjadi `Praktik2App()`, tombol Submit dinonaktifkan saat isian belum valid, dan isian
disimpan dengan `rememberSaveable`.

Bukti: `./jalankan.sh 15s perilaku` → `SEMUA OK (24 cek)`; `assembleDebug` bersih tanpa warning;
`_alat-uji/probe-modul15-sederhana.py` → 24/24 di emulator, 0 FATAL.

## Penggunaan

Disediakan untuk keperluan pembelajaran. Mahasiswa dipersilakan membaca, menjalankan, dan mengubah kode ini untuk belajar.

---

**Ahmad Fathan Hidayatullah** — Program Studi Sarjana Informatika, Universitas Islam Indonesia
