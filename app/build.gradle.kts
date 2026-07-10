plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.navigation.safeargs.kotlin)
}

android {
    namespace = "com.flatcode.simpleadvancedapps"
    compileSdk = 37

    defaultConfig {
        applicationId = "com.flatcode.simpleadvancedapps"
        minSdk = 24
        targetSdk = 37
        versionCode = 5
        versionName = "1.05"

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //Layout
    implementation(libs.androidx.constraintlayout)
    implementation(libs.material)
    //Image
    implementation(libs.circleimageview)                //Circle image
    implementation(libs.glide)            //Glide image
    ksp(libs.glide.ksp)                   //Glide compiler
    implementation(libs.glide.transformations)           //Glide Blur
    implementation(libs.landscapist.glide)       //Glide
    implementation(libs.picasso)              //Picasso
    implementation(libs.coil)                            //Coil
    implementation(libs.lottie)                  //Lottie Files
    implementation(libs.shimmer)               //Facebook Shimmer
    //Life Cycle
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.common.java8)
    //Retrofit
    implementation(libs.retrofit.core) //Retrofit
    implementation(libs.retrofit.converter.gson)   //Gson
    implementation(libs.retrofit.converter.moshi)  //Moshi
    //RxJava
    implementation(libs.retrofit.adapter.rxjava3)
    implementation(libs.rxjava3.android)              //Rxjava
    //Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)   //Core
    implementation(libs.kotlinx.coroutines.android) //Android
    //Navigation
    implementation(libs.navigation.fragment.ktx)  //Navigation Fragment
    implementation(libs.navigation.ui.ktx)      //Navigation Components
    implementation(libs.navigation.dynamic.features)
    //Room
    implementation(libs.room.runtime)          //Room
    ksp(libs.room.compiler)                    //Room Compiler
    implementation(libs.room.ktx)              //Room Kotlin
    //OkHttp
    implementation(platform(libs.okhttp.bom)) //OkHttp
    implementation(libs.okhttp.core)                      //OkHttp
    implementation(libs.okhttp.logging)         //OkHttp
    //Dagger - Hilt
    implementation(libs.hilt.android)     //Dagger Hilt
    ksp(libs.hilt.android.compiler)      //Dagger Hilt Compiler
    //Other's
    implementation(libs.gson)                    //Gson
    implementation(libs.jsoup)                            //Jsoup
    implementation(libs.volley)                 //Volley
    implementation(libs.intuit.ssp)                 //Size Unit
    implementation(libs.intuit.sdp)                 //Size Unit
    implementation(libs.exp4j)                     //Calculator
    implementation(libs.swiperefreshlayout)     //Swipe Refresh
    implementation(libs.play.services.location)     //Weather Location
    implementation(libs.datastore.preferences)   //DataStore
    implementation(libs.timber)              //Timber Log
    ksp(libs.kotlin.metadata.jvm)                       //Kotlin
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}