package com.muxixyz.ccnubox

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.muxixyz.ccnubox.iokit.export.ioKitKoinProvider
import com.muxixyz.ccnubox.main.export.mainKoinProvider
import com.muxixyz.ccnubox.profile.export.profileKoinProvider
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class CCNUBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initARouter()
        initKoin()

    }

    private fun initKoin() {
        startKoin {
            //androidLogger()
            androidLogger(Level.ERROR)
            androidContext(this@CCNUBoxApplication)
            androidFileProperties()
            modules(
                arrayListOf(
                mainKoinProvider,
                ioKitKoinProvider,
                profileKoinProvider)
            )
        }
    }

    private fun initARouter() {
        ARouter.init(this@CCNUBoxApplication)
    }
}