package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import java.util.*


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class DiaryViewModel(application: Application, tteokPosition: Int) : BaseViewModel(application) {

    private val _year = MutableLiveData<Int>()
    val year: MutableLiveData<Int>
        get() = _year

    private val _month = MutableLiveData<Int>()
    val month: MutableLiveData<Int>
        get() = _month

    private val _day = MutableLiveData<Int>()
    val day: MutableLiveData<Int>
        get() = _day

    private val _emoji = MutableLiveData<Int>()
    val emoji: MutableLiveData<Int>
        get() = _emoji

    private val _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
        get() = _title

    private val _content = MutableLiveData<String>()
    val content: MutableLiveData<String>
        get() = _content


    private val _diaryData = MutableLiveData<DiaryData>()
    val diaryData: MutableLiveData<DiaryData>
        get() = _diaryData

    private val _saveEvent = MutableLiveData<Event<Long>>()
    val saveEvent: MutableLiveData<Event<Long>>
        get() = _saveEvent

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: MutableLiveData<Event<Boolean>>
        get() = _closeEvent

    private val _calendarEvent = MutableLiveData<Event<DiaryData>>()
    val calendarEvent: MutableLiveData<Event<DiaryData>>
        get() = _calendarEvent


    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent

    val diary = DiaryData()

    init {

        setDiaryData()

        _emoji.value = tteokPosition
        Log.e("krm0219", "tteok ${emoji.value}")
    }


    private fun setDiaryData() {

        val calendar = Calendar.getInstance()
        var isFind = false

        while (!isFind) {

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            if (repository.selectIdByDate(year, month, day) == 0L) {

                isFind = true
                diary.year = year
                diary.month = month
                diary.day = day
            } else {

                calendar.add(Calendar.DATE, -1)
            }
        }


        Log.e("DiaryViewModel", "DATE ${diary.year}-${diary.month}-${diary.day}")
        _diaryData.value = diary
    }

    fun changeDate() {

        _calendarEvent.value = Event(_diaryData.value!!)
    }

    fun changeEmoji() {

        if (_emoji.value == 24) {

            _emoji.value = 1
        } else {

            _emoji.value = _emoji.value?.plus(1)
        }
    }

    fun cancelDiary() {

        _closeEvent.value = Event(true)
    }

    fun saveDiary() {

        diary.date = "${diary.year}-${diary.month}-${diary.day}"
        diary.emoji = _emoji.value!!
        diary.title = _title.value!!
        if (_content.value != null) {
            diary.message = _content.value!!
        }


        _diaryData.value = diary
        repository.insertDiary(_diaryData.value!!)

        Log.e("ViewModel", "saveDiary")

        val id = repository.selectIdByDate(_diaryData.value!!.year, _diaryData.value!!.month, _diaryData.value!!.day)
        _saveEvent.value = Event(id)
    }

    fun dialogCloseDiary(goMain: Boolean) {

        _dialogCloseEvent.value = Event(goMain)
    }


}