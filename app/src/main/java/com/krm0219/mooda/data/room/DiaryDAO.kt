package com.krm0219.mooda.data.room

import androidx.room.Dao
import androidx.room.Query
import java.util.*

@Dao
interface DiaryDAO : BaseDao<DiaryData> {

    @Query("SELECT * FROM DIARY ORDER BY date1 ASC")
    fun selectAll(): List<DiaryData>

    @Query("SELECT * FROM DIARY WHERE :date < date1 ORDER BY date1 ASC")
    fun selectListOverDate(date: Date): List<DiaryData>


    @Query("SELECT * FROM DIARY WHERE id = :id")
    fun selectDiaryById(id: Long): DiaryData

    @Query("SELECT id FROM DIARY WHERE date1=:date")
    fun selectIdByDate(date: Date): Long


    @Query("DELETE FROM DIARY")
    fun deleteAll()

    @Query("DELETE FROM DIARY WHERE id= :id")
    fun deleteById(id: Long)


}