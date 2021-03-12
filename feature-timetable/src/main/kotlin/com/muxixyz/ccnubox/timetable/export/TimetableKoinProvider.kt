package com.muxixyz.ccnubox.timetable.export

import androidx.room.Room
import com.muxixyz.ccnubox.timetable.data.database.CourseDatabase
import com.muxixyz.ccnubox.timetable.data.database.TimetableLocalRepo
import com.muxixyz.ccnubox.timetable.data.database.TimetableRecordDatabase
import com.muxixyz.ccnubox.timetable.data.network.TimetableRemoteRepo
import com.muxixyz.ccnubox.timetable.data.repository.TimetableRepository
import com.muxixyz.ccnubox.timetable.ui.TimetableViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val timetableKoinProvider: Module = module {

    viewModel { TimetableViewModel(get()) }

    single<ITimetableExportApi> { TimetableApi(get()) }

    //network
    single { TimetableRemoteRepo(get()) }

    // database
    single {
        Room.databaseBuilder(
            androidApplication(),
            CourseDatabase::class.java,
            "course"
        )
            .build()
    }
    single { get<CourseDatabase>().courseDao() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            TimetableRecordDatabase::class.java,
            "timetableRecord"
        )
            .build()
    }
    single { get<TimetableRecordDatabase>().timetableRecordDao() }

    single { TimetableLocalRepo(androidApplication(), get(), get()) }

    single { TimetableRepository(get(), get()) }
}