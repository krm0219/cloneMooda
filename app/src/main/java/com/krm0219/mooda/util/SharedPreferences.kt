package com.krm0219.mooda.util

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONObject
import java.util.*

class SharedPreferences(context: Context) {

    private val PREFS_FILENAME = "com.krm0219.mooda"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)




    private val PREF_KEY_USER_ID = "userId"
    private val PREF_KEY_USER_PW = "userPw"
    private val PREF_KEY_USER_NATION = "userNation"
    private val PREF_KEY_DEVICE_UUID = "deviceUUID"
    private val PREF_KEY_APP_VERSION = "appVersion"


    var userId: String
        get() = prefs.getString(PREF_KEY_USER_ID, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_USER_ID, value).apply()

    var userPw: String
        get() = prefs.getString(PREF_KEY_USER_PW, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_USER_PW, value).apply()

    var userNation: String
        get() = prefs.getString(PREF_KEY_USER_NATION, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_USER_NATION, value).apply()

    var deviceUUID: String
        get() = prefs.getString(PREF_KEY_DEVICE_UUID, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_DEVICE_UUID, value).apply()

    var appVersion: String
        get() = prefs.getString(PREF_KEY_APP_VERSION, "").toString()
        set(value) = prefs.edit().putString(PREF_KEY_APP_VERSION, value).apply()

}