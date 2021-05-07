package com.krm0219.mooda.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.DialogCalendarBinding
import com.krm0219.mooda.viewmodel.DiaryViewModel


class CalendarDialog(val viewModel: DiaryViewModel) : DialogFragment() {


    lateinit var binding: DialogCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_calendar, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//
//        binding.btnDialogClose.setOnClickListener {
//
//            viewModel.dialogCloseDiary(false)
//        }
//
//        binding.btnDialogYes.setOnClickListener {
//
//            viewModel.dialogCloseDiary(true)
//        }
    }
}