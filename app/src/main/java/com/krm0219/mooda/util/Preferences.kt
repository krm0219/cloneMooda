package com.krm0219.mooda.util

import android.content.Context
import android.content.SharedPreferences

object Preferences {

    private const val FILENAME = "com.krm0219.mooda.preferences"
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE)
    }

    private val THIS_YEAR = "this_year"
    private val THIS_MONTH = "this_month"
    private val TODAY = "today"

    var thisYear: Int
        get() = preferences.getInt(THIS_YEAR, 0)
        set(value) = preferences.edit().putInt(THIS_YEAR, value).apply()

    var thisMonth: Int
        get() = preferences.getInt(THIS_MONTH, 0)
        set(value) = preferences.edit().putInt(THIS_MONTH, value).apply()

    var today: Int
        get() = preferences.getInt(TODAY, 0)
        set(value) = preferences.edit().putInt(TODAY, value).apply()
}
