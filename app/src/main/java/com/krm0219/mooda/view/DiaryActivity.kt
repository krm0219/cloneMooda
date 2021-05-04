package com.krm0219.mooda.view

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityDiaryBinding
import com.krm0219.mooda.viewmodel.DiaryViewModel


class DiaryActivity : AppCompatActivity() {
    val tag = "DiaryActivity"

    var emoji = 0
    lateinit var binding: ActivityDiaryBinding
    val viewModel: DiaryViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DiaryViewModel(application, emoji) as T
        }
    }

    private lateinit var dialog: AlertDialog
    private lateinit var calendarDialog: CalendarDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_diary)

        emoji = intent.getIntExtra(EXTRA_EMOJI, 1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel






//        viewModel.monthDataList.observe(this, Observer {
//
//            adapter.setMonthList(it)
//
//            var position = 0
//            for(index in it.indices) {
//
//                if(Preferences.thisMonth == it[index].month) {
//                    position = index
//                }
//            }
//
//            view_pager_main.setCurrentItem(position, false)
//        })

        viewModel.calendarEvent.observe(this, Observer {

            it.getContentIfNotHandled()?.let {

                calendarDialog = CalendarDialog(viewModel)
                calendarDialog.show(supportFragmentManager, "SampleDialog")
            }
        })

        viewModel.closeEvent.observe(this, Observer {

            it.getContentIfNotHandled()?.let {

                dialog = AlertDialog(viewModel)
                dialog.show(supportFragmentManager, "SampleDialog")
            }
        })

        viewModel.dialogCloseEvent.observe(this, Observer {

            it.getContentIfNotHandled()?.let { it1 ->

                if (it1) {

                    dialog.dismiss()
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                } else {

                    dialog.dismiss()
                }
            }
        })


    }

//    private fun closeDialog(isCancel: Boolean) {
//
//        dialog.dismiss()
//
//        if (isCancel) {
//
//            // Main 이동
//            setResult(Activity.RESULT_CANCELED, intent)
//
//            if (!isFinishing)
//                finish()
//        }
//    }


    override fun onResume() {
        super.onResume()
        Log.e(tag, "onResume")

    }

    companion object {

        const val EXTRA_EMOJI = "EXTRA_EMOJI"
        const val REQUEST_ADD_DIARY = 1000
    }
}