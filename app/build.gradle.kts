plugins {
    id("com.android.application")

    id("org.jetbrains.kotlin.android")

    id("androidx.navigation.safeargs.kotlin")

    id("com.google.gms.google-services")

    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.pass.taskmanager"
        minSdk = 21
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // support for java 8
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs + listOf("-opt-in=kotlin.RequiresOptIn")
    }

    composeOptions {
        //kotlinCompilerExtensionVersion("1.1.1")
    }
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val coreVersion = "1.8.0"
    implementation("androidx.core:core-ktx:$coreVersion")

    val appcompatVersion = "1.4.2"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    val activityVersion = "1.4.0"
    implementation("androidx.activity:activity-compose:$activityVersion")

    val composeVersion = "1.1.1"
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.animation:animation:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling:$composeVersion")

    val navVersion = "2.4.2"
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    // use BoM to automatically manage the versions of individual dependencies
    val firebaseBomVersion = "29.3.1"
    implementation(platform("com.google.firebase:firebase-bom:$firebaseBomVersion"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-database-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    implementation("com.google.firebase:firebase-messaging-ktx")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    val materialVersion = "1.6.1"
    implementation("com.google.android.material:material:$materialVersion")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}
