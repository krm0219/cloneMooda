package com.krm0219.mooda.view.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.krm0219.mooda.R
import com.krm0219.mooda.data.CalendarData
import com.krm0219.mooda.databinding.DialogCalendarBinding
import com.krm0219.mooda.util.KUtil
import com.krm0219.mooda.util.SliderLayoutManager
import com.krm0219.mooda.view.adapter.DialogCalendarAdapter
import com.krm0219.mooda.viewmodel.DiaryViewModel


class CalendarDialog(val viewModel: DiaryViewModel, private val data: List<CalendarData>) : DialogFragment() {

    lateinit var binding: DialogCalendarBinding
    lateinit var adapter: DialogCalendarAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_calendar, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        adapter = DialogCalendarAdapter(viewModel, data)
        binding.recyclerviewCalendar.adapter = adapter

        val padding = KUtil.dpToPx(activity as Context, 130F).toInt()
        binding.recyclerviewCalendar.setPadding(0, padding, 0, padding)

        binding.recyclerviewCalendar.layoutManager = SliderLayoutManager(activity).apply {
            callback = object : SliderLayoutManager.OnItemSelectedListener {
                override fun onItemSelected(position: Int) {

                    Log.e("krm0219", "Selected   ${data[position]}")
                    binding.position = position
                }
            }
        }

        setPosition(viewModel.calendarPosition.value!!)
    }

    private fun setPosition(position: Int) {

        binding.position = position
        binding.recyclerviewCalendar.scrollToPosition(position)
    }

    fun shakeAnimation() {

        binding.layoutDialogCalendar.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_shake1))
    }
}