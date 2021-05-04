package com.krm0219.mooda.data.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.util.*


class Converters {

    @TypeConverter
    fun listToJson(value: List<DiaryData>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<DiaryData>::class.java).toList()




    @TypeConverter
    fun fromTimeStamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimeStamp(date: Date?): Long? = date?.time
}