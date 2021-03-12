package com.muxixyz.ccnubox.timetable.data.database

import androidx.room.*
import com.muxixyz.ccnubox.timetable.data.domain.Course
import com.muxixyz.ccnubox.timetable.data.domain.TimetableRecord

@Entity
data class DatabaseCourse constructor(
    @PrimaryKey
    var id: String,
    var name: String,
    var place: String,
    var teacher: String,
    var dayOfWeek: Int,  // 一周的第几天，0 是周一
    var courseTime: String, // 比如：1-2，表示第一节到第二节
    var weeks: String, // 1,2,3,4,5 哪几周上课
    var courseTableId: String // 课表 id
)

fun List<DatabaseCourse>.asCourseDomainModel(): List<Course> = map { it.asDomainModel() }

fun DatabaseCourse.asDomainModel(): Course =
    let {
        Course(
            id = it.id,
            name = it.name,
            place = it.place,
            teacher = it.teacher,
            dayOfWeek = it.dayOfWeek,
            courseTime = it.courseTime,
            weeks = it.weeks,
            courseTableId = it.courseTableId
        )
    }

@Dao
interface CourseDao {

    @Query("select * from databasecourse")
    suspend fun getCourses(): List<DatabaseCourse>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourse(course: DatabaseCourse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCourses(courses: List<DatabaseCourse>)

    @Query("SELECT * FROM databasecourse WHERE id = :courseId")
    suspend fun getCourseById(courseId: String): DatabaseCourse?

    @Update
    suspend fun updateCourse(course: DatabaseCourse)

    @Query("delete from databasecourse")
    fun deleteAllCourses()

    @Query("delete from databasecourse where id = :courseId")
    suspend fun deleteCourseById(courseId: String)
}

@Database(entities = [DatabaseCourse::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}

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