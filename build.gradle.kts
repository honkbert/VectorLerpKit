buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.0")
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

group = "com.robgulley"
version = "0.1"

allprojects {
    repositories {
        mavenCentral()
    }
}