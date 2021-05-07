package com.krm0219.mooda.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.krm0219.mooda.R
import com.krm0219.mooda.databinding.ItemDialogEmojiBinding
import com.krm0219.mooda.viewmodel.DiaryViewModel

class DialogEmojiAdapter(private var viewModel: DiaryViewModel) : RecyclerView.Adapter<DialogEmojiAdapter.ViewHolder>() {

    var clickedPosition = -1
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemDialogEmojiBinding>(layoutInflater, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.item_dialog_emoji
    }

    override fun getItemCount(): Int {

        return 24
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(viewModel, position)
    }

    inner class ViewHolder(private val binding: ItemDialogEmojiBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: DiaryViewModel, position: Int) {

            binding.position = position + 1
            binding.viewModel = viewModel
            binding.clickedPosition = clickedPosition
            binding.executePendingBindings()
        }
    }

    fun setData(position: Int) {

        clickedPosition = position
        notifyDataSetChanged()
    }
}