plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // PLUGINS DO PROFESSOR (AGORA VAI FUNCIONAR)
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
android {
    namespace = "br.com.cadernoreceitas" //
    compileSdk = 36

    defaultConfig {
        applicationId = "br.com.cadernoreceitas" //
        minSdk = 24
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
        compose = true
    }
}

dependencies {
    // Dependências base (corretas)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Dependências do Professor (LiveData)
    // implementation(libs.androidx.runtime.livedata) // <-- 1. APAGUE ESTA LINHA
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6") // <-- MANTENHA ESTA (está correta)

    // Room (R2)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1") // <-- Isso vai parar de dar erro

    // Lifecycle + ViewModel (R3)
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.6")

    // Navigation Compose (R1)
    // implementation(libs.androidx.navigation.compose) // <-- 2. APAGUE ESTA LINHA
    implementation("androidx.navigation:navigation-compose:2.8.2") // <-- MANTENHA ESTA (está correta)

    // Hilt (R4)
    implementation("com.google.dagger:hilt-android:2.52")
    kapt("com.google.dagger:hilt-compiler:2.52") // <-- Isso vai parar de dar erro
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")

    // Gson (Mantenha, você precisa dela para os Converters)
    implementation("com.google.code.gson:gson:2.10.1")

    // Testes
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}


