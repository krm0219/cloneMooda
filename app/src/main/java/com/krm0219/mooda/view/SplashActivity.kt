package com.krm0219.mooda.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.krm0219.mooda.R
import com.krm0219.mooda.util.Preferences
import kotlinx.android.synthetic.main.activity_splash.*
import java.util.*

class SplashActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
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
            Log.e("krm0219", "Today ${Preferences.thisYear} - ${Preferences.thisMonth}")


            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            overridePendingTransition(0, 0)
        }, 3000)
    }
}