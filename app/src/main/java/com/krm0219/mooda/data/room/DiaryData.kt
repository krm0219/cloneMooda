package com.krm0219.mooda.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "DIARY")
data class DiaryData(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "year") var year: Int,
    @ColumnInfo(name = "month") var month: Int,
    @ColumnInfo(name = "day") var day: Int,
    @ColumnInfo(name = "dayOfWeek") var dayOfWeek: String,
    @ColumnInfo(name = "emoji") var emoji: Int,
    @Ignore var emojiId: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "message") var message: String
) {
    constructor() : this(0, 0, 0, 0, "", 0, 0, "", "")


    fun getMonthString(): String {

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MONTH, month - 1)
        val date = calendar.time

        val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)

        return formatter.format(date).toUpperCase(Locale.getDefault())
    }
}
