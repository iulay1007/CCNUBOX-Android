package com.muxixyz.ccnubox.main.data.database

import androidx.room.*
import com.muxixyz.ccnubox.main.data.domain.TimetableRecord


@Entity
data class DatabaseTimetableRecord(
    @PrimaryKey
    var id: String,
    var name: String,
    var sequence: Int,  // 节次
    var startHour: Int,
    var startMinute: Int,
    var endHour: Int,
    var endMinute: Int,
    var timetableId: String // 所属的作息时间表 id
)

fun List<DatabaseTimetableRecord>.asTimetableDomainModel(): List<TimetableRecord> =
    map { it.asDomainModel() }

fun DatabaseTimetableRecord.asDomainModel(): TimetableRecord =
    let {
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

@Dao
interface TimetableRecordDao {

    @Query("select * from databasetimetablerecord")
    suspend fun getTimetableRecords(): List<DatabaseTimetableRecord>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableRecord(timetableRecords: DatabaseTimetableRecord)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimetableRecords(timetableRecordss: List<DatabaseTimetableRecord>)

    @Query("SELECT * FROM databasetimetablerecord WHERE id = :timetableRecordsId")
    suspend fun getTimetableRecordById(timetableRecordsId: String): DatabaseTimetableRecord?

    @Update
    suspend fun updateTimetableRecord(timetableRecords: DatabaseTimetableRecord)

    @Query("delete from databasetimetablerecord")
    fun deleteAllTimetableRecords()

    @Query("delete from databasetimetablerecord where id = :timetableRecordsId")
    suspend fun deleteTimetableRecordById(timetableRecordsId: String)
}

@Database(entities = [DatabaseTimetableRecord::class], version = 1)
abstract class TimetableRecordDatabase : RoomDatabase() {
    abstract fun timetableRecordDao(): TimetableRecordDao
}