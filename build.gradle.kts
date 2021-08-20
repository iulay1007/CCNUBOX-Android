buildscript {

    project.extra["kotlinVersion"] = "1.3.72"
    project.extra["agpVersion"] = "4.0.0"
    project.extra["arouterRegisterVersion"]= "1.0.2"

    repositories {
        google()
        jcenter()
        mavenCentral()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        mavenLocal()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", version = project.extra["kotlinVersion"].toString()))
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("org.koin:koin-gradle-plugin:2.1.6")
//        classpath("me.2bab:bro-gradle-plugin:1.3.6")
        classpath("com.alibaba:arouter-register:1.0.2")
    }

}

allprojects {
    repositories {
        google()
        jcenter()
        mavenLocal()
    }
}

task("clean") {
    delete(rootProject.buildDir)
}