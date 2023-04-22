plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.github.chanjungkim.compose"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
}

dependencies {
    api(libs.core.ktx)
    api(libs.lifecycle.runtime.ktx)
    api(libs.activity.compose)
    api(platform(libs.compose.bom))
    api(libs.ui)
    api(libs.ui.graphics)
    api(libs.ui.tooling.preview)
    api(libs.material3)
    debugApi(libs.androidx.ui.test.manifest)
    debugApi(libs.ui.test.manifest)
    testApi(libs.junit)
    androidTestApi(libs.ui.test.junit4)
    androidTestApi(libs.androidx.runner)
    androidTestApi(libs.androidx.test.ext.junit)
    androidTestApi(libs.espresso.core)
    // Test rules and transitive dependencies:
// Needed for createAndroidComposeRule, but not createComposeRule:
}