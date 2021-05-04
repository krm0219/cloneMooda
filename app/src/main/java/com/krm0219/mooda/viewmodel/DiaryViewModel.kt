package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.util.KUtil
import com.krm0219.mooda.util.Preferences


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

    private val _dayOfWeek = MutableLiveData<String>()
    val dayOfWeek: MutableLiveData<String>
        get() = _dayOfWeek

    private val _emoji = MutableLiveData<Int>()
    val emoji: MutableLiveData<Int>
        get() = _emoji

    private val _emojiResourceId = MutableLiveData<Int>()
    val emojiResourceId: MutableLiveData<Int>
        get() = _emojiResourceId

    private val _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
        get() = _title

    private val _content = MutableLiveData<String>()
    val content: MutableLiveData<String>
        get() = _content


    private val _diaryData = MutableLiveData<DiaryData>()
    val diaryData: MutableLiveData<DiaryData>
        get() = _diaryData

    private val _addEvent = MutableLiveData<Event<Int>>()
    val addEvent: MutableLiveData<Event<Int>>
        get() = _addEvent

    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: MutableLiveData<Event<Boolean>>
        get() = _closeEvent

    private val _calendarEvent = MutableLiveData<Event<DiaryData>>()
    val calendarEvent: MutableLiveData<Event<DiaryData>>
        get() = _calendarEvent


    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent


    init {

        val diary = DiaryData()
        diary.year = Preferences.thisYear
        diary.month = Preferences.thisMonth
        diary.day = Preferences.today
        diary.dayOfWeek = KUtil.getDayOfWeekName(diary.day)

        _diaryData.value = diary
        _emoji.value = tteokPosition
        Log.e("krm0219", "tteok ${emoji.value}")
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

        Log.e("krm0219", "saveDiary")
        //  _saveEvent.call()
    }

    fun dialogCloseDiary(goMain: Boolean) {

        _dialogCloseEvent.value = Event(goMain)
    }


}