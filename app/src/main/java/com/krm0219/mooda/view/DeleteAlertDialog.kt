package com.krm0219.mooda.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.DialogDeleteAlertBinding
import com.krm0219.mooda.viewmodel.ListViewModel


class DeleteAlertDialog(val viewModel: ListViewModel) : DialogFragment() {

    lateinit var binding: DialogDeleteAlertBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_delete_alert, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDialogClose.setOnClickListener {

            viewModel.clickDialogClose(false)
        }

        binding.btnDialogDelete.setOnClickListener {

            viewModel.clickDialogClose(true)
        }
    }
}