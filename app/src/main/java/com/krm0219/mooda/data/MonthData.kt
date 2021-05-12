package com.krm0219.mooda.data

import com.krm0219.mooda.data.room.DiaryData
import java.text.SimpleDateFormat
import java.util.*

data class MonthData(
    var year: Int,  // yyyy
    var month: Int, // MM
    var items: ArrayList<DiaryData>?
) {

    fun getMonthString(): String {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month - 1)
        val date = calendar.time

        val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)

        return formatter.format(date).toUpperCase(Locale.getDefault())
    }
}