/**
 * Verifikasi otomatis untuk seluruh contoh dan latihan Modul 5.
 * Jalankan dengan: java -jar app.jar verifikasi
 */
fun jalankanVerifikasiModul5(): Boolean {
    val hasil = mutableListOf<Pair<String, Boolean>>()

    fun cek(nama: String, kondisi: Boolean) {
        hasil += nama to kondisi
        println(if (kondisi) "  OK   $nama" else "  GAGAL $nama")
    }

    println("Verifikasi Modul 5 — Functional & Idiomatic Kotlin")

    // Bab 1 — lambda dan function type.
    val double = { number: Int -> number * 2 }
    cek("lambda satu parameter dipanggil seperti fungsi", double(4) == 8)
    cek("lambda dua parameter", { a: Int, b: Int -> a * b }(3, 4) == 12)
    val bertipe: (Int) -> Int = { number -> number * 2 }
    cek("function type (Int) -> Int menyimpulkan tipe parameter", bertipe(5) == 10)
    val gabung: (String, String) -> String = { a, b -> a + b }
    cek("function type (String, String) -> String", gabung("Kot", "lin") == "Kotlin")
    var dipanggil = false
    val aksi: () -> Unit = { dipanggil = true }
    cek("() -> Unit belum berjalan sebelum dipanggil", !dipanggil)
    aksi()
    cek("() -> Unit berjalan saat dipanggil", dipanggil)
    cek("latihan bab 1: lambda bertipe", latihanFungsiSebagaiNilai() == 36)

    // Bab 2 — higher-order function dan trailing lambda.
    cek("calculate menjumlahkan", calculate(3, 4) { x, y -> x + y } == 7)
    cek("calculate mengurangkan", calculate(10, 4) { x, y -> x - y } == 6)
    cek("calculate mengalikan", calculate(3, 4, { x, y -> x * y }) == 12)
    cek("trailing lambda setara bentuk di dalam tanda kurung", calculate(3, 4) { x, y -> x + y } == calculate(3, 4, { x, y -> x + y }))
    val latihan1 = latihanBagian1()
    cek("latihan bagian 1: perkalian = 12", latihan1.first == 12)
    cek("latihan bagian 1: lambda maksimum = 4", latihan1.second == 4)

    // Bab 3 — operasi collection.
    cek("filter menyisakan elemen yang cocok", listOf(10, 25, 80).filter { it >= 20 } == listOf(25, 80))
    cek("filter tidak mengubah list asal", listOf(10, 25, 80).let { prices -> prices.filter { it >= 20 }; prices == listOf(10, 25, 80) })
    cek("map mengubah tipe elemen", listOf(10, 25, 80).map { "Rp$it" } == listOf("Rp10", "Rp25", "Rp80"))
    cek("map mempertahankan jumlah elemen", listOf(10, 25, 80).map { it * 2 }.size == 3)
    cek("find mengembalikan elemen pertama yang cocok", listOf(10, 25, 80).find { it >= 20 } == 25)
    cek("find mengembalikan null bila tidak ada", listOf(10, 25, 80).find { it > 100 } == null)
    cek("forEach hanya melakukan aksi (Unit)", listOf(1, 2).forEach { } == Unit)
    cek("sortedBy terurut naik berdasarkan properti", listOf(Product("Pen", 10), Product("Bag", 80), Product("Book", 25)).sortedBy { it.price }.map { it.name } == listOf("Pen", "Book", "Bag"))
    cek("sortedBy tidak mengubah list asal", listOf(Product("Pen", 10), Product("Bag", 80)).let { products -> products.sortedBy { it.price }; products.first().name == "Pen" })
    cek("latihan bab 3: nama produk >= 20", latihanPendahuluan() == listOf("Book", "Bag"))

    // Bab 4 — pipeline.
    val tahap = latihanBagian2()
    cek("latihan bagian 2: setelah filter", tahap.first == listOf(8, 5))
    cek("latihan bagian 2: setelah sortedBy", tahap.second == listOf(5, 8))
    cek("latihan bagian 2: setelah map", tahap.third == listOf(50, 80))
    cek("pipeline penuh: filter → sortedBy → map", listOf(Product("Pen", 10), Product("Book", 25), Product("Bag", 80)).filter { it.price >= 20 }.sortedBy { it.price }.map { it.name } == listOf("Book", "Bag"))
    cek("tahap map mengubah tipe menjadi List<String>", listOf(Product("Book", 25)).map { it.name }.first() == "Book")

    // Bab 5 — scope function.
    val namaNullable: String? = "Alya"
    cek("let mengembalikan hasil lambda", namaNullable?.let { "Hello, $it" } == "Hello, Alya")
    cek("safe call ?. melewati let saat null", (null as String?)?.let { "Hello, $it" } == null)
    cek("apply mengembalikan object yang sama", Profile().apply { name = "Alya" }.name == "Alya")
    cek("apply mengatur lebih dari satu properti", Profile().apply { name = "Alya"; city = "Yogyakarta" }.let { it.name == "Alya" && it.city == "Yogyakarta" })
    cek("also mengembalikan object, bukan hasil lambda", listOf("Alya", "Budi").also { it.size }.size == 2)
    cek("run mengembalikan hasil lambda", Product("Book", 25).run { "$name costs $price" } == "Book costs 25")
    cek("with menerima object sebagai argumen", with(Product("Book", 25)) { "$name: $price" } == "Book: 25")
    cek("latihan bab 5: profil hasil apply", latihanScopeFunction().let { it.name == "Budi" && it.city == "Bandung" })

    // Bab 6 — generics.
    cek("fungsi generic menyimpulkan T = String", firstOrNull(listOf("Alya", "Budi")) == "Alya")
    cek("fungsi generic menyimpulkan T = Int", firstOrNull(listOf(80, 90)) == 80)
    cek("fungsi generic mengembalikan null untuk list kosong", firstOrNull(emptyList<Int>()) == null)
    cek("transformAll mengubah List<String> ke List<Int>", transformAll(listOf("Pen", "Book")) { it.length } == listOf(3, 4))
    cek("transformAll dapat mengubah List<Int> ke List<String>", transformAll(listOf(1, 2)) { "n$it" } == listOf("n1", "n2"))
    cek("latihan bab 6: label harga hasil transformAll", latihanGenerics() == listOf("Rp10", "Rp25", "Rp80"))

    // Latihan akhir.
    cek("latihan 1a: nama produk >= 20 terurut harga", latihan1DaftarProduk() == listOf("Book", "Bag"))
    cek("latihan 2: isEven menyaring bilangan genap", latihan2IsEven() == listOf(8, 12, 20))
    cek("latihan 3: pipeline nilai format teks", latihan3PipelineNilai() == listOf("Nilai: 72", "Nilai: 88", "Nilai: 95"))
    cek("latihan 4: apply mengatur owner dan balance", latihan4AkunApplyDanAlso().let { it.owner == "Budi" && it.balance == 50_000 })
    val latihan5 = latihan5LastOrNull()
    cek("latihan 5: T = String mengembalikan elemen terakhir", latihan5.first == "Bag")
    cek("latihan 5: T = Int mengembalikan elemen terakhir", latihan5.second == 80)
    cek("latihan 5: list kosong menghasilkan null", latihan5.third == null)
    cek("lastOrNull list kosong null, list terisi elemen terakhir", lastOrNull(emptyList<String>()) == null && lastOrNull(listOf(7)) == 7)

    val gagal = hasil.count { !it.second }
    println("Total: ${hasil.size} pemeriksaan, $gagal gagal")
    return gagal == 0
}
