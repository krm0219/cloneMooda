package com.krm0219.mooda.data

import android.app.Application
import android.util.Log
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.data.room.DiaryDAO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

// 데이터 소스를 접근하는데 필요한 로직을 캡슐화
// viewModel 에서는 Repository 를 통해 데이터에 접근
class Repository(application: Application) {


    private var diaryDao: DiaryDAO

    init {

        val database = AppDatabase.getInstance(application)!!
        diaryDao = database.diaryDao()
    }


    fun insertDiary(entity: DiaryData) {

        Observable.just(entity)
            .subscribeOn(Schedulers.io())
            .subscribe({

                diaryDao.insert(entity)
            }, {

                Log.e("Exception", "Error $it")
            })
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

    fun deleteDiary(entity: DiaryData) {

        Observable.just(entity)
            .subscribeOn(Schedulers.io())
            .subscribe({

                diaryDao.delete(entity)
            }, {

                Log.e("Exception", "Error $it")
            })
    }

    fun getDiary(id: String): Single<DiaryData> {

        return diaryDao.getDiaryById(Integer.parseInt(id))
    }


//    fun getAll(): LiveData<List<Diary>> {
//
//        return todos
//    }
//
//    fun insert(todo: Todo) {
//
//        Observable.just(todo)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//
//                todoDao.insert(todo)
//            }, {
//
//                Log.e("Exception", "Error $it")
//            })
//    }
//
//    fun delete(todo: Todo) {
//
//        todoDao.delete(todo)
//    }
//
//
//    private val retrofit: Retrofit = RetrofitAPI.getInstance()
//    private val api = retrofit.create(BaseService::class.java)
//
//    fun getMovieData(): LiveData<List<Movie>> {
//
//        val data = MutableLiveData<List<Movie>>()
//
//        api.getUpcomingMovie().enqueue(object : Callback<UpComingMovie> {
//            override fun onFailure(call: Call<UpComingMovie>, t: Throwable) {
//                t.stackTrace
//            }
//
//            override fun onResponse(call: Call<UpComingMovie>, response: Response<UpComingMovie>) {
//                data.value = response.body()!!.movieList
//
//            }
//        })
//
//        return data
//    }
//
//
//    private val githubClient = RetrofitAPI.client
//
//    suspend fun getRepositories(query: String) {
//
//        githubClient?.getRepositories(query)
//    }
}