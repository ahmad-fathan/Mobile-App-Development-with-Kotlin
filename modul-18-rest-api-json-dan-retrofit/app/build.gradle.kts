plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.todoapp"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.example.todoapp"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    debugImplementation(libs.androidx.ui.tooling)
}

/**
 * Pemeriksaan perilaku tanpa emulator: menjalankan `main()` pada `verifikasi.kt`.
 * Classpath diambil dari build debug, sehingga Gson, OkHttp, dan Retrofit tersedia.
 *
 *   ./gradlew verifikasi          # atau: ./jalankan.sh 18 perilaku
 */
tasks.register<JavaExec>("verifikasi") {
    group = "verification"
    description = "Menjalankan verifikasi aturan REST API, JSON, dan Retrofit modul 18."
    mainClass.set("com.example.todoapp.VerifikasiKt")
    val kelasDebug = tasks.named("compileDebugKotlin")
    dependsOn(kelasDebug)
    // AAR tidak dapat dibaca JVM; artifactView "android-classes-jar" memakai transform AGP
    // sehingga setiap dependensi tampil sebagai classes.jar.
    val pustaka = configurations.getByName("debugRuntimeClasspath").incoming.artifactView {
        attributes {
            attribute(Attribute.of("artifactType", String::class.java), "android-classes-jar")
        }
    }.files
    classpath = files(kelasDebug.map { it.outputs.files }) + pustaka
}
