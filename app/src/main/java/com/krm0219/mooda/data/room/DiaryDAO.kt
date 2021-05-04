package com.krm0219.mooda.data.room

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single

@Dao
interface DiaryDAO : BaseDao<DiaryData> {

//    @Query("SELECT * FROM DIARY")
//    suspend fun getAll(): LiveData<List<Diary>>

    @Query("SELECT * FROM DIARY WHERE id = :id")
    fun getDiaryById(id: Int?): Single<DiaryData>



    @Query("SELECT * FROM DIARY")
    suspend fun selectAll(): List<DiaryData>

    @Query("SELECT * FROM DIARY WHERE year=:year AND month=:month AND day=:day")
    suspend fun selectByDate(year: Int, month: Int, day: Int): DiaryData

    @Query("SELECT id FROM DIARY WHERE year=:year AND month=:month AND day=:day")
    suspend fun selectIdByDate(year: Int, month: Int, day: Int): Int


    @Query("DELETE FROM DIARY")
    suspend fun deleteAll()
}