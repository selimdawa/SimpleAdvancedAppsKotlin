// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    extra.apply{
        set("hilt_version", "2.44.2")
        set("nav_version", "2.5.3")
    }
    dependencies {
        val hilt_version = rootProject.extra["hilt_version"]
        val nav_version = rootProject.extra["nav_version"]
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")
    }
}

plugins {
    id("com.android.application") version "9.2.1" apply false
    id("com.android.library") version "9.2.1" apply false
    id("org.jetbrains.kotlin.android") version "2.3.21" apply false
    //id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    //id("com.google.dagger.hilt.android")) version "2.44.2" apply false
}