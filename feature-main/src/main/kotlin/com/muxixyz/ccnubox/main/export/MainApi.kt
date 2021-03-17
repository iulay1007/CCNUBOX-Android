package com.muxixyz.ccnubox.main.export

import com.muxixyz.ccnubox.main.data.repository.ScheduleRepository
import com.muxixyz.ccnubox.main.data.repository.TimetableRepository

class MainApi(private val timetableRepository: TimetableRepository, private val scheduleRepository: ScheduleRepository) : IMainExportApi {

}