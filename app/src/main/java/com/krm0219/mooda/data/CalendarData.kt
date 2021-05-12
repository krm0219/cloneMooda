package com.krm0219.mooda.data

import java.text.SimpleDateFormat
import java.util.*


data class CalendarData(
    var date1: Date,
    var emoji: Int,
) {

    fun getFormatYear(): Int {

        val formatter = SimpleDateFormat("yyyy", Locale.ENGLISH)
        return formatter.format(date1).toInt()
    }

    fun getFormatMonth(): Int {

        val formatter = SimpleDateFormat("MM", Locale.ENGLISH)
        return formatter.format(date1).toInt()
    }

    fun getFormatDay(): Int {

        val formatter = SimpleDateFormat("dd", Locale.ENGLISH)
        return formatter.format(date1).toInt()
    }

    fun getDayString(): String {

        val formatter = SimpleDateFormat("EE", Locale.KOREA)
        return formatter.format(date1).toUpperCase(Locale.getDefault())
    }
}

