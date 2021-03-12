import com.muxixyz.ccnubox.build.BC.Versions
import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.implementation
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
//    id("me.2bab.bro")
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
                arg("AROUTER_MODULE_NAME", project.getName())
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            isTestCoverageEnabled = true
            ndk {
                setAbiFilters(setOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86"))
            }
        }

        getByName("release") {
            isMinifyEnabled = true
            isZipAlignEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFiles(file("proguard-rules.pro"))
            ndk {
                setAbiFilters(setOf("arm64-v8a", "armeabi-v7a"))
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

    implementation(project(Project.featureHome))
    implementation(project(Project.featureHomeExport))
    implementation(project(Project.featureLibrary))
    implementation(project(Project.featureLibraryExport))
    implementation(project(Project.featureProfile))
    implementation(project(Project.featureProfileExport))
    implementation(project(Project.featureSchedule))
    implementation(project(Project.featureScheduleExport))
    implementation(project(Project.featureTimetable))
    implementation(project(Project.featureTimetableExport))
    implementation(project(Project.featureCCNUToolbox))
    implementation(project(Project.featureCCNUToolboxExport))
    implementation(project(Project.featureUniToolbox))
    implementation(project(Project.featureUniToolboxExport))

    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.featureCommon))

    implementation(project(Project.infrastructureIOKit))
//    kapt(Deps.broCompiler)
    implementation(Deps.arouterApi)
    kapt(Deps.arouterCompiler)

    implementation(Deps.kotlinGroup)

//    implementation(Deps.broRuntimeGroup)
    implementation(Deps.koinRuntimeGroup)
    implementation(Deps.jetpackUIGroup)
    implementation(Deps.networkGroup)
    implementation(Deps.gson)
    debugImplementation(Deps.stethoOKHttp)
}

kapt {
    useBuildCache = false
}