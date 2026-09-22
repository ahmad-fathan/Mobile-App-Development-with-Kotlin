/**
 * Titik masuk seluruh contoh Modul 3.
 *
 * Jalankan tanpa argumen untuk memilih contoh dari menu,
 * atau dengan ./gradlew run --args=verifikasi untuk menjalankan verifikasi otomatis.
 */
fun main(args: Array<String>) {
    if (args.firstOrNull() == "verifikasi") {
        val lulus = jalankanVerifikasiModul3()
        if (!lulus) kotlin.system.exitProcess(1)
        return
    }

    while (true) {
        println()
        println("=== Modul 3: OOP in Kotlin & Null Safety ===")
        println(" 1. Class dan object")
        println(" 2. Constructor, getter, dan setter")
        println(" 3. Inheritance")
        println(" 4. Abstract class dan interface")
        println(" 5. Visibility modifier")
        println(" 6. Null safety")
        println(" 7. Smart cast")
        println(" 8. Latihan akhir")
        println(" 9. Verifikasi otomatis")
        println(" 0. Keluar")
        print("Pilih menu: ")

        val pilihan = readlnOrNull()?.trim() ?: break

        when (pilihan) {
            "1" -> menuClassDanObject()
            "2" -> menuConstructorGetterSetter()
            "3" -> menuInheritance()
            "4" -> menuAbstractDanInterface()
            "5" -> menuVisibilityModifier()
            "6" -> menuNullSafety()
            "7" -> menuSmartCast()
            "8" -> menuLatihanAkhirModul3()
            "9" -> jalankanVerifikasiModul3()
            "0" -> return
            else -> println("Pilihan tidak tersedia")
        }
    }
}

private fun menuClassDanObject() {
    contohClassObject()
    contohProperty()
    contohMethod()
    latihanBuatObject()
}

private fun menuConstructorGetterSetter() {
    contohConstructor()
    contohPropertyDiConstructor()
    contohCustomSetter()
    latihanVolume()
}

private fun menuInheritance() {
    contohInheritance()
    contohOverriding()
    contohSuper()
    latihanWariskanProperty()
}

private fun menuAbstractDanInterface() {
    contohAbstractClass()
    contohInterface()
    latihanKontrak()
}

private fun menuVisibilityModifier() {
    contohPrivate()
    contohProtected()
    contohInternal()
    latihanEnkapsulasi()
}

private fun menuNullSafety() {
    contohTipeNonNullable()
    contohTipeNullable()
    contohSafeCall()
    contohElvis()
    contohNotNullAssertion()
    latihanNullSafety()
}

private fun menuSmartCast() {
    contohSmartCastIf()
    contohSmartCastWhen()
    latihanSmartCast()
}

private fun menuLatihanAkhirModul3() {
    latihan1SmartDevice()
    latihan2NullSafety()
    latihan3SmartCast()
    latihan4NamaTampilan()
    latihan5JamTanganPintar()
}
