package com.krm0219.mooda.data

import com.krm0219.mooda.data.room.DiaryData
import java.io.Serializable

class MoodaData : Serializable {

    var isPresent = false

    var year = 0
    var monthName = ""
    var month: Int = 0

    var diaryDataList: ArrayList<DiaryData> = ArrayList()
}

