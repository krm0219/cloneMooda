package com.krm0219.mooda.view.adapter

import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.data.room.DiaryData
import com.krm0219.mooda.databinding.ItemListDiaryBinding
import com.krm0219.mooda.viewmodel.ListViewModel

class ListAdapter(private var viewModel: ListViewModel) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var diarys: List<DiaryData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemListDiaryBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_list_diary
    }

    override fun getItemCount(): Int {

        return diarys.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, position)
    }

    inner class ViewHolder(private val binding: ItemListDiaryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: ListViewModel, position: Int) {

            val diary = diarys[position]

            Log.e("ListAdapter", "position $position // data ${diary.emoji}")
            binding.position = position
            binding.diary = diary
            binding.viewModel = viewModel
            binding.executePendingBindings()

            binding.textItemDiaryDay.paintFlags = binding.textItemDiaryDay.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }
    }

    fun setDiaryList(diarys: List<DiaryData>) {

        this.diarys = diarys
        notifyDataSetChanged()
    }
}