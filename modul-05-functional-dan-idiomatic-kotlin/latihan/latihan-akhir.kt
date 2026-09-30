/**
 * Latihan Akhir Modul 5 — lima soal yang menggabungkan seluruh konsep.
 *
 * Product dideklarasikan di bab 3.
 */

// --- Soal 1 ---------------------------------------------------------------

/**
 * Soal 1
 * a. Nama produk dengan harga paling sedikit 20, terurut berdasarkan harga.
 * b. Produk pertama yang harganya lebih dari 100; hasil null ditangani → "No match".
 * c. Setiap nama hasil (a) dicetak dengan forEach.
 */
fun latihan1DaftarProduk(): List<String> {
    val products = listOf(
        Product("Pen", 10),
        Product("Book", 25),
        Product("Bag", 80)
    )

    val names = products
        .filter { it.price >= 20 }
        .sortedBy { it.price }
        .map { it.name }

    println("1a. nama produk >= 20: $names")

    val mahal: Product? = products.find { it.price > 100 }
    println("1b. ${mahal?.let { "${it.name} harganya ${it.price}" } ?: "No match"}")

    print("1c. forEach: ")
    names.forEach { print("$it ") }
    println()

    return names
}

// --- Soal 2 ---------------------------------------------------------------

/**
 * Soal 2: variabel isEven bertipe (Int) -> Boolean, dipakai bersama filter.
 * Output yang diharapkan sebelum dijalankan: [8, 12, 20].
 */
fun latihan2IsEven(): List<Int> {
    val isEven: (Int) -> Boolean = { number -> number % 2 == 0 }
    val evenNumbers = listOf(3, 8, 12, 7, 20).filter(isEven)

    println("2. bilangan genap: $evenNumbers")
    return evenNumbers
}

// --- Soal 3 ---------------------------------------------------------------

/**
 * Soal 3: satu pipeline atas listOf(72, 95, 60, 88).
 * filter  → List<Int>    : [72, 95, 88]
 * sortedBy→ List<Int>    : [72, 88, 95]
 * map     → List<String> : [Nilai: 72, Nilai: 88, Nilai: 95]
 */
fun latihan3PipelineNilai(): List<String> {
    val scores = listOf(72, 95, 60, 88)

    val lulus: List<Int> = scores.filter { it >= 70 }
    val urut: List<Int> = lulus.sortedBy { it }
    val teks: List<String> = urut.map { "Nilai: $it" }

    println("3. filter (List<Int>)     : $lulus")
    println("   sortedBy (List<Int>)   : $urut")
    println("   map (List<String>)     : $teks")

    return teks
}

// --- Soal 4 ---------------------------------------------------------------

class Account {
    var owner: String = ""
    var balance: Int = 0

    override fun toString(): String = "Account(owner=$owner, balance=$balance)"
}

/**
 * Soal 4: apply mengatur object dan mengembalikan object yang sama, sehingga juga
 * dapat langsung dirangkai dengan also. Variabel hasilnya tetap berisi object Account
 * karena apply dan also sama-sama mengembalikan object, bukan nilai lambda.
 */
fun latihan4AkunApplyDanAlso(): Account {
    val account = Account().apply {
        owner = "Budi"
        balance = 50_000
    }.also { println("Account created for ${it.owner}") }

    println("4. $account")
    return account
}

// --- Soal 5 ---------------------------------------------------------------

/**
 * Soal 5: elemen terakhir atau null bila list kosong, tanpa indeks langsung.
 * Pemanggilan pertama T = String, kedua T = Int, ketiga T = Int (ditulis eksplisit
 * pada emptyList<Int>() karena tidak ada elemen yang dapat menyimpulkan tipenya).
 */
fun <T> lastOrNull(items: List<T>): T? {
    return if (items.isEmpty()) null else items.last()
}

fun latihan5LastOrNull(): Triple<String?, Int?, Int?> {
    val lastString: String? = lastOrNull(listOf("Pen", "Book", "Bag"))
    val lastInt: Int? = lastOrNull(listOf(10, 25, 80))
    val lastEmpty: Int? = lastOrNull(emptyList<Int>())

    println("5. T = String : $lastString")
    println("   T = Int    : $lastInt")
    println("   T = Int    : $lastEmpty")

    return Triple(lastString, lastInt, lastEmpty)
}
