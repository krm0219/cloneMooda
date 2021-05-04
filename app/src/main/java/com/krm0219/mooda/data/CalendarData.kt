package com.krm0219.mooda.data

import com.krm0219.mooda.data.room.DiaryData
import java.io.Serializable


class CalendarData : Serializable {

    var year = 0
    var monthName = ""
    var month: Int = 0
    var day: Int = 0
    var dayOfWeek = ""

    var diaryEntity = DiaryData()
}

