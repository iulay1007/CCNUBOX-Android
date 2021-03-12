import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.BC.Deps
import com.muxixyz.ccnubox.build.implementation

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(project(Project.featureHomeExport))
    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.infrastructureIOKit))

    implementation(project(Project.featureScheduleExport))
    implementation(project(Project.featureTimetableExport))
    implementation(project(Project.featureCCNUToolboxExport))
    implementation(project(Project.featureUniToolboxExport))
    implementation(project(Project.featureProfileExport))

    implementation(Deps.arouterApi)
    implementation(Deps.koinRuntimeGroup)
    implementation(Deps.jetpackUIGroup)
    implementation(Deps.networkGroup)
    implementation(Deps.jetpackLifeCycleRuntimeGroup)
    implementation(Deps.roomRuntimeGroup)
    kapt(Deps.jetpackRoomCompiler)
}