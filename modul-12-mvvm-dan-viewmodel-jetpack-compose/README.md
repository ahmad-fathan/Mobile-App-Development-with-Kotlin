# Modul 12 — MVVM and ViewModel in Jetpack Compose

Proyek Android Studio untuk **Bagian 1–13** dan **Latihan 1–6** modul *MVVM and ViewModel in Jetpack Compose*.
Aplikasi contoh: **Study Tasks** — ketik judul, tekan **Add task**, lihat tugas ber-checkbox; judul kosong ditolak dengan pesan.

## Struktur

```
app/src/main/java/com/example/studytasks/
├── MainActivity.kt                 # Bagian 7.13 — Scaffold + TasksRoute di dalam tema
├── StudyTask.kt                    # Bagian 7.1 — data class (id, title, isDone)
├── TaskRepository.kt               # Bagian 7.2 — interface data layer
├── InMemoryTaskRepository.kt       # Bagian 7.3 — implementasi di memori
├── TasksUiState.kt                 # Bagian 7.4 — UI state (draft, tasks, errorMessage)
├── TasksViewModel.kt               # Bagian 7.6–7.9 — StateFlow + aksi + Factory
├── TasksRoute.kt                   # Bagian 7.10 — viewModel() + collectAsStateWithLifecycle()
├── TasksScreen.kt                  # Bagian 7.11 — layar stateless
├── TasksList.kt                    # Bagian 7.12 — LazyColumn, key = id, Checkbox
├── TasksPreview.kt                 # Bagian 9 — tiga preview tanpa ViewModel
├── TampilanBersama.kt              # JudulBab / KodeBlok / Tabel untuk berkas bab
├── verifikasi.kt                   # aturan data layer + Tabel 2–9 + pemeriksaan (76 cek)
├── bab/
│   ├── bab-01-pendahuluan.kt                       # Bagian 1
│   ├── bab-02-satu-composable-mengerjakan-semua.kt # Bagian 2 — TaskScreenBefore
│   ├── bab-03-separation-of-concerns-dan-lapisan.kt# Bagian 3
│   ├── bab-04-pola-mvvm.kt                         # Bagian 4
│   ├── bab-05-viewmodel-dan-lifecycle.kt           # Bagian 5
│   ├── bab-06-tempat-menyimpan-state.kt            # Bagian 6
│   ├── bab-07-model-dan-repository.kt              # Bagian 7.1–7.3
│   ├── bab-08-uistate-dan-viewmodel.kt             # Bagian 7.4–7.9
│   ├── bab-09-route-screen-dan-list.kt             # Bagian 7.10–7.13, 9, 10
│   ├── bab-10-langkah-selanjutnya.kt               # Bagian 11
│   └── bab-11-menguji-dan-kesalahan-umum.kt        # Bagian 12–13
├── latihan/latihan-akhir.kt        # Latihan 1–6 (3 & 4 diimplementasikan penuh)
└── ui/theme/                       # Color.kt, Theme.kt, Type.kt
```

## Menjalankan

```bash
cd modul-12-mvvm-dan-viewmodel-jetpack-compose
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug          # app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat aktif
```

Dari root repositori:

```bash
./jalankan.sh 12             # build APK
./jalankan.sh 12 perilaku    # verifikasi tanpa emulator
```

## Verifikasi

| Jalur | Perintah | Hasil terverifikasi |
| --- | --- | --- |
| Kompilasi + lint | `./gradlew clean compileDebugKotlin lintDebug assembleDebug` | BUILD SUCCESSFUL, 0 error, 0 warning kompilasi; lint 0 error (14 warning tooling template: versi lebih baru, `colors.xml` bawaan, `backup_rules.xml`) |
| Aturan tanpa emulator | `./jalankan.sh 12 perilaku` | **76/76 lulus** |
| Perilaku di emulator | `python3 _alat-uji/probe-modul12.py` | **12/12 lulus**, 0 FATAL di logcat |

Yang diperiksa `verifikasi.kt` (Kotlin stdlib saja, tanpa Android):

- aturan data layer: `require` menolak judul kosong, `nextId++` memberi ID berbeda untuk judul sama, `getTasks()` mengembalikan salinan, `toggleTask` memakai `copy()` dan tidak mengubah ID;
- Tabel 2 (urusan), 3 (MVVM), 4 (tiga lifecycle), 5 (dampak peristiwa), 6 (pemilik state), 7 (ketahanan nilai), 8 (pemeriksaan aplikasi), 9 (kesalahan umum);
- aturan turunan yang dipakai kode: `judulTersimpan()` dan `perluPesanValidasi()`.

Yang diperiksa `probe-modul12.py` di emulator (Medium_Phone_API_36.0):

| Kasus | Harapan | Hasil |
| --- | --- | --- |
| T1 judul spasi saja | pesan "Enter a task title.", tidak ada tugas | OK |
| T2 tambah "Read Compose documentation" | tugas muncul, input dikosongkan | OK |
| T3 tekan checkbox | status selesai berubah, baris tetap satu (key stabil) | OK |
| T4 judul sama dua kali | dua baris dengan ID berbeda | OK |
| T5 rotasi | draf dan daftar tetap ada (ViewModel dipertahankan) | OK |
| T6 Back lalu buka lagi | daftar kosong (data hanya di memori) | OK |
| T7 logcat crash | 0 FATAL | OK |

Skrip probe sengaja disimpan **di luar repo**: `SOURCE CODE/_alat-uji/probe-modul12.py`.

## Mencoba berkas bab di emulator

`MainActivity` memasang `TasksRoute()` (aplikasi jadi). Untuk melihat isi bab, ganti pemanggilan itu dengan
composable `@Preview` dari berkas bab — semuanya ada di tabel berikut.

| Composable | Berkas | Bagian |
| --- | --- | --- |
| `PendahuluanScreen()` | `bab/bab-01-pendahuluan.kt` | 1 |
| `SatuComposableMengerjakanSemuaScreen()`, `PraktikTaskScreenBefore()` | `bab/bab-02-…` | 2 |
| `SeparationOfConcernsScreen()` | `bab/bab-03-…` | 3 |
| `PolaMvvmScreen()` | `bab/bab-04-…` | 4 |
| `ViewModelDanLifecycleScreen()` | `bab/bab-05-…` | 5 |
| `TempatMenyimpanStateScreen()` | `bab/bab-06-…` | 6 |
| `ModelDanRepositoryScreen()` | `bab/bab-07-…` | 7.1–7.3 |
| `UiStateDanViewModelScreen()` | `bab/bab-08-…` | 7.4–7.9 |
| `RouteScreenDanListScreen()`, `PraktikTasksScreenTanpaViewModel()` | `bab/bab-09-…` | 7.10–7.13, 9, 10 |
| `LangkahSelanjutnyaScreen()` | `bab/bab-10-…` | 11 |
| `MengujiDanKesalahanUmumScreen()` | `bab/bab-11-…` | 12–13 |
| `LatihanTasksRoute()` | `latihan/latihan-akhir.kt` | Latihan 3–4 |

## Catatan dependensi

Kedua library Lifecycle ditambahkan ke version catalog dengan versi yang sama (`lifecycleRuntimeKtx`),
sesuai Bagian 7.13 — Compose BOM tidak menentukan versi Lifecycle:

```toml
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleRuntimeKtx" }
androidx-lifecycle-runtime-compose = { group = "androidx.lifecycle", name = "lifecycle-runtime-compose", version.ref = "lifecycleRuntimeKtx" }
```
