#!/usr/bin/env python3
"""Probe perilaku Student Profile Editor (modul 09) lewat adb + uiautomator.

Menjalankan Tabel 5 modul (T1-T6) pada perangkat/emulator yang aktif. Mencetak teks
yang terlihat, status enabled tombol Save/Cancel (label Compose ada di node terpisah,
jadi dipetakan lewat bounds), dan menyimpan tangkapan layar ke /tmp/m09-<langkah>.png.

    python3 probe-modul09.py

Keluaran akhir: daftar OK/GAGAL per kasus uji, dan jumlah FATAL di logcat crash.
"""
import re
import subprocess
import sys
import time
from pathlib import Path

ADB = str(Path.home() / "Library/Android/sdk/platform-tools/adb")
PKG = "com.example.studentprofileeditor"
LAUNCH = f"{PKG}/.MainActivity"

# Koordinat dari bounds uiautomator pada Medium_Phone_API_36.0 (1080x2400).
EDIT_BIO = (208, 884)
FIELD = (500, 840)
BTN_SAVE = (187, 1124)
BTN_CANCEL = (430, 1124)

TETAP = {"BS", "Online", "Budi Santoso", "Informatics student", "Student bio", "Bio",
         "Save", "Cancel", "Edit bio", "Student Profile"}


def adb(*args, timeout=180):
    return subprocess.run([ADB, *args], capture_output=True, text=True, timeout=timeout).stdout


def tap(xy, jeda=1.0):
    adb("shell", "input", "tap", str(xy[0]), str(xy[1]))
    time.sleep(jeda)


def dump():
    adb("shell", "uiautomator", "dump", "/sdcard/ui.xml")
    return adb("shell", "cat", "/sdcard/ui.xml")


def foto(nama):
    with open(f"/tmp/m09-{nama}.png", "wb") as f:
        f.write(subprocess.run([ADB, "exec-out", "screencap", "-p"], capture_output=True).stdout)


def kotak(b):
    return tuple(map(int, re.findall(r"-?\d+", b)))


def potret(x):
    """(field, semua_teks, tombol) — tombol: label -> enabled.

    Tombol Compose yang nonaktif tidak selalu menjadi node class=Button: Material 3
    menggantinya dengan android.view.View ber-enabled=false pada kotak yang sama
    (Save nonaktif pun hilang dari accessibility tree sebagai Button). Karena itu
    status ditentukan oleh node mana pun yang kotaknya menutupi label tombol:
    bila ada satu saja yang enabled=false, tombolnya nonaktif.
    """
    field, teks, label, kandidat = "", [], [], []
    for m in re.finditer(r"<node[^>]*>", x):
        s = m.group(0)

        def g(k, b=s):
            mm = re.search(k + r'="([^"]*)"', b)
            return mm.group(1) if mm else ""

        t, cls, bnd = g("text"), g("class"), g("bounds")
        if not bnd:
            continue
        if t:
            teks.append(t)
            label.append((t, kotak(bnd)))
            if cls.endswith("EditText"):
                field = t
        if cls.endswith("Button") or (cls == "android.view.View" and g("enabled") == "false"):
            kandidat.append([kotak(bnd), g("enabled"), cls])

    peta = {}
    for box, enabled, cls in kandidat:
        x1, y1, x2, y2 = box
        nama = next((t for t, lb in label
                     if lb[0] >= x1 - 2 and lb[1] >= y1 - 2 and lb[2] <= x2 + 2 and lb[3] <= y2 + 2), None)
        if not nama:
            continue
        # enabled=false menang: itu penanda tombol benar-benar dinonaktifkan.
        if nama not in peta or enabled == "false":
            peta[nama] = enabled
    return field, teks, peta


def lihat(judul):
    field, teks, tombol = potret(dump())
    print(f"\n--- {judul}")
    print(f"    field: {field!r}")
    print(f"    teks : {[t for t in teks if t not in TETAP]}")
    print(f"    tombol: {tombol}")
    return field, teks, tombol


def mulai():
    adb("shell", "settings", "put", "system", "accelerometer_rotation", "0")
    adb("shell", "settings", "put", "system", "user_rotation", "0")
    adb("shell", "am", "force-stop", PKG)
    adb("shell", "am", "start", "-W", "-n", LAUNCH)
    time.sleep(3)


def buka_edit():
    tap(EDIT_BIO)
    tap(FIELD, jeda=1.2)


def kosongkan():
    adb("shell", "input", "keycombination", "113", "29")   # CTRL+A
    time.sleep(0.6)
    adb("shell", "input", "keyevent", "67")                # DEL
    time.sleep(0.6)


