package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.MonthData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.util.Preferences


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class MainViewModel(application: Application) : BaseViewModel(application) {

    private val _monthDataList = MutableLiveData<List<MonthData>>()
    val monthDataList: MutableLiveData<List<MonthData>>
        get() = _monthDataList

    private val _addEvent = MutableLiveData<Event<Int>>()
    val addEvent: MutableLiveData<Event<Int>>
        get() = _addEvent

    private val _itemClickEvent = MutableLiveData<Event<Long>>()
    val itemClickEvent: MutableLiveData<Event<Long>>
        get() = _itemClickEvent


    private val _developerEvent = MutableLiveData<Event<Boolean>>()
    val developerEvent: MutableLiveData<Event<Boolean>>
        get() = _developerEvent


    init {

     //   setMonthData()
    }


     fun setMonthData() {

        val data = ArrayList<MonthData>()
        val list = repository.selectAll()

        for (diary in list) {

            val year = diary.year
            val month = diary.month
            var position = -1

            Log.e("krm0219", "YEAR-Month $year $month ${diary.day}")
            for (j in data.indices) {

                if (data[j].year == year && data[j].month == month) {

                    position = j
                    break
                }
            }

            if (position != -1) {

                data[position].items?.add(diary)
            } else {

                val items = ArrayList<DiaryData>()
                items.add(diary)
                data.add(MonthData(year, month, items))
            }
        }

        if (list.isEmpty()) {

            data.add(MonthData(Preferences.thisYear, Preferences.thisMonth, null))
        }

        _monthDataList.value = data
    }

    fun onClickEvent() {
        // TODO_ Emoji 선택
        _addEvent.value = Event(1)
    }


    fun getAllData() {

        val list = repository.selectAll()

        for (i in list.indices) {


            Log.e("krm0219", "DATA $i  ${list[i].year}-${list[i].month}-${list[i].day} // ${list[i].emoji}")
        }
    }

    fun getData() {

        setMonthData()
        //  _diaryData.value = repository.selectDiaryById(_id)
    }


    fun developerMode() {

        _developerEvent.value = Event(true)
    }


    fun clickEmojiItem(id: Long) {

        Log.e("krm0219", "clickEmojiItem  $id")
        _itemClickEvent.value = Event(id)
    }


}