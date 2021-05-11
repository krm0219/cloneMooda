package com.krm0219.mooda.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.CalendarData1
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.view.DiaryActivity
import java.util.*


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class DiaryViewModel(application: Application, private val method: String, tteokPosition: Int, private val diaryId: Long) : BaseViewModel(application) {

    lateinit var diary: DiaryData

    private val _diaryData = MutableLiveData<DiaryData>()
    val diaryData: MutableLiveData<DiaryData>
        get() = _diaryData

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


    // top_title
    private val _closeEvent = MutableLiveData<Event<Boolean>>()
    val closeEvent: MutableLiveData<Event<Boolean>>
        get() = _closeEvent

    private val _saveEvent = MutableLiveData<Event<Long>>()
    val saveEvent: MutableLiveData<Event<Long>>
        get() = _saveEvent


    // emoji Dialog
    private val _emojiEvent = MutableLiveData<Event<Int>>()
    val emojiEvent: MutableLiveData<Event<Int>>
        get() = _emojiEvent

    private val _emojiClicked = MutableLiveData<Int>()
    val emojiClicked: MutableLiveData<Int>
        get() = _emojiClicked

    private val _emojiDialogCloseEvent = MutableLiveData<Event<Int>>()
    val emojiDialogCloseEvent: MutableLiveData<Event<Int>>
        get() = _emojiDialogCloseEvent


    // calendar Dialog
    private val _calendarDataList = MutableLiveData<List<CalendarData1>>()
    val calendarDataList: MutableLiveData<List<CalendarData1>>
        get() = _calendarDataList

    private val _calendarData = MutableLiveData<CalendarData1>()
    val calendarData: MutableLiveData<CalendarData1>
        get() = _calendarData

    private val _calendarEvent = MutableLiveData<Event<List<CalendarData1>>>()
    val calendarEvent: MutableLiveData<Event<List<CalendarData1>>>
        get() = _calendarEvent

    private val _calendarDialogCloseEvent = MutableLiveData<Event<Int>>()
    val calendarDialogCloseEvent: MutableLiveData<Event<Int>>
        get() = _calendarDialogCloseEvent


    // close Dialog
    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent


    init {

        if (method == DiaryActivity.METHOD_ADD) {

            setDiaryData()
            _emoji.value = tteokPosition
        } else {

            setDiary()
        }

        initCalendar()
        initEmojiCount()
    }


    private fun setDiaryData() {

        diary = DiaryData()
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

    private fun setDiary() {

        diary = repository.selectDiaryById(diaryId)
        _diaryData.value = diary
        _emoji.value = diary.emoji
        _content.value = diary.message
    }


    private fun initCalendar() {

        val data = ArrayList<CalendarData1>()
//        data.add(CalendarData1(null, 0, 0, 0, 0))
//        data.add(CalendarData1(null, 0, 0, 0, 0))
//        data.add(CalendarData1(null, 0, 0, 0, 0))

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -1)
        val diaryData = repository.selectListOverDate("${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}")

        for (index in 0..365) {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            data.add(CalendarData1("$year-$month-$day", year, month, day, 0))
            calendar.add(Calendar.DATE, 1)
        }
        for (diary in diaryData) {
            for (cal in data) {

                if (diary.date == cal.date) {

                    cal.emoji = diary.emoji
                    break
                }
            }
        }

//        data.add(CalendarData1(null, 0, 0, 0, 0))
//        data.add(CalendarData1(null, 0, 0, 0, 0))
//        data.add(CalendarData1(null, 0, 0, 0, 0))
        _calendarDataList.value = data
    }


    fun changeDate() {

        _calendarEvent.value = Event(_calendarDataList.value!!)
    }

    fun changeEmoji() {

        _emojiEvent.value = Event(_emoji.value!!)
    }


    fun cancelDiary() {

        _closeEvent.value = Event(true)
    }

    fun saveDiary() {

        Log.e("ViewModel", "saveDiary")

        diary.date = "${diary.year}-${diary.month}-${diary.day}"
        diary.emoji = _emoji.value!!
        diary.title = _title.value!!
        if (_content.value != null) {
            diary.message = _content.value!!
        }

        _diaryData.value = diary

        if (method == DiaryActivity.METHOD_ADD) {

            repository.insertDiary(_diaryData.value!!)
            val id = repository.selectIdByDate(_diaryData.value!!.year, _diaryData.value!!.month, _diaryData.value!!.day)
            _saveEvent.value = Event(id)
        } else {

            repository.updateDiary(_diaryData.value!!)
            _saveEvent.value = Event(diaryId)
        }
    }

    fun dialogCloseDiary(goMain: Boolean) {

        _dialogCloseEvent.value = Event(goMain)
    }

    fun dialogCloseCalendar(position: Int) {

        if (position == -1) {

            _calendarDialogCloseEvent.value = Event(-1)
        } else {

            // position emoji   비교
            if (_calendarDataList.value!![position].emoji == 0) {

                _calendarDialogCloseEvent.value = Event(position)
            } else {

                // TODO_ animation
            }
        }
    }


    fun initEmojiCount() {

        _emojiClicked.value = 0
    }

    fun clickEmoji(position: Int) {

        if (_emojiClicked.value == position) {
            // 2번 클릭
            emojiDialogClose(position)
            initEmojiCount()
        } else {
            // 1번 클릭

            _emojiClicked.value = position
        }
    }

    fun emojiDialogClose(position: Int) {

        if (position != 0) {

            _emoji.value = position
        }
        _emojiDialogCloseEvent.value = Event(position)
    }
}