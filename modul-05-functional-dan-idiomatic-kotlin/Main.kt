/**
 * Titik masuk seluruh contoh Modul 5.
 *
 * Jalankan tanpa argumen untuk memilih contoh dari menu, atau dengan
 * java -jar app.jar verifikasi untuk menjalankan verifikasi otomatis.
 */
fun main(args: Array<String>) {
    if (args.firstOrNull() == "verifikasi") {
        val lulus = jalankanVerifikasiModul5()
        if (!lulus) kotlin.system.exitProcess(1)
        return
    }

    while (true) {
        println()
        println("=== Modul 5: Functional & Idiomatic Kotlin ===")
        println("1. Fungsi sebagai nilai: lambda dan function type")
        println("2. Higher-order function")
        println("3. Mengolah collection dengan operasi fungsional")
        println("4. Menggabungkan operasi menjadi pipeline")
        println("5. Scope function")
        println("6. Generics dasar")
        println("7. Latihan akhir")
        println("8. Verifikasi otomatis")
        println("0. Keluar")
        print("Pilih menu: ")

        val pilihan = readlnOrNull()?.trim() ?: break

        when (pilihan) {
            "1" -> menuFungsiSebagaiNilai()
            "2" -> menuHigherOrderFunction()
            "3" -> menuOperasiCollection()
            "4" -> menuPipeline()
            "5" -> menuScopeFunction()
            "6" -> menuGenerics()
            "7" -> menuLatihanAkhirModul5()
            "8" -> jalankanVerifikasiModul5()
            "0" -> return
            else -> println("Pilihan tidak tersedia")
        }
    }
}

private fun menuFungsiSebagaiNilai() {
    contohLambda()
    contohLambdaDuaInput()
    contohFunctionType()
    latihanFungsiSebagaiNilai()
}

private fun menuHigherOrderFunction() {
    contohHigherOrderFunction()
    contohTrailingLambda()
    latihanBagian1()
}

private fun menuOperasiCollection() {
    contohHasilTidakMengubahAsal()
    contohFilter()
    contohMap()
    contohFind()
    contohForEach()
    contohSortedBy()
    latihanPendahuluan()
}

private fun menuPipeline() {
    contohPipeline()
    latihanBagian2()
}

private fun menuScopeFunction() {
    contohLet()
    contohApply()
    contohAlso()
    contohRunDanWith()
    latihanScopeFunction()
}

private fun menuGenerics() {
    contohListGeneric()
    contohFungsiGeneric()
    contohFungsiGenericDenganLambda()
    latihanGenerics()
}

private fun menuLatihanAkhirModul5() {
    latihan1DaftarProduk()
    latihan2IsEven()
    latihan3PipelineNilai()
    latihan4AkunApplyDanAlso()
    latihan5LastOrNull()
}
