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
import com.krm0219.mooda.databinding.DialogDeleteAlertBinding
import com.krm0219.mooda.viewmodel.ListViewModel


class DeleteAlertDialog(val viewModel: ListViewModel) : DialogFragment() {

    lateinit var binding: DialogDeleteAlertBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_delete_alert, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
    }
}