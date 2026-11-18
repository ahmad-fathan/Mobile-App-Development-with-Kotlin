# Modul 16 — Aplikasi Multi-Screen dan Pola Navigasi

Proyek Android Studio untuk seluruh bagian modul *Aplikasi Multi-Screen dan Pola Navigasi*.
Aplikasi contoh: **Mini Campus App** — empat layar (Home, Courses, Profile, Course Detail),
satu **Navigation Bar** tiga item, dan satu **CourseViewModel** sebagai sumber data.

Modul ini menguji tiga hal yang tidak terlihat dari tampilan: apakah hierarki layar membedakan
top-level dan secondary destination, apakah Navigation Bar bereaksi terhadap destination aktif,
dan apakah layar tetap tidak mengetahui `NavController` (MVVM + UDF).

## Struktur

```
app/src/main/java/com/example/minicampus/
├── MainActivity.kt                 # setContent { MiniCampusTheme { CampusApp() } }
├── model/Course.kt                 # data class Course(id, title, description)
├── viewmodel/CourseViewModel.kt    # data dummy + StateFlow + getCourseById(id)
├── navigation/
│   ├── Destinations.kt             # Home, Courses, Profile (object), CourseDetail(Int) (@Serializable)
│   └── CampusApp.kt                # Scaffold + NavigationBar + NavHost + navigateToTopLevel()
├── ui/screens/
│   ├── HomeScreen.kt               # "Welcome to Mini Campus"
│   ├── CoursesScreen.kt            # dua kartu mata kuliah, callback onCourseClick(id)
│   ├── CourseDetailScreen.kt       # judul + deskripsi, "Course not found", tombol Back
│   └── ProfileScreen.kt            # identitas mahasiswa dummy
├── ui/theme/                       # Color.kt, Theme.kt (MiniCampusTheme), Type.kt
└── verifikasi.kt                   # model back stack + 40 pemeriksaan (tanpa emulator)
```

Pemetaan berkas → bagian modul:

| Berkas | Bagian modul |
| --- | --- |
| `model/Course.kt` | 9.1 Langkah 1: Model |
| `viewmodel/CourseViewModel.kt` | 9.2 Langkah 2: ViewModel |
| `navigation/Destinations.kt` | 9.3 Langkah 3: Type-safe route |
| `ui/screens/*.kt` | 9.4 Langkah 4: Empat layar |
| `navigation/CampusApp.kt` | 9.5 Langkah 5: Navigasi |
| `MainActivity.kt` | 9.6 Langkah 6: Memanggil CampusApp |
| `verifikasi.kt` | 10 Praktik pertukaran data, 11 Pengujian, 12 Latihan 1–3 |

## Dependensi yang ditambahkan

`gradle/libs.versions.toml`:

```toml
navigationCompose = "2.10.2"

androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-material-icons-core = { group = "androidx.compose.material", name = "material-icons-core" }

kotlin-serialization = { id = "org.jetbrains.kotlin.plugin.serialization", version.ref = "kotlin" }
```

Plugin serialization dipasang di `build.gradle.kts` tingkat proyek (`apply false`) dan di
`app/build.gradle.kts`. `material-icons-core` tidak diberi versi karena mengikuti Compose BOM.

## Menjalankan

```bash
cd modul-16-aplikasi-multi-screen-dan-pola-navigasi
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug          # app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat aktif
```

Dari root repositori:

```bash
./jalankan.sh 16             # build APK
./jalankan.sh 16 perilaku    # 40 pemeriksaan tanpa emulator (./gradlew verifikasi)
```

## Verifikasi

| Jalur | Perintah | Hasil terverifikasi |
| --- | --- | --- |
| Kompilasi + APK | `./gradlew clean assembleDebug` | BUILD SUCCESSFUL, 0 error, 0 warning Kotlin |
| Lint | `./gradlew lintDebug` | 0 error (warning hanya versi tooling/dependensi lebih baru) |
| Aturan tanpa emulator | `./jalankan.sh 16 perilaku` | **40/40 lulus** |
| Perilaku di emulator | `python3 probe-modul16.py` | **19/19 lulus**, 0 FATAL di logcat |

Yang diperiksa `verifikasi.kt` (memakai kode aplikasi yang sebenarnya — `CourseViewModel` dan
`navigation/Destinations.kt` — ditambah model back stack yang meniru `NavController`):

- Tabel 4: sembilan skenario pengujian, dari Home terpilih sampai Back di Home menutup aplikasi;
- Tabel 5: isi back stack tiap langkah (`Home → Courses → CourseDetail(courseId=1)`, dst.);
- `popUpTo(Home) { saveState = true }` + `launchSingleTop` + `restoreState` pada
  `navigateToTopLevel()`: menu tidak menumpuk, menu aktif tidak menambah salinan;
- `showBottomBar`: Navigation Bar hilang hanya di Course Detail;
- aliran data: yang dikirim antarlayar hanya `courseId`, data dibaca ulang lewat
  `getCourseById()`, dan ID tak dikenal menghasilkan `null` (layar menampilkan "Course not found");
- Latihan 1–3: mata kuliah ketiga, ID 99, dan efek menghapus `popUpTo`.

## Perilaku yang dibuktikan di emulator (Medium_Phone_API_36.0)

| Kasus (Tabel 4) | Hasil terverifikasi |
| --- | --- |
| Jalankan aplikasi | "Welcome to Mini Campus", item **Home** terpilih |
| Tekan Courses / Profile / Home | layar berganti dan item terpilih ikut berubah |
| Tekan **Mobile App Development** | judul + "Learning Android with Jetpack Compose." |
| Layar detail | Navigation Bar tidak tampil, tidak ada item terpilih |
| Tombol **Back** di detail | kembali ke Courses, Navigation Bar tampil lagi |
| Tekan **Data Science** | detail menampilkan Data Science, bukan mata kuliah sebelumnya |
| Back sistem di detail | kembali ke Courses |
| Tekan Courses berulang lalu Back sistem | langsung Home (tanpa Courses berulang) |
| Back sistem di Home | aplikasi keluar (launcher aktif) |

Skrip probe disimpan di luar repositori: `SOURCE CODE/_alat-uji/probe-modul16.py`.
Tangkapan layar tiap langkah ditulis ke `/tmp/m16-*.png`.