def ketik(s):
    adb("shell", "input", "text", s.replace(" ", "%s"))
    time.sleep(1.4)


def isi(kata):
    buka_edit()
    kosongkan()
    if kata:
        ketik(kata)


r = {}
mulai()
field, teks, tombol = lihat("keadaan awal: mode baca")
foto("awal")
r["awal_mode_baca"] = "Edit bio" in teks

# ---- T3: draf kosong → Save nonaktif, ketukan Save tidak berefek -----------
isi("")
field, teks, tombol = lihat("T3: draf kosong")
foto("T3-kosong")
r["T3_save_nonaktif"] = tombol.get("Save") == "false"
r["T3_pesan_kesalahan"] = any("must contain 1" in t for t in teks)
tap(BTN_SAVE)
field2, teks2, _ = lihat("T3: setelah Save diketuk pada draf kosong")
r["T3_tetap_di_editor"] = "Edit bio" not in teks2

# ---- T3b: hanya spasi ------------------------------------------------------
ketik("   ")
field, teks, tombol = lihat("T3b: draf hanya spasi")
foto("T3b-spasi")
r["T3b_save_nonaktif"] = tombol.get("Save") == "false"
r["T3b_penghitung_mentah"] = "3/80 characters" in teks

# ---- T4: 81 karakter -------------------------------------------------------
isi("a" * 81)
field, teks, tombol = lihat("T4: draf 81 karakter")
foto("T4-81")
r["T4_save_nonaktif"] = tombol.get("Save") == "false"
r["T4_pesan_kesalahan"] = any("must contain 1" in t for t in teks)
r["T4_penghitung_81"] = "81/80 characters" in teks
r["T4_tidak_dipotong"] = len(field) == 81

# ---- T5: rotasi saat mengedit ---------------------------------------------
adb("shell", "settings", "put", "system", "user_rotation", "1")
time.sleep(3)
field_l, _, _ = lihat("T5: landscape")
foto("T5-landscape")
adb("shell", "settings", "put", "system", "user_rotation", "0")
time.sleep(3)
field_p, teks_p, tombol_p = lihat("T5: kembali ke portrait")
foto("T5-portrait")
r["T5_draf_dipulihkan"] = len(field_p) == 81
r["T5_masih_mode_edit"] = "Bio" in teks_p
r["T5_tombol_kembali"] = "Save" in teks_p and "Cancel" in teks_p

# ---- T1: draf valid lalu Save ---------------------------------------------
isi("Mobile app developer")
field, teks, tombol = lihat("T1: draf valid")
foto("T1-valid")
r["T1_save_aktif"] = tombol.get("Save") == "true"
tap(BTN_SAVE)
field_s, teks_s, tombol_s = lihat("T1: setelah Save")
foto("T1-sesudah-save")
r["T1_bio_tersimpan"] = "Mobile app developer" in teks_s
r["T1_editor_tertutup"] = "Edit bio" in teks_s and "Bio" not in teks_s

# ---- T6: draf mentah "  Hi  " → Save → Edit lagi ---------------------------
isi("  Hi  ")
lihat("T6: draf mentah '  Hi  '")
foto("T6-draf-mentah")
tap(BTN_SAVE)
_, teks_s6, _ = lihat("T6: setelah Save (harus tersimpan 'Hi')")
foto("T6-sesudah-save")
r["T6_trim_saat_simpan"] = "Hi" in teks_s6 and "  Hi  " not in teks_s6
tap(EDIT_BIO)
field_e, _, _ = lihat("T6: setelah Edit lagi (harus mulai dari 'Hi')")
foto("T6-edit-lagi")
r["T6_mulai_dari_tersimpan"] = field_e == "Hi"
tap(BTN_CANCEL)

# ---- T2: draf baru lalu Cancel --------------------------------------------
isi("Bio yang dibatalkan")
tap(BTN_CANCEL)
_, teks2b, _ = lihat("T2: setelah Cancel")
foto("T2-sesudah-cancel")
r["T2_bio_tersimpan_tetap"] = "Hi" in teks2b and "Bio yang dibatalkan" not in teks2b

n_fatal = len(re.findall(r"FATAL EXCEPTION", adb("logcat", "-b", "crash", "-d")))
r["tanpa_crash"] = n_fatal == 0

print("\n================ RINGKASAN ================")
for k, v in r.items():
    print(f"  {'OK   ' if v else 'GAGAL'} {k}")
print(f"\n{sum(r.values())}/{len(r)} lulus — FATAL di logcat crash: {n_fatal}")
sys.exit(0 if all(r.values()) else 1)
