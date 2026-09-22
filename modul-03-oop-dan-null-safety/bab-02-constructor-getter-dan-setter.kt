/**
 * Bab 2 — Constructor, Getter, dan Setter
 */

/** Primary constructor: parameter ditulis langsung di header class. */
class DeviceProfile(
    val name: String,
    val category: String
)

/** val/var di depan parameter membuatnya otomatis menjadi property. */
class Student(
    val name: String,
    var semester: Int
)

/**
 * Custom setter memakai backing field `field` untuk membatasi volume ke 0..100.
 * Nilai di luar rentang diabaikan, sehingga volume lama tetap dipertahankan.
 */
class VolumeDevice {
    var volume: Int = 10
        set(value) {
            field = if (value in 0..100) {
                value
            } else {
                field
            }
        }
}

/** Setiap object dapat dibuat dengan nilai yang berbeda. */
fun contohConstructor() {
    val tv = DeviceProfile("Android TV", "Entertainment")
    val lamp = DeviceProfile("Desk Lamp", "Lighting")

    println("${tv.name} — ${tv.category}")
    println("${lamp.name} — ${lamp.category}")
}

/** name tidak dapat diubah (val), semester dapat diubah (var). */
fun contohPropertyDiConstructor() {
    val student = Student("Andi", 3)

    println(student.name)
    student.semester = 4
    println(student.semester)

    // student.name = "Budi" // ditolak compiler: name memakai val
}

/** Getter dan setter dibuat otomatis; setter hanya ada pada property var. */
fun contohCustomSetter() {
    val tv = VolumeDevice()

    println("volume awal             : ${tv.volume}")
    tv.volume = 50
    println("volume = 50 diterima    : ${tv.volume}")
    tv.volume = 150
    println("volume = 150 diabaikan  : ${tv.volume}")
}

/** Latihan Bagian: setter hanya menerima nilai 0..100. */
fun volumeDalamRentang(nilai: Int): Int {
    val device = VolumeDevice()
    device.volume = nilai
    return device.volume
}

fun latihanVolume() {
    for (nilai in listOf(50, 150, -5, 0, 100)) {
        println("volume = $nilai → ${volumeDalamRentang(nilai)}")
    }
}
