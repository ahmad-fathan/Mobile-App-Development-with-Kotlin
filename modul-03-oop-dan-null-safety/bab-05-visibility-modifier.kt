/**
 * Bab 5 — Visibility Modifier
 *
 * private   : hanya di dalam class yang sama
 * protected : class sendiri dan subclass-nya
 * internal  : di mana saja dalam module yang sama
 * public    : di mana saja (default bila modifier tidak ditulis)
 */

/** private melindungi data: saldo hanya dapat diubah melalui method deposit(). */
class BankAccount {
    private var balance = 0

    fun deposit(amount: Int) {
        balance += amount
    }

    fun showBalance() {
        println(balance)
    }

    /** Getter read-only untuk dibaca dari luar tanpa membuka akses tulis. */
    fun saldo(): Int = balance
}

/** protected: member dapat diakses di dalam class dan di dalam subclass-nya. */
open class StatusDevice(val name: String) {
    protected var status = "off"

    fun showStatus() {
        println("$name: $status")
    }
}

class MonitorDevice(name: String) : StatusDevice(name) {
    fun turnOn() {
        status = "on" // boleh: protected diakses dari subclass
    }
}

/** internal: dapat diakses di mana saja dalam module yang sama. */
internal class DeviceManager {
    fun info(): String = "DeviceManager aktif"
}

/** account.balance = 1000 dari luar class akan ditolak compiler. */
fun contohPrivate() {
    val account = BankAccount()
    account.deposit(1000)
    account.showBalance()
}

fun contohProtected() {
    val monitor = MonitorDevice("Monitor")
    monitor.turnOn()
    monitor.showStatus()
}

fun contohInternal() {
    println(DeviceManager().info())
}

/** Latihan Bagian: saldo hanya berubah melalui deposit(), bukan dari luar class. */
fun saldoSetelahDeposit(vararg jumlah: Int): Int {
    val account = BankAccount()
    jumlah.forEach { account.deposit(it) }
    return account.saldo()
}

fun latihanEnkapsulasi() {
    println("saldo: ${saldoSetelahDeposit(500, 250, 1000)}")
}
