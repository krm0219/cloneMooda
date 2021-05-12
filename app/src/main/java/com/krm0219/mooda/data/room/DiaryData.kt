package com.krm0219.mooda.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "DIARY")
data class DiaryData(
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "date1") var date1: Date,
    @ColumnInfo(name = "emoji") var emoji: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "message") var message: String
) {
    constructor() : this(0, Date(), 0, "", "")

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

    fun getMonthString(): String {

        val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)
        return formatter.format(date1).toUpperCase(Locale.getDefault())
    }

    fun getDayString(): String {

        val formatter = SimpleDateFormat("EE", Locale.ENGLISH)
        return formatter.format(date1).toUpperCase(Locale.getDefault())
    }
}
