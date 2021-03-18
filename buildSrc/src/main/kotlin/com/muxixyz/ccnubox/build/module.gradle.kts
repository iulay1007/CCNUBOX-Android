package com.muxixyz.ccnubox.build

import com.android.build.gradle.LibraryExtension
import com.muxixyz.ccnubox.build.BC.Versions
import com.muxixyz.ccnubox.build.BC.Deps

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
//    id("me.2bab.bro")
}

configure<LibraryExtension> {

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        compileSdkVersion(Versions.compileSdkVersion)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

//        kapt {
//            arguments {
//                arg("AROUTER_MODULE_NAME", project.getName())
//            }
//        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles(file("proguard-rules.pro"))
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    buildFeatures {
        dataBinding = true
        // for view binding :
        // viewBinding = true
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    testOptions {
        unitTests.isReturnDefaultValues = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
}

dependencies {
//    implementation(Deps.broRuntimeGroup)
    implementation(Deps.koinRuntimeGroup)
//    kapt(Deps.broCompiler)
//    implementation(Deps.arouterApi)
//    kapt(Deps.arouterCompiler)
}

kapt {
    useBuildCache = false
}