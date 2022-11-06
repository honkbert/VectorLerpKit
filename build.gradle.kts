buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        classpath("com.android.tools.build:gradle:7.2.2")
    }
}

group = "com.robgulley"
version = "0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}