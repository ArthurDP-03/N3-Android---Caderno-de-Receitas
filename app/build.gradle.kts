plugins {
    // Plugins que você definiu no raiz
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    // Mude "br.com.meuapp" para o namespace real do seu app (ex: "br.com.cadernoreceitas")
    namespace = "br.com.cadernoreceitas"
    compileSdk = 34

    defaultConfig {
        // Mude "br.com.meuapp" para o ID real do seu app (ex: "br.com.cadernoreceitas")
        applicationId = "br.com.cadernoreceitas"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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

    // Configura o Java 17 (necessário para o AGP 8+)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Configura as opções do Kotlin
    kotlinOptions {
        jvmTarget = "17"
    }

    // O plugin `org.jetbrains.kotlin.plugin.compose` já cuida disso.
    // buildFeatures { compose = true }
    // composeOptions { ... }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Dependências principais do AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Jetpack Compose (BOM)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)

    // Hilt (Injeção de Dependência)
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ADICIONE O ROOM (Banco de Dados)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Para suporte a Flow/Coroutines
    kapt(libs.androidx.room.compiler)      // O processador de anotações que faltava

    // ADICIONE O GSON (Para os Type Converters)
    implementation(libs.gson)

    // Dependências de Teste
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.ui.test.junit4)

    // Debug (Compose)
    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
}

// Configuração específica do Kapt (necessária para o Hilt)
kapt {
    correctErrorTypes = true
}