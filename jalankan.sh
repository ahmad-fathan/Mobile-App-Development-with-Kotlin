#!/bin/bash
# Menjalankan satu modul.
#
#   ./jalankan.sh              # menu contoh modul 02 (interaktif)
#   ./jalankan.sh 03           # menu contoh modul 03
#   ./jalankan.sh 03 verifikasi  # pemeriksaan otomatis modul 03
#   ./jalankan.sh 06           # build APK modul Android (modul 06 ke atas)
#   ./jalankan.sh 06 installDebug  # pasang APK ke emulator/perangkat
#   ./jalankan.sh 09 perilaku     # periksa perilaku tanpa emulator (verifikasi.kt)
#   ./jalankan.sh 10 perilaku     # periksa aturan list/grid modul 10 tanpa emulator
#   ./jalankan.sh 11 perilaku     # periksa aturan desain + layout adaptif modul 11
#   ./jalankan.sh 12 perilaku     # periksa aturan data layer + MVVM modul 12
#   ./jalankan.sh 13 perilaku     # periksa aturan UI state + StateFlow + UDF modul 13
#   ./jalankan.sh 15 perilaku     # periksa aturan navigation + back stack modul 15
#   ./jalankan.sh 15s perilaku    # modul 15 versi sederhana (folder *-sederhana)
#   ./jalankan.sh 16 perilaku     # periksa aturan multi-screen + back stack modul 16
#   ./jalankan.sh 17 perilaku     # periksa aturan coroutine (sekuensial vs konkuren) modul 17
#   ./jalankan.sh 18 perilaku     # periksa aturan REST API, JSON, dan Retrofit modul 18
#
# Modul konsol (02–05) memakai Kotlin compiler bawaan Android Studio bila ada,
# sehingga tidak perlu memasang kotlinc terpisah. Modul Android (punya gradlew)
# otomatis dialihkan ke Gradle.

set -e

MODUL="${1:-02}"
PERINTAH="${2:-}"

# --- cari folder modul ------------------------------------------------------
# Suffix "s" = versi sederhana dari modul bernomor sama (mis. 15s ->
# modul-15-*-sederhana). Tanpa suffix, pola lama tetap dipakai sehingga
# `./jalankan.sh 15` selalu menunjuk modul versi lengkap.
FOLDER=""
for kandidat in modul-"${MODUL%s}"-*; do
    if [ -d "$kandidat" ]; then
        case "$MODUL" in
            *s) case "$kandidat" in *-sederhana) FOLDER="$kandidat" ;; esac ;;
            *) case "$kandidat" in *-sederhana) ;; *) FOLDER="$kandidat" ;; esac ;;
        esac
        [ -n "$FOLDER" ] && break
    fi
done

if [ -z "$FOLDER" ]; then
    echo "Folder modul-$MODUL-* tidak ditemukan di $(pwd)."
    echo "Modul yang tersedia:"
    ls -d modul-* 2>/dev/null || true
    exit 1
fi

