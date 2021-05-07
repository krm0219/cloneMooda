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
import com.krm0219.mooda.databinding.DialogEmojiBinding
import com.krm0219.mooda.view.adapter.DialogEmojiAdapter
import com.krm0219.mooda.viewmodel.DiaryViewModel


class EmojiDialog(val viewModel: DiaryViewModel) : DialogFragment() {

    lateinit var binding: DialogEmojiBinding
    lateinit var adapter: DialogEmojiAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val window = dialog?.window
        window?.setGravity(Gravity.CENTER)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT)

        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_emoji, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        adapter = DialogEmojiAdapter(viewModel)
        binding.recyclerviewDialogEmoji.adapter = adapter
    }


    fun clickedEmoji(position: Int) {

        adapter.setData(position)
    }
}