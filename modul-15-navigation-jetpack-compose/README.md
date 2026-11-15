# Modul 15 — Navigation with Jetpack Compose

Proyek Android Studio untuk seluruh bagian modul *Navigation with Jetpack Compose*.
Aplikasi contoh: **TaskNav** — daftar tugas → detail tugas → kembali, memakai **route type-safe**
(`@Serializable object` / `data class`) dan argumen berupa ID.

Modul ini menguji tiga hal yang tidak terlihat dari tampilan: apakah route ditulis sebagai tipe
Kotlin (bukan string), apakah data yang dikirim antar-screen seminimal mungkin (ID, bukan objek),
dan apakah screen tetap tidak mengetahui `NavController`.

## Struktur

```
app/src/main/java/com/example/tasknav/
├── MainActivity.kt                 # setContent { TaskNavTheme { AppNavHost() } }
├── data/TaskRepository.kt          # model Task + tiga data contoh + getTask(id) + getTasksByCourse()
├── navigation/Routes.kt            # TaskList, TaskDetail, About, CourseTasks, Welcome (@Serializable)
├── navigation/AppNavHost.kt        # navigation graph; satu-satunya pemegang NavController
├── ui/TaskListScreen.kt            # daftar tugas + tombol About + preview
├── ui/TaskDetailScreen.kt          # detail satu tugas + tombol ke CourseTasks + 2 preview
├── ui/CourseTasksScreen.kt         # Latihan 4: tugas satu mata kuliah, judul "Databases (1 task)"
├── ui/AboutScreen.kt               # Latihan 3: About + tabel modul
├── ui/WelcomeScreen.kt             # Latihan 6: layar pembuka "Get started"
├── TampilanBersama.kt              # JudulBab / KodeBlok / Tabel / VerifikasiRingkas
├── verifikasi.kt                   # model back stack + semua tabel modul + 39 pemeriksaan
├── bab/
│   ├── bab-01-mengapa-navigasi-dan-lima-komponen.kt
│   ├── bab-02-back-stack-route-typesafe-dan-proyek.kt
│   ├── bab-03-membangun-navigation-graph.kt
│   ├── bab-04-kirim-data-antar-screen.kt
│   └── bab-05-aplikasi-backstack-pengujian-dan-navigation3.kt
├── latihan/latihan-akhir.kt        # jawaban Latihan 1, 2, 5 (3, 4, 6 berupa layar)
└── ui/theme/                       # Color.kt, Theme.kt, Type.kt
```

Pemetaan berkas → bagian modul:

| Berkas | Bagian modul |
| --- | --- |
| `bab-01` | 2 Mengapa aplikasi butuh navigasi, 3 Lima komponen dasar, 3.1 Bagaimana komponen bekerja sama |
| `bab-02` | 4 Back stack, 5 Route string vs type-safe, 6 Menyiapkan proyek (6.1 dependensi, 6.2 struktur) |
| `bab-03` | 7 Membangun navigation graph (7.1 route, 7.2 NavController+NavHost, 7.3 event, 7.4 kembali) |
| `bab-04` | 8 Mengirim data antar-screen (8.1–8.5, termasuk argumen opsional dan ViewModel) |
| `bab-05` | 9 Aplikasi latihan, 10 Mengendalikan back stack, 11 Menguji, 12 Kesalahan umum, 13 Navigation 3 |

## Dependensi yang ditambahkan

`gradle/libs.versions.toml`:

```toml
navigationCompose = "2.10.2"

androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }

kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```

Plugin serialization dipasang di `build.gradle.kts` tingkat proyek (`apply false`) dan di
`app/build.gradle.kts`. Bila build gagal karena Android Studio lebih lama dari yang dibutuhkan
Navigation 2.10, turunkan `navigationCompose` ke `2.9.8`.

## Menjalankan

```bash
cd modul-15-navigation-jetpack-compose
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug          # app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat aktif
```

Dari root repositori:

```bash
./jalankan.sh 15             # build APK
./jalankan.sh 15 perilaku    # verifikasi tanpa emulator
```

Untuk mencoba layar bab, ganti `AppNavHost()` di `MainActivity` dengan salah satu dari
`MengapaNavigasiScreen()`, `BackStackDanRouteScreen()`, `MembangunGraphScreen()`,
`KirimDataScreen()`, `AplikasiDanKesalahanScreen()`, atau `LatihanScreen()`.

Latihan 6 (Welcome sebagai start destination) dicoba dengan satu argumen, tanpa mengubah kode lain:

```kotlin
AppNavHost(startDestination = Welcome)
```

## Verifikasi

| Jalur | Perintah | Hasil terverifikasi |
| --- | --- | --- |
| Kompilasi + APK | `./gradlew assembleDebug` | BUILD SUCCESSFUL, 0 error, 0 warning Kotlin |
| Lint | `./gradlew lintDebug` | 0 error (warning hanya versi tooling/dependensi lebih baru) |
| Aturan tanpa emulator | `./jalankan.sh 15 perilaku` | **39/39 lulus** |
| Perilaku di emulator | `python3 _alat-uji/probe-modul15.py` | **18/18 lulus**, 0 FATAL di logcat |

Yang diperiksa `verifikasi.kt` (Kotlin stdlib saja, tanpa Compose maupun Android):

- push/pop back stack: entri teratas = layar yang terlihat, Back pada start destination menutup aplikasi;
- `popUpTo<Target>` dengan dan tanpa `inclusive` (Gambar 5) serta `launchSingleTop` saat ketukan ganda;
- aturan argumen: tipe dasar, enum, nullable, dan `List` dari tipe dasar langsung didukung;
  hanya kelas buatan sendiri yang butuh `NavType` khusus; entri route hanya membawa ID;
- seluruh tabel modul: Tabel 1 (lima komponen), Tabel 2 (route string vs type-safe),
  Tabel 4 (cara kembali), Tabel 5 (tipe argumen), Tabel 6 (opsi back stack),
  Tabel 7 (skenario uji), Tabel 8 (kesalahan umum), dan perbedaan Navigation 3.

## Perilaku yang dibuktikan di emulator (Medium_Phone_API_36.0)

| Kasus (Tabel 7 modul) | Harapan |
| --- | --- |
| Jalankan aplikasi | layar **My Tasks** menampilkan tiga tugas |
| Ketuk "Submit lab report" | layar **Task #2** berisi *Computer Networks*, due 18 Nov, status open |
| Ketuk **Back** di app bar | kembali ke My Tasks |
| Buka tugas 3, lalu putar emulator | tetap **Quiz: SQL joins**, `Status: done` — argumen bertahan |
| Back sistem di layar daftar | aplikasi tertutup |
| Latihan 3: tombol **About** | layar About terbuka, Back kembali ke My Tasks |
| Latihan 4: tombol "All tasks in …" | judul **Computer Networks (1 task)** |

Skrip probe sengaja disimpan **di luar repo**: `SOURCE CODE/_alat-uji/probe-modul15.py`.
Tangkapan layar tiap langkah ditulis ke `/tmp/m15-*.png`.
