package com.krm0219.mooda.viewmodel

import android.annotation.SuppressLint
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

    var month: Int = Preferences.thisMonth
    private val _monthPosition = MutableLiveData<Int>()
    val monthPosition: MutableLiveData<Int>
        get() = _monthPosition


    private val _addEvent = MutableLiveData<Event<Int>>()
    val addEvent: MutableLiveData<Event<Int>>
        get() = _addEvent

    private val _closeEvent = MutableLiveData<Event<Int>>()
    val closeEvent: MutableLiveData<Event<Int>>
        get() = _closeEvent


    private val _itemClickEvent = MutableLiveData<Event<Long>>()
    val itemClickEvent: MutableLiveData<Event<Long>>
        get() = _itemClickEvent

    private val _developerEvent = MutableLiveData<Event<Boolean>>()
    val developerEvent: MutableLiveData<Event<Boolean>>
        get() = _developerEvent


    fun setMonthData() {

        clickDeveloper = 0

        var hasThisMonth = false
        val data = ArrayList<MonthData>()
        val list = repository.selectAll()

        for (diary in list) {

            val year = diary.getFormatYear()
            val month = diary.getFormatMonth()
            var position = -1

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

            if (month == Preferences.thisMonth)
                hasThisMonth = true
        }


        if (!hasThisMonth) {

            data.add(MonthData(Preferences.thisYear, Preferences.thisMonth, null))
        }

        _monthDataList.value = data


        // month Position
        for (index in data.indices) {

            if (data[index].month == month) {

                _monthPosition.value = index
                break
            }
        }
    }


    fun onClickAddEvent() {

        // TODO_ Emoji 선택 position
        _addEvent.value = Event(1)
    }

    fun dialogClose(position: Int) {

        _closeEvent.value = Event(position)
    }


    @SuppressLint("SimpleDateFormat")
    fun setMonthPosition(id: Long) {

        val diary = repository.selectDiaryById(id)
        month = diary.getFormatMonth()
    }


    //
    fun onClickEmojiItem(id: Long) {

        _itemClickEvent.value = Event(id)
    }

    var clickDeveloper = 0
    fun onClickDeveloperMode() {

        if (clickDeveloper == 9) {

            clickDeveloper = 0
            _developerEvent.value = Event(true)
        } else {
            clickDeveloper++
            Log.e("krm0219", "  click Developer $clickDeveloper")
        }
    }
}