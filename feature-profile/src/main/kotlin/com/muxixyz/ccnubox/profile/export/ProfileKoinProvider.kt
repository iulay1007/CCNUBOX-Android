package com.muxixyz.ccnubox.profile.export

import com.muxixyz.ccnubox.profile.ui.profile.ProfileFragment
import org.koin.core.module.Module
import org.koin.dsl.module

val profileKoinProvider: Module = module {
    single<IProfileFragment>{ProfileFragment()}
}