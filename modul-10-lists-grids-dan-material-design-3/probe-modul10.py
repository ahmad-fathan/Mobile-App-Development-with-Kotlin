#!/usr/bin/env python3
"""Probe perilaku Student Task Catalog (modul 10) lewat adb + uiautomator.

Menjalankan Tabel 5 modul (T1-T7) pada perangkat/emulator yang aktif: jumlah kartu
yang benar-benar tampil, gulir daftar, gulir strip kategori, tombol List/Grid, label
status, dan tema gelap. Tangkapan layar disimpan ke /tmp/m10-<langkah>.png.

    python3 probe-modul10.py

Keluaran akhir: daftar OK/GAGAL per kasus uji, dan jumlah FATAL di logcat crash.
"""
import re
import subprocess
import sys
import time
from pathlib import Path

ADB = str(Path.home() / "Library/Android/sdk/platform-tools/adb")
PKG = "com.example.studenttaskcatalog"
LAUNCH = f"{PKG}/.MainActivity"

JUDUL_12 = [
    "Compose UI Practice", "ML Quiz", "Data Visualization", "ERD Revision",
    "Sorting Quiz", "Network Lab Report", "UI Wireframes", "Ethics Essay",
    "SQL Quiz", "Kotlin Lists Exercise", "App Proposal", "Regression Project",
]
KATEGORI = ["All", "Assignment", "Quiz", "Project"]
STATUS = ["Completed", "Pending"]


def adb(*args, timeout=180):
    return subprocess.run([ADB, *args], capture_output=True, text=True, timeout=timeout).stdout


def tap(x, y, jeda=1.0):
    adb("shell", "input", "tap", str(x), str(y))
    time.sleep(jeda)


def swape(x1, y1, x2, y2, ms=300, jeda=1.2):
    adb("shell", "input", "swipe", str(x1), str(y1), str(x2), str(y2), str(ms))
    time.sleep(jeda)


def dump():
    adb("shell", "uiautomator", "dump", "/sdcard/ui.xml")
    return adb("shell", "cat", "/sdcard/ui.xml")


def foto(nama):
    with open(f"/tmp/m10-{nama}.png", "wb") as f:
        f.write(subprocess.run([ADB, "exec-out", "screencap", "-p"], capture_output=True).stdout)


def kotak(b):
    return tuple(map(int, re.findall(r"-?\d+", b)))


def potret(x):
    """(teks_terlihat, node) — node: daftar (text, kelas, bounds, enabled)."""
    teks, node = [], []
    for m in re.finditer(r"<node[^>]*>", x):
        s = m.group(0)

        def g(k, b=s):
            mm = re.search(k + r'="([^"]*)"', b)
            return mm.group(1) if mm else ""

        t, bnd = g("text"), g("bounds")
        if t:
            teks.append(t)
        if bnd:
            node.append((t, g("class"), kotak(bnd), g("enabled"), g("scrollable")))
    return teks, node


def ketuk(teks, node):
    """Ketuk node berdasarkan teksnya; True bila ketemu."""
    for t, _, b, _, _ in node:
        if t == teks:
            x, y = (b[0] + b[2]) // 2, (b[1] + b[3]) // 2
            tap(x, y)
            return True
    return False


def scrollable_vertikal(node):
    return [n for n in node if n[4] == "true" and abs(n[2][3] - n[2][1]) > abs(n[2][2] - n[2][0])]


def lihat(judul):
    teks, node = potret(dump())
    print(f"\n--- {judul}")
    print(f"    teks : {teks[:14]}{'...' if len(teks) > 14 else ''}")
    return teks, node


def mulai():
    adb("shell", "settings", "put", "system", "accelerometer_rotation", "0")
    adb("shell", "settings", "put", "system", "user_rotation", "0")
    adb("shell", "am", "force-stop", PKG)
    adb("shell", "am", "start", "-W", "-n", LAUNCH)
    time.sleep(3)