# --- modul Android (punya Gradle wrapper) -----------------------------------
if [ -x "$FOLDER/gradlew" ]; then
    echo "Modul    : $FOLDER (proyek Android)"
    echo "Perintah : Gradle"
    echo
    cd "$FOLDER"
    if [ "$PERINTAH" = "verifikasi" ]; then
        ./gradlew assembleDebug
    elif [ "$PERINTAH" = "perilaku" ]; then
        # Modul yang mendaftarkan tugas Gradle `verifikasi` (mis. modul 16) memakai Gradle,
        # karena kode yang diuji memerlukan androidx/kotlinx di classpath.
        if grep -q 'tasks.register<JavaExec>("verifikasi")' app/build.gradle.kts 2>/dev/null; then
            ./gradlew verifikasi
            exit $?
        fi
        # Modul Android yang menyertakan verifikasi.kt (Kotlin stdlib saja) dapat
        # diperiksa tanpa Gradle dan tanpa emulator: kompilasi berkas itu sendiri.
        KOTLINC_AS="/Applications/Android Studio.app/Contents/plugins/Kotlin/kotlinc/bin/kotlinc"
        JBR_AS="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
        if [ -x "$KOTLINC_AS" ]; then
            KOTLINC="$KOTLINC_AS"
            export JAVA_HOME="${JAVA_HOME:-$JBR_AS}"
        else
            KOTLINC="$(command -v kotlinc)"
        fi
        SUMBER="app/src/main/java"
        VERIFIKASI="$(find "$SUMBER" -name 'verifikasi.kt' | head -1)"
        if [ -z "$VERIFIKASI" ]; then
            echo "Modul ini tidak menyertakan verifikasi.kt."
            exit 1
        fi
        rm -rf build/verifikasi && mkdir -p build/verifikasi
        # verifikasi.kt hanya memakai Kotlin stdlib, tetapi ia MEMAKAI model data layer di paket
        # akar (StudyTask, TasksUiState, repository). Ikutkan berkas pakar akar yang tidak
        # mengimpor AndroidX, supaya aturan yang diuji adalah kode yang benar-benar dipakai app.
        # Akar package (app/src/main/java): buang bagian path sepanjang nama package.
        PAKET="$(grep -m1 '^package ' "$VERIFIKASI" | sed 's/^package //' | tr -d '\r')"
        AKAR_SUMBER="${VERIFIKASI%/${PAKET//.//}/verifikasi.kt}"
        # verifikasi.kt sendiri + SEMUA berkas .kt di pohon sumber yang murni Kotlin (tanpa
        # AndroidX, tanpa anotasi plugin seperti @Serializable) — model data, repository, dan UI
        # state bisa berada di subpackage. Berkas ber-@Serializable butuh plugin compiler, jadi
        # dilewati karena verifikasi hanya menguji aturan, bukan route.
        BERKAS=("$VERIFIKASI")
        while IFS= read -r berkas; do
            [ "$berkas" = "$VERIFIKASI" ] && continue
            grep -q "^import androidx" "$berkas" && continue
            grep -q "@Serializable" "$berkas" && continue
            BERKAS+=("$berkas")
        done < <(find "$AKAR_SUMBER" -name '*.kt')
        # Modul yang memakai kotlinx.coroutines (modul 17) butuh jar coroutines saat kompilasi
        # DAN saat dijalankan. Jar-nya diambil dari cache Gradle karena proyek ini tidak
        # menambahkannya sebagai dependensi aplikasi.
        JAR_COROUTINES=""
        CP_KOTLINC=""
        if grep -lq "kotlinx\.coroutines" "${BERKAS[@]}" 2>/dev/null; then
            JAR_COROUTINES="$(find "$HOME/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlinx/kotlinx-coroutines-core-jvm" -name 'kotlinx-coroutines-core-jvm-*.jar' ! -name '*-sources.jar' 2>/dev/null | head -1)"
            if [ -n "$JAR_COROUTINES" ]; then
                CP_KOTLINC="-cp $JAR_COROUTINES"
            else
                echo "kotlinx-coroutines-core-jvm tidak ditemukan di cache Gradle."
                exit 1
            fi
        fi
        "$KOTLINC" "${BERKAS[@]}" $CP_KOTLINC -include-runtime -d build/verifikasi/verifikasi.jar
        if [ -n "$JAR_COROUTINES" ]; then
            PAKET="$(grep -m1 '^package ' "$VERIFIKASI" | sed 's/^package //' | tr -d '\r')"
            java -cp "build/verifikasi/verifikasi.jar:$JAR_COROUTINES" "${PAKET}.VerifikasiKt"
        else
            java -jar build/verifikasi/verifikasi.jar
        fi
    elif [ -n "$PERINTAH" ]; then
        ./gradlew "$PERINTAH"
    else
        ./gradlew assembleDebug
        echo
        echo "APK: $FOLDER/app/build/outputs/apk/debug/app-debug.apk"
        echo "Pasang ke emulator/perangkat: ./jalankan.sh $MODUL installDebug"
    fi
    exit $?
fi

# --- cari compiler Kotlin ---------------------------------------------------
KOTLINC_AS="/Applications/Android Studio.app/Contents/plugins/Kotlin/kotlinc/bin/kotlinc"
JBR_AS="/Applications/Android Studio.app/Contents/jbr/Contents/Home"

if [ -x "$KOTLINC_AS" ]; then
    KOTLINC="$KOTLINC_AS"
    export JAVA_HOME="${JAVA_HOME:-$JBR_AS}"
    SUMBER="Android Studio"
elif command -v kotlinc >/dev/null 2>&1; then
    KOTLINC="$(command -v kotlinc)"
    SUMBER="PATH"
else
    echo "Kotlin compiler tidak ditemukan."
    echo "Pasang salah satu: Android Studio, atau 'brew install kotlin'."
    exit 1
fi

# --- kompilasi seluruh berkas .kt di folder modul menjadi satu jar ----------
echo "Modul    : $FOLDER"
echo "Compiler : $KOTLINC ($SUMBER)"
echo

cd "$FOLDER"
"$KOTLINC" . -include-runtime -d app.jar

# --- jalankan ---------------------------------------------------------------
if [ -n "$PERINTAH" ]; then
    java -jar app.jar "$PERINTAH"
else
    java -jar app.jar
fi
