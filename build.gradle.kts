// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        mavenCentral()
    }

    // NOTE: Do not place your application dependencies here; they belong
    // in the individual module build.gradle files
    dependencies {

        val agpVersion = "7.2.1"
        classpath("com.android.tools.build:gradle:$agpVersion")

        val kotlinVersion = "1.5.31"
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")

        val googleServicesVersion = "4.3.10"
        classpath("com.google.gms:google-services:$googleServicesVersion")

        val crashlyticsVersion = "2.8.1"
        classpath("com.google.firebase:firebase-crashlytics-gradle:$crashlyticsVersion")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
