# Modul 18 — REST APIs, JSON, dan Retrofit

Proyek Android Studio untuk seluruh bagian modul *REST APIs, JSON, and Retrofit*.
Aplikasi contoh: **Todo App** — mengambil 200 task dari
[JSONPlaceholder](https://jsonplaceholder.typicode.com/) lewat Retrofit, lalu menampilkannya
dengan tiga state UI: **Loading**, **Success**, dan **Error**.

## Struktur

```
app/src/main/java/com/example/todoapp/
├── MainActivity.kt                  # menu 8 layar: tujuh bagian modul + latihan
├── api/
│   ├── Todo.kt                      # data class Todo + contoh JSON object/array + bacaSatuTodo()
│   ├── Post.kt                      # data class Post (Latihan 2)
│   ├── TodoApiService.kt            # @GET("todos"), @GET("todos/{id}"), @GET("posts")
│   ├── RetrofitClient.kt            # base URL + GsonConverterFactory + create()
│   └── TodoApiPalsu.kt              # implementasi palsu untuk mencoba 3 state tanpa internet
├── ui/
│   ├── TodoUiState.kt               # sealed interface: Loading, Success, Error
│   ├── TodoViewModel.kt             # loadTodos() di viewModelScope + TodoDetailViewModel
│   ├── TodoScreen.kt                # collectAsStateWithLifecycle + when + LoadingView/TodoList/ErrorView
│   └── theme/                       # Color.kt, Theme.kt (TodoAppTheme)
├── AturanApi.kt                     # aturan murni: gabungAlamat, kelompokStatus, ringkasanSelesai, pesanKegagalan
├── TampilanBersama.kt               # JudulBab, KodeBlok, Tabel
├── bab/                             # satu berkas per bagian modul
│   ├── bab-01-kenapa-data-perlu-diambil-dari-server.kt    # Bagian 2
│   ├── bab-02-client-server-dan-http.kt                   # Bagian 3
│   ├── bab-03-rest-api.kt                                 # Bagian 4
│   ├── bab-04-json.kt                                     # Bagian 5
│   ├── bab-05-mengambil-data-dengan-retrofit.kt           # Bagian 6
│   └── bab-06-menampilkan-loading-success-dan-error.kt    # Bagian 7
├── latihan/latihan-akhir.kt         # lima soal Bagian 9
└── verifikasi.kt                    # 42 pemeriksaan tanpa emulator
```

Nomor pada nama berkas bab adalah **urutan berkas**, sedangkan KDoc menyebut **nomor Bagian**
dokumennya, karena Bagian 1 adalah pendahuluan yang tidak punya kode.

| Berkas | Bagian modul |
| --- | --- |
| `api/Todo.kt` | 5.4 Memetakan JSON ke data class |
| `api/TodoApiService.kt` | 6.3 Langkah 2: interface API |
| `api/RetrofitClient.kt` | 6.4 Langkah 3: instance Retrofit |
| `ui/TodoUiState.kt` | 7.1 Tiga state UI |
| `ui/TodoViewModel.kt` | 7.2 ViewModel dan coroutine |
| `ui/TodoScreen.kt` | 7.3 Screen menggambar setiap state |
| `MainActivity.kt` | 7.4 Menghubungkan ke MainActivity |
| `verifikasi.kt` | 8 Pengujian, 9 Latihan 1–5 |

## Dependensi yang ditambahkan

`gradle/libs.versions.toml`:

```toml
retrofit = "3.0.0"

retrofit = { module = "com.squareup.retrofit2:retrofit", version.ref = "retrofit" }
retrofit-gson = { module = "com.squareup.retrofit2:converter-gson", version.ref = "retrofit" }
```

`app/build.gradle.kts`: `implementation(libs.retrofit)` + `implementation(libs.retrofit.gson)`.

`AndroidManifest.xml` — sering terlupa, tanpa baris ini semua request gagal:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Menjalankan

```bash
./jalankan.sh 18                # build APK debug
./jalankan.sh 18 instalDebug    # pasang ke emulator/perangkat
./jalankan.sh 18 perilaku       # 42 pemeriksaan tanpa emulator

cd modul-18-rest-api-json-dan-retrofit
./gradlew verifikasi            # jalur yang sama lewat Gradle
```

Menu aplikasi: **1–6** layar bab, **7** aplikasi sebenarnya (`TodoScreen()` dengan jaringan),
**8** latihan akhir.

## Menguji tiga state

Bagian 8 modul meminta tiga skenario. Dua di antaranya tidak nyaman diulang di kelas
(mode pesawat, mengubah endpoint), jadi layar **Bab 6** memakai `TodoApiPalsu`:

| Skenario | Cara mencoba |
| --- | --- |
| Success | chip **Berhasil** → Loading ±0,9 detik, lalu daftar 3 task |
| Error tanpa koneksi | chip **TanpaKoneksi** → `No internet connection` + tombol Retry |
| Error 404 | chip **EndpointSalah** → `Server error: 404` |

Skenario aslinya tetap dapat dicoba pada menu **7**: aktifkan mode pesawat lalu tekan Retry
(`IOException`), atau ubah `@GET("todos")` menjadi `@GET("todoss")` (`HttpException` 404).

## Catatan yang terbukti saat pengujian

- **`baseUrl` wajib diakhiri `/`.** Retrofit menerima `https://host` (path otomatis `/`), tetapi
  menolak `https://host/api` tanpa garis miring — diverifikasi di `verifikasi.kt`.
- **Nama properti data class harus sama persis dengan key JSON.** `userid` pada JSON tidak
  mengisi properti `userId`; Gson membiarkannya `0`/`null`. Diuji di `verifikasi.kt`.
- **`collectAsStateWithLifecycle()` butuh `import androidx.compose.runtime.getValue`.** Tanpa
  import itu, `by` gagal dikompilasi dengan pesan
  `Type 'State<TodoUiState>' has no method 'getValue(...)'`.
- **Tidak perlu `withContext(Dispatchers.IO)`**: fungsi `suspend` Retrofit sudah main-safe.
- **Error jaringan tidak tertangkap `catch (e: HttpException)` saja.** Koneksi mati melempar
  `IOException`; keduanya ditangani terpisah lewat `pesanKegagalan()` di `AturanApi.kt`.
