package com.krm0219.mooda.data

import com.krm0219.mooda.util.KUtil


data class CalendarData1(
    var year: Int,
    var month: Int,
    var day: Int,
    var emoji: Int,
) {

    fun getDayString(): String {

        return KUtil.getDayOfWeekNameKorean(day)
    }
}

