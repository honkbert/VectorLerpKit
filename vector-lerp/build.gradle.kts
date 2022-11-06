plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.7.20"
    id("com.android.library")
    id("maven-publish")
}

group = "com.robgulley"
version = "0.1"

repositories {
    mavenCentral()
    google()
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 27
        targetSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }
    jvm()
    macosX64()
    linuxX64()
    linuxArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.3.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val jvmMain by getting
        val linuxX64Main by getting
        val linuxArm64Main by getting
        val macosX64Main by getting

    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        configureEach {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}