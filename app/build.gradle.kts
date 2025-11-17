plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.safeargs)
    // jika nanti mau pakai annotation processor (Glide compiler), uncomment baris berikut dan
    // tambahkan alias/kode plugin kapt di versionCatalog atau gunakan: id("kotlin-kapt")
    // id("kotlin-kapt")
}

android {
    namespace = "com.example.mynavigation"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.mynavigation"
        minSdk = 33
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Navigation (kamu sudah punya versi di libs, cukup satu set)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // tambahan yang dibutuhkan untuk fitur bahan/cart
    implementation("androidx.recyclerview:recyclerview:1.3.0")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.google.code.gson:gson:2.10.1")

    // jika mau gunakan Glide annotation processor (optional, but recommended for some features)
    // apply the kapt plugin and add:
    // kapt("com.github.bumptech.glide:compiler:4.15.1")
}
