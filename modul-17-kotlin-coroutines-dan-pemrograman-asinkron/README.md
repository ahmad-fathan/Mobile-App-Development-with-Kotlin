# Modul 17 — Kotlin Coroutines and Asynchronous Programming

Proyek Android Studio untuk seluruh bagian modul *Kotlin Coroutines and Asynchronous Programming*.
Aplikasi contoh: **Coroutine Demo** — tombol **Load** memuat tugas (2 dtk) dan pengumuman (1 dtk)
bersamaan, **Load (fail)** memunculkan pesan error, **Cancel** menghentikan pemuatan, **Tap me**
membuktikan layar tetap merespons, dan **Block UI** sengaja membekukan main thread 3 dtk.

## Struktur

```
app/src/main/java/com/example/coroutinedemo/
├── MainActivity.kt                       # Scaffold + TasksScreen()
├── AturanPemrosesan.kt                   # transisi UI state + aturan waktu (fungsi murni, tanpa Android)
├── TampilanBersama.kt                    # JudulBab / KodeBlok / Tabel / KotakAlur / PanahBawah
├── verifikasi.kt                         # 28 pemeriksaan tanpa emulator
├── data/
│   ├── Task.kt                           # model tugas (id, title, course)
│   └── TaskRepository.kt                 # server tiruan: withContext(IO) + delay, shouldFail
├── ui/
│   ├── main/
│   │   ├── TasksUiState.kt               # snapshot layar (isLoading, tasks, announcement, error, tapCount)
│   │   ├── TasksViewModel.kt             # viewModelScope + launch + coroutineScope + 2 async + try/catch
│   │   └── TasksScreen.kt                # TasksScreen (route) + TasksContent + 3 preview
│   └── theme/
├── bab/
│   ├── bab-01-mengapa-pemrograman-asinkron.kt      # Bagian 2
│   ├── bab-02-dasar-dasar-coroutine.kt             # Bagian 3
│   ├── bab-03-coroutine-scope-dan-dispatcher.kt    # Bagian 4
│   ├── bab-04-structured-concurrency.kt            # Bagian 5
│   ├── bab-05-menangani-error.kt                   # Bagian 6
│   ├── bab-06-praktik-coroutine-demo-mvvm.kt       # Bagian 7
│   └── bab-07-workmanager-dan-service.kt           # Bagian 8
└── latihan/latihan-akhir.kt              # enam soal Bagian 9 (soal 1 dan 6 dijalankan sungguhan)
```

Nomor berkas `bab-NN` urut dari 1, sedangkan KDoc-nya menyebut nomor **Bagian** dokumen
(`bab-01` = Bagian 2) — karena Bagian 1 adalah pendahuluan tanpa kode.

## Menjalankan

```bash
cd modul-17-kotlin-coroutines-dan-pemrograman-asinkron
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
./gradlew assembleDebug          # app/build/outputs/apk/debug/app-debug.apk
./gradlew installDebug           # pasang ke emulator/perangkat aktif
```

Dari root repositori:

```bash
./jalankan.sh 17             # build APK
./jalankan.sh 17 perilaku    # verifikasi tanpa emulator
```

Untuk mencoba layar bab, ganti `TasksScreen()` di `MainActivity` dengan salah satu dari
`MainThreadScreen()`, `DasarCoroutineScreen()`, `ScopeDispatcherScreen()`,
`StructuredConcurrencyScreen()`, `PenangananErrorScreen()`, `PraktikScreen()`,
`DiLuarCoroutineScreen()`, atau `LatihanScreen()`.

## Verifikasi

| Jalur | Perintah | Hasil terverifikasi |
| --- | --- | --- |
| Kompilasi + lint | `./gradlew clean compileDebugKotlin lintDebug assembleDebug` | BUILD SUCCESSFUL, 0 error |
| Aturan tanpa emulator | `./jalankan.sh 17 perilaku` | **28/28 lulus** |
| Perilaku di emulator | `python3 _alat-uji/probe-modul17.py` | **17/17 lulus**, 0 FATAL di logcat |
| Layar bab di emulator | `_alat-uji/smoke-bab-modul17.sh` | **8/8 layar** render, 0 FATAL |

Yang diukur `verifikasi.kt` (stdlib + kotlinx-coroutines-core, tanpa AndroidX):

- sekuensial 2000 + 1000 ms **terukur** ±3,1 dtk vs konkuren **terukur** ±2,1 dtk, plus rumus
  `totalWaktuSekuensial` / `totalWaktuKonkuren` yang dipakai layar bab;
- `job.cancel()` menghentikan pekerjaan di titik suspensi dan `CancellationException` memang dilempar;
- `withContext(Dispatchers.IO)` benar-benar berjalan di thread lain;
- child yang gagal membatalkan sibling-nya, dan `IOException` sampai ke pemanggil;
- transisi UI state (`mulaiMemuat` → `selesaiMemuat` / `gagalMemuat` / `dibatalkan`, `setelahKetuk`) —
  fungsi yang sama dipakai `TasksViewModel`;
- repository mengembalikan data yang benar dan `shouldFail = true` melempar `IOException`;
- Latihan 1 (async ketiga 1500 ms tidak menambah total) dan Latihan 6 (`urutkanJudul`).

## Perilaku yang dibuktikan di emulator (Medium_Phone_API_36.0)

| Kasus | Hasil terukur |
| --- | --- |
| Load, lalu Tap me 3× | `Tap me: 3`; data tampil ±2,4 dtk → suspend tidak menghambat thread |
| Lama pemuatan | 2,32–2,57 dtk, bukan 3 dtk → `async` berjalan bersamaan |
| Load (fail) | `Failed to load: Server is not reachable`, tidak ada tugas termuat |
| Load lalu segera Cancel | `Loading cancelled`, data tidak pernah muncul |
| Block UI lalu Tap me | perintah tap tertahan 3,11 dtk vs 0,05 dtk tanpa blok → main thread terhambat |
| Rotasi saat memuat | pemuatan berlanjut, data lengkap sesudahnya |
| Latihan 1 di layar | bersamaan **2025 ms**, satu per satu **4541 ms** (diukur, bukan dikarang) |

Skrip probe dan smoke test sengaja disimpan **di luar repo**: `SOURCE CODE/_alat-uji/`.
