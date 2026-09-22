/**
 * Latihan Akhir Modul 3 — lima soal yang menggabungkan seluruh konsep.
 */

/**
 * Soal 1: Smart Device
 * abstract class SmartDevice (name, abstract turnOn(), showName()),
 * interface Connectable (connect()), class SmartLight yang mewarisi keduanya.
 */
abstract class ExerciseSmartDevice(val name: String) {
    abstract fun turnOn()

    fun showName() {
        println(name)
    }
}

interface ExerciseConnectable {
    fun connect()
}

class SmartLight(name: String) : ExerciseSmartDevice(name), ExerciseConnectable {
    var status = "off"
        private set

    var connected = false
        private set

    override fun turnOn() {
        status = "on"
        println("$name is ON")
    }

    override fun connect() {
        connected = true
        println("$name connected")
    }
}

fun latihan1SmartDevice() {
    val light = SmartLight("Desk Lamp")
    light.showName()
    light.turnOn()
    light.connect()
}

/**
 * Soal 2: Null Safety
 * Cetak panjang studentName dengan aman dan "Unknown" bila nilainya null, tanpa !!.
 */
fun latihan2NullSafety() {
    var studentName: String? = null

    println(studentName?.length ?: "Unknown")
    println(studentName ?: "Unknown")

    studentName = "Andi"
    println(studentName?.length ?: "Unknown")
}

/**
 * Soal 3: Smart Cast
 * String → panjangnya, Int → data * 2, tipe lain → "Unknown".
 */
fun showInfoLatihan3(data: Any) {
    when (data) {
        is String -> println(data.length)
        is Int -> println(data * 2)
        else -> println("Unknown")
    }
}

fun latihan3SmartCast() {
    showInfoLatihan3("Kotlin")
    showInfoLatihan3(10)
    showInfoLatihan3(true)
}

/**
 * Soal 4: Nama Tampilan Mahasiswa
 * displayName() mengembalikan nickname bila tersedia, name bila nickname null.
 */
class StudentDisplay(
    val name: String,
    val nickname: String?
) {
    fun displayName(): String {
        return nickname ?: name
    }
}

fun latihan4NamaTampilan() {
    val denganNickname = StudentDisplay("Andi Pratama", "Andi")
    val tanpaNickname = StudentDisplay("Budi Santoso", null)

    println(denganNickname.displayName())
    println(tanpaNickname.displayName())
}

/**
 * Soal 5: Jam Tangan Pintar
 * Me warisi SmartDevice dari Latihan 1 serta mengimplementasikan Connectable dan
 * Rechargeable. Property battery private dengan custom setter 0..100; charge()
 * menambah 20 dan berhenti di 100.
 */
interface ExerciseRechargeable {
    fun charge()
}

class SmartWatch(
    name: String,
    batteryAwal: Int = 0
) : ExerciseSmartDevice(name), ExerciseConnectable, ExerciseRechargeable {

    /**
     * Custom setter: nilai di luar 0..100 dijepit ke batas terdekat,
     * sehingga battery tidak pernah melebihi 100 meski charge() dipanggil berulang.
     */
    var battery: Int = batteryAwal.coerceIn(0, 100)
        private set(value) {
            field = value.coerceIn(0, 100)
        }

    var connected = false
        private set

    override fun turnOn() {
        println("$name is ON")
    }

    override fun connect() {
        connected = true
        println("$name connected")
    }

    override fun charge() {
        battery += 20
        println("battery: $battery")
    }
}

fun latihan5JamTanganPintar() {
    val watch = SmartWatch("Smart Watch", batteryAwal = 50)

    watch.showName()
    watch.turnOn()
    watch.connect()
    watch.charge()
    watch.charge()
    watch.charge()
    watch.charge()

    println("battery akhir: ${watch.battery}")
}
