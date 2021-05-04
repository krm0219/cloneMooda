package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.MonthData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class MainViewModel(application: Application) : BaseViewModel(application) {

    private val _openEvent = MutableLiveData<Event<Int>>()
    val openEvent: LiveData<Event<Int>> get() = _openEvent


    private val _monthDataList = MutableLiveData<List<MonthData>>()
    val monthDataList: MutableLiveData<List<MonthData>>
        get() = _monthDataList

    private val _addEvent = MutableLiveData<Event<Int>>()
    val addEvent: MutableLiveData<Event<Int>>
        get() = _addEvent

    init {


        setMonthData()
    }


    private fun setMonthData() {

        Log.e("krm0219", "MainViewModel  setMonthData")
        val data = ArrayList<MonthData>()

        data.add(MonthData(2021, 1, null))
        data.add(MonthData(2021, 4, null))

        monthDataList.value = data


        // Room DB 전체 뒤져서 값 세팅
        //   monthDataList 세팅
    }

    fun onClickEvent() {

        _addEvent.value = Event(5)
    }


//    private val repository = Repository(application)
//    private val todos = repository.getAll()
//
//
//    fun getAll(): LiveData<List<Todo>> {
//
//        return repository.getAll()
//    }
//
//    fun insert(todo: Todo) {
//
//        repository.insert(todo)
//    }
//
//    fun delete(todo: Todo) {
//
//        repository.delete(todo)
//    }
}