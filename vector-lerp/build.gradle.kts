plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.0"
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
    compileSdkVersion(31)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdkVersion(27)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    android{
        publishLibraryVariants("release", "debug")
    }
    macosX64()
    linuxX64()
    linuxArm64()

    sourceSets {
        val androidMain by getting
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.2.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val desktopMain by creating {
            dependsOn(commonMain)
        }
        val desktopTest by creating

        val linuxX64Main by getting {
            dependsOn(desktopMain)
        }
        val linuxArm64Main by getting {
            dependsOn(desktopMain)
        }
        val macosX64Main by getting {
            dependsOn(desktopMain)
        }

    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        configureEach {
            kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
        }
    }
}