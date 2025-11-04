plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // Compose plugin removed - now using Fragments + XML
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
}

android {
    namespace = "student.projects.animalsindistress"
    compileSdk = 36

    defaultConfig {
        applicationId = "student.projects.animalsindistress"
        minSdk = 35
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
    lint {
        // Disable the PropertyEscape check that's causing issues with local.properties
        disable += "PropertyEscape"
        // Avoid file locking issues on Windows
        checkReleaseBuilds = false
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        // Compose disabled - now using Fragments + XML
        compose = false
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    
    // Compose dependencies removed - now using Fragments + XML
    // implementation(libs.androidx.activity.compose)
    // implementation(platform(libs.androidx.compose.bom))
    // implementation(libs.androidx.compose.ui)
    // implementation(libs.androidx.compose.ui.graphics)
    // implementation(libs.androidx.compose.ui.tooling.preview)
    // implementation(libs.androidx.compose.material3)
    // implementation(libs.androidx.navigation.compose)
    // implementation(libs.coil.compose)
    // implementation(libs.androidx.compose.material.icons.extended)
    // implementation(libs.androidx.compose.ui.text.google.fonts)
    
    // XML-based UI components
    implementation(libs.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)
    implementation(libs.androidx.fragment.ktx)
    
    // Navigation for Fragments
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    
    // Coil for ImageView in XML-based RecyclerView
    implementation("io.coil-kt:coil:2.6.0")
    
    // Kotlinx Serialization (optional for future data parsing)
    implementation(libs.kotlinx.serialization.json)

    // Firebase Bill of Materials to manage consistent versions
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    // Firebase services
    implementation("com.google.firebase:firebase-analytics-ktx")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("com.google.firebase:firebase-storage-ktx")
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Compose test dependencies removed
    // androidTestImplementation(platform(libs.androidx.compose.bom))
    // androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    // debugImplementation(libs.androidx.compose.ui.tooling)
    // debugImplementation(libs.androidx.compose.ui.test.manifest)
}