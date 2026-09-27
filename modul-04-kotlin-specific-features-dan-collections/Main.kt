/**
 * Titik masuk seluruh contoh Modul 4.
 *
 * Jalankan tanpa argumen untuk memilih contoh dari menu, atau dengan
 * java -jar app.jar verifikasi untuk menjalankan verifikasi otomatis.
 */
fun main(args: Array<String>) {
    if (args.firstOrNull() == "verifikasi") {
        val lulus = jalankanVerifikasiModul4()
        if (!lulus) kotlin.system.exitProcess(1)
        return
    }

    while (true) {
        println()
        println("=== Modul 4: Kotlin-Specific Features & Collections ===")
        println(" 1. Data class")
        println(" 2. Enum class")
        println(" 3. Object dan companion object")
        println(" 4. Extension function")
        println(" 5. Array")
        println(" 6. Collection read-only dan mutable")
        println(" 7. List dan MutableList")
        println(" 8. Set dan MutableSet")
        println(" 9. Map dan MutableMap")
        println("10. Memilih dan menggabungkan collection")
        println("11. Destructuring")
        println("12. Latihan akhir")
        println("13. Verifikasi otomatis")
        println(" 0. Keluar")
        print("Pilih menu: ")

        val pilihan = readlnOrNull()?.trim() ?: break

        when (pilihan) {
            "1" -> menuDataClass()
            "2" -> menuEnumClass()
            "3" -> menuObjectDanCompanion()
            "4" -> menuExtensionFunction()
            "5" -> menuArray()
            "6" -> menuCollectionReadOnlyMutable()
            "7" -> menuListDanMutableList()
            "8" -> menuSetDanMutableSet()
            "9" -> menuMapDanMutableMap()
            "10" -> menuMemilihCollection()
            "11" -> menuDestructuring()
            "12" -> menuLatihanAkhirModul4()
            "13" -> jalankanVerifikasiModul4()
            "0" -> return
            else -> println("Pilihan tidak tersedia")
        }
    }
}

private fun menuDataClass() {
    contohToString()
    contohEquals()
    contohCopy()
    latihanDataClass()
}

private fun menuEnumClass() {
    contohEnum()
    contohEnumDenganWhen()
    latihanEnum()
}

private fun menuObjectDanCompanion() {
    contohObjectDeclaration()
    contohCompanionObject()
    latihanObjectDanCompanion()
}

private fun menuExtensionFunction() {
    contohExtensionString()
    contohExtensionDataClass()
    latihanProduct()
}

private fun menuArray() {
    contohArray()
    contohArrayTipeDasar()
    latihanArray()
}

private fun menuCollectionReadOnlyMutable() {
    contohTigaJenisCollection()
    contohReadOnly()
    contohReadOnlyTidakMenjaminTidakBerubah()
    latihanReadOnlyDanMutable()
}

private fun menuListDanMutableList() {
    contohList()
    contohIterasiList()
    contohMutableList()
    latihanKeranjangBelanja()
}

private fun menuSetDanMutableSet() {
    contohSet()
    contohMutableSet()
    latihanTagKeahlian()
}

private fun menuMapDanMutableMap() {
    contohMap()
    contohMutableMap()
    latihanDaftarNilai()
}

private fun menuMemilihCollection() {
    contohCollectionBerisiObject()
    contohRangkumanCollection()
    latihanMemilihCollection()
}

private fun menuDestructuring() {
    contohDestructuring()
    contohUrutanDestructuring()
    contohDestructuringMap()
    latihanContohGabungan()
}

private fun menuLatihanAkhirModul4() {
    latihan1ProgramMataKuliah()
    latihan2DeskripsiStatus()
    latihan3TagKeahlian()
    latihan4DaftarNilai()
    latihan5KonfigurasiAplikasi()
}
