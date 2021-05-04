package com.krm0219.mooda.diary

import android.app.Application
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.CalendarData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.SingleLiveEvent

class DiaryViewModel(application: Application) : BaseViewModel(application) {

    // MutableLiveData  :  값의 get/set 모두 할 수 있다.
    // LiveData         :  값의 get()만 할 수 있다.

    private val _calendarDate = MutableLiveData<CalendarData>()
    val calendarData: MutableLiveData<CalendarData> = _calendarDate

    private val _topTitle = MutableLiveData<String>()
    val topTitle: MutableLiveData<String> = _topTitle

    private val _day = MutableLiveData<SpannableString>()
    val day: MutableLiveData<SpannableString> = _day

    private val _dayOfWeek = MutableLiveData<String>()
    val dayOfWeek: MutableLiveData<String> = _dayOfWeek


    private val _emojiPosition = MutableLiveData<Int>()
    val emojiPosition: MutableLiveData<Int> = _emojiPosition

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _message = MutableLiveData<String>()
    val message: MutableLiveData<String> = _message


    private val _cancelEvent = SingleLiveEvent<Any>()
    val cancelEvent: LiveData<Any> = _cancelEvent

    private val _saveEvent = SingleLiveEvent<Any>()
    val saveEvent: LiveData<Any> = _saveEvent


    fun setCalendarData(value: CalendarData) {

        _calendarDate.value = value

        val spannableString = SpannableString(_calendarDate.value!!.day.toString())
        spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)

        _topTitle.value = "${_calendarDate.value!!.monthName} ${_calendarDate.value!!.year}"
        _day.value = spannableString
        _dayOfWeek.value = _calendarDate.value!!.dayOfWeek
    }


    fun setEmojiPosition(value: Int) {

        _emojiPosition.value = value
    }

    fun setTitle(value: String) {

        _title.value = value
    }

    fun setMessage(value: String) {
        _message.value = value
    }


    fun changeEmoji() {

        _emojiPosition.value.let {

            if (it != null) {

                if (it == 10)
                    _emojiPosition.value = 1
                else
                    _emojiPosition.value = it + 1

                Log.e("krm0219", "emoji Position ${_emojiPosition.value}")
            }
        }
    }


    fun cancelDiary() {

        _cancelEvent.call()
    }

    fun saveDiary() {

        Log.e("krm0219", "saveDiary")
        _saveEvent.call()
    }
}