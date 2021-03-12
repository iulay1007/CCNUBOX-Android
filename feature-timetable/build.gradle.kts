import com.muxixyz.ccnubox.build.BC.Project
import com.muxixyz.ccnubox.build.BC.Deps

plugins {
    id("com.muxixyz.ccnubox.build.module")
}

dependencies {
    implementation(project(Project.featureTimetableExport))
    implementation(project(Project.featureUIKit))
    implementation(project(Project.featureIOKit))
    implementation(project(Project.infrastructureIOKit))

    implementation(project(Project.featureScheduleExport))
    implementation(project(Project.featureHomeExport))
    implementation(project(Project.featureCCNUToolboxExport))
    implementation(project(Project.featureUniToolboxExport))
    implementation(project(Project.featureProfileExport))

    implementation(Deps.arouterApi)
    implementation(Deps.koin)
    implementation(Deps.koinScope)
    implementation(Deps.koinViewModel)
    implementation(Deps.jetpackAppCompat)
    implementation(Deps.jetpackMaterial)
    implementation(Deps.jetpackConstraintLayout)
    implementation(Deps.retrofit)
    implementation(Deps.retrofitGsonAdapter)
    implementation(Deps.okHttp)
    implementation(Deps.okHttpLogger)
    implementation(Deps.jetpackLifeCycleRuntime)
    implementation(Deps.jetpackLifeCycleExt)
    implementation(Deps.jetpackLifeCycleViewModelKtx)
    implementation(Deps.jetpackRoomRuntime)
    implementation(Deps.jetpackRoomKtx)
    kapt(Deps.jetpackRoomCompiler)
}
