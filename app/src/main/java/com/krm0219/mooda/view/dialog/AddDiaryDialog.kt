package com.krm0219.mooda.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.DialogAddDiaryBinding
import com.krm0219.mooda.util.ViewAnimation
import com.krm0219.mooda.viewmodel.MainViewModel


class AddDiaryDialog(val viewModel: MainViewModel) : DialogFragment() {


    lateinit var binding: DialogAddDiaryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_diary, container, false)
        ViewAnimation.init(binding.imageView1)
        ViewAnimation.init(binding.imageView2)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("krm0219", "HERE")
        binding.viewModel = viewModel



        ViewAnimation.showIn(binding.imageView1)
        ViewAnimation.showIn(binding.imageView2)
    }
}