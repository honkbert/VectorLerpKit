buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.android.tools.build:gradle:4.0.2")
    }
}

group = "com.robgulley"
version = "1.0"

allprojects {
    repositories {
        jcenter()
        mavenCentral()
    }
}