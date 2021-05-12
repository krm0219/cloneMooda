package com.krm0219.mooda.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.krm0219.mooda.data.CalendarData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.BaseViewModel
import com.krm0219.mooda.util.Event
import com.krm0219.mooda.view.DiaryActivity
import java.text.SimpleDateFormat
import java.util.*


// ViewModel에서 Context가 필요한 경우 AndroidViewModel 클래스를 상속받아
// Application 객체를 넘길 것을 권장 !!
// Context를 갖고 있으면 메모리 누수의 원인이 된다
class DiaryViewModel(application: Application, private val method: String, tteokPosition: Int, private val diaryId: Long) : BaseViewModel(application) {

    lateinit var diary: DiaryData

    private val _diaryData = MutableLiveData<DiaryData>()
    val diaryData: MutableLiveData<DiaryData>
        get() = _diaryData

    private val _emoji = MutableLiveData<Int>()
    val emoji: MutableLiveData<Int>
        get() = _emoji

    private val _title = MutableLiveData<String>()
    val title: MutableLiveData<String>
        get() = _title

    private val _content = MutableLiveData<String>()
    val content: MutableLiveData<String>
        get() = _content


    // top button
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
    private val _calendarDataList = MutableLiveData<List<CalendarData>>()
    val calendarDataList: MutableLiveData<List<CalendarData>>
        get() = _calendarDataList

    private val _calendarPosition = MutableLiveData<Int>()
    val calendarPosition: MutableLiveData<Int>
        get() = _calendarPosition

    private val _calendarEvent = MutableLiveData<Event<List<CalendarData>>>()
    val calendarEvent: MutableLiveData<Event<List<CalendarData>>>
        get() = _calendarEvent

    private val _calendarDialogCloseEvent = MutableLiveData<Event<Int>>()
    val calendarDialogCloseEvent: MutableLiveData<Event<Int>>
        get() = _calendarDialogCloseEvent


    // close Dialog
    private val _dialogCloseEvent = MutableLiveData<Event<Boolean>>()
    val dialogCloseEvent: MutableLiveData<Event<Boolean>>
        get() = _dialogCloseEvent


    val formatter = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH)

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

            val dateString = formatter.format(calendar.time)
            val date = formatter.parse(dateString)

            //    if (repository.selectIdByDate(year, month, day) == 0L) {
            if (repository.selectIdByDate(date!!) == 0L) {

                Log.e("krm0219", "diary Date  $dateString")
                isFind = true
                diary.date1 = date
            } else {

                calendar.add(Calendar.DATE, -1)
            }
        }

        _diaryData.value = diary
    }

    // TODO_
    fun setDiaryData(position: Int) {

        diary = DiaryData()
        diary.date1 = _calendarDataList.value!![position].date1
        _diaryData.value = diary
    }

    private fun setDiary() {

        diary = repository.selectDiaryById(diaryId)
        _diaryData.value = diary
        _emoji.value = diary.emoji
        _content.value = diary.message
    }


    private fun initCalendar() {

        val data = ArrayList<CalendarData>()


        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -1)

        val dateString = formatter.format(calendar.time)
        val diaryData = repository.selectListOverDate(formatter.parse(dateString)!!)

        for (index in 0..365) {

            val formatString = formatter.format(calendar.time)
            data.add(CalendarData(formatter.parse(formatString)!!, 0))
            calendar.add(Calendar.DATE, 1)
        }

        for (diary in diaryData) {
            for (cal in data) {
                if (diary.date1 == cal.date1) {

                    cal.emoji = diary.emoji
                    break
                }
            }
        }

        _calendarDataList.value = data
    }


    // Top Title Button
    fun closeDiary() {

        _closeEvent.value = Event(true)
    }

    @SuppressLint("SimpleDateFormat")
    fun saveDiary() {

        //    diary.date = "${diary.year}-${diary.month}-${diary.day}"
        diary.emoji = _emoji.value!!
        diary.title = _title.value!!
        if (_content.value != null) {
            diary.message = _content.value!!
        }
        _diaryData.value = diary


        if (method == DiaryActivity.METHOD_ADD) {

            repository.insertDiary(_diaryData.value!!)
            val id = repository.selectIdByDate(diary.date1)
            _saveEvent.value = Event(id)
        } else {

            repository.updateDiary(_diaryData.value!!)
            _saveEvent.value = Event(diaryId)
        }
    }


    fun changeDate(date: Date) {

        _calendarEvent.value = Event(_calendarDataList.value!!)

        for (index in _calendarDataList.value!!.indices) {

            val data = _calendarDataList.value!![index]

            if (data.date1 == date) {

                _calendarPosition.value = index
                Log.e("krm0219", "here  ${_calendarPosition.value}")
                break
            }
        }
    }

    fun changeEmoji() {

        _emojiEvent.value = Event(_emoji.value!!)
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

                _calendarDialogCloseEvent.value = Event(-10)
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