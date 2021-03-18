package com.muxixyz.ccnubox.main.export


interface ITimetableExportApi {
    suspend fun getTimetable(): List<Any>
}