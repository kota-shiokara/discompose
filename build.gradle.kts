import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
}

group = "jp.ikanoshiokara"
version = "0.1.0"

kotlin {
    jvmToolchain(20)
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    implementation(compose.desktop.currentOs)

    // Audio Library
    val korauVersion = "4.0.2"
    implementation("com.soywiz.korlibs.korau:korau:$korauVersion")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe)
            packageName = "discompose"
            // versionが1.0.0を超えたらversionをそのままつっこもう
            packageVersion = "1.0.0"
        }
    }
}
