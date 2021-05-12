package com.krm0219.mooda.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

class KUtil {
    companion object {

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


        fun getScreenWidth(context: Context): Int {
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val dm = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(dm)

            Log.e("krm0219", "getScreenWidth   ${dm.widthPixels} / ${dm.heightPixels}")
            return dm.widthPixels
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