/**
 * Bab 3 — Inheritance
 */

/** Parent class harus open agar dapat diwarisi (class bersifat final secara default). */
open class BaseDevice(
    val name: String
) {
    var status = "off"
}

/**
 * Child class menambahkan property miliknya sendiri.
 * Parameter name tidak memakai val karena hanya diteruskan ke constructor parent.
 */
class SmartTvDevice(
    name: String
) : BaseDevice(name) {
    var channel = 1
}

/** Method parent diberi open sebagai izin bahwa method boleh diganti child. */
open class SwitchableDevice(val name: String) {
    var status = "off"

    open fun turnOn() {
        status = "on"
        println("$name is ON")
    }
}

/** override mengganti implementasi method parent sepenuhnya. */
class OverridingTvDevice(name: String) : SwitchableDevice(name) {
    var displayOn = false
        private set

    override fun turnOn() {
        status = "on"
        displayOn = true
        println("$name TV display is ON")
    }
}

/** super menjalankan perilaku parent terlebih dahulu, lalu menambahkan perilaku baru. */
class SuperTvDevice(name: String) : SwitchableDevice(name) {
    var displayReady = false
        private set

    override fun turnOn() {
        super.turnOn()
        displayReady = true
        println("TV display is ready")
    }
}

/** Child mewarisi name dan status dari parent, lalu menambah channel. */
fun contohInheritance() {
    val tv = SmartTvDevice("Living Room")

    println("warisan : ${tv.name} (status ${tv.status})")
    println("sendiri : channel ${tv.channel}")
}

fun contohOverriding() {
    val tv = OverridingTvDevice("Bedroom")
    tv.turnOn()
}

fun contohSuper() {
    val tv = SuperTvDevice("Kitchen")
    tv.turnOn()
}

/** Latihan Bagian: tampilkan property warisan dan property milik child. */
fun latihanWariskanProperty() {
    val tv = SmartTvDevice("Living Room")

    println("${tv.name} — status ${tv.status} — channel ${tv.channel}")
}
