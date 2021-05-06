package com.krm0219.mooda.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.krm0219.mooda.R
import com.krm0219.mooda.diary.DiaryActivity1
import com.krm0219.mooda.util.KUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity1 : AppCompatActivity() {
    val tag = "MainActivity"

    // Vertical View Pager  (Month 기준)
    private lateinit var adapter: MainViewPagerAdapter1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 이번달 기준
        val present = KUtil.getMainListData(Calendar.getInstance())
        present.isPresent = true
        KUtil.mainList.add(present)

        adapter = MainViewPagerAdapter1(this)    // viewPagerList
        view_pager_main.adapter = adapter

        //  initEmoji()


        // FIXME  Wheel UI로 선택할 수 있도록 라이브러리 찾기
        // val btn_main_add: Button = findViewById(R.id.btn_main_add)

        btn_main_add.setOnClickListener {

            // TEST_
//            val intent = Intent(this@MainActivity, CameraActivity::class.java)
//            startActivity(intent)

            val intent = Intent(this@MainActivity1, DiaryActivity1::class.java)
            //  requestActivity.launch(intent)
            startActivityForResult(intent, KUtil.REQUEST_ADD_DIARY)
        }
    }


    override fun onResume() {
        super.onResume()

        Log.e("krm0219", "onResume")
    }
//
//
//    private fun initEmoji() {
//
//        CoroutineScope(Dispatchers.IO).launch {
//
//            val diaryList: ArrayList<Diary> = AppDatabase.getInstance(this@MainActivity1)!!.diaryDao().selectAll() as ArrayList<Diary>
//            Log.e("Room", "DB List ${diaryList.size}")
//
//            for (diary in diaryList) {
//
//                setEmojiData(diary)
//
//                for (data in KUtil.calendarList) {
//
//                    if (diary.year == data.year && diary.month == data.month && diary.day == data.day) {
//
//                        data.diaryEntity = diary
//                        Log.e("krm0219", "${diary.year}-${diary.month}-${diary.day}  //  ${diary.emoji}")
//                    }
//                }
//            }
//
//
//            launch(Dispatchers.Main) {
//
//                // year, month 정렬
//                Collections.sort(KUtil.mainList, CompareDateAsc())
//
//                adapter.notifyDataSetChanged()
//                view_pager_main.setCurrentItem(KUtil.mainList.size, false)
//            }
//        }
//    }
//
//
//    private fun setEmojiData(diary: Diary) {
//
//        var inputData = false
//
//        // 몇월달 데이터로 넣을지 검색
//        for (index in KUtil.mainList.indices) {
//
////            Log.e("krm0219", "${diary.year} vs ${viewPagerList[index].year}")
////            Log.e("krm0219", "${diary.month} vs ${viewPagerList[index].month}")
//
//            if (diary.year == KUtil.mainList[index].year && diary.month == KUtil.mainList[index].month) {
//
//                KUtil.mainList[index].diaryList.add(diary)
//                inputData = true
//                break
//            }
//        }
//
//        if (!inputData) {
//
//            val calendar = Calendar.getInstance()
//            calendar.set(diary.year, diary.month - 1, diary.day)
//            val data = KUtil.getMainListData(calendar)
//            data.diaryList.add(diary)
//
//            KUtil.mainList.add(data)
//        }
//
//    }
//
//
//    val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) { result: ActivityResult ->
//
//        if (result.resultCode == Activity.RESULT_OK) {
//
//            val addId = result.data!!.getIntExtra("add_diary_id", -1)
//
//            Log.e("krm0219", "onActivityResult")
//
//            CoroutineScope(Dispatchers.IO).launch {
//
//                val addDiary = AppDatabase.getInstance(this@MainActivity1)!!.diaryDao().selectById(addId)
//                setEmojiData(addDiary)
//
//                launch(Dispatchers.Main) {
//
//                    // year, month 정렬
//                    Collections.sort(KUtil.mainList, CompareDateAsc())
//
//                    adapter.notifyDataSetChanged()
//                    view_pager_main.setCurrentItem(KUtil.mainList.size, false)
//                }
//            }
//        }
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (requestCode == KUtil.REQUEST_ADD_DIARY) {
//            if (resultCode == Activity.RESULT_OK) {
//
//
//                val addId = data!!.getIntExtra("add_diary_id", -1)
//
//                Log.e("krm0219", "onActivityResult")
//
//
////                for (index in KUtil.mainList.indices) {
////
////                    KUtil.mainList[index].diaryList.clear()
////                }
//
//
//                CoroutineScope(Dispatchers.IO).launch {
//
//                    val addDiary = AppDatabase.getInstance(this@MainActivity1)!!.diaryDao().selectById(addId)
//                    setEmojiData(addDiary)
//
//                    launch(Dispatchers.Main) {
//
//                        // year, month 정렬
//                        Collections.sort(KUtil.mainList, CompareDateAsc())
//
//                        adapter.notifyDataSetChanged()
//                        view_pager_main.setCurrentItem(KUtil.mainList.size, false)
//                    }
//                }
//            }
//        } else if (requestCode == KUtil.REQUEST_LIST_DIARY) {
//
//            Log.e("krm0219", "onActivityResult2")
//        }
//    }
//
//
//    internal class CompareDateAsc : Comparator<MoodaData> {
//        override fun compare(o1: MoodaData, o2: MoodaData): Int {
//
//            return if (o1.year == o2.year) {
//                o1.month.compareTo(o2.month)
//            } else {
//                o1.year.compareTo(o2.year)
//            }
//        }
//    }
}