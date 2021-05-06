package com.krm0219.mooda.diary

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.krm0219.mooda.R
import com.krm0219.mooda.data.AppDatabase
import com.krm0219.mooda.data.CalendarData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.KUtil
import kotlinx.android.synthetic.main.activity_diary.*
import kotlinx.android.synthetic.main.dialog_default.*
import kotlinx.android.synthetic.main.top_title.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class DiaryActivity2 : AppCompatActivity() {
    val tag = "DiaryActivity"

    var calendarData: CalendarData? = null
    private var diaryDataData: DiaryData = DiaryData()

    var emojiPosition = 1
    var isSaved = false


    private lateinit var imm: InputMethodManager

    private var method: String? = null
    private var dbID: Int = -1
    var editDiaryData: DiaryData? = null





    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diary2)
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager


        method = intent.getStringExtra("method")

        if (method == "ADD") {

            // Emoji 설정 안되어 있는 날이 저절로 세팅되도록,,, (desc)
            for (index in 0..KUtil.calendarList.size) {

                calendarData = KUtil.calendarList[KUtil.calendarList.size - index - 1]

                if (calendarData!!.diaryEntity.emoji == 0) {

                    Log.e(tag, " INPUT  ${calendarData!!.month} / ${calendarData!!.day} / ${calendarData!!.dayOfWeek}")
                    break
                }
            }
        } else {

            dbID = intent.getIntExtra("edit_diary_id", -1)

            if (dbID == -1) {

                // Error
                Toast.makeText(this, "Error!!", Toast.LENGTH_SHORT).show()
                setResult(Activity.RESULT_CANCELED)
                overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_slide_down)
                finish()
            } else {

//                CoroutineScope(Dispatchers.IO).launch {
//
//                    editDiary = AppDatabase.getInstance(this@DiaryActivity)!!.diaryDao().selectById(dbID)
//
//                    for (index in 0..KUtil.calendarList.size) {
//
//                        if (KUtil.calendarList[index].year == editDiary!!.year && KUtil.calendarList[index].month == editDiary!!.month
//                            && KUtil.calendarList[index].day == editDiary!!.day
//                        ) {
//
//                            Log.e(
//                                tag, "${KUtil.calendarList[index].year} / ${KUtil.calendarList[index].month}" +
//                                        " / ${KUtil.calendarList[index].day} / ${KUtil.calendarList[index].diaryEntity.emoji}"
//                            )
//
//                            calendarData = KUtil.calendarList[index]
//                            break
//                        }
//                    }
//
//                    emojiPosition = calendarData!!.diaryEntity.emoji
//                    Log.e("krm0219", "Diary Not Null  $emojiPosition")
//                    onResume()
//                }
            }
        }


        // clickListener
        layout_top_close.setOnClickListener {

            notSavedDialog()
        }

        layout_top_check.setOnClickListener {

            isSaved = true

            diaryDataData.year = calendarData!!.year
            diaryDataData.month = calendarData!!.month
            diaryDataData.day = calendarData!!.day
            diaryDataData.dayOfWeek = calendarData!!.dayOfWeek
            diaryDataData.emoji = emojiPosition
            diaryDataData.title = text_diary_title.text.toString()
            diaryDataData.message = edit_diary_message.text.toString()


            if (method == "EDIT") {

            //    diaryDataData.id = dbID
                calendarData!!.diaryEntity = diaryDataData


                CoroutineScope(Dispatchers.IO).launch {

                    AppDatabase.getInstance(this@DiaryActivity2)!!.diaryDao().insert(diaryDataData)

//                    val intent = Intent()
//                    intent.putExtra("edit_diary_id", diaryDataData.id)
//                    setResult(Activity.RESULT_OK, intent)
//                    overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_slide_down)
//                    finish()
                }
            } else {        // ADD

                calendarData!!.diaryEntity = diaryDataData

                CoroutineScope(Dispatchers.IO).launch {

                    AppDatabase.getInstance(this@DiaryActivity2)!!.diaryDao().insert(diaryDataData)

                    val id = AppDatabase.getInstance(this@DiaryActivity2)!!.diaryDao().selectIdByDate(diaryDataData.year, diaryDataData.month, diaryDataData.day)
                    Log.e("krm0219", "  ID > $id")


                    val intent = Intent()
                    intent.putExtra("add_diary_id", id)
                    setResult(Activity.RESULT_OK, intent)
                    overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_slide_down)
                    finish()
                }
            }


            //   insertDiary(diaryData)


        }


        layout_diary_date.setOnClickListener {

            // TODO    DatePicker

            //     dialog_diary_calendar.visibility = View.VISIBLE
//            wheel_dialog_calendar.data =  KUtil.calendarList

            Log.e("krm0219", "Size  ${KUtil.calendarList.size}")

            val listener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->

                Log.e(tag, "$year / ${month + 1} / $dayOfMonth")

                val data = getCalendarData(year, month + 1, dayOfMonth)

                if (data == null) {

                    Toast.makeText(this, "Another Day Select...", Toast.LENGTH_SHORT).show()
                } else {

                    calendarData = data
                    onResume()
                }
            }


            val pickerDialog = DatePickerDialog(this, listener, calendarData!!.year, calendarData!!.month - 1, calendarData!!.day)
            pickerDialog.setCanceledOnTouchOutside(false)
            pickerDialog.show()

            val minCalendar = Calendar.getInstance()
            minCalendar.add(Calendar.YEAR, -1)

            pickerDialog.datePicker.minDate = minCalendar.timeInMillis
            pickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        }


        img_diary_emoji.setOnClickListener {

            if (emojiPosition == 10)
                emojiPosition = 0

            emojiPosition++
            val resourceId = resources.getIdentifier("emoji$emojiPosition", "drawable", packageName)
            img_diary_emoji.setBackgroundResource(resourceId)

            val resourceId1 = resources.getIdentifier("text_emoji_$emojiPosition", "string", packageName)
            text_diary_title.setText(resourceId1)
            setHighLighter()
        }


        btn_dialog_no.setOnClickListener {

            edit_diary_message.requestFocus()
        }

        btn_dialog_yes.setOnClickListener {

            setResult(Activity.RESULT_CANCELED)
            overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_slide_down)
            finish()
        }
    }


    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume")

        if (calendarData != null) {
            // init
            text_top_title.text = "${calendarData!!.monthName} ${calendarData!!.year}"

            val spannableString = SpannableString(calendarData!!.day.toString())
            spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
            text_diary_day.text = spannableString

            text_diary_day_of_the_week.text = calendarData!!.dayOfWeek

            val resourceId = resources.getIdentifier("emoji$emojiPosition", "drawable", packageName)
            img_diary_emoji.setBackgroundResource(resourceId)


            if (calendarData!!.diaryEntity.title == "") {

                val resourceId1 = resources.getIdentifier("text_emoji_$emojiPosition", "string", packageName)
                text_diary_title.setText(resourceId1)
            } else {

               // text_diary_title.text = calendarData!!.diaryEntity.title
            }

            setHighLighter()
            edit_diary_message.setText(calendarData!!.diaryEntity.message)
        }
    }


    private fun getCalendarData(year: Int, month: Int, day: Int): CalendarData? {

        var data: CalendarData? = null

        for (index in 0..KUtil.calendarList.size) {

            if (KUtil.calendarList[index].year == year && KUtil.calendarList[index].month == month
                && KUtil.calendarList[index].day == day
            ) {

                Log.e(
                    tag, "${KUtil.calendarList[index].year} / ${KUtil.calendarList[index].month}" +
                            " / ${KUtil.calendarList[index].day} / ${KUtil.calendarList[index].diaryEntity.emoji}"
                )

                if (KUtil.calendarList[index].diaryEntity.emoji == 0) {

                    data = KUtil.calendarList[index]
                } else if (editDiaryData?.year == year && editDiaryData?.month == month && editDiaryData?.day == day) {

                    Log.e(tag, "  Edit Data")
                    data = KUtil.calendarList[index]
                }else {

                    Log.e(tag, "  Emoji Not Null")
                    return null
                }
                break
            }
        }

        return data
    }


    override fun onBackPressed() {
        // super.onBackPressed()

        if (!isSaved) {

            notSavedDialog()
        }
    }


    private fun insertDiary(diaryData: DiaryData) {

        CoroutineScope(Dispatchers.IO).launch {

            AppDatabase.getInstance(this@DiaryActivity2)!!.diaryDao().insert(diaryData)
        }
    }

    private fun notSavedDialog() {

        // focus 막기
        imm.hideSoftInputFromWindow(edit_diary_message.windowToken, 0)
        edit_diary_message.clearFocus()

        text_dialog_title.visibility = View.GONE
        text_dialog_message.text = resources.getString(R.string.msg_not_saved)
        btn_dialog_yes.visibility = View.VISIBLE
        btn_dialog_delete.visibility = View.GONE
    }


    private fun setHighLighter() {

        text_diary_title.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {

                text_diary_title.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = text_diary_title.width + 50

                val params = RelativeLayout.LayoutParams(width, KUtil.dpToPx(this@DiaryActivity2, 30f).toInt())
                params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                img_diary_highlighter.layoutParams = params
            }
        })
    }
}