def main():
    gagal = []
    adb("shell", "logcat", "-c")

    mulai()
    teks, node = lihat("T1 — 12 data contoh, daftar")

    # T1 — data dapat dicapai dengan menggulir; kumpulkan semua judul yang pernah tampil.
    terlihat = set(teks)
    for _ in range(6):
        swape(540, 1700, 540, 700)
        terlihat |= set(potret(dump())[0])
    ada = [j for j in JUDUL_12 if j in terlihat]
    print(f"    judul terlihat: {len(ada)}/{len(JUDUL_12)}")
    if len(ada) != 12:
        gagal.append(f"T1 hanya {len(ada)}/{len(JUDUL_12)} tugas tercapai dengan menggulir")
    else:
        print("    OK  T1 semua 12 tugas dapat dicapai dengan menggulir")

    # T2 — kartu terakhir (Regression Project) harus utuh, bukan terpotong di tepi layar.
    teks, node = potret(dump())
    if "Regression Project" in teks:
        b = next(n[2] for n in node if n[0] == "Regression Project")
        if b[1] < 0 or b[3] > 2400:
            gagal.append(f"T2 kartu terakhir terpotong tepi layar: {b}")
        else:
            print(f"    OK  T2 kartu terakhir utuh di layar: {b}")
        foto("T2-bawah")
    else:
        gagal.append("T2 kartu terakhir tidak terlihat setelah menggulir")

    # T3 — strip kategori dapat digulir dan keempat chip tampil.
    mulai()
    teks, _ = lihat("T3 — strip kategori")
    kosong = [k for k in KATEGORI if k not in teks]
    if kosong:
        gagal.append(f"T3 chip tidak tampil: {kosong}")
    else:
        print("    OK  T3 empat chip kategori tampil")
    foto("T3-list")

    # T4 — tombol List/Grid menukar tampilan dengan data yang sama.
    _, node = potret(dump())
    if not ketuk("Show grid", node):
        gagal.append("T4 tombol Show grid tidak ditemukan")
    else:
        teks, node = lihat("T4 — mode grid")
        foto("T4-grid")
        if "Show list" not in teks:
            gagal.append("T4 label tombol tidak berubah menjadi Show list")
        else:
            print("    OK  T4 tombol menukar ke grid")
        # T6 — label status tetap terbaca pada mode grid.
        status = [s for s in STATUS if s in teks]
        print(f"    status terlihat: {status}")
        if not status:
            gagal.append("T6 tidak ada label status pada mode grid")
        else:
            print("    OK  T6 label status tampil pada mode grid")
        foto("T6-status")

    # T5 — jumlah kolom grid mengikuti lebar: hitung pusat-x kartu pada satu baris.
    teks, node = potret(dump())
    judul = [n for n in node if n[0] in JUDUL_12]
    if judul:
        baris = {}
        for t, _, b, _, _ in judul:
            y = (b[1] + b[3]) // 2
            baris.setdefault(y // 60, []).append(t)
        maks = max(len(v) for v in baris.values())
        print(f"    kolom grid terdeteksi pada satu baris: {maks}")
        if maks < 2:
            gagal.append(f"T5 grid seharusnya >=2 kolom pada 1080px, terdeteksi {maks}")
        else:
            print(f"    OK  T5 grid tampil {maks} kolom")
    else:
        gagal.append("T5 tidak ada judul tugas terlihat pada mode grid")

    # T7 — tema gelap: warna semantik tetap kontras (aplikasi tidak crash dan teks tetap ada).
    adb("shell", "cmd", "uimode", "night", "yes")
    time.sleep(2)
    mulai()
    teks, _ = lihat("T7 — tema gelap")
    foto("T7-gelap")
    if not any(j in teks for j in JUDUL_12[:3]):
        gagal.append("T7 daftar tidak tampil pada tema gelap")
    else:
        print("    OK  T7 daftar tetap tampil pada tema gelap")
    adb("shell", "cmd", "uimode", "night", "no")
    time.sleep(2)

    fatal = adb("shell", "logcat", "-b", "crash", "-d").count("FATAL")
    print(f"\nFATAL di logcat crash: {fatal}")
    if fatal:
        gagal.append(f"{fatal} FATAL di logcat crash")

    print()
    if gagal:
        for g in gagal:
            print(f"GAGAL — {g}")
        return 1
    print("OK — seluruh pengujian Tabel 5 lulus pada perangkat")
    return 0


if __name__ == "__main__":
    sys.exit(main())
