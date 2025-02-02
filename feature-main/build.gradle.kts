import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.implementation

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(project(Project.featureMainExport))
    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.infrastructureIOKit))

    implementation(project(Project.featureToolboxExport))
    implementation(project(Project.featureProfileExport))

    implementation(Deps.arouterApi)
    implementation(Deps.koinRuntimeGroup)
    implementation(Deps.jetpackUIGroup)
    implementation(Deps.networkGroup)
    implementation(Deps.jetpackLifeCycleRuntimeGroup)
    implementation(Deps.roomRuntimeGroup)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    kapt(Deps.jetpackRoomCompiler)
    implementation(kotlin("reflect"))
}