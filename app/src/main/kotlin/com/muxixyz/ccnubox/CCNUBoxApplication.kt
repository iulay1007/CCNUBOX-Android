package com.muxixyz.ccnubox

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.muxixyz.ccnubox.home.export.homeKoinProvider
import com.muxixyz.ccnubox.timetable.export.timetableKoinProvider
import com.muxixyz.ccnubox.iokit.export.ioKitKoinProvider
import com.muxixyz.ccnubox.schedule.export.scheduleKoinProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class CCNUBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initARouter()
        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@CCNUBoxApplication)
            androidFileProperties()
            modules(
                homeKoinProvider,
                ioKitKoinProvider,
                scheduleKoinProvider,
                timetableKoinProvider
            )
        }
    }

    private fun initARouter() {
        ARouter.init(this@CCNUBoxApplication)
    }
}