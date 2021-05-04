package com.krm0219.mooda.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityMainBinding
import com.krm0219.mooda.util.Preferences
import com.krm0219.mooda.view.adapter.MainAdapter
import com.krm0219.mooda.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val tag = "MainActivity"

    lateinit var binding: ActivityMainBinding
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setRecyclerView()



        viewModel.addEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it1 ->

                Log.e("krm0219", "Clicked Emoji $it1")
                val intent = Intent(this, DiaryActivity::class.java)
                intent.putExtra(DiaryActivity.EXTRA_EMOJI, it1)
                requestActivity.launch(intent)
                //  startActivityForResult(intent, DiaryActivity.REQUEST_ADD_DIARY)
            }
        })
    }


    private fun setRecyclerView() {

        val adapter = MainAdapter(viewModel)

        binding.viewPagerMain.adapter = adapter


        viewModel.monthDataList.observe(this, Observer {

            adapter.setMonthList(it)

            var position = 0
            for (index in it.indices) {

                if (Preferences.thisMonth == it[index].month) {
                    position = index
                }
            }

            view_pager_main.setCurrentItem(position, false)
        })
    }


    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {

            //   val addId = result.data?.getIntExtra("add_diary_id", -1)

            Log.e("krm0219", "onActivityResult   RESULT_OK")

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
        } else if (result.resultCode == Activity.RESULT_CANCELED) {

            Log.e("krm0219", "onActivityResult   RESULT_CANCELED")
        }
    }


}