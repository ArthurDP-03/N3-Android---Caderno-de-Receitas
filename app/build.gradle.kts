plugins {
    // Plugins que você definiu no raiz
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android")

    // MUDANÇA: A linha 'id("org.jetbrains.kotlin.kapt")' foi substituída pela linha abaixo
    id("com.google.devtools.ksp")
}

android {
    namespace = "br.com.meuapp" // Mude para o seu namespace
    compileSdk = 34

    defaultConfig {
        applicationId = "br.com.meuapp" // Mude para o seu ID
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

    // ATENÇÃO:
    // Como estamos usando o novo `org.jetbrains.kotlin.plugin.compose` (do seu raiz),
    // NÃO precisamos mais dos blocos `buildFeatures { compose = true }`
    // nem `composeOptions { ... }`.
    // O plugin já cuida disso.

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
    // As bibliotecas vêm do 'toml'
    implementation(libs.hilt.android)

    // MUDANÇA: A linha 'kapt(libs.hilt.compiler)' foi substituída pela linha abaixo
    ksp(libs.hilt.compiler)

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

// MUDANÇA: O bloco 'kapt { ... }' foi removido