package com.muxixyz.ccnubox.main.export

import androidx.room.Room
import com.muxixyz.ccnubox.main.data.database.*
import com.muxixyz.ccnubox.main.data.repository.ScheduleRepository
import com.muxixyz.ccnubox.main.data.network.ScheduleRemoteRepo
import com.muxixyz.ccnubox.main.data.network.TimetableRemoteRepo
import com.muxixyz.ccnubox.main.data.repository.TimetableRepository
import com.muxixyz.ccnubox.main.ui.home.HomeFragment
import com.muxixyz.ccnubox.main.ui.home.HomeViewModel
import com.muxixyz.ccnubox.main.ui.main.MainViewModel
import com.muxixyz.ccnubox.main.ui.schedule.ScheduleFragment
import com.muxixyz.ccnubox.main.ui.schedule.ScheduleViewModel
import com.muxixyz.ccnubox.main.ui.todo.TodoFragment
import com.muxixyz.ccnubox.main.ui.todo.TodoViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val mainKoinProvider: Module = module {

    single { ScheduleFragment() }
    single { TodoFragment() }
    single { HomeFragment() }

    viewModel { MainViewModel(get()) }
    viewModel { HomeViewModel() }
    viewModel { ScheduleViewModel(get(),get()) }
    viewModel { TodoViewModel(get()) }

    single<IMainExportApi> { MainApi(get(), get()) }

    //network
    single { ScheduleRemoteRepo(get()) }

    single { TimetableRemoteRepo(get()) }

    // database
    single {
        Room.databaseBuilder(androidApplication(), ScheduleDatabase::class.java, "schedule")
            .build()
    }
    single { get<ScheduleDatabase>().scheduleDao() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            DerivedScheduleDatabase::class.java,
            "derived schedule"
        )
            .build()
    }
    single { get<DerivedScheduleDatabase>().derivedScheduleDao() }

    single {
        Room.databaseBuilder(androidApplication(), CourseDatabase::class.java, "course")
            .build()
    }
    single { get<CourseDatabase>().courseDao() }

    single {
        Room.databaseBuilder(
            androidApplication(),
            TimetableRecordDatabase::class.java,
            "timetable record"
        )
            .build()
    }
    single { get<TimetableRecordDatabase>().timetableRecordDao() }

    single { ScheduleLocalRepo(androidApplication(), get(), get()) }

    single { TimetableLocalRepo(androidApplication(), get(), get()) }

    single { ScheduleRepository(get(), get()) }

    single { TimetableRepository(get(), get()) }
}