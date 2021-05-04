package com.krm0219.mooda.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.krm0219.mooda.R
import com.krm0219.mooda.util.KUtil
import com.krm0219.mooda.util.Preferences
import com.krm0219.mooda.view.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        img_splash_emoji1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_shake))
        img_splash_emoji3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_shake))
        img_splash_emoji5.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_shake))

        Handler(Looper.getMainLooper()).postDelayed({

            img_splash_emoji2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_shake))
            img_splash_emoji4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_shake))
        }, 400)



        Handler(Looper.getMainLooper()).postDelayed({

            val calendar = Calendar.getInstance()
            Preferences.thisYear = calendar.get(Calendar.YEAR)
            Preferences.thisMonth = calendar.get(Calendar.MONTH) + 1
            Preferences.today = calendar.get(Calendar.DAY_OF_MONTH)
            Log.e("krm0219", "Today ${Preferences.thisYear} - ${Preferences.thisMonth} - ${Preferences.today}")


            // 날짜 세팅
            calendar.add(Calendar.YEAR, -1)

            for (index in 0..365) {

                val data = KUtil.getCalendarData(calendar)
                KUtil.calendarList.add(data)

                calendar.add(Calendar.DATE, 1)
            }

            val present = KUtil.getCalendarData(Calendar.getInstance())
            KUtil.calendarList.add(present)


            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }, 3000)
    }
}