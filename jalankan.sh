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
#
# Modul konsol (02–05) memakai Kotlin compiler bawaan Android Studio bila ada,
# sehingga tidak perlu memasang kotlinc terpisah. Modul Android (punya gradlew)
# otomatis dialihkan ke Gradle.

set -e

MODUL="${1:-02}"
PERINTAH="${2:-}"

# --- cari folder modul ------------------------------------------------------
FOLDER=""
for kandidat in modul-"$MODUL"-*; do
    if [ -d "$kandidat" ]; then
        FOLDER="$kandidat"
        break
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
        BERKAS=("$VERIFIKASI")
        while IFS= read -r berkas; do
            [ "$berkas" = "$VERIFIKASI" ] && continue
            grep -q "^import androidx" "$berkas" || BERKAS+=("$berkas")
        done < <(find "$(dirname "$VERIFIKASI")" -maxdepth 1 -name '*.kt')
        "$KOTLINC" "${BERKAS[@]}" -include-runtime -d build/verifikasi/verifikasi.jar
        java -jar build/verifikasi/verifikasi.jar
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
