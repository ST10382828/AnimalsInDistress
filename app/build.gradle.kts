plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.gms.google-services")
    id("org.sonarqube") version "7.0.1.6134"
    // Hilt removed for now to resolve build issues; can reintroduce later
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
        compose = true
    }
}

// SonarQube Configuration
sonarqube {
    properties {
        property("sonar.projectKey", "ST10382828_AnimalsInDistress")
        property("sonar.organization", "st10382828")
        property("sonar.projectName", "Animals In Distress")
        property("sonar.projectVersion", "1.0")
        property("sonar.sources", "src/main/java")
        property("sonar.tests", "src/test,src/androidTest")
        property("sonar.language", "kotlin")
        property("sonar.sourceEncoding", "UTF-8")
        // Exclude build files and generated code
        property("sonar.exclusions", "**/build/**,**/generated/**,**/*.xml,**/*.json")
        // Kotlin specific
        property("sonar.kotlin.linter.reportPaths", "build/reports/detekt/detekt.xml")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    
    // Material Components for XML views
    implementation("com.google.android.material:material:1.11.0")
    
    // AndroidX libraries for Fragments and XML
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    // Hilt temporarily omitted
    // Coil for images
    implementation(libs.coil.compose)
    // Coil for ImageView in XML-based RecyclerView
    implementation("io.coil-kt:coil:2.6.0")
    // Glide for advanced image caching
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    // Material Icons Extended
    implementation(libs.androidx.compose.material.icons.extended)
    // Kotlinx Serialization (optional for future data parsing)
    implementation(libs.kotlinx.serialization.json)
    // Google Fonts in Compose
    implementation(libs.androidx.compose.ui.text.google.fonts)

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
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}