package com.muxixyz.ccnubox.home.export

import androidx.room.Room
import com.muxixyz.ccnubox.home.data.HomeRepository
import com.muxixyz.ccnubox.home.data.database.HomeLocalRepo
import com.muxixyz.ccnubox.home.data.database.TodoDatabase
import com.muxixyz.ccnubox.home.data.network.HomeRemoteRepo
import com.muxixyz.ccnubox.home.ui.HomeViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val homeKoinProvider: Module = module {

    viewModel { HomeViewModel(get()) }

    single<IHomeExportApi> { HomeApi(get()) }

    //network
    single { HomeRemoteRepo(get()) }

    // database
    single {
        Room.databaseBuilder(androidApplication(), TodoDatabase::class.java, "todo")
            .build()
    }
    single { get<TodoDatabase>().todoDao() }

    single { HomeLocalRepo(androidApplication(), get())}

    single { HomeRepository(get(), get()) }
}