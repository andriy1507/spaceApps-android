buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.0.0-alpha15")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.32")
        classpath("com.google.gms:google-services:4.3.5")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.5.2")
        classpath("com.google.firebase:perf-plugin:1.3.5")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.33-beta")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven(url = "https://kotlin.bintray.com/kotlinx")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}