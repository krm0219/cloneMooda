package com.krm0219.mooda.data

import android.app.Application
import android.util.Log
import com.krm0219.mooda.data.room.DiaryDAO
import com.krm0219.mooda.data.room.DiaryData
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

// 데이터 소스를 접근하는데 필요한 로직을 캡슐화
// viewModel 에서는 Repository 를 통해 데이터에 접근
class Repository(application: Application) {

    companion object {
        val TAG = "Repository"
    }

    private var diaryDao: DiaryDAO

    init {

        val database = AppDatabase.getInstance(application)!!
        diaryDao = database.diaryDao()
    }


    fun insertDiary(entity: DiaryData) {

        diaryDao.insert(entity)
        Log.e("Repository", "insertDiary")
//
//        Observable.just(entity)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//
//                diaryDao.insert(entity)
//                Log.e("Repository", "insertDiary")
//            }, {
//
//                Log.e("Exception", "Error $it")
//            })
    }

    fun updateDiary(entity: DiaryData) {

        Observable.just(entity)
            .subscribeOn(Schedulers.io())
            .subscribe({

                diaryDao.update(entity)
            }, {

                Log.e("Exception", "Error $it")
            })
    }


    fun deleteDiaryById(id: Long) {

        diaryDao.deleteById(id)
        Log.e("Repository", "deleteDiary")
    }


    fun deleteDiary(entity: DiaryData) {

        Observable.just(entity)
            .subscribeOn(Schedulers.io())
            .subscribe({

                diaryDao.delete(entity)
            }, {

                Log.e("Exception", "Error $it")
            })
    }

    fun deleteAllDiary() {

        diaryDao.deleteAll()
        Log.e(TAG, "deleteAllDiary")
    }


    fun selectAll(): List<DiaryData> {

        return diaryDao.selectAll()
    }


    fun selectDiaryById(id: Long): DiaryData {

        return diaryDao.selectDiaryById(id)
    }

    fun selectIdByDate(year: Int, month: Int, day: Int): Long {

        return diaryDao.selectIdByDate(year, month, day)
    }

    fun selectByDate(year: Int, month: Int, day: Int): DiaryData {

        return diaryDao.selectByDate(year, month, day)
    }


    fun selectListByDate(year: Int, month: Int): List<DiaryData> {

        return diaryDao.selectListByDate(year, month)
    }


    fun selectListOverDate(date: String): List<DiaryData> {

        return diaryDao.selectListOverDate(date)
    }


}