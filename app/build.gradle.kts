plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // REMOVE: alias(libs.plugins.kotlin.compose)  ← We don't need Compose
}

android {
    namespace = "com.example.quizz"
    compileSdk = 36  // Changed from 36 to 34 (more stable)

    defaultConfig {
        applicationId = "com.example.quizz"
        minSdk = 23
        targetSdk = 36 // Changed from 36 to 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // REMOVE buildFeatures for Compose
    // We're using XML layouts, not Compose
}

dependencies {
    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation(libs.androidx.activity)

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}