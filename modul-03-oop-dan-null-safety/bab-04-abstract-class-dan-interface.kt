/**
 * Bab 4 — Abstract Class dan Interface
 *
 * Abstract class menjawab "object ini termasuk jenis apa?" (class dasar yang umum),
 * interface menjawab "object ini dapat melakukan apa?" (kontrak perilaku).
 */

/**
 * Abstract class: hanya mendefinisikan struktur umum, tidak dapat dibuat object-nya.
 * Dapat berisi abstract method tanpa isi sekaligus method biasa yang sudah jadi.
 */
abstract class AbstractSmartDevice(
    val name: String
) {
    abstract fun turnOn()

    fun showName() {
        println(name)
    }
}

/** Setiap child wajib mengimplementasikan semua abstract method milik parent. */
class AbstractTvDevice(name: String) : AbstractSmartDevice(name) {
    override fun turnOn() {
        println("$name TV is ON")
    }
}

/** Interface: daftar perilaku yang harus disediakan oleh class pemakainya. */
interface Connectable {
    fun connect()
    fun disconnect()
}

class ConnectableTvDevice : Connectable {
    override fun connect() {
        println("TV connected")
    }

    override fun disconnect() {
        println("TV disconnected")
    }
}

interface Rechargeable {
    fun charge()
}

/**
 * Satu class dapat mewarisi satu abstract class sekaligus
 * mengimplementasikan beberapa interface.
 */
class SmartWatchDevice : AbstractSmartDevice("Smart Watch"), Connectable, Rechargeable {
    var connected = false
        private set

    var charging = false
        private set

    override fun turnOn() {
        println("$name is ON")
    }

    override fun connect() {
        connected = true
        println("Watch connected")
    }

    override fun disconnect() {
        connected = false
        println("Watch disconnected")
    }

    override fun charge() {
        charging = true
        println("Watch charging")
    }
}

/** Object SmartTvDevice memakai method warisan showName() dan turnOn(). */
fun contohAbstractClass() {
    val tv = AbstractTvDevice("Living Room")
    tv.showName()
    tv.turnOn()
}

/** Class yang berbeda dapat mengimplementasikan interface yang sama. */
fun contohInterface() {
    val tv = ConnectableTvDevice()
    tv.connect()
    tv.disconnect()
}

/** Latihan Bagian: class turunan memenuhi abstract class dan dua interface. */
fun latihanKontrak() {
    val watch = SmartWatchDevice()

    watch.showName()
    watch.turnOn()
    watch.connect()
    watch.charge()
}
