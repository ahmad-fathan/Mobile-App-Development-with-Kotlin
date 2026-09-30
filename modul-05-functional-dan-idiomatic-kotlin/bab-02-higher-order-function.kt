/**
 * Bab 2 — Higher-Order Function
 *
 * Higher-order function adalah fungsi yang menerima fungsi lain sebagai parameter,
 * atau mengembalikan fungsi sebagai hasil. Pada modul ini fokus pada jenis pertama.
 */

/** calculate menentukan kapan operasi dijalankan; lambda menentukan apa yang dilakukan. */
fun calculate(a: Int, b: Int, operation: (Int, Int) -> Int): Int {
    return operation(a, b)
}

fun contohHigherOrderFunction() {
    val sum = calculate(3, 4) { x, y -> x + y }
    val difference = calculate(10, 4) { x, y -> x - y }
    println(sum)
    println(difference)
}

/**
 * Trailing lambda: bila parameter terakhir sebuah fungsi adalah fungsi, lambda boleh
 * ditulis di luar tanda kurung. Kedua baris di bawah setara.
 */
fun contohTrailingLambda() {
    val a = calculate(3, 4, { x, y -> x + y })
    val b = calculate(3, 4) { x, y -> x + y }

    println(a)
    println(b)
    println("kedua bentuk sama: ${a == b}")
}

/**
 * Latihan Bagian 1
 * 1. Output dari calculate(3, 4) { x, y -> x * y } adalah 12.
 * 2. Lambda yang mengembalikan bilangan yang lebih besar dari kedua input.
 */
fun latihanBagian1(): Pair<Int, Int> {
    val perkalian = calculate(3, 4) { x, y -> x * y }
    println("calculate(3, 4) { x * y } = $perkalian")

    val maksimum = calculate(3, 4) { x, y -> if (x > y) x else y }
    println("calculate(3, 4) { maksimum } = $maksimum")

    return perkalian to maksimum
}
