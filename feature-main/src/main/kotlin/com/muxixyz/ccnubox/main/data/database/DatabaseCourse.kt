package com.muxixyz.ccnubox.main.data.database


import androidx.room.*
import com.muxixyz.ccnubox.main.data.domain.Course

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
