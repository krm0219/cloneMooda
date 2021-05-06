package com.krm0219.mooda.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.databinding.ItemMainEmojiBinding
import com.krm0219.mooda.viewmodel.MainViewModel

class MainEmojiAdapter(private var viewModel: MainViewModel, private var diarys: ArrayList<DiaryData>?) : RecyclerView.Adapter<MainEmojiAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemMainEmojiBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_main_emoji
    }

    override fun getItemCount(): Int {

        return if (diarys == null)
            0
        else
            diarys!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, position)
    }

    inner class ViewHolder(private val binding: ItemMainEmojiBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: MainViewModel, position: Int) {

            val diary = diarys?.get(position)

            Log.e("ViewPager", "position $position // data ${diary?.emoji}")
            binding.position = position
            binding.diary = diary
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }
    }
}