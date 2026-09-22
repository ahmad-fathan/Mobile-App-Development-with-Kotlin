import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.4.10"
    application
}

repositories {
    mavenCentral()
}

// Setiap modul punya folder sendiri di root proyek, mis. modul-02-kotlin-programming-essentials/.
// Berkas .kt di dalamnya cukup dikompilasi tanpa harus memakai deklarasi package.
//
// Karena tiap modul memakai nama berkas yang sama (Main.kt, verifikasi.kt, latihan/latihan-akhir.kt),
// hanya satu modul yang dikompilasi pada satu waktu supaya nama kelas JVM tidak bertabrakan.
// Pilih modul dengan -Pmodul=<nomor>, mis. ./gradlew run -Pmodul=03
val modulAktif = (findProperty("modul") as String?)?.trim() ?: "02"

sourceSets {
    main {
        kotlin.setSrcDirs(listOf("."))
        kotlin.include("modul-$modulAktif-*/**/*.kt")
        resources.setSrcDirs(emptyList<String>())
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass = "MainKt"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
