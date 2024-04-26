pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        kotlin("jvm") version "1.9.23"
        id("org.jetbrains.compose") version "1.6.1"
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver") version "0.7.0"
}

toolchainManagement {
    jvm {
        javaRepositories {
            repository("foojay") {
                resolverClass = org.gradle.toolchains.foojay.FoojayToolchainResolver::class.java
            }
        }
    }
}

rootProject.name = "discompose"
