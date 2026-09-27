#!/bin/bash
# Menjalankan satu modul Kotlin tanpa Gradle dan tanpa emulator.
#
#   ./jalankan.sh              # menu contoh modul 02 (interaktif)
#   ./jalankan.sh 03           # menu contoh modul 03
#   ./jalankan.sh 03 verifikasi  # pemeriksaan otomatis modul 03
#
# Memakai Kotlin compiler bawaan Android Studio bila ada, sehingga tidak perlu
# memasang kotlinc terpisah. Bila tidak ditemukan, dipakai kotlinc dari PATH.

set -e

MODUL="${1:-02}"
PERINTAH="${2:-}"

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

echo "Modul    : $FOLDER"
echo "Compiler : $KOTLINC ($SUMBER)"
echo

# --- kompilasi seluruh berkas .kt di folder modul menjadi satu jar ----------
cd "$FOLDER"
"$KOTLINC" . -include-runtime -d app.jar

# --- jalankan ---------------------------------------------------------------
if [ -n "$PERINTAH" ]; then
    java -jar app.jar "$PERINTAH"
else
    java -jar app.jar
fi
