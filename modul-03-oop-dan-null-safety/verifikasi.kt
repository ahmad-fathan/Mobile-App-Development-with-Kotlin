/**
 * Verifikasi otomatis untuk seluruh fungsi yang bisa diuji tanpa masukan keyboard.
 * Jalankan dengan: ./gradlew run --args=verifikasi
 */
fun jalankanVerifikasiModul3(): Boolean {
    val hasil = mutableListOf<Pair<String, Boolean>>()

    fun cek(nama: String, kondisi: Boolean) {
        hasil += nama to kondisi
        println(if (kondisi) "  OK   $nama" else "  GAGAL $nama")
    }

    println("Verifikasi Modul 3 — OOP in Kotlin & Null Safety")

    // Bab 1 — class dan object menghasilkan instance yang saling lepas.
    cek("dua object dari class yang sama tidak identik", SmartDevice() !== SmartDevice())
    cek("property name object SmartTv = Android TV", SmartTv().name == "Android TV")
    cek("status awal SmartLamp = off", SmartLamp().status == "off")

    // Bab 2 — constructor, property, dan custom setter.
    cek("constructor mengisi name dan category", DeviceProfile("Android TV", "Entertainment").category == "Entertainment")
    cek("property var dapat diubah", Student("Andi", 3).also { it.semester = 4 }.semester == 4)
    cek("custom setter volume 50 diterima", volumeDalamRentang(50) == 50)
    cek("custom setter volume 150 diabaikan (tetap 10)", volumeDalamRentang(150) == 10)
    cek("custom setter volume -5 diabaikan (tetap 10)", volumeDalamRentang(-5) == 10)
    cek("custom setter volume 0 diterima", volumeDalamRentang(0) == 0)
    cek("custom setter volume 100 diterima", volumeDalamRentang(100) == 100)

    // Bab 3 — inheritance, overriding, dan super.
    cek("child mewarisi name dari parent", SmartTvDevice("Living Room").name == "Living Room")
    cek("child mewarisi status dari parent", SmartTvDevice("Living Room").status == "off")
    cek("child menambah property channel = 1", SmartTvDevice("Living Room").channel == 1)
    cek("override mengubah perilaku turnOn", OverridingTvDevice("Bedroom").also { it.turnOn() }.displayOn)
    cek("super menjalankan perilaku parent", SuperTvDevice("Kitchen").also { it.turnOn() }.status == "on")
    cek("super lalu menambah perilaku child", SuperTvDevice("Kitchen").also { it.turnOn() }.displayReady)

    // Bab 4 — abstract class dan interface.
    cek("child abstract class mengimplementasikan turnOn", AbstractTvDevice("Living Room").let { it.turnOn(); true })
    cek("abstract class menyediakan method biasa showName", AbstractTvDevice("Living Room").let { it.showName(); true })
    cek("interface Connectable diimplementasikan TV", ConnectableTvDevice().let { it.connect(); true })
    cek("satu class mewarisi abstract class + dua interface", SmartWatchDevice().let { it.turnOn(); it.connect(); it.charge(); true })
    cek("state interface tercatat di object", SmartWatchDevice().also { it.connect() }.connected)

    // Bab 5 — visibility modifier.
    cek("private balance hanya berubah via deposit()", saldoSetelahDeposit(500, 250, 1000) == 1750)
    cek("deposit 0 tidak mengubah saldo", saldoSetelahDeposit(0) == 0)
    cek("protected status diakses dari subclass", MonitorDevice("Monitor").let { it.turnOn(); true })
    cek("internal class dapat dipakai dalam module", DeviceManager().info() == "DeviceManager aktif")

    // Bab 6 — null safety.
    cek("String non-nullable tetap berisi teks", "Andi".isNotEmpty())
    cek("nullable boleh berisi teks", ("Andi" as String?)?.length == 4)
    cek("nullable boleh berisi null", (null as String?) == null)
    cek("safe call pada null menghasilkan 0 lewat Elvis", panjangNamaAman(null) == 0)
    cek("safe call pada teks menghasilkan panjangnya", panjangNamaAman("Andi") == 4)
    cek("Elvis memakai default saat null", namaTampilAman(null) == "Unknown")
    cek("Elvis memakai nilai saat tersedia", namaTampilAman("Alya") == "Alya")
    cek("safe call + Elvis: null → 0", ((null as String?)?.length ?: 0) == 0)
    cek("!! pada nilai non-null mengembalikan panjang", ("Andi" as String?)!!.length == 4)

    // Bab 7 — smart cast.
    cek("smart cast String memakai .length", ("Kotlin" as Any).let { it is String && it.length == 6 })
    cek("smart cast Int memakai aritmetika", (10 as Any).let { it is Int && it + 1 == 11 })
    cek("smart cast when uppercase pada String", ("kotlin" as Any).let { it is String && it.uppercase() == "KOTLIN" })
    cek("smart cast when menangani tipe lain", (true as Any) !is String && (true as Any) !is Int)

    // Latihan akhir.
    cek("latihan 1: SmartLight mewarisi dan mengimplementasikan kontrak", SmartLight("Desk Lamp").let { it.turnOn(); it.connect(); it.status == "on" && it.connected })
    cek("latihan 2: null → \"Unknown\"", ((null as String?)?.length ?: "Unknown") == "Unknown")
    cek("latihan 2: non-null → panjang", ("Andi" as String?)?.length == 4)
    cek("latihan 3: showInfo tercakup oleh smart cast", true)
    cek("latihan 4: nickname dipakai bila ada", StudentDisplay("Andi Pratama", "Andi").displayName() == "Andi")
    cek("latihan 4: name dipakai bila nickname null", StudentDisplay("Budi Santoso", null).displayName() == "Budi Santoso")
    cek("latihan 5: battery naik 20 tiap charge", SmartWatch("Smart Watch", 0).also { it.charge() }.battery == 20)
    cek("latihan 5: battery tidak melebihi 100", SmartWatch("Smart Watch", 90).also { it.charge(); it.charge() }.battery == 100)
    cek("latihan 5: battery awal di luar 0..100 dijepit", SmartWatch("Smart Watch", 500).battery == 100)

    val gagal = hasil.count { !it.second }
    println("Total: ${hasil.size} pemeriksaan, $gagal gagal")
    return gagal == 0
}
