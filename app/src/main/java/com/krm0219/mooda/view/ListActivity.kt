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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityListBinding
import com.krm0219.mooda.util.VisiblePositionChangeListener
import com.krm0219.mooda.view.adapter.ListAdapter
import com.krm0219.mooda.view.dialog.DeleteAlertDialog
import com.krm0219.mooda.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {

    var diaryId = 0L
    lateinit var binding: ActivityListBinding
    val viewModel: ListViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                ListViewModel(application, diaryId) as T
        }
    }

    lateinit var adapter: ListAdapter
    private lateinit var dialog: DeleteAlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_list)

        diaryId = intent.getLongExtra(EXTRA_DIARY_ID, 0L)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_list)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setRecyclerView()


        viewModel.editEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it1 ->

                val intent = Intent(this, DiaryActivity::class.java)
                intent.putExtra(DiaryActivity.EXTRA_METHOD, DiaryActivity.METHOD_EDIT)
                intent.putExtra(DiaryActivity.EXTRA_DIARY_ID, it1)
                requestActivity.launch(intent)
            }
        })

        viewModel.deleteEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { id ->

                Log.e(TAG, "deleteEvent  id $id")
                dialog = DeleteAlertDialog(viewModel)
                dialog.show(supportFragmentManager, "deleteDialog")
            }
        })

        viewModel.backEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {

                finish()
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
            }
        })


        viewModel.dialogCloseEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { isDelete ->

                if (isDelete) {

                    dialog.dismiss()
                    viewModel.setDiaryData(CALLED_DELETE)
                } else {

                    dialog.dismiss()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
        viewModel.setDiaryData(CALLED_RESUME)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right)
    }

    private fun setRecyclerView() {

        adapter = ListAdapter(viewModel)
        binding.recyclerviewList.adapter = adapter


        recyclerview_list.addOnScrollListener(VisiblePositionChangeListener(recyclerview_list.layoutManager as LinearLayoutManager?,
            object : VisiblePositionChangeListener.OnChangeListener {
                override fun onFirstVisiblePositionChanged(position: Int) {

                    viewModel.setTitle(position)
                }

                override fun onFirstInvisiblePositionChanged(position: Int) {

                    viewModel.setTitle(position + 1)
                }
            }
        ))

        viewModel.diaryDataList.observe(this, Observer {

            adapter.setDiaryList(it)
        })


        viewModel.position.observe(this, Observer {

            Log.e(TAG, "Position  $it")
            recyclerview_list.scrollToPosition(it)
        })
    }


    private val requestActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->

        if (result.resultCode == Activity.RESULT_OK) {

            val addId = result.data?.getLongExtra(DiaryActivity.EXTRA_DIARY_ID, -1)
            Log.e(TAG, "requestActivity  $addId")
            //  viewModel.getData()


//            adapter.setMonthList(it)
//
//            var position = 0
//            for (index in it.indices) {
//
//                if (Preferences.thisMonth == it[index].month) {
//                    position = index
//                }
//            }
//
//            view_pager_main.setCurrentItem(position, false)


            //   val addId = result.data?.getIntExtra("add_diary_id", -1)

            Log.e("krm0219", "onActivityResult   RESULT_OK")


            //    adapter.addDiaryData(2021, 4, dia)

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

    companion object {

        const val TAG = "ListActivity"
        const val EXTRA_DIARY_ID = "EXTRA_DIARY_ID"

        const val CALLED_DELETE = "DELETE"
        const val CALLED_RESUME = "RESUME"
    }
}