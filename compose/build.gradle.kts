plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.github.chanjungkim.compose"
    compileSdk = 33

    buildFeatures {
        compose = true
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

    testApi(libs.junit)
    androidTestApi(libs.androidx.test.ext.junit)
    androidTestApi(libs.espresso.core)
}