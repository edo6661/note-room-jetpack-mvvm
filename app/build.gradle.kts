plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  id("org.jetbrains.kotlin.kapt")
  id("com.google.dagger.hilt.android")
  alias(libs.plugins.kotlin.serialization)

}

android {
  namespace = "com.example.notes"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.notes"
    minSdk = 30
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
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    compose = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  // navigation
  val nav_version = "2.8.3"
  implementation("androidx.navigation:navigation-compose:$nav_version")

  // hilt
  val hilt_version = "2.52"
  implementation("com.google.dagger:hilt-android:$hilt_version")
  kapt("com.google.dagger:hilt-compiler:$hilt_version")

  // room
  val room_version = "2.6.1"
  implementation("androidx.room:room-runtime:$room_version")
  implementation("androidx.room:room-ktx:$room_version")
  kapt("androidx.room:room-compiler:$room_version")

  // icons
  implementation("androidx.compose.material:material-icons-core")
  implementation("androidx.compose.material:material-icons-extended")


  // serialization
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

}

kapt {
  correctErrorTypes = true
}