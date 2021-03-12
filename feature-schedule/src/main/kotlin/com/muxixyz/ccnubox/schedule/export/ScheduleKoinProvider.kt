package com.muxixyz.ccnubox.schedule.export

import androidx.room.Room
import com.muxixyz.ccnubox.schedule.data.ScheduleRepository
import com.muxixyz.ccnubox.schedule.data.database.DerivedScheduleDatabase
import com.muxixyz.ccnubox.schedule.data.database.ScheduleDatabase
import com.muxixyz.ccnubox.schedule.data.database.ScheduleLocalRepo
import com.muxixyz.ccnubox.schedule.data.domain.DerivedSchedule
import com.muxixyz.ccnubox.schedule.data.network.ScheduleRemoteRepo
import com.muxixyz.ccnubox.schedule.ui.ScheduleFragment
import com.muxixyz.ccnubox.schedule.ui.ScheduleViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val scheduleKoinProvider: Module = module {
    single<IScheduledFragment> { ScheduleFragment() }


    viewModel { ScheduleViewModel(get()) }

    //network
    single { ScheduleRemoteRepo(get()) }

    // database
    single {
        Room.databaseBuilder(androidApplication(), ScheduleDatabase::class.java, "schedule")
            .build()
    }
    single { get<ScheduleDatabase>().scheduleDao() }

    single {
        Room.databaseBuilder(androidApplication(), DerivedScheduleDatabase::class.java, "derived schedule")
            .build()
    }
    single { get<DerivedScheduleDatabase>().derivedScheduleDao() }

    single { ScheduleLocalRepo(androidApplication(), get(), get()) }

    single { ScheduleRepository(get(), get()) }
}
