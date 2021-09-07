import com.muxixyz.ccnubox.build.BC.Versions
import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.implementation

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {

    compileSdkVersion(Versions.compileSdkVersion)

    defaultConfig {
        minSdkVersion(Versions.minSdkVersion)
        targetSdkVersion(Versions.targetSdkVersion)
        versionCode = 3 * 10000 + 0 * 100 + 0
        versionName = "3.0.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        kapt {
            arguments {
                arg("AROUTER_MODULE_NAME", project.name)
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
            ndk {
//                setAbiFilters(setOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86"))

            }
        }

        getByName("release") {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles(file("proguard-rules.pro"))
            ndk {
     //           setAbiFilters(setOf("arm64-v8a", "armeabi-v7a"))
            }
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    sourceSets["main"].java.srcDir("src/main/kotlin")

    buildFeatures {
        dataBinding = true
        // for view binding :
        // viewBinding = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }

    compileOptions {
        sourceCompatibility = Versions.sourceCompatibilityVersion
        targetCompatibility = Versions.targetCompatibilityVersion
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to arrayOf("*.jar"))))

    implementation(project(Project.featureMain))
    implementation(project(Project.featureMainExport))
    implementation(project(Project.featureProfile))
    implementation(project(Project.featureProfileExport))
    implementation(project(Project.featureToolbox))
    implementation(project(Project.featureToolboxExport))

    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.featureCommon))

    implementation(project(Project.infrastructureIOKit))
    implementation(Deps.arouterApi)
    kapt(Deps.arouterCompiler)

    implementation(Deps.kotlinGroup)

    implementation(Deps.koinRuntimeGroup)
    implementation(Deps.jetpackUIGroup)
    implementation(Deps.networkGroup)
    implementation(Deps.gson)
    debugImplementation(Deps.stethoOKHttp)
}

kapt {
    useBuildCache = false
}