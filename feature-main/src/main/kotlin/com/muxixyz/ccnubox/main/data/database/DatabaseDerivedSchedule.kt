package com.muxixyz.ccnubox.main.data.database

import androidx.room.*
import com.muxixyz.ccnubox.main.data.domain.DerivedSchedule

@Entity
data class DatabaseDerivedSchedule(
    @PrimaryKey
    var id: Int,
    var name: String,
    var teacher: String,
    var place: String,
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