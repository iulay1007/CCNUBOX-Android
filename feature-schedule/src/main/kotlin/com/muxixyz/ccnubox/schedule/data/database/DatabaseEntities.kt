package com.muxixyz.ccnubox.schedule.data.database

import androidx.room.*
import com.muxixyz.ccnubox.schedule.data.domain.DerivedSchedule
import com.muxixyz.ccnubox.schedule.data.domain.Schedule

@Entity
data class DatabaseSchedule(
    @PrimaryKey
    var id: Int,
    var title: String,
    var content: String,
    var startTime: String,
    var endTime: String,
    var repeatMode: Int,
    var cron: String,
    var priority: Int,
    var categoryId: Int,
    var createdAt: String,
    var updatedAt: String,
    var cellColorId: Int
)

fun List<DatabaseSchedule>.asScheduleDomainModel(): List<Schedule> {
    return map {
        it.asScheduleDomainModel()
    }
}

fun DatabaseSchedule.asScheduleDomainModel(): Schedule {
    return let {
        Schedule(
            id = it.id,
            title = it.title,
            content = it.content,
            startTime = it.startTime,
            endTime = it.endTime,
            repeatMode = it.repeatMode,
            cron = it.cron,
            priority = it.priority,
            categoryId = it.categoryId,
            createdAt = it.createdAt,
            updatedAt = it.updatedAt,
            cellColorId = it.cellColorId
        )
    }
}

@Dao
interface ScheduleDao {

    @Query("select * from databaseschedule")
    suspend fun getSchedules(): List<DatabaseSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedule(schedule: DatabaseSchedule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSchedules(schedules: List<DatabaseSchedule>)

    @Query("SELECT * FROM databaseschedule WHERE id = :scheduleId")
    suspend fun getScheduleById(scheduleId: Int): DatabaseSchedule?

    @Update
    suspend fun updateSchedule(schedule: DatabaseSchedule)

    @Query("delete from databaseschedule")
    fun deleteAllSchedules()

    @Query("delete from databaseschedule where id = :scheduleId")
    suspend fun deleteScheduleById(scheduleId: Int)
}

@Database(entities = [DatabaseSchedule::class], version = 1)
abstract class ScheduleDatabase : RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao
}


@Entity
data class DatabaseDerivedSchedule(
    @PrimaryKey
    var id: Int,
    var name: String,
    var teacher: String,
    var place: String,
    var title: String,
    var content: String,
    var isInterval: Boolean, // false 为时间点，true 为时间段。时间点类型的只有一个 startTime
    var startTime: String,
    var endTime: String,
    var priority: Int,
    var king: Int, //课程--1，日程--0
    var categoryId: Int,
    var cellColorId: Int,
    var scheduleId: String,
    var courseId: String
)

fun List<DatabaseDerivedSchedule>.asDerivedScheduleDomainModel(): List<DerivedSchedule> {
    return map {
        it.asDerivedScheduleDomainModel()
    }
}

fun DatabaseDerivedSchedule.asDerivedScheduleDomainModel(): DerivedSchedule {
    return let {
        DerivedSchedule(
            id = it.id,
            name = it.name,
            teacher = it.teacher,
            place = it.place,
            title = it.title,
            content = it.content,
            isInterval = it.isInterval,
            startTime = it.startTime,
            endTime = it.endTime,
            priority = it.priority,
            king = it.king,
            categoryId = it.categoryId,
            cellColorId = it.cellColorId,
            scheduleId = it.scheduleId,
            courseId = it.courseId
        )
    }
}

@Dao
interface DerivedScheduleDao {

    @Query("select * from databasederivedSchedule")
    suspend fun getDerivedSchedules(): List<DatabaseDerivedSchedule>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDerivedSchedule(derivedSchedule: DatabaseDerivedSchedule)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDerivedSchedules(derivedSchedules: List<DatabaseDerivedSchedule>)

    @Query("SELECT * FROM databasederivedSchedule WHERE id = :derivedScheduleId")
    suspend fun getDerivedScheduleById(derivedScheduleId: Int): DatabaseDerivedSchedule?

    @Update
    suspend fun updateDerivedSchedule(derivedSchedule: DatabaseDerivedSchedule)

    @Query("delete from databasederivedSchedule")
    fun deleteAllDerivedSchedules()

    @Query("delete from databasederivedSchedule where id = :derivedScheduleId")
    suspend fun deleteDerivedScheduleById(derivedScheduleId: Int)
}

@Database(entities = [DatabaseDerivedSchedule::class], version = 1)
abstract class DerivedScheduleDatabase : RoomDatabase() {
    abstract fun derivedScheduleDao(): DerivedScheduleDao
}