/**
 * Bab 4 — Extension Function
 *
 * Extension function menambah fungsi baru pada tipe yang sudah ada tanpa mengubah
 * kode aslinya. Tipe yang diperluas ditulis di depan nama fungsi (receiver type),
 * dan di dalam fungsi kata kunci `this` merujuk ke object yang memanggilnya.
 */

fun String.greeting(): String = "Hello, $this!"

data class StudentGrade(
    val name: String,
    val score: Int
)

/** Receiver type di sini adalah data class buatan sendiri. */
fun StudentGrade.isPassed(): Boolean = score >= 60

fun contohExtensionString() {
    println("Ali".greeting())
}

/** Class StudentGrade tidak berubah sama sekali. */
fun contohExtensionDataClass() {
    val student = StudentGrade("Ali", 85)
    println(student.isPassed())
}

/**
 * Latihan Bagian 1: Product
 * Menggabungkan data class, copy(), dan extension function.
 */
data class Product(val name: String, val price: Int)

fun Product.isExpensive(): Boolean = price > 500_000

fun latihanProduct() {
    val laptop = Product("Laptop", 12_000_000)
    val mouse = Product("Mouse", 150_000)

    // 1. Dua product dicetak.
    println(laptop)
    println(mouse)

    // 2. Salinan dengan harga berbeda; object asli tidak berubah.
    val laptopPromo = laptop.copy(price = 9_500_000)
    println(laptopPromo)
    println("harga asli tetap: ${laptop.price}")

    // 3. dan 4. isExpensive() mengembalikan true bila price > 500000.
    println("${laptop.name} mahal? ${laptop.isExpensive()}")
    println("${mouse.name} mahal? ${mouse.isExpensive()}")
}
