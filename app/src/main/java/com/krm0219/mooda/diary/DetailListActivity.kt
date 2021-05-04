package com.krm0219.mooda.diary

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.krm0219.mooda.R
import com.krm0219.mooda.data.AppDatabase
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.util.KUtil
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.dialog_default.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class DetailListActivity : AppCompatActivity() {


    var calendarPosition = -1
    var emojiPosition = -1


    private val diaryList = ArrayList<DiaryData>()
    private var currentDiary = DiaryData()
    private var currentPosition = -1

    private lateinit var adapter: DetailViewPagerAdapter
    var clickedPosition = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        calendarPosition = intent.getIntExtra("calendarPosition", 0)
        emojiPosition = intent.getIntExtra("emojiPosition", 0)

        currentDiary = KUtil.mainList[calendarPosition].diaryDataList[emojiPosition]

        init()

        adapter = DetailViewPagerAdapter(this, diaryList)
        view_pager_detail.adapter = adapter
        view_pager_detail.setCurrentItem(currentPosition, false)




        view_pager_detail.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                Log.e("krm0219", "Viewpager Positon  $position")

                val diary = diaryList[position]
                text_detail_top_title.text = KUtil.getMonthName(diary.month - 1) + " " + diary.year
            }
        })


        adapter.setOnItemClickListener(object : DetailViewPagerAdapter.OnItemClickListener {
            override fun onItemClick(v: View?, position: Int, method: String) {

                if (method == "Edit") {

                    clickedPosition = position
                    val diary = diaryList[clickedPosition]

                    CoroutineScope(Dispatchers.IO).launch {

                        val id = AppDatabase.getInstance(this@DetailListActivity)!!.diaryDao().selectIdByDate(diary.year, diary.month, diary.day)
                        Log.e("krm0219", "  ID > $id")

                        val intent = Intent(this@DetailListActivity, DiaryEditActivity::class.java)
                        intent.putExtra("method", "EDIT")
                        intent.putExtra("edit_diary_id", id)
                        overridePendingTransition(R.anim.anim_slide_up, R.anim.anim_slide_down)
                        startActivityForResult(intent, KUtil.REQUEST_EDIT_DIARY)
                    }
                } else if (method == "Delete") {

                    dialog_diary_delete.visibility = View.VISIBLE
                    btn_dialog_yes.visibility = View.GONE
                    btn_dialog_delete.visibility = View.VISIBLE

                    text_dialog_title.visibility = View.VISIBLE
                    text_dialog_title.text = resources.getString(R.string.text_diary_delete)
                    text_dialog_message.text = resources.getString(R.string.msg_diary_delete)

                    clickedPosition = position
                }
            }
        })



        layout_detail_top_back.setOnClickListener {

            finish()
            overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
        }


        btn_dialog_no.setOnClickListener {

            dialog_diary_delete.visibility = View.GONE
        }

        btn_dialog_delete.setOnClickListener {

            val diary = diaryList[clickedPosition]

            // DELETE
            CoroutineScope(Dispatchers.IO).launch {

                val entity = AppDatabase.getInstance(this@DetailListActivity)!!.diaryDao().selectByDate(diary.year, diary.month, diary.day)

                AppDatabase.getInstance(this@DetailListActivity)!!.diaryDao().delete(entity)


                launch(Dispatchers.Main) {

                    dialog_diary_delete.visibility = View.GONE

                    init()
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }


    private fun init() {

        for (i in KUtil.mainList.indices) {
            for (j in KUtil.mainList[i].diaryDataList.indices) {

                Log.e("krm0219", "i j ::  $i / $j")

                diaryList.add(KUtil.mainList[i].diaryDataList[j])
            }
        }

        for (i in diaryList.indices) {

            Log.e("krm0219", "DATA  ${diaryList[i].month}.${diaryList[i].day} -> ${diaryList[i].emoji}")

            if (diaryList[i] == currentDiary) {

                currentPosition = i
                break
            }
        }


        text_detail_top_title.text = KUtil.mainList[calendarPosition].monthName + " " + KUtil.mainList[calendarPosition].year

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == KUtil.REQUEST_EDIT_DIARY) {
            if (resultCode == Activity.RESULT_OK) {

                val editId = data!!.getIntExtra("edit_diary_id", -1)

                CoroutineScope(Dispatchers.IO).launch {

//                    val editDiary = AppDatabase.getInstance(this@DetailListActivity)!!.diaryDao().selectById(editId)
//                    setGridView(editDiary)

                    launch(Dispatchers.Main) {

                        adapter.notifyDataSetChanged()
                        view_pager_detail.setCurrentItem(clickedPosition, false)
                    }
                }
            }
        }
    }


    private fun setGridView(diaryDataData: DiaryData) {

        Log.e("krm0219", "HERE")

        var inputData = false
        var setData = false

        // 몇월달 데이터로 넣을지 검색
        for (index in KUtil.mainList.indices) {

            Log.e("krm0219", "${diaryDataData.year} vs ${KUtil.mainList[index].year}")
            Log.e("krm0219", "${diaryDataData.month} vs ${KUtil.mainList[index].month}")

            if (diaryDataData.year == KUtil.mainList[index].year && diaryDataData.month == KUtil.mainList[index].month) {
                for (j in KUtil.mainList[index].diaryDataList.indices) {

                    if (KUtil.mainList[index].diaryDataList[j].day == diaryDataData.day) {

                        KUtil.mainList[index].diaryDataList[j] = diaryDataData
                        setData = true
                        break
                    }
                }

                if (!setData) {

                    KUtil.mainList[index].diaryDataList.add(diaryDataData)
                }

                inputData = true
                break
            }
        }

        if (!inputData) {

            val calendar = Calendar.getInstance()
            calendar.set(diaryDataData.year, diaryDataData.month - 1, diaryDataData.day)
            val data = KUtil.getMainListData(calendar)
            data.diaryDataList.add(diaryDataData)

            KUtil.mainList.add(data)
        }

        // year, month 정렬
    //    Collections.sort(KUtil.mainList, MainActivity.CompareDateAsc())

        diaryList[clickedPosition] = diaryDataData
    }

}