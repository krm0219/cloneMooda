package com.krm0219.mooda.util

import android.content.Context
import android.util.Log
import android.util.TypedValue
import com.krm0219.mooda.data.CalendarData
import com.krm0219.mooda.data.MoodaData
import java.text.SimpleDateFormat
import java.util.*

class KUtil {
    companion object {

        var calendarList: ArrayList<CalendarData> = ArrayList()
        var mainList: ArrayList<MoodaData> = ArrayList()


        val REQUEST_ADD_DIARY = 1200
        val REQUEST_EDIT_DIARY = 1300
        val REQUEST_LIST_DIARY = 1400


        fun getMainListData(calendar: Calendar): MoodaData {

            val data = MoodaData()

            data.year = calendar.get(Calendar.YEAR)
            data.month = calendar.get(Calendar.MONTH) + 1
            data.monthName = getMonthName(calendar.get(Calendar.MONTH))

            Log.e("krm0219", "getMainListData  > ${data.monthName} / ${calendar.time}")

            return data
        }


        fun getCalendarData(calendar: Calendar): CalendarData {

            val data = CalendarData()

            data.year = calendar.get(Calendar.YEAR)
            data.month = calendar.get(Calendar.MONTH) + 1
           // data.monthName = getMonthName(calendar.get(Calendar.MONTH))
            data.day = calendar.get(Calendar.DATE)
           // data.dayOfWeek = getDayOfWeekName(calendar.get(Calendar.DATE))

            //    Log.e("krm0219", "data  > ${data.monthName} / ${data.dayOfWeek}  / ${calendar.time}")

            return data
        }


        fun getMonthName(month: Int): String {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, month)
            val date = calendar.time

            val formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)

            return formatter.format(date).toUpperCase(Locale.getDefault())
        }

        fun getDayOfWeekName(day: Int): String {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DATE, day)
            val date = calendar.time

            val formatter = SimpleDateFormat("EE", Locale.ENGLISH)

            return formatter.format(date).toUpperCase(Locale.getDefault())
        }

        fun getDayOfWeekNameKorean(day: Int): String {

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.DATE, day)
            val date = calendar.time

            val formatter = SimpleDateFormat("EE", Locale.KOREA)

            return formatter.format(date).toUpperCase(Locale.getDefault())
        }


        fun dpToPx(context: Context, dp: Float): Float {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
        }

        fun PxToDp(context: Context, px: Float): Float {

            var density = context.resources.displayMetrics.density
            Log.e("Util", "PxToDp  denistiry $density")

            when (density) {
                1.0F -> {
                    density *= 4.0F
                }
                1.5F -> {
                    density *= (8 / 3)
                }
                2.0F -> {
                    density *= 2.0F
                }
            }

            return px / density
        }

    }


    /*
    *


        var formatter = SimpleDateFormat("MMMM", Locale.ENGLISH)
        var date = formatter.format(Date()).toUpperCase(Locale.getDefault())

        Log.e("krm0219", "Month  $date")*/
}