package com.muxixyz.ccnubox.main.data.network

import com.muxixyz.ccnubox.main.data.database.DatabaseTimetableRecord
import com.muxixyz.ccnubox.main.data.domain.TimetableRecord

data class NetworkTimetableRecord(
    var id: String,
    var name: String,
    var sequence: Int,  // 节次
    var startHour: Int,
    var startMinute: Int,
    var endHour: Int,
    var endMinute: Int,
    var timetableId: String // 所属的作息时间表 id
)

fun List<NetworkTimetableRecord>.asTimetableRecordDomainModel(): List<TimetableRecord> =
    map {
        TimetableRecord(
            id = it.id,
            name = it.name,
            sequence = it.sequence,
            startHour = it.startHour,
            startMinute = it.startMinute,
            endHour = it.endHour,
            endMinute = it.endMinute,
            timetableId = it.timetableId
        )
    }

fun List<NetworkTimetableRecord>.asTimetableRecordDatabaseModel(): List<DatabaseTimetableRecord> =
    map {
        DatabaseTimetableRecord(
            id = it.id,
            name = it.name,
            sequence = it.sequence,
            startHour = it.startHour,
            startMinute = it.startMinute,
            endHour = it.endHour,
            endMinute = it.endMinute,
            timetableId = it.timetableId
        )
    }
