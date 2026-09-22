/**
 * Bab 1 — Class dan Object
 */

/** Class kosong: masih berupa cetak biru tanpa data dan perilaku. */
class SmartDevice

/** Property: val untuk read-only, var untuk nilai yang dapat berubah. */
class SmartTv {
    val name = "Android TV"
    var status = "off"
}

/** Method: fungsi di dalam class yang membaca dan mengubah property object itu sendiri. */
class SmartLamp {
    var status = "off"

    fun turnOn() {
        status = "on"
        println("Device is ON")
    }

    fun turnOff() {
        status = "off"
        println("Device is OFF")
    }
}

/** Class dengan property dan method yang dipakai bersama-sama. */
class SmartSpeaker {
    val name = "Living Room Speaker"

    fun turnOn() {
        println("$name is ON")
    }
}

/** Satu class dapat menghasilkan banyak object yang saling lepas. */
fun contohClassObject() {
    val tv = SmartDevice()
    val lamp = SmartDevice()

    println("tv   : ${tv.javaClass.simpleName}")
    println("lamp : ${lamp.javaClass.simpleName}")
    println("object yang berbeda: ${tv !== lamp}")
}

/** Membaca property memakai nama object, tanda titik, lalu nama property. */
fun contohProperty() {
    val device = SmartTv()

    println(device.name)
    println(device.status)
}

/** Method mengubah property milik object yang memanggilnya. */
fun contohMethod() {
    val lamp = SmartLamp()

    println("status awal: ${lamp.status}")
    lamp.turnOn()
    println("setelah turnOn(): ${lamp.status}")
    lamp.turnOff()
    println("setelah turnOff(): ${lamp.status}")
}

/** Latihan Bagian: buat object lalu pakai property dan method-nya. */
fun latihanBuatObject() {
    val speaker = SmartSpeaker()

    println(speaker.name)
    speaker.turnOn()
}
