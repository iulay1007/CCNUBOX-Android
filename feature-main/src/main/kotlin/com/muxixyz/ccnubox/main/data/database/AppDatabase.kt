package com.muxixyz.ccnubox.main.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DatabaseCourse::class, DatabaseDerivedSchedule::class,
        DatabaseSchedule::class, DatabaseTimetableRecord::class],
    version =1 ,exportSchema = false
)
abstract class  AppDatabase() : RoomDatabase() {
    abstract fun courseDao(): CourseDao
    abstract fun derivedScheduleDao(): DerivedScheduleDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun timetableRecordDao(): TimetableRecordDao
}

