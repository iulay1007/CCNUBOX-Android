package com.muxixyz.ccnubox.build

import org.gradle.api.JavaVersion

// BuildConfig for Gradle Scripts
object BC {

    object Project {
        const val featureMain = ":feature-main"
        const val featureMainExport = ":feature-main-export"

        const val featureToolbox = ":feature-toolbox"
        const val featureToolboxExport = ":feature-toolbox-export"

        const val featureProfile = ":feature-profile"
        const val featureProfileExport = ":feature-profile-export"

        const val featureIOKit = ":feature-iokit"
        const val featureUIKit = ":feature-uikit"

        const val featureCommon = ":feature-common"

        const val infrastructureIOKit = ":infras-iokit"
    }

    // How to query versions:
    //
    // - Google Maven Repo: https://maven.google.com/web/index.html
    // - AndroidX releases: https://developer.android.com/jetpack/androidx/versions
    // - JCenter: https://bintray.com/bintray/jcenter
    object Versions {

        // Android SDK Versions
        const val compileSdkVersion = 29
        const val minSdkVersion = 21
        const val targetSdkVersion = 29

        // Java Compatible Version
        const val ktVer = "1.3.72"
        const val ktCoroutineVer = "1.3.7"
        val sourceCompatibilityVersion = JavaVersion.VERSION_1_8
        val targetCompatibilityVersion = JavaVersion.VERSION_1_8

        // Modulization / DI
        const val broVer = "1.3.6"
        const val koinVer = "2.1.6"
        const val arouterVer = "1.5.1"

        // ARouter
        const val arouterAPIVer = "1.5.1"
        const val arouterCompilerVer = "1.5.1"
        const val arouterRegisterVer = "1.0.2"

        // Jetpack
        const val jectpackAppCompatVer = "1.2.0-rc01"
        const val jectpackMaterialVer = "1.2.0-beta01"
        const val jetpackArchCoreVer = "2.1.0"
        const val jetpackLifecycleVer = "2.2.0"
        const val jetpackRoomVer = "2.2.5"
        const val jectpackTestVer = "1.3.0-rc01"
        const val jetpackEspressoVer = "3.3.0-rc01"


        // Other 3rd libs
        const val retrofitVer = "2.9.0"
        const val okhttpVer = "4.7.2"
    }

    object Deps {

        // Kotlin
        const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.ktVer}"
        const val kotlinCoroutineCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.ktCoroutineVer}"
        const val kotlinCoroutineAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.ktCoroutineVer}"
        val kotlinGroup = setOf(
            kotlinStd,
            kotlinCoroutineCore,
            kotlinCoroutineAndroid
        )

        const val arouterApi = "com.alibaba:arouter-api:${Versions.arouterAPIVer}"
        const val arouterCompiler = "com.alibaba:arouter-compiler:${Versions.arouterCompilerVer}"
        const val arouterRegister = "com.alibaba:arouter-register:${Versions.arouterRegisterVer}"

        const val koin = "org.koin:koin-android:$${Versions.koinVer}"
        const val koinScope =
            "org.koin:koin-androidx-scope:$${Versions.koinVer}" // use the androidx dep
        const val koinViewModel =
            "org.koin:koin-androidx-viewmodel:${Versions.koinVer}" // use the androidx dep
        const val koinTest = "org.koin:koin-test:$${Versions.koinVer}"
        val koinRuntimeGroup = setOf(
            koin,
            koinScope,
            koinViewModel
        )

        // Jetpack - UI
        const val jetpackAppCompat = "androidx.appcompat:appcompat:${Versions.jectpackAppCompatVer}"
        const val jetpackMaterial =
            "com.google.android.material:material:${Versions.jectpackMaterialVer}"
        const val jetpackConstraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        val jetpackUIGroup = setOf(
            jetpackAppCompat,
            jetpackMaterial,
            jetpackConstraintLayout
        )

        // Jetpack - Lifecycle
        const val jetpackLifeCycleRuntime =
            "androidx.lifecycle:lifecycle-runtime:${Versions.jetpackLifecycleVer}"
        const val jetpackLifeCycleExt =
            "androidx.lifecycle:lifecycle-extensions:${Versions.jetpackLifecycleVer}"
        const val jetpackLifeCycleViewModelKtx =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.jetpackLifecycleVer}"
        const val jetpackLifeCycleCompiler =
            "androidx.lifecycle:lifecycle-compiler:${Versions.jetpackLifecycleVer}"
        val jetpackLifeCycleRuntimeGroup = setOf(
            jetpackLifeCycleRuntime,
            jetpackLifeCycleExt,
            jetpackLifeCycleViewModelKtx
        )

        // Jetpack - Room
        const val jetpackRoomRuntime = "androidx.room:room-runtime:${Versions.jetpackRoomVer}"
        const val jetpackRoomKtx = "androidx.room:room-ktx:${Versions.jetpackRoomVer}"
        const val jetpackRoomCompiler = "androidx.room:room-compiler:${Versions.jetpackRoomVer}"
        val roomRuntimeGroup = setOf(
            jetpackRoomRuntime,
            jetpackRoomKtx
        )

        // Other 3rd libs
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVer}"
        const val retrofitGsonAdapter =
            "com.squareup.retrofit2:converter-gson:${Versions.retrofitVer}"
        const val okHttp = "com.squareup.okhttp3:okhttp:${Versions.okhttpVer}"
        const val okHttpLogger = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVer}"
        val networkGroup = setOf(
            retrofit,
            retrofitGsonAdapter,
            okHttp,
            okHttpLogger
        )

        const val gson = "com.google.code.gson:gson:2.8.6"
        const val stethoOKHttp = "com.facebook.stetho:stetho-okhttp3:1.5.1"

        // Test

    }
}