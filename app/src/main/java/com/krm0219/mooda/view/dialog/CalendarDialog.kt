package com.krm0219.mooda.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.krm0219.mooda.R
import com.krm0219.mooda.data.CalendarData1
import com.krm0219.mooda.data.MonthData
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.databinding.DialogCalendarBinding
import com.krm0219.mooda.view.adapter.DialogCalendarAdapter
import com.krm0219.mooda.viewmodel.DiaryViewModel


class CalendarDialog(val viewModel: DiaryViewModel, private val datas: List<CalendarData1>) : DialogFragment() {

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

        adapter = DialogCalendarAdapter(viewModel)
        binding.recyclerviewCalendar.adapter = adapter


        adapter.setDateList(datas)
        binding.recyclerviewCalendar.scrollToPosition(datas.size-1)
    }

    fun clickedDate(dates: List<CalendarData1>) {

    }
}