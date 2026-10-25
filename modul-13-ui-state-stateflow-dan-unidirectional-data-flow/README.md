# Modul 13 — UI State, StateFlow, and Unidirectional Data Flow

Proyek Android Studio untuk seluruh bagian modul *UI State, StateFlow, and Unidirectional Data Flow*.
Aplikasi contoh: **Study Tasks** — ketik judul, tekan **Add task**, centang untuk menandai selesai,
filter **Show incomplete only**, hapus per baris, dan **Clear completed** (latihan).

Yang diuji modul ini bukan tampilan, melainkan tiga hal: apakah satu layar dimodelkan sebagai satu
snapshot state, apakah setiap aksi punya jalur UDF yang jelas, dan apakah UI hanya menerima aliran
read-only.

## Struktur

```
app/src/main/java/com/example/tasks/
├── MainActivity.kt                 # Scaffold + TasksRoute()
├── Task.kt                         # model tugas (id, title, isCompleted) + idTugasBaru()
├── TasksUiState.kt                 # snapshot layar + derived state + teks pesan
├── TasksViewModel.kt               # MutableStateFlow privat, StateFlow read-only, 6 aksi
├── TasksScreen.kt                  # TasksRoute + TasksScreen + TasksList + 3 preview
├── TampilanBersama.kt              # JudulBab / KodeBlok / Tabel / KotakAlur untuk berkas bab
├── verifikasi.kt                   # aturan state/UDF + semua tabel modul + 64 pemeriksaan
├── bab/
│   ├── bab-01-ui-state-model-dan-derived-state.kt
│   ├── bab-02-stateflow-dan-koleksi-lifecycle.kt
│   ├── bab-03-unidirectional-data-flow.kt
│   ├── bab-04-umur-state-dan-batas-stateflow.kt
│   └── bab-05-kesalahan-umum-dan-praktik.kt
├── latihan/latihan-akhir.kt        # simulasi Delete a Task + Clear completed
└── ui/theme/                       # Color.kt, Theme.kt, Type.kt
```

Pemetaan berkas → bagian modul:

| Berkas | Bagian modul |
| --- | --- |
| `bab-01` | Conflicting values, what is UI state, state vs events, ownership, model, derived state, copy() |
| `bab-02` | What is StateFlow, Flow/StateFlow/Compose State, private mutable state, update(), route, lifecycle-aware collection |
| `bab-03` | UDF, UDF & MVVM, add/toggle/filter, render state, stable identity, trace satu interaksi |
| `bab-04` | Recomposition/rotation/process death, StateFlow bukan event queue |
| `bab-05` | Common mistakes, setup proyek, guided practice, independent practice, check your understanding |

## Menjalankan

```bash
cd modul-13-ui-state-stateflow-dan-unidirectional-data-flow
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug          # app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat aktif
```

Dari root repositori:

```bash
./jalankan.sh 13             # build APK
./jalankan.sh 13 perilaku    # verifikasi tanpa emulator
```

Untuk mencoba layar bab, ganti `TasksRoute()` di `MainActivity` dengan salah satu dari
`UiStateScreen()`, `StateFlowScreen()`, `UdFlowScreen()`, `KehidupanStateScreen()`,
`KesalahanUmumScreen()`, atau `LatihanScreen()`.

## Verifikasi

| Jalur | Perintah | Hasil terverifikasi |
| --- | --- | --- |
| Kompilasi + lint | `./gradlew clean compileDebugKotlin lintDebug assembleDebug` | BUILD SUCCESSFUL, 0 error, 0 warning kompilasi |
| Aturan tanpa emulator | `./jalankan.sh 13 perilaku` | **64/64 lulus** |
| Perilaku di emulator | `python3 _alat-uji/probe-modul13.py` | **21/21 lulus**, 0 FATAL di logcat |

Yang diperiksa `verifikasi.kt` (Kotlin stdlib saja, tanpa Android):

- aturan murni `judulTersimpan()` dan stateSetelahTambah/Status/Hapus/HapusSelesai — fungsi yang
  sama dipakai ViewModel, jadi uji dan aplikasi tidak dapat berbeda;
- derived state: `completedCount`, `visibleTasks`, `summaryText` selalu konsisten dengan daftar;
- `copy()` menghasilkan snapshot baru tanpa mengubah snapshot lama;
- filter tidak mengubah daftar lengkap, ringkasan tetap menghitung daftar lengkap;
- penghapusan memakai ID sehingga tetap benar saat filter aktif;
- seluruh tabel modul: state vs event, pemilik nilai, peran API, MVVM/UDF/StateFlow, trace enam
  langkah, perilaku lifecycle, antrean StateFlow, kesalahan umum, berkas + dependensi proyek,
  guided practice, syarat independent practice, dan tujuh tanya-jawab.

## Perilaku yang dibuktikan di emulator (Medium_Phone_API_36.0)

| Kasus | Harapan |
| --- | --- |
| Layar awal | "My tasks", "0 of 0 tasks completed", empty state |
| Add task | tugas muncul, ringkasan 0 of 1, input dikosongkan oleh state (bukan oleh layar) |
| Add dengan input kosong | pesan "Enter a task title.", tidak ada tugas baru |
| Mengetik setelah error | pesan hilang (state, bukan event sekali jalan) |
| Centang checkbox | ringkasan 1 of 2 — checkbox tidak berubah sendiri |
| Filter incomplete-only | tugas selesai hilang dari daftar, ringkasan tetap 1 of 2 |
| Delete saat filter aktif | tugas terpilih hilang, ringkasan menjadi 1 of 1 |
| Clear completed | daftar kosong, ringkasan 0 of 0 |
| Rotasi | tugas tetap ada (ViewModel dipertahankan) |
| Back lalu buka lagi | daftar kosong (state hanya di memori) |

Skrip probe sengaja disimpan **di luar repo**: `SOURCE CODE/_alat-uji/probe-modul13.py`.
