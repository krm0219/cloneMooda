package com.krm0219.mooda.view

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ActivityDiaryBinding
import com.krm0219.mooda.view.dialog.CalendarDialog
import com.krm0219.mooda.view.dialog.CloseAlertDialog
import com.krm0219.mooda.view.dialog.EmojiDialog
import com.krm0219.mooda.viewmodel.DiaryViewModel
import kotlinx.android.synthetic.main.activity_diary.*


class DiaryActivity : AppCompatActivity() {

    var emoji = 0
    var diaryId = 0L
    var method = ""
    lateinit var binding: ActivityDiaryBinding
    val viewModel: DiaryViewModel by viewModels {

        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                DiaryViewModel(application, method, emoji, diaryId) as T
        }
    }

    private lateinit var dialogClose: CloseAlertDialog
    private lateinit var calendarDialog: CalendarDialog
    private lateinit var emojiDialog: EmojiDialog

    private lateinit var imm: InputMethodManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_diary)

        method = intent.getStringExtra(EXTRA_METHOD).toString()   // ADD, EDIT

        if (method == METHOD_ADD) {

            emoji = intent.getIntExtra(EXTRA_EMOJI, 1)
        } else {

            diaryId = intent.getLongExtra(EXTRA_DIARY_ID, 0L)
        }


        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        text_diary_day.paintFlags = text_diary_day.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        setObserve()
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }


    private fun setObserve() {

        // Top Title
        viewModel.closeEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {

                dialogClose = CloseAlertDialog(viewModel)
                dialogClose.show(supportFragmentManager, "alertDialog")
            }
        })

        viewModel.dialogCloseEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it1 ->

                if (it1) {

                    dialogClose.dismiss()
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                } else {

                    dialogClose.dismiss()
                }
            }
        })

        viewModel.saveEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { id ->

                intent.putExtra(EXTRA_DIARY_ID, id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        })


        // Calendar
        viewModel.calendarEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { it1 ->

                calendarDialog = CalendarDialog(viewModel, it1)
                calendarDialog.show(supportFragmentManager, "calendarDialog")

                //   calendarDialog.clickedDate(it)
            }
        })

        viewModel.calendarDialogCloseEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { _ ->

                calendarDialog.dismiss()
            }
        })


        // Emoji
        viewModel.emojiEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let {

                emojiDialog = EmojiDialog(viewModel)
                emojiDialog.show(supportFragmentManager, "emojiDialog")

                viewModel.initEmojiCount()
            }
        })

        viewModel.emojiClicked.observe(this, Observer {

            if (it != 0) {

                emojiDialog.clickedEmoji(it)
            }
        })

        viewModel.emojiDialogCloseEvent.observe(this, Observer {
            it.getContentIfNotHandled()?.let { _ ->

                emojiDialog.dismiss()
            }
        })


        // DATA
        viewModel.title.observe(this, Observer {

            Log.e(TAG, "EDIT $it \n")
        })

        viewModel.emoji.observe(this, Observer {

            Log.e(TAG, "EMOJI $it")
            val resourceId = resources.getIdentifier("text_emoji_$it", "string", packageName)
            text_diary_title.setText(resourceId)
        })
    }


    companion object {

        const val TAG = "DiaryActivity"

        const val EXTRA_METHOD = "EXTRA_METHOD"
        const val EXTRA_EMOJI = "EXTRA_EMOJI"
        const val EXTRA_DIARY_ID = "EXTRA_DIARY_ID"

        const val METHOD_ADD = "METHOD_ADD"
        const val METHOD_EDIT = "METHOD_EDIT"
    }
}