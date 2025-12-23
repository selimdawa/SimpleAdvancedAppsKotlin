plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.flatcode.simpleadvancedapps"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.flatcode.simpleadvancedapps"
        minSdk = 24
        targetSdk = 36
        versionCode = 4
        versionName = "1.04"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        kapt { arguments { arg("room.schemaLocation", "$projectDir/schemas") } }
    }

    signingConfigs {
        create("release") {
            storeFile =
                file("D:\\MyProjects\\Kotlin\\Simple Advanced Apps\\Simple Advanced Apps\\SimpleAdvancedApps.jks")
            storePassword = "00000000"
            keyAlias = "SimpleAdvancedApps"
            keyPassword = "00000000"
        }
    }
    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        //kotlinCompilerExtensionVersion = "1.5.10"
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
}

dependencies {
    //def lifecycle_version = "2.5.1"
    //def retrofit_version = "2.9.0"
    //def room_version = "2.5.0"
    //def coroutines_version = "1.6.4"
    //def compose_ui_version = "1.3.3"

    val hilt_version = rootProject.extra["hilt_version"]
    val nav_version = rootProject.extra["nav_version"]
    val lifecycle_version = "2.6.1"
    val retrofit_version = "2.9.0"
    val room_version = "2.5.0"
    val coroutines_version = "1.6.4"
    val compose_ui_version = "1.3.3"
    //App Req
    implementation("androidx.core:core-ktx:1.12.0")                      //Kotlin Core
    //implementation("androidx.core:core-ktx:1.9.0")                      //Kotlin Core
    implementation("androidx.appcompat:appcompat:1.6.1")                //Appcompat for class
    implementation("androidx.preference:preference-ktx:1.2.1")          //Shared preference
    testImplementation("junit:junit:4.13.2")                            //Test
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    //Layout
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")  //Constraint Layout
    implementation("com.google.android.material:material:1.11.0")        //Material
    implementation("androidx.legacy:legacy-support-v4:1.0.0")           //Legacy Support
    implementation("androidx.cardview:cardview:1.0.0")                  //Card View
    implementation("androidx.recyclerview:recyclerview:1.3.2")          //Recycle View
    //implementation("androidx.recyclerview:recyclerview:1.0.0")          //Recycle View
    implementation("androidx.fragment:fragment-ktx:1.6.2")              //Kotlin Fragment
    //implementation("androidx.fragment:fragment-ktx:1.5.5")              //Kotlin Fragment
    implementation("androidx.activity:activity-ktx:1.8.2")              //Kotlin Activity
    //implementation("androidx.activity:activity-ktx:1.6.1")              //Kotlin Activity
    //Image
    implementation("de.hdodenhof:circleimageview:3.1.0")                //Circle image
    implementation("com.github.bumptech.glide:glide:4.15.1")            //Glide image
    //noinspection KaptUsageInsteadOfKsp
    kapt("com.github.bumptech.glide:compiler:4.15.1")                   //Glide compiler
    implementation("jp.wasabeef:glide-transformations:4.3.0")           //Glide Blur
    implementation("com.github.skydoves:landscapist-glide:1.6.1")       //Glide
    implementation("com.squareup.picasso:picasso:2.71828")              //Picasso
    //Life Cycle
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")     //Life Cycle Extensions
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    implementation("android.arch.lifecycle:common-java8:1.1.1")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version") //Retrofit
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")   //Gson
    implementation("com.squareup.retrofit2:converter-moshi:$retrofit_version")  //Moshi
    implementation("com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2")
    //RxJava
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.5.0")      //Rxjava
    implementation("io.reactivex.rxjava2:rxandroid:2.1.1")              //Rxjava
    //Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version")   //Core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version") //Android
    //Navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")  //Navigation Fragment
    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")      //Navigation Components
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
    //Room
    implementation("androidx.room:room-runtime:$room_version")          //Room
    kapt("androidx.room:room-compiler:$room_version")                   //Room Compiler
    implementation("androidx.room:room-ktx:$room_version")              //Room Kotlin
    //OkHttp
    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0")) //OkHttp
    implementation("com.squareup.okhttp3:okhttp")                      //OkHttp
    implementation("com.squareup.okhttp3:logging-interceptor")         //OkHttp
    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$hilt_version")     //Dagger Hilt
    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")      //Dagger Hilt Compiler
    kapt("androidx.hilt:hilt-compiler:1.0.0")                          //Hilt Compiler
    kapt("com.google.dagger:hilt-compiler:$hilt_version")              //Hilt Compiler
    //Compose
    implementation("androidx.activity:activity-compose:1.6.1")         //Compose Activity
    implementation("androidx.compose.ui:ui:$compose_ui_version")       //Compose Ui
    implementation("androidx.compose.material:material:1.3.1")         //Compose Material
    implementation("androidx.compose.ui:ui-tooling-preview:$compose_ui_version")  //Compose Preview
    //Compose Ui Preview
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_ui_version")//Compose Test
    debugImplementation("androidx.compose.ui:ui-tooling:$compose_ui_version")    //Compose Ui Tooling
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version")//Compose MVVM
    implementation("com.opencsv:opencsv:5.5.2")                        //Open CSV
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.2-alpha")
    implementation("com.google.accompanist:accompanist-pager:0.26.1-alpha")    //Pager
    implementation("io.github.raamcosta.compose-destinations:core:1.1.2-beta")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")      //Compose Hilt
    //Others
    implementation("io.coil-kt:coil:2.2.2")                            //Coil
    implementation("com.google.code.gson:gson:2.10.1")                    //Gson
    implementation("org.jsoup:jsoup:1.13.1")                            //Jsoup
    implementation("com.android.volley:volley:1.2.1")                 //Volley
    implementation("com.intuit.ssp:ssp-android:1.1.0")                 //Size Unit
    implementation("com.intuit.sdp:sdp-android:1.1.0")                 //Size Unit
    implementation("net.objecthunter:exp4j:0.4.8")                     //Calculator
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")     //Swipe Refresh
    implementation("com.google.android.gms:play-services-location:21.2.0")     //Weather Location
    //noinspection GradleDependency
    implementation("com.airbnb.android:lottie:3.4.0")                  //Lottie Files
    implementation("com.facebook.shimmer:shimmer:0.5.0")               //Facebook Shimmer
    implementation("androidx.datastore:datastore-preferences:1.0.0")   // DataStore
    implementation("com.jakewharton.timber:timber:5.0.1")              //Timber Log
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.5.6") //Binding
}
kapt {
    correctErrorTypes = true
}