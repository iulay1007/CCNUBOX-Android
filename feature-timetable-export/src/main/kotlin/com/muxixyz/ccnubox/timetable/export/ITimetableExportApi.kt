package com.muxixyz.ccnubox.timetable.export


interface ITimetableExportApi {
    suspend fun getTimetable(): List<Any>
}