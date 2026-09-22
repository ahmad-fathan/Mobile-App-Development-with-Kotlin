/**
 * Bab 7 — Smart Cast
 *
 * Setelah tipe diperiksa dengan `is`, Kotlin memperlakukan nilai tersebut
 * sebagai tipe yang lebih spesifik tanpa konversi manual.
 */

/** Smart cast dengan if: di dalam blok, value sudah bertipe String atau Int. */
fun describe(value: Any) {
    if (value is String) {
        println("String length: ${value.length}")
    }

    if (value is Int) {
        println("Integer value: $value")
    }
}

/** Smart cast dengan when: tiap cabang `is` memperlakukan value sesuai tipenya. */
fun describeWhen(value: Any) {
    when (value) {
        is String -> println("Text: ${value.uppercase()}")
        is Int -> println("Number: ${value + 1}")
        else -> println("Unknown type")
    }
}

/** Latihan Bagian: String → panjang, Int → nilai × 2, tipe lain → "Unknown". */
fun showInfo(data: Any) {
    when (data) {
        is String -> println(data.length)
        is Int -> println(data * 2)
        else -> println("Unknown")
    }
}

fun contohSmartCastIf() {
    describe("Kotlin")
    describe(10)
}

fun contohSmartCastWhen() {
    describeWhen("kotlin")
    describeWhen(10)
    describeWhen(true)
}

fun latihanSmartCast() {
    showInfo("Kotlin")
    showInfo(10)
    showInfo(true)
}
