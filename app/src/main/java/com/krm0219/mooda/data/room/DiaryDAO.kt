package com.krm0219.mooda.data.room

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface DiaryDAO : BaseDao<DiaryData> {

    @Query("SELECT * FROM DIARY ORDER BY date ASC")
    fun selectAll(): List<DiaryData>

    @Query("SELECT * FROM DIARY WHERE id = :id")
    fun selectDiaryById1(id: Long): Single<DiaryData>

    @Query("SELECT * FROM DIARY WHERE id = :id")
    fun selectDiaryById(id: Long): DiaryData

    @Query("SELECT * FROM DIARY WHERE year=:year AND month=:month AND day=:day")
    fun selectByDate(year: Int, month: Int, day: Int): DiaryData

    @Query("SELECT * FROM DIARY WHERE year=:year AND month=:month")
    fun selectListByDate(year: Int, month: Int): List<DiaryData>


    @Query("SELECT id FROM DIARY WHERE year=:year AND month=:month AND day=:day")
    fun selectIdByDate(year: Int, month: Int, day: Int): Long


    @Query("DELETE FROM DIARY")
    fun deleteAll()

    @Query("DELETE FROM DIARY WHERE id= :id")
    fun deleteById(id: Long)
}